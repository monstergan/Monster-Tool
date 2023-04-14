package com.monster.core.api.crypto.exception;

/**
 * 未配置KEY运行时异常
 */
public class KeyNotConfiguredException extends RuntimeException {

    public KeyNotConfiguredException(String message) {
        super(message);
    }
}
