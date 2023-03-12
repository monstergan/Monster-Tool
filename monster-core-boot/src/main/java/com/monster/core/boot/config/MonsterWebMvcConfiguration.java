package com.monster.core.boot.config;

import com.kte.core.boot.props.KteFileProperties;
import com.kte.core.boot.props.KteUploadProperties;
import com.kte.core.boot.resolver.TokenArgumentResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WEB配置
 * <p>
 * Create on 2023/3/12 22:12
 *
 * @author monster gan
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
@EnableConfigurationProperties({
	KteUploadProperties.class, KteFileProperties.class
})
public class MonsterWebMvcConfiguration implements WebMvcConfigurer {

	private final KteUploadProperties kteUploadProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String path = kteUploadProperties.getSavePath();
		registry.addResourceHandler("/upload/**")
			.addResourceLocations("file:" + path + "/upload/");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new TokenArgumentResolver());
	}

}
