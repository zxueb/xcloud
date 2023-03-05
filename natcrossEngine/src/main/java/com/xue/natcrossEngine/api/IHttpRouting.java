package com.xue.natcrossEngine.api;

import com.xue.natcrossEngine.model.HttpRoute;

/**
 * <p>
 * http 路由器
 * </p>
 *
 * @author Pluto
 * @since 2021-04-26 08:54:44
 */
public interface IHttpRouting {

	/**
	 * 获取有效路由
	 *
	 * @param host
	 * @return
	 */
	public HttpRoute pickEffectiveRoute(String host);

}
