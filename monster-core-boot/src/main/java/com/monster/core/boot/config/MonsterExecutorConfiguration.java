package com.monster.core.boot.config;


import com.monster.core.boot.error.ErrorType;
import com.monster.core.boot.error.ErrorUtil;
import com.kte.core.context.KteContext;
import com.kte.core.context.KteRunnableWrapper;
import com.kte.core.launch.props.KteProperties;
import com.kte.core.log.constant.EventConstant;
import com.kte.core.log.event.ErrorLogEvent;
import com.kte.core.log.model.LogError;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ErrorHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步处理
 * <p>
 * Create on 2023/3/12 22:11
 *
 * @author monster gan
 */
@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
@AllArgsConstructor
public class MonsterExecutorConfiguration extends AsyncConfigurerSupport {

	private final KteContext kteContext;
	private final KteProperties kteProperties;
	private final ApplicationEventPublisher publisher;

	@Bean
	public TaskExecutorCustomizer taskExecutorCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-task-");
			taskExecutor.setTaskDecorator(KteRunnableWrapper::new);
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		};
	}

	@Bean
	public TaskSchedulerCustomizer taskSchedulerCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-scheduler");
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			taskExecutor.setErrorHandler(new KteErrorHandler(kteContext, kteProperties, publisher));
		};
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new KteAsyncUncaughtExceptionHandler(kteContext, kteProperties, publisher);
	}

	@RequiredArgsConstructor
	private static class KteAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
		private final KteContext kteContext;
		private final KteProperties kteProperties;
		private final ApplicationEventPublisher eventPublisher;

		@Override
		public void handleUncaughtException(@NonNull Throwable error, @NonNull Method method, @NonNull Object... params) {
			log.error("Unexpected exception occurred invoking async method: {}", method, error);
			LogError logError = new LogError();
			// 服务信息、环境、异常类型
			logError.setParams(ErrorType.ASYNC.getType());
			logError.setEnv(kteProperties.getEnv());
			logError.setServiceId(kteProperties.getName());
			logError.setRequestUri(kteContext.getRequestId());
			// 堆栈信息
			ErrorUtil.initErrorInfo(error, logError);
			Map<String, Object> event = new HashMap<>(16);
			event.put(EventConstant.EVENT_LOG, logError);
			eventPublisher.publishEvent(new ErrorLogEvent(event));
		}
	}

	@RequiredArgsConstructor
	private static class KteErrorHandler implements ErrorHandler {
		private final KteContext kteContext;
		private final KteProperties kteProperties;
		private final ApplicationEventPublisher eventPublisher;

		@Override
		public void handleError(@NonNull Throwable error) {
			log.error("Unexpected scheduler exception", error);
			LogError logError = new LogError();
			// 服务信息、环境、异常类型
			logError.setParams(ErrorType.SCHEDULER.getType());
			logError.setServiceId(kteProperties.getName());
			logError.setEnv(kteProperties.getEnv());
			logError.setRequestUri(kteContext.getRequestId());
			// 堆栈信息
			ErrorUtil.initErrorInfo(error, logError);
			Map<String, Object> event = new HashMap<>(16);
			event.put(EventConstant.EVENT_LOG, logError);
			eventPublisher.publishEvent(new ErrorLogEvent(event));
		}
	}

}
