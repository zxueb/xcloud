package com.xue.natcrossEngine.serverside.client.handler;


import com.xue.natcrossEngine.channel.SocketChannel;
import com.xue.natcrossEngine.common.Optional;
import com.xue.natcrossEngine.serverside.client.adapter.PassValueNextEnum;

/**
 *
 * <p>
 * 传值方式客户端是配置的处理接口
 * </p>
 *
 * @author Pluto
 * @since 2020-01-08 16:47:40
 * @param <R>
 * @param <W>
 */
public interface IPassValueHandler<R, W> {

	/**
	 * 处理方法
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:48:01
	 * @param socketChannel 交互通道
	 * @param optional      可以重设值
	 * @return
	 */
	PassValueNextEnum proc(SocketChannel<? extends R, ? super W> socketChannel, Optional<? extends R> optional);

}
