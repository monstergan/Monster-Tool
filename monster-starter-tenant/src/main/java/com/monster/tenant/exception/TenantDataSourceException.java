package com.monster.tenant.exception;

/**
 * 租户数据源异常
 *
 * @author monster gan
 */
public class TenantDataSourceException extends RuntimeException {

    public TenantDataSourceException(String message) {
        super(message);
    }

    /**
     * 提高性能
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
