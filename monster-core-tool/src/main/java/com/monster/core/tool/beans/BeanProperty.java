package com.monster.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bean属性
 * <p>
 * Create on 2023/3/12 22:24
 *
 * @author monster gan
 */
@Getter
@AllArgsConstructor
public class BeanProperty {
	private final String name;
	private final Class<?> type;

}
