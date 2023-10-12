package com.monster.tenant.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 指定租户表排除
 *
 * @author monster
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Component
public @interface TableExclude {
    String value() default "";
}
