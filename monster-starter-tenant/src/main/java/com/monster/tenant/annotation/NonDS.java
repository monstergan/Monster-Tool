package com.monster.tenant.annotation;

import java.lang.annotation.*;

/**
 * 排除租户数据源自动切换
 *
 * @author monster gan
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonDS {
}
