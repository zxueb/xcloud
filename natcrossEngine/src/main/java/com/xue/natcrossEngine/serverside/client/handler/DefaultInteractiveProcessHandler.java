package com.xue.natcrossEngine.serverside.client.handler;

import com.xue.natcrossEngine.serverside.client.process.ClientConnectProcess;
import com.xue.natcrossEngine.serverside.client.process.ClientControlProcess;

/**
 *
 * <p>
 * 默认的接收处理handler
 * </p>
 *
 * @author Pluto
 * @since 2021-04-26 17:22:31
 */
public class DefaultInteractiveProcessHandler extends InteractiveProcessHandler {

	public static final DefaultInteractiveProcessHandler INSTANCE = new DefaultInteractiveProcessHandler();

	public DefaultInteractiveProcessHandler() {
		this.addLast(ClientControlProcess.INSTANCE);
		this.addLast(ClientConnectProcess.INSTANCE);
	}

}
