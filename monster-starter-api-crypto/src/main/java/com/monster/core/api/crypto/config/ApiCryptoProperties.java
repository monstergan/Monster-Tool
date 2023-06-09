package com.monster.core.api.crypto.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 签名配置
 */
@Getter
@Setter
@ConfigurationProperties(ApiCryptoProperties.PREFIX)
public class ApiCryptoProperties {


	/**
	 * 前缀
	 */
	public static final String PREFIX = "kte.api.crypto";

	/**
	 * 是否开启 api 签名
	 */
	private Boolean enabled = Boolean.TRUE;

	/**
	 * url的参数签名，传递的参数名。例如：/user?data=签名后的数据
	 */
	private String paramName = "data";

	/**
	 * aes 密钥
	 */
	private String aesKey;

	/**
	 * des 密钥
	 */
	private String desKey;

	/**
	 * rsa 私钥
	 */
	private String rsaPrivateKey;
}
