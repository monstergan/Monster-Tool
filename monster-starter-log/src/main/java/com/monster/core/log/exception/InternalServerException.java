package com.monster.core.log.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.monster.core.tool.api.ResultCode.FAILURE;

/**
 * InternalServerException
 */
@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {


	private final Integer errorCode;

	public InternalServerException(Exception e) {
		super(e);
		this.errorCode = FAILURE.getCode();
	}

	public InternalServerException(String message) {
		super(message);
		this.errorCode = FAILURE.getCode();
	}

	public InternalServerException(String message, Exception e) {
		super(message, e);
		this.errorCode = FAILURE.getCode();
	}

	public InternalServerException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
