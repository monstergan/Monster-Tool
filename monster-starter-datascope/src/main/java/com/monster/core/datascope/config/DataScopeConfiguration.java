package com.monster.core.datascope.config;

import com.monster.core.datascope.handler.DataScopeHandler;
import com.monster.core.datascope.handler.MonsterDataScopeHandler;
import com.monster.core.datascope.handler.MonsterScopeModelHandler;
import com.monster.core.datascope.handler.ScopeModelHandler;
import com.monster.core.datascope.interceptor.DataScopeInterceptor;
import com.monster.core.datascope.props.DataScopeProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据权限配置类
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties(DataScopeProperties.class)
public class DataScopeConfiguration {

	private final JdbcTemplate jdbcTemplate;

	@Bean
	@ConditionalOnMissingBean(ScopeModelHandler.class)
	public ScopeModelHandler scopeModelHandler() {
		return new MonsterScopeModelHandler(jdbcTemplate);
	}

	@Bean
	@ConditionalOnBean(ScopeModelHandler.class)
	@ConditionalOnMissingBean(DataScopeHandler.class)
	public DataScopeHandler dataScopeHandler(ScopeModelHandler scopeModelHandler) {
		return new MonsterDataScopeHandler(scopeModelHandler);
	}

	@Bean
	@ConditionalOnBean(DataScopeHandler.class)
	@ConditionalOnMissingBean(DataScopeInterceptor.class)
	public DataScopeInterceptor interceptor(DataScopeHandler dataScopeHandler, DataScopeProperties dataScopeProperties) {
		return new DataScopeInterceptor(dataScopeHandler, dataScopeProperties);
	}

}
