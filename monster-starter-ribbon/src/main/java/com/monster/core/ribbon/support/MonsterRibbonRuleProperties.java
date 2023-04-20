package com.monster.core.ribbon.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Ribbon 配置
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(MonsterRibbonRuleProperties.PROPERTIES_PREFIX)
public class MonsterRibbonRuleProperties {
	public static final String PROPERTIES_PREFIX = "monster.ribbon.rule";

	/**
	 * 是否开启，默认：false
	 */
	private boolean enabled = true;

	/**
	 * 是否开启服务权重
	 */
	private boolean weight = true;

	/**
	 * 服务的tag，用于灰度，匹配：nacos.discovery.metadata.tag
	 */
	private String tag;
	/**
	 * 优先的ip列表，支持通配符，例如：10.20.0.8*、10.20.0.*
	 */
	private List<String> priorIpPattern = new ArrayList<>();
}
