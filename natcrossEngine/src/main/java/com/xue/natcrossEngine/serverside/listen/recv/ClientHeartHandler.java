package com.xue.natcrossEngine.serverside.listen.recv;

import com.xue.natcrossEngine.channel.SocketChannel;
import com.xue.natcrossEngine.model.InteractiveModel;
import com.xue.natcrossEngine.model.enumeration.InteractiveTypeEnum;
import com.xue.natcrossEngine.model.enumeration.NatcrossResultEnum;

/**
 * <p>
 * 心跳检测
 * </p>
 *
 * @author Pluto
 * @since 2020-04-15 13:02:09
 */
public class ClientHeartHandler implements IRecvHandler<InteractiveModel, InteractiveModel> {

	public static final ClientHeartHandler INSTANCE = new ClientHeartHandler();

	@Override
	public boolean proc(InteractiveModel model,
			SocketChannel<? extends InteractiveModel, ? super InteractiveModel> channel) throws Exception {
		InteractiveTypeEnum interactiveTypeEnum = InteractiveTypeEnum.getEnumByName(model.getInteractiveType());
		if (!InteractiveTypeEnum.HEART_TEST.equals(interactiveTypeEnum)) {
			return false;
		}
		InteractiveModel sendModel = InteractiveModel.of(model.getInteractiveSeq(), InteractiveTypeEnum.COMMON_REPLY,
				NatcrossResultEnum.SUCCESS.toResultModel());

		channel.writeAndFlush(sendModel);

		return true;
	}

}
