package com.monster.transaction.config;

import com.kte.core.launch.props.KtePropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Seata配置类
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@KtePropertySource(value = "classpath:/monster-transaction.yml")
public class TransactionConfiguration {
}
