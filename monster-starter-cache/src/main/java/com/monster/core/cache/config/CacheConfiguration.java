package com.monster.core.cache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfiguration {
}
