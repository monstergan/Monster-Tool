package com.monster.trace;

import com.monster.core.launch.props.MonsterPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eric
 */
@Configuration(proxyBeanMethods = false)
@MonsterPropertySource(value = "classpath:/monster-trace.yml")
public class TraceAutoConfiguration {
}
