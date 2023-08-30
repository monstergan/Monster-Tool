package com.monster.core.log.config;

import com.kte.core.launch.props.KteProperties;
import com.monster.core.launch.server.ServerInfo;
import com.monster.core.log.aspect.ApiLogAspect;
import com.monster.core.log.aspect.LogTraceAspect;
import com.monster.core.log.event.ApiLogListener;
import com.monster.core.log.event.ErrorLogListener;
import com.monster.core.log.event.UsualLogListener;
import com.monster.core.log.feign.ILogClient;
import com.monster.core.log.filter.LogTraceFilter;
import com.monster.core.log.logger.MonsterLogger;
import com.monster.core.log.props.MonsterRequestLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * 日志工具自动配置
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@EnableConfigurationProperties(MonsterRequestLogProperties.class)
public class LogToolAutoConfiguration {

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public LogTraceAspect logTraceAspect() {
		return new LogTraceAspect();
	}

	@Bean
	public MonsterLogger monsterLogger() {
		return new MonsterLogger();
	}

	@Bean
	public FilterRegistrationBean<LogTraceFilter> logTraceFilterRegistration() {
		FilterRegistrationBean<LogTraceFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new LogTraceFilter());
		registration.addUrlPatterns("/*");
		registration.setName("LogTraceFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	@Bean
	@ConditionalOnMissingBean(name = "apiLogListener")
	public ApiLogListener apiLogListener(ILogClient logService, ServerInfo serverInfo, KteProperties kteProperties) {
		return new ApiLogListener(logService, serverInfo, kteProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "errorEventListener")
	public ErrorLogListener errorEventListener(ILogClient logService, ServerInfo serverInfo, KteProperties kteProperties) {
		return new ErrorLogListener(logService, serverInfo, kteProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "usualEventListener")
	public UsualLogListener usualEventListener(ILogClient logService, ServerInfo serverInfo, KteProperties kteProperties) {
		return new UsualLogListener(logService, serverInfo, kteProperties);
	}

}
