package com.xue.natcrossEngine.serverside.listen.recv;

import com.xue.natcrossEngine.channel.SocketChannel;

/**
 * <p>
 * 接收处理器
 * </p>
 *
 * @author Pluto
 * @since 2020-04-15 11:13:20
 */
public interface IRecvHandler<R, W> {

	/**
	 * 处理方法
	 *
	 * @param model
	 * @param channel
	 * @return
	 * @throws Exception
	 * @author Pluto
	 * @since 2021-04-26 17:25:19
	 */
    boolean proc(R model, SocketChannel<? extends R, ? super W> channel) throws Exception;

}
