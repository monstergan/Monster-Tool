package com.monster.core.sms.config;

import com.monster.core.redis.cache.RedisUtils;
import com.monster.core.sms.YunpianSmsTemplate;
import com.monster.core.sms.props.SmsProperties;
import com.yunpian.sdk.YunpianClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 云片短信配置类
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnClass(YunpianClient.class)
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.name", havingValue = "yunpian")
public class YunpianSmsConfiguration {

    private final RedisUtils redisUtils;

    @Bean
    public YunpianSmsTemplate yunpianSmsTemplate(SmsProperties smsProperties) {
        YunpianClient client = new YunpianClient(smsProperties.getAccessKey()).init();
        return new YunpianSmsTemplate(smsProperties, client, redisUtils);
    }

}
