package com.monster.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * bean map key，提高性能
 * <p>
 * Create on 2023/3/12 22:26
 *
 * @author monster gan
 */
@EqualsAndHashCode
@AllArgsConstructor
public class MonsterBeanMapKey {

	private final Class type;
	private final int require;

}
