package com.monster.core.redis.config;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 序列化接口
 */
public interface RedisSerializerConfigAble {

    /**
     * 序列化接口
     *
     * @param properties 配置
     * @return RedisSerializer
     */
    RedisSerializer<Object> redisSerializer(RedisProperties properties);

    /**
     * 默认的序列化方式
     *
     * @param properties 配置
     * @return RedisSerializer
     */
    default RedisSerializer<Object> defaultRedisSerializer(RedisProperties properties) {
        RedisProperties.SerializerType serializerType = properties.getSerializerType();
        if (RedisProperties.SerializerType.JDK == serializerType) {
            /**
             * SpringBoot扩展了ClassLoader，进行分离打包的时候，使用到JdkSerializationRedisSerializer的地方
             * 会因为ClassLoader的不同导致加载不到Class
             * 指定使用项目的ClassLoader
             *
             * JdkSerializationRedisSerializer默认使用{@link sun.misc.Launcher.AppClassLoader}
             * SpringBoot默认使用{@link org.springframework.boot.loader.LaunchedURLClassLoader}
             */
            ClassLoader classLoader = this.getClass().getClassLoader();
            return new JdkSerializationRedisSerializer(classLoader);
        }
        return new GenericJackson2JsonRedisSerializer();
    }

}
