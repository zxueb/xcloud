package com.xue.engine.serverside.listen.control;

import com.xue.engine.channel.SocketChannel;
import com.xue.engine.model.InteractiveModel;
import com.xue.engine.model.enumeration.InteractiveTypeEnum;
import com.xue.engine.model.enumeration.NatcrossResultEnum;
import com.xue.engine.model.interactive.ServerWaitModel;
import com.xue.engine.serverside.listen.IServerListen;
import com.xue.engine.serverside.listen.recv.IRecvHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * <p>
 * 控制socket实例
 * </p>
 *
 * @author Pluto
 * @since 2019-07-17 11:03:56
 */
@Slf4j
public class ControlSocket implements IControlSocket, Runnable {

	private volatile Thread myThread = null;
	private volatile boolean started = false;
	private volatile boolean cancelled = false;

	protected final SocketChannel<? extends InteractiveModel, ? super InteractiveModel> socketChannel;

	protected List<IRecvHandler<? super InteractiveModel, ? extends InteractiveModel>> recvHandlerList = new LinkedList<>();

	protected IServerListen serverListen;

	public ControlSocket(SocketChannel<? extends InteractiveModel, ? super InteractiveModel> socketChannel) {
		this.socketChannel = socketChannel;
	}

	@Override
	public boolean isValid() {
		SocketChannel<? extends InteractiveModel, ? super InteractiveModel> socketChannel = this.socketChannel;

		Socket socket = (socketChannel == null) ? null : socketChannel.getSocket();
		boolean closeFlag = (socket == null) ? true
				: !socket.isConnected() || socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown();
		if (closeFlag) {
			return false;
		}

		try {
			// 心跳测试
			InteractiveModel interactiveModel = InteractiveModel.of(InteractiveTypeEnum.HEART_TEST, null);
			socketChannel.writeAndFlush(interactiveModel);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public void close() {
		if (this.cancelled) {
			return;
		}
		this.cancelled = true;

		Thread myThread;
		if ((myThread = this.myThread) != null) {
			this.myThread = null;
			myThread.interrupt();
		}

		if (this.socketChannel != null) {
			try {
				this.socketChannel.close();
			} catch (IOException e) {
				// do no thing
			}
		}

		IServerListen serverListenThread = this.serverListen;
		if (Objects.nonNull(serverListenThread)) {
			this.serverListen = null;
			serverListenThread.controlCloseNotice(this);
		}

	}

	@Override
	public boolean sendClientWait(String socketPartKey) {
		InteractiveModel model = InteractiveModel.of(InteractiveTypeEnum.SERVER_WAIT_CLIENT,
				new ServerWaitModel(socketPartKey));

		try {
			this.socketChannel.writeAndFlush(model);
		} catch (Throwable e) {
			return false;
		}

		return true;
	}

	@Override
	public void startRecv() {
		if (this.started) {
			return;
		}
		this.started = true;

		Thread myThread = this.myThread;
		if (myThread == null || !myThread.isAlive()) {
			myThread = this.myThread = new Thread(this);
			myThread.setName("control-recv-" + this.formatServerListenInfo());
			myThread.start();
		}
	}

	@Override
	public void run() {
		SocketChannel<? extends InteractiveModel, ? super InteractiveModel> socketChannel = this.socketChannel;
		while (this.started && !this.cancelled) {
			try {
				InteractiveModel interactiveModel = socketChannel.read();

				log.info("监听线程 [{}] 接收到控制端口发来的消息：[ {} ]", this.formatServerListenInfo(), interactiveModel);

				boolean proc = false;
				for (IRecvHandler<? super InteractiveModel, ? extends InteractiveModel> handler : this.recvHandlerList) {
					proc = handler.proc(interactiveModel, this.socketChannel);
					if (proc) {
						break;
					}
				}

				if (!proc) {
					log.warn("无处理方法的信息：[{}]", interactiveModel);

					InteractiveModel result = InteractiveModel.of(interactiveModel.getInteractiveSeq(),
							InteractiveTypeEnum.COMMON_REPLY,
							NatcrossResultEnum.UNKNOW_INTERACTIVE_TYPE.toResultModel());
					socketChannel.writeAndFlush(result);
				}

			} catch (Exception e) {
				log.error("读取或写入异常", e);
				if (e instanceof IOException || !this.isValid()) {
					this.close();
				}
			}
		}
	}

	private String formatServerListenInfo() {
		if (Objects.isNull(this.serverListen)) {
			return null;
		}
		return this.serverListen.formatInfo();
	}

	@Override
	public void setServerListen(IServerListen serverListen) {
		this.serverListen = serverListen;
	}

	/**
	 * 添加处理器
	 *
	 * @author Pluto
	 * @since 2020-04-15 13:13:24
	 * @param handler
	 * @return
	 */
	public ControlSocket addRecvHandler(IRecvHandler<? super InteractiveModel, ? extends InteractiveModel> handler) {
		this.recvHandlerList.add(handler);
		return this;
	}

}
