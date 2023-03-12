package com.monster.core.tool.utils;


import java.nio.charset.StandardCharsets;

/**
 * Create on 2023/3/12 22:28
 *
 * @author monster gan
 */
public class Base64Util extends org.springframework.util.Base64Utils {

	/**
	 * 编码
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String encode(String value) {
		return Base64Util.encode(value, StandardCharsets.UTF_8);
	}

	/**
	 * 编码
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String encode(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		return new String(encode(val), charset);
	}

	/**
	 * 编码URL安全
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String encodeUrlSafe(String value) {
		return Base64Util.encodeUrlSafe(value, StandardCharsets.UTF_8);
	}

	/**
	 * 编码URL安全
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String encodeUrlSafe(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		return new String(encodeUrlSafe(val), charset);
	}

	/**
	 * 解码
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String decode(String value) {
		return Base64Util.decode(value, StandardCharsets.UTF_8);
	}

	/**
	 * 解码
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String decode(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		byte[] decodedValue = decode(val);
		return new String(decodedValue, charset);
	}

	/**
	 * 解码URL安全
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String decodeUrlSafe(String value) {
		return Base64Util.decodeUrlSafe(value, StandardCharsets.UTF_8);
	}

	/**
	 * 解码URL安全
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String decodeUrlSafe(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		byte[] decodedValue = decodeUrlSafe(val);
		return new String(decodedValue, charset);
	}

}
