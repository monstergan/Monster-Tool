package com.monster.core.api.crypto.core;

import com.monster.core.api.crypto.annotation.encrypt.ApiEncrypt;
import com.monster.core.api.crypto.bean.CryptoInfoBean;
import com.monster.core.api.crypto.config.ApiCryptoProperties;
import com.monster.core.api.crypto.exception.EncryptBodyFailException;
import com.monster.core.api.crypto.util.ApiCryptoUtil;
import com.monster.core.tool.jackson.JsonUtil;
import com.monster.core.tool.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 加密响应体
 */
@Slf4j
@Order(1)
@Configuration(proxyBeanMethods = false)
@ControllerAdvice
@RequiredArgsConstructor
@ConditionalOnProperty(value = ApiCryptoProperties.PREFIX + ".enabled", havingValue = "true", matchIfMissing = true)
public class ApiEncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
	private final ApiCryptoProperties properties;

	@Override
	public boolean supports(MethodParameter returnType, @NonNull Class converterType) {
		return ClassUtil.isAnnotated(returnType.getMethod(), ApiEncrypt.class);
	}

	@Nullable
	@Override
	public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
								  @NonNull Class selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
		if (body == null) {
			return null;
		}
		response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
		CryptoInfoBean cryptoInfoBean = ApiCryptoUtil.getEncryptInfo(returnType);
		if (cryptoInfoBean != null) {
			byte[] bodyJsonBytes = JsonUtil.toJsonAsBytes(body);
			return ApiCryptoUtil.encryptData(properties, bodyJsonBytes, cryptoInfoBean);
		}
		throw new EncryptBodyFailException();
	}
}
