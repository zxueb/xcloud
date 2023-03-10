package com.xue.engine.serverside.listen.recv;

import com.xue.engine.channel.SocketChannel;
import com.xue.engine.model.InteractiveModel;
import com.xue.engine.model.enumeration.InteractiveTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * <p>
 * 统一回复 处理器
 * </p>
 *
 * @author Pluto
 * @since 2020-04-15 13:02:09
 */
@Slf4j
public class CommonReplyHandler implements IRecvHandler<InteractiveModel, InteractiveModel> {

	public static final CommonReplyHandler INSTANCE = new CommonReplyHandler();

	@Getter
	@Setter
	private IRecvHandler<InteractiveModel, InteractiveModel> handler;

	@Override
	public boolean proc(InteractiveModel model,
			SocketChannel<? extends InteractiveModel, ? super InteractiveModel> channel) throws Exception {
		InteractiveTypeEnum interactiveTypeEnum = InteractiveTypeEnum.getEnumByName(model.getInteractiveType());
		if (!InteractiveTypeEnum.COMMON_REPLY.equals(interactiveTypeEnum)) {
			return false;
		}

		IRecvHandler<InteractiveModel, InteractiveModel> handler;
		if (Objects.isNull(handler = this.handler)) {
			log.info("common reply: {}", model);
			return true;
		}

		return handler.proc(model, channel);
	}

}
