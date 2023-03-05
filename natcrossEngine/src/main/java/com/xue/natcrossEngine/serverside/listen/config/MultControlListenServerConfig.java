package com.xue.natcrossEngine.serverside.listen.config;

import com.alibaba.fastjson.JSONObject;
import com.xue.natcrossEngine.api.socketpart.AbsSocketPart;
import com.xue.natcrossEngine.serverside.listen.ServerListenThread;
import com.xue.natcrossEngine.serverside.listen.clear.IClearInvalidSocketPartThread;
import com.xue.natcrossEngine.serverside.listen.control.IControlSocket;
import com.xue.natcrossEngine.serverside.listen.control.MultControlSocket;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * <p>
 * 多客户端；监听服务配置
 * </p>
 *
 * @author Pluto
 * @since 2020-01-08 16:53:17
 */
public class MultControlListenServerConfig implements IListenServerConfig {

	protected final IListenServerConfig baseConfig;

	protected final MultControlSocket multControlSocket = new MultControlSocket();

	public MultControlListenServerConfig(IListenServerConfig baseConfig) {
		this.baseConfig = baseConfig;
	}

	@Override
	public ServerSocket createServerSocket() throws Exception {
		return this.baseConfig.createServerSocket();
	}

	@Override
	public IControlSocket newControlSocket(Socket socket, JSONObject config) {
		IControlSocket controlSocket = this.baseConfig.newControlSocket(socket, config);
		multControlSocket.addControlSocket(controlSocket);
		return multControlSocket;
	}

	@Override
	public IClearInvalidSocketPartThread newClearInvalidSocketPartThread(ServerListenThread serverListenThread) {
		return this.baseConfig.newClearInvalidSocketPartThread(serverListenThread);
	}

	@Override
	public AbsSocketPart newSocketPart(ServerListenThread serverListenThread) {
		return this.baseConfig.newSocketPart(serverListenThread);
	}

	@Override
	public Integer getListenPort() {
		return this.baseConfig.getListenPort();
	}

	@Override
	public Charset getCharset() {
		return this.baseConfig.getCharset();
	}

}
