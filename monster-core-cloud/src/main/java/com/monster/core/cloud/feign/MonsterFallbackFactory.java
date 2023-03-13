package com.monster.core.cloud.feign;

import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 默认 Fallback，避免写过多fallback类
 * <p>
 * Create by monster gan on 2023/3/13 14:44
 */
@AllArgsConstructor
public class MonsterFallbackFactory<T> implements FallbackFactory<T> {
    private final Target<T> target;

    @Override
    @SuppressWarnings("unchecked")
    public T create(Throwable cause) {
        final Class<T> targetType = target.type();
        final String targetName = target.name();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setUseCache(true);
        enhancer.setCallback(new MonsterFeignFallback<>(targetType, targetName, cause));
        return (T) enhancer.create();
    }
}
