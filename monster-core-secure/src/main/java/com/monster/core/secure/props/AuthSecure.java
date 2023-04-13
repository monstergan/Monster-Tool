package com.monster.core.secure.props;

import com.monster.core.secure.provider.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义授权规则
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSecure {

	/**
	 * 请求方法
	 */
	private HttpMethod method;
	/**
	 * 请求路径
	 */
	private String pattern;
	/**
	 * 规则表达式
	 */
	private String expression;

}
