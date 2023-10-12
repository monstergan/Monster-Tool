package com.monster.core.sms.config;

import com.github.qcloudsms.SmsMultiSender;
import com.monster.core.redis.cache.RedisUtils;
import com.monster.core.sms.TencentSmsTemplate;
import com.monster.core.sms.props.SmsProperties;
import com.monster.core.tool.utils.Func;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云短信配置类
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnClass(SmsMultiSender.class)
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.name", havingValue = "tencent")
public class TencentSmsConfiguration {

    private final RedisUtils redisUtils;

    @Bean
    public TencentSmsTemplate tencentSmsTemplate(SmsProperties smsProperties) {
        SmsMultiSender smsSender = new SmsMultiSender(Func.toInt(smsProperties.getAccessKey()), smsProperties.getSecretKey());
        return new TencentSmsTemplate(smsProperties, smsSender, redisUtils);
    }

}
