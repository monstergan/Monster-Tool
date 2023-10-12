package com.monster.core.sms.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.monster.core.redis.cache.RedisUtils;
import com.monster.core.sms.AliSmsTemplate;
import com.monster.core.sms.props.SmsProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信配置类
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnClass(IAcsClient.class)
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.name", havingValue = "aliyun")
public class AliSmsConfiguration {

    private final RedisUtils redisUtils;

    @Bean
    public AliSmsTemplate aliSmsTemplate(SmsProperties smsProperties) {
        IClientProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKey(), smsProperties.getSecretKey());
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return new AliSmsTemplate(smsProperties, acsClient, redisUtils);
    }

}
