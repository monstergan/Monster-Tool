package com.monster.tenant.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * 指定租户ID动态数据源切换
 *
 * @author monster gan
 */
@DS("#tenantId")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantParamDS {
}
