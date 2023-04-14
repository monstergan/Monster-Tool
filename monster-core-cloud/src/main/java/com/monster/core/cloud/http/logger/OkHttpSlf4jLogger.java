package com.monster.core.cloud.http.logger;

import lombok.extern.slf4j.Slf4j;

/**
 * Create by monster gan on 2023/3/13 14:48
 */
@Slf4j
public class OkHttpSlf4jLogger implements HttpLoggingInterceptor.Logger {
	@Override
	public void log(String message) {
		log.info(message);
	}
}
