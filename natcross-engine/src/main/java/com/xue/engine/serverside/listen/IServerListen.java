package com.xue.engine.serverside.listen;

import com.xue.engine.serverside.listen.control.IControlSocket;

/**
 * <p>
 * 端口监听服务接口
 * </p>
 *
 * @author Pluto
 * @since 2021-07-01 13:46:20
 */
public interface IServerListen {

	/**
	 * 格式化信息
	 *
	 * @return
	 */
	String formatInfo();

	/**
	 * 控制端口通知关闭
	 *
	 * @param controlSocket
	 */
	void controlCloseNotice(IControlSocket controlSocket);

}
