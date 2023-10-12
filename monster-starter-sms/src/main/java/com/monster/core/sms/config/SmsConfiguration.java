package com.monster.core.sms.config;

import com.monster.core.sms.props.SmsProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Sms配置类
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties(SmsProperties.class)
public class SmsConfiguration {
}
