package com.monster.core.tool.function;

/**
 * @author Eric
 * @Description: TODO
 */
@FunctionalInterface
public interface CheckedRunnable {
	/**
	 * Run this runnable.
	 *
	 * @throws Throwable CheckedException
	 */
	void run() throws Throwable;
}
