package com.monster.core.api.crypto.core;

import com.monster.core.api.crypto.annotation.decrypt.ApiDecrypt;
import com.monster.core.api.crypto.bean.CryptoInfoBean;
import com.monster.core.api.crypto.config.ApiCryptoProperties;
import com.monster.core.api.crypto.util.ApiCryptoUtil;
import com.monster.core.tool.jackson.JsonUtil;
import com.monster.core.tool.utils.Charsets;
import com.monster.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Parameter;

/**
 * 解密参数解析器
 */
@RequiredArgsConstructor
public class ApiDecryptParamResolver implements HandlerMethodArgumentResolver {
	private final ApiCryptoProperties properties;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return AnnotatedElementUtils.hasAnnotation(parameter.getParameter(), ApiDecrypt.class);
	}

	@Nullable
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Parameter parameter = methodParameter.getParameter();
		ApiDecrypt apiDecrypt = AnnotatedElementUtils.getMergedAnnotation(parameter, ApiDecrypt.class);
		String text = webRequest.getParameter(properties.getParamName());
		if (StringUtil.isBlank(text)) {
			return null;
		}
		CryptoInfoBean infoBean = new CryptoInfoBean(apiDecrypt.value(), apiDecrypt.secretKey());
		byte[] textBytes = text.getBytes(Charsets.UTF_8);
		byte[] decryptData = ApiCryptoUtil.decryptData(properties, textBytes, infoBean);
		return JsonUtil.readValue(decryptData, parameter.getType());
	}
}
