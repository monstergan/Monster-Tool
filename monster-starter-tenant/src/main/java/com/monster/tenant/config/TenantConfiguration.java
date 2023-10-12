package com.monster.tenant.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.monster.core.mp.config.MybatisPlusConfiguration;
import com.monster.tenant.*;
import com.monster.tenant.aspect.MonsterTenantAspect;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 多租户配置类
 *
 * @author monster gan
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@AutoConfigureBefore(MybatisPlusConfiguration.class)
@EnableConfigurationProperties(MonsterTenantProperties.class)
public class TenantConfiguration {
    /**
     * 自定义多租户处理器
     *
     * @param tenantProperties 多租户配置类
     * @return TenantHandler
     */
    @Bean
    @Primary
    public TenantLineHandler monsterTenantHandler(MonsterTenantProperties tenantProperties) {
        return new MonsterTenantHandler(tenantProperties);
    }

    /**
     * 自定义租户拦截器
     *
     * @param tenantHandler    多租户处理器
     * @param tenantProperties 多租户配置类
     * @return KteTenantInterceptor
     */
    @Bean
    @Primary
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantHandler, MonsterTenantProperties tenantProperties) {
        MonsterTenantInterceptor tenantInterceptor = new MonsterTenantInterceptor();
        tenantInterceptor.setTenantLineHandler(tenantHandler);
        tenantInterceptor.setTenantProperties(tenantProperties);
        return tenantInterceptor;
    }

    /**
     * 自定义租户id生成器
     *
     * @return TenantId
     */
    @Bean
    @ConditionalOnMissingBean(TenantId.class)
    public TenantId tenantId() {
        return new MonsterTenantId();
    }

    /**
     * 自定义租户切面
     */
    @Bean
    public MonsterTenantAspect MonsterTenantAspect() {
        return new MonsterTenantAspect();
    }
}
