package com.xue.engine.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xue.engine.utils.AESUtil;
import com.xue.engine.utils.MD5Signature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 *
 * <p>
 * 基于InteractiveModel模型的加密交互模型
 * </p>
 *
 * @author Pluto
 * @since 2020-01-08 16:38:52
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SecretInteractiveModel extends InteractiveModel {

	public SecretInteractiveModel(InteractiveModel model) {
		super(model);
	}

	/**
	 * 时间戳
	 */
	private Long timestamp;

	/**
	 * 签名
	 */
	private String autograph;

	/**
	 * InteractiveModel模型jsonString加密值
	 */
	private String encrypt;

	/**
	 * 字符编码
	 */
	private String charset = StandardCharsets.UTF_8.name();

	/**
	 * 加密消息
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:39:46
	 * @param key
	 * @throws Exception
	 */
	public void encryptMsg(Key key) throws Exception {
		String encryptBase64 = AESUtil.encryptBase64(key, super.toJSONString().getBytes(this.charset));
		this.encrypt = encryptBase64;
	}

	/**
	 * 解密消息
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:39:53
	 * @param key
	 * @throws Exception
	 */
	public void decryptMsg(Key key) throws Exception {
		byte[] decryptBase64 = AESUtil.decryptBase64(key, this.encrypt);
		String interactiveJsonString = new String(decryptBase64, this.charset);
		InteractiveModel model = JSON.parseObject(interactiveJsonString, InteractiveModel.class);
		super.fullValue(model);
	}

	/**
	 * 签名模型
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:39:59
	 * @param tokenKey
	 */
	public void autographMsg(String tokenKey) {
		String signature = MD5Signature.getSignature(Charset.forName(this.charset), tokenKey, this.timestamp.toString(),
				this.encrypt, this.charset);
		this.autograph = signature;
	}

	/**
	 * 检查签名
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:40:09
	 * @param tokenKey
	 * @return
	 */
	public boolean checkAutograph(String tokenKey) {
		String signature = MD5Signature.getSignature(Charset.forName(this.charset), tokenKey, this.timestamp.toString(),
				this.encrypt, this.charset);
		return StringUtils.equals(this.autograph, signature);
	}

	/**
	 * 填充消息
	 *
	 * @author Pluto
	 * @since 2020-01-08 16:40:17
	 * @param key
	 * @param tokenKey
	 * @throws Exception
	 */
	public void fullMessage(Key key, String tokenKey) throws Exception {
		this.timestamp = System.currentTimeMillis();
		this.encryptMsg(key);
		this.autographMsg(tokenKey);
	}

	@Override
	public String toJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("charset", this.charset);
		jsonObject.put("timestamp", this.timestamp);
		jsonObject.put("encrypt", this.encrypt);
		jsonObject.put("autograph", this.autograph);
		return jsonObject.toJSONString();
	}

}
