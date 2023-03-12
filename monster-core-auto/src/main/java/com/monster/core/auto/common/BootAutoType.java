package com.monster.core.auto.common;

import com.monster.core.auto.annotation.AutoContextInitializer;
import com.monster.core.auto.annotation.AutoFailureAnalyzer;
import com.monster.core.auto.annotation.AutoListener;
import com.monster.core.auto.annotation.AutoRunListener;

/**
 * 注解类型
 * <p>
 * Create on 2023/3/12
 *
 * @author monster gan
 */
public enum BootAutoType {

    /**
     * 注解处理的类型
     */
    CONTEXT_INITIALIZER(AutoContextInitializer.class.getName(), "org.springframework.context.ApplicationContextInitializer"),
    LISTENER(AutoListener.class.getName(), "org.springframework.context.ApplicationListener"),
    RUN_LISTENER(AutoRunListener.class.getName(), "org.springframework.boot.SpringApplicationRunListener"),
    FAILURE_ANALYZER(AutoFailureAnalyzer.class.getName(), "org.springframework.boot.diagnostics.FailureAnalyzer"),
    COMPONENT("org.springframework.stereotype.Component", "org.springframework.boot.autoconfigure.EnableAutoConfiguration");

    private final String annotationName;
    private final String configureKey;

    BootAutoType(String annotationName, String configureKey) {
        this.annotationName = annotationName;
        this.configureKey = configureKey;
    }

    public final String getAnnotationName() {
        return annotationName;
    }

    public final String getConfigureKey() {
        return configureKey;
    }


}
