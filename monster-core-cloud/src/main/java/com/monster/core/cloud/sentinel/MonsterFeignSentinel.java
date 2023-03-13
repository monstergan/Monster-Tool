package com.monster.core.cloud.sentinel;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.monster.core.cloud.feign.MonsterFallbackFactory;
import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * feign集成sentinel自动配置
 * <p>
 * Create by monster gan on 2023/3/13 14:52
 */
public class MonsterFeignSentinel {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends Feign.Builder implements ApplicationContextAware {
        private Contract contract = new Contract.Default();
        private ApplicationContext applicationContext;
        private FeignContext feignContext;

        @Override
        public Feign.Builder invocationHandlerFactory(
                InvocationHandlerFactory invocationHandlerFactory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Builder contract(Contract contract) {
            this.contract = contract;
            return this;
        }

        @Override
        public Feign build() {
            super.invocationHandlerFactory(new InvocationHandlerFactory() {
                @SneakyThrows
                @Override
                public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {
                    // 注解取值以避免循环依赖的问题
                    FeignClient feignClient = AnnotationUtils.findAnnotation(target.type(), FeignClient.class);
                    Class fallback = feignClient.fallback();
                    Class fallbackFactory = feignClient.fallbackFactory();
                    String contextId = feignClient.contextId();

                    if (!StringUtils.hasText(contextId)) {
                        contextId = feignClient.name();
                    }

                    Object fallbackInstance;
                    FallbackFactory fallbackFactoryInstance;
                    // 判断fallback类型
                    if (void.class != fallback) {
                        fallbackInstance = getFromContext(contextId, "fallback", fallback, target.type());
                        return new MonsterSentinelInvocationHandler(target, dispatch, new FallbackFactory.Default(fallbackInstance));
                    }
                    if (void.class != fallbackFactory) {
                        fallbackFactoryInstance = (FallbackFactory) getFromContext(contextId, "fallbackFactory", fallbackFactory, FallbackFactory.class);
                        return new MonsterSentinelInvocationHandler(target, dispatch, fallbackFactoryInstance);
                    }
                    // 默认fallbackFactory
                    MonsterFallbackFactory MonsterFallbackFactory = new MonsterFallbackFactory(target);
                    return new MonsterSentinelInvocationHandler(target, dispatch, MonsterFallbackFactory);
                }

                private Object getFromContext(String name, String type, Class fallbackType, Class targetType) {
                    Object fallbackInstance = feignContext.getInstance(name, fallbackType);
                    if (fallbackInstance == null) {
                        throw new IllegalStateException(
                                String.format("No %s instance of type %s found for feign client %s",
                                        type, fallbackType, name)
                        );
                    }

                    if (!targetType.isAssignableFrom(fallbackType)) {
                        throw new IllegalStateException(
                                String.format("Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                                        type, fallbackType, targetType, name)
                        );
                    }
                    return fallbackInstance;
                }
            });
            super.contract(new SentinelContractHolder(contract));
            return super.build();
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
            feignContext = this.applicationContext.getBean(FeignContext.class);
        }
    }

}
