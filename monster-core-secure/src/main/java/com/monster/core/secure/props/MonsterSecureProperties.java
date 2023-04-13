package com.monster.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端校验配置
 */
@Data
@ConfigurationProperties("kte.secure")
public class MonsterSecureProperties {

	/**
	 * 鉴权放行请求
	 */
	private final List<String> skipUrl = new ArrayList<>();
	/**
	 * 授权配置
	 */
	private final List<AuthSecure> auth = new ArrayList<>();
	/**
	 * 基础认证配置
	 */
	private final List<BasicSecure> basic = new ArrayList<>();
	/**
	 * 签名认证配置
	 */
	private final List<SignSecure> sign = new ArrayList<>();
	/**
	 * 客户端配置
	 */
	private final List<ClientSecure> client = new ArrayList<>();
	/**
	 * 开启鉴权规则
	 */
	private Boolean enabled = false;
	/**
	 * 开启授权规则
	 */
	private Boolean authEnabled = true;
	/**
	 * 开启基础认证规则
	 */
	private Boolean basicEnabled = true;
	/**
	 * 开启签名认证规则
	 */
	private Boolean signEnabled = true;
	/**
	 * 开启客户端规则
	 */
	private Boolean clientEnabled = true;

}
