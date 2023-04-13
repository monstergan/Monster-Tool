package com.monster.core.secure.interceptor;

import com.monster.core.secure.provider.ResponseProvider;
import com.monster.core.secure.utils.AuthUtil;
import com.monster.core.tool.jackson.JsonUtil;
import com.monster.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名认证拦截器
 */
@Slf4j
@AllArgsConstructor
public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		if (null == AuthUtil.getUser()) {
			log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			ResponseProvider.write(response);
			return false;
		}
		return true;
	}

}
