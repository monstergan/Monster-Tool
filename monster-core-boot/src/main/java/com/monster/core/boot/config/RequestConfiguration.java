package com.monster.core.boot.config;


import com.kte.core.boot.request.KteRequestFilter;
import com.kte.core.boot.request.RequestProperties;
import com.kte.core.boot.request.XssProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * 过滤器配置类
 * <p>
 * Create on 2023/3/12 22:12
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties({RequestProperties.class, XssProperties.class})
public class RequestConfiguration {
    private final RequestProperties requestProperties;
    private final XssProperties xssProperties;

    /**
     * 全局过滤器
     */
    @Bean
    public FilterRegistrationBean<KteRequestFilter> kteFilterRegistration() {
        FilterRegistrationBean<KteRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new KteRequestFilter(requestProperties, xssProperties));
        registration.addUrlPatterns("/*");
        registration.setName("kteRequestFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }
}
