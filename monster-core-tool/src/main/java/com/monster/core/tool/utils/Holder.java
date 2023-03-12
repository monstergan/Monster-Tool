package com.monster.core.tool.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Eric
 * @Description: 一些常用单例的对象
 */
public class Holder {

	/**
	 * RANDOM
	 */
	public final static Random RANDOM = new Random();

	/**
	 * SECURE_RANDOM
	 */
	public final static SecureRandom SECURE_RANDOM = new SecureRandom();
}
