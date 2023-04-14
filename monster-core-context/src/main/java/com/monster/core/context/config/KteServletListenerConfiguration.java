package com.monster.core.context.config;

import com.monster.core.context.MonsterHttpHeadersGetter;
import com.monster.core.context.listener.MonsterServletRequestListener;
import com.monster.core.context.props.MonsterContextProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监听器自动配置
 *
 * @author monster
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class KteServletListenerConfiguration {

    @Bean
    public ServletListenerRegistrationBean<?> registerCustomListener(MonsterContextProperties properties,
                                                                     MonsterHttpHeadersGetter httpHeadersGetter) {
        return new ServletListenerRegistrationBean<>(new MonsterServletRequestListener(properties, httpHeadersGetter));
    }

}
