package com.monster.core.prometheus.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;


import com.monster.core.launch.props.MonsterPropertySource;
import com.monster.core.prometheus.endpoint.AgentEndpoint;
import com.monster.core.prometheus.endpoint.ServiceEndpoint;
import com.monster.core.prometheus.service.RegistrationService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Prometheus配置类
 *
 * @author monster
 */
@Configuration(proxyBeanMethods = false)
@MonsterPropertySource(value = "classpath:/monster-prometheus.yml")
public class PrometheusConfiguration {

    @Bean
    public RegistrationService registrationService(DiscoveryClient discoveryClient) {
        return new RegistrationService(discoveryClient);
    }

    @Bean
    public AgentEndpoint agentController(NacosDiscoveryProperties properties) {
        return new AgentEndpoint(properties);
    }

    @Bean
    public ServiceEndpoint serviceController(RegistrationService registrationService) {
        return new ServiceEndpoint(registrationService);
    }


}
