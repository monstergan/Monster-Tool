package com.monster.core.context.config;

import com.monster.core.context.MonsterContext;
import com.monster.core.context.MonsterHttpHeadersGetter;
import com.monster.core.context.MonsterServletContext;
import com.monster.core.context.ServletHttpHeadersGetter;
import com.monster.core.context.props.MonsterContextProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * 服务上下文配置
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(MonsterContextProperties.class)
public class MonsterContextAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MonsterHttpHeadersGetter kteHttpHeadersGetter(MonsterContextProperties contextProperties) {
        return new ServletHttpHeadersGetter(contextProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public MonsterContext kteContext(MonsterContextProperties contextProperties, MonsterHttpHeadersGetter httpHeadersGetter) {
        return new MonsterServletContext(contextProperties, httpHeadersGetter);
    }


}
