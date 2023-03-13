package com.monster.core.tool.config;

import com.monster.core.tool.support.BinderSupplier;
import com.monster.core.tool.utils.SpringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

/**
 * 工具配置类
 * <p>
 * Create on 2023/3/13 10:44
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
public class ToolConfiguration {

	/**
	 * Spring上下文缓存
	 */
	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}

	/**
	 * Binder支持类
	 */
	@Bean
	@ConditionalOnMissingBean
	public Supplier<Object> binderSupplier() {
		return new BinderSupplier();
	}

}
