package com.xue.natcrossEngine.serverside.listen.config;

import com.alibaba.fastjson.JSONObject;
import com.xue.natcrossEngine.api.socketpart.AbsSocketPart;
import com.xue.natcrossEngine.serverside.listen.ServerListenThread;
import com.xue.natcrossEngine.serverside.listen.clear.IClearInvalidSocketPartThread;
import com.xue.natcrossEngine.serverside.listen.control.IControlSocket;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * <p>
 * 穿透监听服务配置
 * </p>
 *
 * @author Pluto
 * @since 2020-01-08 16:51:04
 */
public interface IListenServerConfig {

	/**
	 * 获取监听的端口
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:51:17
	 * @return
	 */
	Integer getListenPort();

	/**
	 * 新建无效端口处理线程
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:51:26
	 * @param serverListenThread
	 * @return
	 */
	IClearInvalidSocketPartThread newClearInvalidSocketPartThread(ServerListenThread serverListenThread);

	/**
	 * 创建隧道伙伴
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:51:41
	 * @param serverListenThread
	 * @return
	 */
	AbsSocketPart newSocketPart(ServerListenThread serverListenThread);

	/**
	 * 获取字符集
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:51:57
	 * @return
	 */
	Charset getCharset();

	/**
	 * 新建控制器
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:52:05
	 * @param config
	 * @return
	 */
	IControlSocket newControlSocket(Socket socket, JSONObject config);

	/**
	 * 创建监听端口
	 *
	 * @author Pluto
	 * @since 2020-01-09 13:24:13
	 * @return
	 * @throws Exception
	 */
	public ServerSocket createServerSocket() throws Exception;
}
