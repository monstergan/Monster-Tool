package com.monster.core.launch.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 * <p>
 * Create on 2023/3/13 13:34
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MonsterLaunchConfiguration {
}
