package com.monster.core.secure.config;

import com.monster.core.secure.handler.IPermissionHandler;
import com.monster.core.secure.handler.ISecureHandler;
import com.monster.core.secure.handler.MonsterPermissionHandler;
import com.monster.core.secure.handler.SecureHandlerHandler;
import com.monster.core.secure.registry.SecureRegistry;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * secure注册默认配置
 */
@Order
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@AutoConfigureBefore(SecureConfiguration.class)
public class RegistryConfiguration {
	private final JdbcTemplate jdbcTemplate;

	@Bean
	@ConditionalOnMissingBean(SecureRegistry.class)
	public SecureRegistry secureRegistry() {
		return new SecureRegistry();
	}

	@Bean
	@ConditionalOnMissingBean(ISecureHandler.class)
	public ISecureHandler secureHandler() {
		return new SecureHandlerHandler();
	}

	@Bean
	@ConditionalOnMissingBean(IPermissionHandler.class)
	public IPermissionHandler permissionHandler() {
		return new MonsterPermissionHandler(jdbcTemplate);
	}

}
