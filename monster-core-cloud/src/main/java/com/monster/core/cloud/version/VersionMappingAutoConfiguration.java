package com.monster.core.cloud.version;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by monster gan on 2023/3/13 14:57
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
public class VersionMappingAutoConfiguration {
    @Bean
    public WebMvcRegistrations kteWebMvcRegistrations() {
        return new MonsterWebMvcRegistrations();
    }

}
