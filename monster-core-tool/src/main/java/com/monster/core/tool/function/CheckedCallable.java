package com.monster.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * 受检的 Callable
 * <p>
 * Create on 2023/3/13 10:51
 *
 * @author monster gan
 */
@FunctionalInterface
public interface CheckedCallable<T> {
    /**
     * Run this callable.
     *
     * @return result
     * @throws Throwable CheckedException
     */
    @Nullable
    T call() throws Throwable;
}
