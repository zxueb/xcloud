package com.xue.natcrossEngine.serverside.listen.config;

import com.xue.natcrossEngine.api.secret.AESSecret;
import com.xue.natcrossEngine.api.socketpart.AbsSocketPart;
import com.xue.natcrossEngine.api.socketpart.SecretSocketPart;
import com.xue.natcrossEngine.serverside.listen.ServerListenThread;
import com.xue.natcrossEngine.utils.AESUtil;
import lombok.Getter;
import lombok.Setter;

import java.security.Key;

/**
 *
 * <p>
 * 交互及隧道都加密
 * </p>
 *
 * @author Pluto
 * @since 2020-01-08 15:05:55
 */
public class AllSecretSimpleListenServerConfig extends SecretSimpleListenServerConfig {

	@Setter
	@Getter
	private Key passwayKey;

	public AllSecretSimpleListenServerConfig(Integer listenPort) {
		super(listenPort);
	}

	@Override
	public AbsSocketPart newSocketPart(ServerListenThread serverListenThread) {
		AESSecret secret = new AESSecret();
		secret.setAesKey(this.passwayKey);
		SecretSocketPart secretSocketPart = new SecretSocketPart(serverListenThread);
		secretSocketPart.setSecret(secret);
		return secretSocketPart;
	}

	/**
	 * BASE64格式设置隧道加密密钥
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:52:25
	 * @param key
	 */
	public void setBasePasswayKey(String key) {
		this.passwayKey = AESUtil.createKeyByBase64(key);
	}

}
