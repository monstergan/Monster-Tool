package com.monster.core.log.props;

import com.monster.core.launch.log.KteLogLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 日志配置
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(KteLogLevel.REQ_LOG_PROPS_PREFIX)
public class MonsterRequestLogProperties {

	/**
	 * 是否开启请求日志
	 */
	private Boolean enabled = true;

	/**
	 * 是否开启异常日志推送
	 */
	private Boolean errorLog = true;


	/**
	 * 日志级别配置，默认：BODY
	 */
	private KteLogLevel level = KteLogLevel.BODY;
}
