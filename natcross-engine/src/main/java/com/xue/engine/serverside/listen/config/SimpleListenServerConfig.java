package com.xue.engine.serverside.listen.config;

import com.alibaba.fastjson.JSONObject;
import com.xue.engine.api.socketpart.AbsSocketPart;
import com.xue.engine.api.socketpart.SimpleSocketPart;
import com.xue.engine.channel.InteractiveChannel;
import com.xue.engine.channel.SocketChannel;
import com.xue.engine.model.InteractiveModel;
import com.xue.engine.serverside.listen.ServerListenThread;
import com.xue.engine.serverside.listen.clear.ClearInvalidSocketPartThread;
import com.xue.engine.serverside.listen.clear.IClearInvalidSocketPartThread;
import com.xue.engine.serverside.listen.control.ControlSocket;
import com.xue.engine.serverside.listen.control.IControlSocket;
import com.xue.engine.serverside.listen.recv.ClientHeartHandler;
import com.xue.engine.serverside.listen.recv.CommonReplyHandler;
import com.xue.engine.serverside.listen.serversocket.ICreateServerSocket;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 简单的交互、隧道；监听服务配置
 * </p>
 *
 * @author Pluto
 * @since 2020-01-08 16:53:17
 */
@Data
@NoArgsConstructor
public class SimpleListenServerConfig implements IListenServerConfig {

	private Integer listenPort;

	private Long invaildMillis = 60000L;
	private Long clearInterval = 10L;

	private Charset charset = StandardCharsets.UTF_8;

	private ICreateServerSocket createServerSocket;

	private int streamCacheSize = 8196;

	public SimpleListenServerConfig(Integer listenPort) {
		this.listenPort = listenPort;
	}

	@Override
	public ServerSocket createServerSocket() throws Exception {
		if (this.createServerSocket == null) {
			ServerSocketChannel openServerSocketChannel = SelectorProvider.provider().openServerSocketChannel();
			openServerSocketChannel.bind(new InetSocketAddress(this.getListenPort()));
			return openServerSocketChannel.socket();
//			return new ServerSocket(this.getListenPort());
		} else {
			return this.createServerSocket.createServerSocket(this.getListenPort());
		}
	}

	/**
	 * 创建controlSocket使用channel
	 *
	 * @author Pluto
	 * @since 2020-04-15 13:19:49
	 * @param socket
	 * @return
	 */
	protected SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newControlSocketChannel(
			Socket socket) {
		InteractiveChannel interactiveChannel;
		try {
			interactiveChannel = new InteractiveChannel(socket);
		} catch (IOException e) {
			return null;
		}
		return interactiveChannel;
	}

	@Override
	public IControlSocket newControlSocket(Socket socket, JSONObject config) {
		SocketChannel<? extends InteractiveModel, ? super InteractiveModel> controlSocketChannel = this
				.newControlSocketChannel(socket);
		ControlSocket controlSocket = new ControlSocket(controlSocketChannel);
		controlSocket.addRecvHandler(CommonReplyHandler.INSTANCE);
		controlSocket.addRecvHandler(ClientHeartHandler.INSTANCE);
		return controlSocket;
	}

	@Override
	public IClearInvalidSocketPartThread newClearInvalidSocketPartThread(ServerListenThread serverListenThread) {
		ClearInvalidSocketPartThread clearInvalidSocketPartThread = new ClearInvalidSocketPartThread(
				serverListenThread);
		clearInvalidSocketPartThread.setClearIntervalSeconds(this.getClearInterval());
		return clearInvalidSocketPartThread;
	}

	@Override
	public AbsSocketPart newSocketPart(ServerListenThread serverListenThread) {
		SimpleSocketPart socketPart = new SimpleSocketPart(serverListenThread);
		socketPart.setInvaildMillis(this.getInvaildMillis());
		socketPart.setStreamCacheSize(this.getStreamCacheSize());
		return socketPart;
	}

}
