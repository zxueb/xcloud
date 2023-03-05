package com.xue.natcrossEngine.serverside.client.adapter;

import com.xue.natcrossEngine.model.InteractiveModel;
import com.xue.natcrossEngine.serverside.client.config.IClientServiceConfig;
import com.xue.natcrossEngine.serverside.client.handler.DefaultInteractiveProcessHandler;

/**
 *
 * <p>
 * 默认的预读后处理适配器
 * </p>
 *
 * @author Pluto
 * @since 2021-04-26 17:06:25
 */
public class DefaultReadAheadPassValueAdapter extends ReadAheadPassValueAdapter<InteractiveModel, InteractiveModel> {

	public DefaultReadAheadPassValueAdapter(IClientServiceConfig<InteractiveModel, InteractiveModel> config) {
		super(config);
		this.addLast(DefaultInteractiveProcessHandler.INSTANCE);
	}

}
