package com.monster.core.boot.config;

import com.kte.core.launch.props.KtePropertySource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 自动配置类
 * <p>
 * Create on 2023/3/12 22:11
 *
 * @author monster gan
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@KtePropertySource(value = "classpath:/kte-boot.yml")
public class MonsterBootAutoConfiguration {

}
