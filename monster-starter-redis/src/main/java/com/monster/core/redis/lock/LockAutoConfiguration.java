package com.monster.core.redis.lock;

import com.monster.core.tool.utils.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动化配置
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedissonClient.class)
@EnableConfigurationProperties(LockProperties.class)
@ConditionalOnProperty(value = "monster.lock.enabled", havingValue = "true")
public class LockAutoConfiguration {

    private static Config singleConfig(LockProperties properties) {
        Config config = new Config();
        SingleServerConfig serversConfig = config.useSingleServer();
        serversConfig.setAddress(properties.getAddress());
        String password = properties.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        serversConfig.setConnectionPoolSize(properties.getPoolSize());
        serversConfig.setConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        return config;
    }

    private static Config masterSlaveConfig(LockProperties properties) {
        Config config = new Config();
        MasterSlaveServersConfig serversConfig = config.useMasterSlaveServers();
        serversConfig.setMasterAddress(properties.getMasterAddress());
        serversConfig.addSlaveAddress(properties.getSlaveAddress());
        String password = properties.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        serversConfig.setMasterConnectionPoolSize(properties.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(properties.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        return config;
    }

    private static Config sentinelConfig(LockProperties properties) {
        Config config = new Config();
        SentinelServersConfig serversConfig = config.useSentinelServers();
        serversConfig.setMasterName(properties.getMasterName());
        serversConfig.addSentinelAddress(properties.getSentinelAddress());
        String password = properties.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        serversConfig.setMasterConnectionPoolSize(properties.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(properties.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        return config;
    }

    private static Config clusterConfig(LockProperties properties) {
        Config config = new Config();
        ClusterServersConfig serversConfig = config.useClusterServers();
        serversConfig.addNodeAddress(properties.getNodeAddress());
        String password = properties.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setMasterConnectionPoolSize(properties.getPoolSize());
        serversConfig.setMasterConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setSlaveConnectionPoolSize(properties.getPoolSize());
        serversConfig.setSlaveConnectionMinimumIdleSize(properties.getIdleSize());
        serversConfig.setIdleConnectionTimeout(properties.getConnectionTimeout());
        serversConfig.setConnectTimeout(properties.getConnectionTimeout());
        serversConfig.setTimeout(properties.getTimeout());
        return config;
    }

    private static RedissonClient redissonClient(LockProperties properties) {
        LockProperties.Mode mode = properties.getMode();
        Config config;
        switch (mode) {
            case sentinel:
                config = sentinelConfig(properties);
                break;
            case cluster:
                config = clusterConfig(properties);
                break;
            case master:
                config = masterSlaveConfig(properties);
                break;
            case single:
                config = singleConfig(properties);
                break;
            default:
                config = new Config();
                break;
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLockClient redisLockClient(LockProperties properties) {
        return new RedisLockClientImpl(redissonClient(properties));
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLockAspect redisLockAspect(RedisLockClient redisLockClient) {
        return new RedisLockAspect(redisLockClient);
    }


}
