package com.monster.core.boot.props;

import com.kte.core.tool.utils.PathUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;


/**
 * 文件上传配置
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("kte.upload")
public class MonsterUploadProperties {

	/**
	 * 文件保存目录，默认：jar 包同级目录
	 */
	@Nullable
	private String savePath = PathUtil.getJarPath();
}
