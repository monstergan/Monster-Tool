package com.monster.core.tool.config;

import com.monster.core.tool.convert.EnumToStringConverter;
import com.monster.core.tool.convert.StringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * enum 《-》 String 转换配置
 * <p>
 * Create on 2023/3/13 10:41
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
public class MonsterConverterConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new EnumToStringConverter());
		registry.addConverter(new StringToEnumConverter());
	}
}
