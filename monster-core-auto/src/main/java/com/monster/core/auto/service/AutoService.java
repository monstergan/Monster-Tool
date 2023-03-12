package com.monster.core.auto.service;

import java.lang.annotation.*;

/**
 * Create on 2023/3/12 21:50
 *
 * @author monster gan
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoService {
    /**
     * Returns the interfaces implemented by this service provider.
     *
     * @return interface array
     */
    Class<?>[] value();

}
