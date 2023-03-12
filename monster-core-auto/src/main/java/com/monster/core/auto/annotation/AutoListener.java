package com.monster.core.auto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * ApplicationListener 处理
 * <p>
 * Create by on 2023/3/12 21:42
 * @author monster gan
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface AutoListener {
}
