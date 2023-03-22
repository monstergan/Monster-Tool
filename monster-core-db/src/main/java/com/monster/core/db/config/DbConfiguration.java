package com.monster.core.db.config;

import com.monster.core.launch.props.MonsterPropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@MonsterPropertySource(value = "classpath:/monster-db.yml")
public class DbConfiguration {
}
