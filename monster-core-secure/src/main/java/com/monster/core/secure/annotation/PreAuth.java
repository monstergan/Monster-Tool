package com.monster.core.secure.annotation;

import java.lang.annotation.*;

/**
 * 权限注解 用于检查权限 规定访问权限
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuth {

    /**
     * Spring el
     * <a href="https://docs.spring.io/spring/docs/5.1.6.RELEASE/spring-framework-reference/core.html#expressions">文档地址</a>
     */
    String value();
}
