package com.monster.core.launch.props;

import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 自定义资源文件读取，优先级最低
 * <p>
 * Create  on 2023/3/13 13:45
 *
 * @author monster gan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MonsterPropertySource {

    /**
     * Indicate the resource location(s) of the properties file to be loaded.
     * for example, {@code "classpath:/com/example/app.yml"}
     *
     * @return location(s)
     */
    String value();

    /**
     * load app-{activeProfile}.yml
     *
     * @return {boolean}
     */
    boolean loadActiveProfile() default true;

    /**
     * Get the order value of this resource.
     *
     * @return order
     */
    int order() default Ordered.LOWEST_PRECEDENCE;

}
