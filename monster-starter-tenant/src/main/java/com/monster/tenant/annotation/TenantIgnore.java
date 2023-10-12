package com.monster.tenant.annotation;

import java.lang.annotation.*;

/**
 * 排除租户逻辑
 *
 * @author monster gan
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantIgnore {
}
