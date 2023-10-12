package com.monster.core.redis.config;

import com.monster.core.jwt.config.JwtRedisConfiguration;
import com.monster.core.redis.cache.RedisUtils;
import com.monster.core.redis.serializer.RedisKeySerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate配置
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({JwtRedisConfiguration.class, RedisAutoConfiguration.class})
@EnableConfigurationProperties(RedisProperties.class)
public class RedisTemplateConfiguration implements RedisSerializerConfigAble {

	/**
	 * value 值 序列化
	 *
	 * @return RedisSerializer
	 */
	@Bean
	@ConditionalOnMissingBean(RedisSerializer.class)
	@Override
	public RedisSerializer<Object> redisSerializer(RedisProperties properties) {
		return defaultRedisSerializer(properties);
	}

	@Bean(name = "redisTemplate")
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// key 序列化
		RedisKeySerializer keySerializer = new RedisKeySerializer();
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		// value 序列化
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(ValueOperations.class)
	public ValueOperations valueOperations(RedisTemplate redisTemplate) {
		return redisTemplate.opsForValue();
	}

	@Bean
	public RedisUtils redisUtils(RedisTemplate<String, Object> redisTemplate) {
		return new RedisUtils(redisTemplate);
	}

}
