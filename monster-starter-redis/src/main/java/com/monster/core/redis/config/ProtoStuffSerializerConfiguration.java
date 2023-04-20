package com.monster.core.redis.config;

import com.monster.core.redis.serializer.ProtoStuffSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 序列化配置
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisTemplateConfiguration.class)
@ConditionalOnClass(name = "io.protostuff.Schema")
public class ProtoStuffSerializerConfiguration implements RedisSerializerConfigAble {

	@Bean
	@ConditionalOnMissingBean
	@Override
	public RedisSerializer<Object> redisSerializer(RedisProperties properties) {
		if (RedisProperties.SerializerType.ProtoStuff == properties.getSerializerType()) {
			return new ProtoStuffSerializer();
		}
		return defaultRedisSerializer(properties);
	}
}
