package com.monster.core.redis.exception;

/**
 * 加锁等待超时异常
 */
public class RedisLockException extends RuntimeException {
    public RedisLockException() {
    }

    public RedisLockException(String message) {
        super(message);
    }

    public RedisLockException(Throwable cause) {
        super(cause);
    }
}
