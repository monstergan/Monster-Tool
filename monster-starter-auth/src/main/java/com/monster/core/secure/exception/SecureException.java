package com.monster.core.secure.exception;

import com.monster.core.tool.api.IResultCode;
import com.monster.core.tool.api.ResultCode;
import lombok.Getter;

/**
 * Secure异常
 *
 * @author monster gan
 */
public class SecureException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;

    @Getter
    private final IResultCode resultCode;

    public SecureException(String message) {
        super(message);
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }

    public SecureException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public SecureException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
