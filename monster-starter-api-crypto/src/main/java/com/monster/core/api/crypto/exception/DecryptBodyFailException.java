package com.monster.core.api.crypto.exception;

/**
 * 解密数据失败异常
 */
public class DecryptBodyFailException extends RuntimeException {

    public DecryptBodyFailException(String message) {
        super(message);
    }
}
