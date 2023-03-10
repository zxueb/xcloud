package com.xue.engine.api.passway;

import com.xue.engine.api.IBelongControl;
import com.xue.engine.api.secret.ISecret;
import com.xue.engine.channel.LengthChannel;
import com.xue.engine.executor.NatcrossExecutor;
import com.xue.engine.nio.NioHallows;
import com.xue.engine.utils.Tools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 *
 * <p>
 * 加密型隧道，一侧加密，一侧原样输入、输出
 * </p>
 *
 * @author Pluto
 * @since 2020-01-08 15:55:29
 */
@Data
@Slf4j
public class SecretPassway implements Runnable {

	/**
	 * 通道模式
	 */
	public static enum Mode {
		/**
		 * 从无加密接受到加密输出
		 */
		noToSecret,
		/**
		 * 从加密接受到无加密输出
		 */
		secretToNo
	}

	private boolean alive = false;

	private ISecret secret;

	private Mode mode;

	private IBelongControl belongControl;

	private int streamCacheSize = 8192;

	private Socket recvSocket;
	private Socket sendSocket;

	@Override
	public void run() {
		try {
			if (Mode.noToSecret.equals(this.mode)) {
				noToSecret();
			} else {
				secretToNo();
			}
		} catch (Exception e) {
			//
		}
		log.debug("one InputToOutputThread closed");
		// 传输完成后退出
		this.cancel();
	}

	/**
	 * 从加密侧输入，解密后输出到无加密侧
	 *
	 * @author Pluto
	 * @since 2020-01-08 15:56:43
	 * @throws Exception
	 */
	private void secretToNo() throws Exception {
		@SuppressWarnings("resource")
    LengthChannel recvChannel = new LengthChannel(this.recvSocket);

		OutputStream outputStream = this.sendSocket.getOutputStream();
		SocketChannel outputChannel = this.sendSocket.getChannel();

		ISecret secret = this.secret;

		byte[] read;
		byte[] decrypt;
		while (this.alive) {
			read = recvChannel.read();
			if (read == null) {
				break;
			}

			decrypt = secret.decrypt(read);

			if (Objects.isNull(outputChannel)) {
				outputStream.write(decrypt);
				outputStream.flush();
			} else {
				Tools.channelWrite(outputChannel, ByteBuffer.wrap(decrypt));
			}
		}
	}

	/**
	 * 从无加密侧，经过加密后输出到加密侧
	 *
	 * @author Pluto
	 * @since 2020-01-08 15:57:09
	 * @throws Exception
	 */
	private void noToSecret() throws Exception {
		InputStream inputStream = this.recvSocket.getInputStream();

		@SuppressWarnings("resource")
		LengthChannel sendChannel = new LengthChannel(this.sendSocket);

		// 字段赋值局部变量，入栈
		boolean alive = this.alive;
		ISecret secret = this.secret;

		int len = -1;
		byte[] arrayTemp = new byte[this.streamCacheSize];
		byte[] encrypt;
		while (alive && (len = inputStream.read(arrayTemp)) > 0) {
			encrypt = secret.encrypt(arrayTemp, 0, len);
			sendChannel.writeAndFlush(encrypt);
		}
	}

	/**
	 * 判断是否有效
	 *
	 * @author Pluto
	 * @since 2020-01-08 15:57:41
	 * @return
	 */
	public boolean isValid() {
		return this.alive;
	}

	/**
	 * 退出
	 *
	 * @author Pluto
	 * @since 2020-01-08 15:57:48
	 */
	public void cancel() {
		if (!this.alive) {
			return;
		}
		this.alive = false;

		NioHallows.release(this.recvSocket.getChannel());

		try {
			Socket sendSocket = this.sendSocket;
			if ((sendSocket = this.sendSocket) != null) {
				// TCP 挥手步骤，对方调用 shutdownOutput 后等价完成 socket.close
				sendSocket.shutdownOutput();
			}
		} catch (IOException e) {
			// do no thing
		}

		IBelongControl belong;
		if ((belong = this.belongControl) != null) {
			this.belongControl = null;
			belong.noticeStop();
		}
	}

	/**
	 * 启动
	 *
	 * @author Pluto
	 * @since 2020-01-08 15:57:53
	 */
	public void start() {
		if (this.alive) {
			return;
		}
		this.alive = true;
		NatcrossExecutor.executePassway(this);
	}

}
