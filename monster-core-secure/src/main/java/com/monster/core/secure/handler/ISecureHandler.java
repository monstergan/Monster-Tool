package com.monster.core.secure.handler;

import com.monster.core.secure.props.AuthSecure;
import com.monster.core.secure.props.BasicSecure;
import com.monster.core.secure.props.SignSecure;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

/**
 * 拦截器集合
 */
public interface ISecureHandler {

	/**
	 * token拦截器
	 *
	 * @return tokenInterceptor
	 */
	HandlerInterceptorAdapter tokenInterceptor();

	/**
	 * auth拦截器
	 *
	 * @param authSecures 授权集合
	 * @return HandlerInterceptorAdapter
	 */
	HandlerInterceptorAdapter authInterceptor(List<AuthSecure> authSecures);

	/**
	 * basic拦截器
	 *
	 * @param basicSecures 基础认证集合
	 * @return HandlerInterceptorAdapter
	 */
	HandlerInterceptorAdapter basicInterceptor(List<BasicSecure> basicSecures);

	/**
	 * sign拦截器
	 *
	 * @param signSecures 签名认证集合
	 * @return HandlerInterceptorAdapter
	 */
	HandlerInterceptorAdapter signInterceptor(List<SignSecure> signSecures);

	/**
	 * client拦截器
	 *
	 * @param clientId 客户端id
	 * @return clientInterceptor
	 */
	HandlerInterceptorAdapter clientInterceptor(String clientId);


}
