package com.monster.core.api.crypto.core;


import com.monster.core.api.crypto.annotation.decrypt.ApiDecrypt;
import com.monster.core.api.crypto.bean.CryptoInfoBean;
import com.monster.core.api.crypto.bean.DecryptHttpInputMessage;
import com.monster.core.api.crypto.config.ApiCryptoProperties;
import com.monster.core.api.crypto.exception.DecryptBodyFailException;
import com.monster.core.api.crypto.util.ApiCryptoUtil;
import com.monster.core.tool.utils.ClassUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 解密请求体
 */
@Slf4j
@Order(1)
@Configuration(proxyBeanMethods = false)
@ControllerAdvice
@RequiredArgsConstructor
@ConditionalOnProperty(value = ApiCryptoProperties.PREFIX + ".enabled", havingValue = "true", matchIfMissing = true)
public class ApiDecryptRequestBodyAdvice implements RequestBodyAdvice {
	private final ApiCryptoProperties properties;

	@Override
	public boolean supports(MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
		return ClassUtil.isAnnotated(methodParameter.getMethod(), ApiDecrypt.class);
	}

	@Override
	public Object handleEmptyBody(Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter,
								  @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

	@NonNull
	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, @NonNull MethodParameter parameter,
										   @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		// 判断 body 是否为空
		InputStream messageBody = inputMessage.getBody();
		if (messageBody.available() <= 0) {
			return inputMessage;
		}
		byte[] decryptedBody = null;
		CryptoInfoBean cryptoInfoBean = ApiCryptoUtil.getDecryptInfo(parameter);
		if (cryptoInfoBean != null) {
			// base64 byte array
			byte[] bodyByteArray = StreamUtils.copyToByteArray(messageBody);
			decryptedBody = ApiCryptoUtil.decryptData(properties, bodyByteArray, cryptoInfoBean);
		}
		if (decryptedBody == null) {
			throw new DecryptBodyFailException("Decryption error, " +
				"please check if the selected source data is encrypted correctly." +
				" (解密错误，请检查选择的源数据的加密方式是否正确。)");
		}
		InputStream inputStream = new ByteArrayInputStream(decryptedBody);
		return new DecryptHttpInputMessage(inputStream, inputMessage.getHeaders());
	}

	@NonNull
	@Override
	public Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}
}
