package com.monster.core.cloud.http;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Create on 2023/3/13 14:46
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MonsterHttpProperties.class)
public class MonsterHttpConfiguration {
}
