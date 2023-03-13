package com.monster.core.launch.config;


import com.monster.core.launch.props.MonsterProperties;
import com.monster.core.launch.props.MonsterPropertySourcePostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 * Create on 2023/3/13 13:36
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(MonsterProperties.class)
public class MonsterPropertyConfiguration {
    @Bean
    public MonsterPropertySourcePostProcessor monsterPropertySourcePostProcessor() {
        return new MonsterPropertySourcePostProcessor();
    }
}
