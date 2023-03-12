package com.monster.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Create on 2023/3/12 22:25
 *
 * @author monster gan
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MonsterBeanCopierKey {
	private final Class<?> source;
	private final Class<?> target;
	private final boolean useConverter;
	private final boolean nonNull;
}
