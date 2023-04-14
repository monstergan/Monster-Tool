package com.monster.core.cloud.annotation;

import java.lang.annotation.*;

/**
 * Create by monster gan on 2023/3/13 14:39
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiVersion {
    /**
     * header 路径中的版本
     *
     * @return 版本号
     */
    String value() default "";

}
