package com.monster.core.oss.rule;

import com.monster.core.secure.utils.AuthUtil;
import com.monster.core.tool.utils.*;
import lombok.AllArgsConstructor;

/**
 * 默认存储桶生成规则
 */
@AllArgsConstructor
public class MonsterOssRule implements OssRule {

	/**
	 * 租户模式
	 */
	private final Boolean tenantMode;

	@Override
	public String bucketName(String bucketName) {
		String prefix = (tenantMode) ? AuthUtil.getTenantId().concat(StringPool.DASH) : StringPool.EMPTY;
		return prefix + bucketName;
	}

	@Override
	public String fileName(String originalFilename, String pathName) {
		if (ObjectUtil.isNotEmpty(pathName)) {
			//如果路径包含"&"，则文件名使用原文件名上传至OSS
			if (originalFilename.contains(StringPool.AMPERSAND)) {
				return pathName + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + FileUtil.getNameWithoutExtension(originalFilename) + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
			}
			return pathName + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
		}
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
