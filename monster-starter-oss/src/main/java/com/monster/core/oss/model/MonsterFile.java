package com.monster.core.oss.model;

import lombok.Data;

/**
 * MonsterFile
 */
@Data
public class MonsterFile {
	/**
	 * 文件地址
	 */
	private String link;
	/**
	 * 域名地址
	 */
	private String domain;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 初始文件名
	 */
	private String originalName;
	/**
	 * 附件表ID
	 */
	private Long attachId;
}
