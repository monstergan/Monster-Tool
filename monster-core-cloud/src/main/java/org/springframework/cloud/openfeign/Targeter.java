package org.springframework.cloud.openfeign;

import feign.Feign;
import feign.Target;

/**
 * Create by monster gan on 2023/3/13 15:00
 */
public interface Targeter {
	/**
	 * target
	 *
	 * @param factory
	 * @param feign
	 * @param context
	 * @param target
	 * @param <T>
	 * @return T
	 */
	<T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context,
				 Target.HardCodedTarget<T> target);
}
