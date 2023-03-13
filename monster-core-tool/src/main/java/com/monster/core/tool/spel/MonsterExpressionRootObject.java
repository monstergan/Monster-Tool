package com.monster.core.tool.spel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author Eric
 * @Description: TODO
 */
@Getter
@AllArgsConstructor
public class MonsterExpressionRootObject {

	private final Method method;

	private final Object[] args;

	private final Object target;

	private final Class<?> targetClass;

	private final Method targetMethod;
}
