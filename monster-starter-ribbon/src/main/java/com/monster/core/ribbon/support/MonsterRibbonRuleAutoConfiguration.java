package com.monster.core.ribbon.support;

import com.monster.core.ribbon.rule.DiscoveryEnabledRule;
import com.monster.core.ribbon.rule.MetadataAwareRule;
import com.monster.core.ribbon.rule.WeightAwareRule;
import com.monster.core.ribbon.utils.BeanUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * ribbon rule auto configuration
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RibbonClientConfiguration.class)
@EnableConfigurationProperties(MonsterRibbonRuleProperties.class)
public class MonsterRibbonRuleAutoConfiguration {

    @Bean
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = MonsterRibbonRuleProperties.PROPERTIES_PREFIX + ".enabled", matchIfMissing = true)
    public static class MetadataAwareRuleConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public DiscoveryEnabledRule discoveryEnabledRule() {
            return new MetadataAwareRule();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(MonsterRibbonRuleProperties.PROPERTIES_PREFIX + ".weight")
    public static class WeightAwareRuleConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public DiscoveryEnabledRule discoveryEnabledRule() {
            return new WeightAwareRule();
        }
    }

}
