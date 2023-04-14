package com.monster.core.secure.handler;

import com.monster.core.secure.interceptor.*;
import com.monster.core.secure.props.AuthSecure;
import com.monster.core.secure.props.BasicSecure;
import com.monster.core.secure.props.SignSecure;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

/**
 * Secure处理器
 */
public class SecureHandlerHandler implements ISecureHandler {

	@Override
	public HandlerInterceptorAdapter tokenInterceptor() {
		return new TokenInterceptor();
	}

	@Override
	public HandlerInterceptorAdapter authInterceptor(List<AuthSecure> authSecures) {
		return new AuthInterceptor(authSecures);
	}

	@Override
	public HandlerInterceptorAdapter basicInterceptor(List<BasicSecure> basicSecures) {
		return new BasicInterceptor(basicSecures);
	}

	@Override
	public HandlerInterceptorAdapter signInterceptor(List<SignSecure> signSecures) {
		return new SignInterceptor(signSecures);
	}

	@Override
	public HandlerInterceptorAdapter clientInterceptor(String clientId) {
		return new ClientInterceptor(clientId);
	}

}
