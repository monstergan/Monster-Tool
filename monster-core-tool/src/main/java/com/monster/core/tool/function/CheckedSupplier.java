package com.monster.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * @author Eric
 */
@FunctionalInterface
public interface CheckedSupplier<T> {
	/**
	 * Run the Supplier
	 *
	 * @return T
	 * @throws Throwable CheckedException
	 */
	@Nullable
	T get() throws Throwable;
}
