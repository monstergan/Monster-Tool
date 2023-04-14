package com.monster.core.secure.props;


import com.monster.core.secure.provider.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签名授权规则
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignSecure {

	/**
	 * 请求方法
	 */
	private HttpMethod method;
	/**
	 * 请求路径
	 */
	private String pattern;
	/**
	 * 加密方式
	 */
	private String crypto;

}
