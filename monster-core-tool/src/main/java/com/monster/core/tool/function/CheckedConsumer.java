package com.monster.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * @author Eric
 * @Description: 受检的 Consumer
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

	/**
	 * Run the Consumer
	 *
	 * @param t T
	 * @throws Throwable UncheckedException
	 */
	@Nullable
	void accept(@Nullable T t) throws Throwable;
}
