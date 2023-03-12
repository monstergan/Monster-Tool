package com.monster.core.tool.utils;

import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;

/**
 * Spring AntPath 规则文件过滤
 * <p>
 * Create on 2023/3/12 22:28
 *
 * @author monster gan
 */
@AllArgsConstructor
public class AntPathFilter implements FileFilter, Serializable {
	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

	private final String pattern;

	/**
	 * 过滤规则
	 *
	 * @param pathname 路径
	 * @return boolean
	 */
	@Override
	public boolean accept(File pathname) {
		String filePath = pathname.getAbsolutePath();
		return PATH_MATCHER.match(pattern, filePath);
	}


}
