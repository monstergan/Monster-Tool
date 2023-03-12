package com.monster.core.boot.request;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric
 */
@Data
@ConfigurationProperties("kte.request")
public class RequestProperties {

	/**
	 * 开启自定义request
	 */
	private Boolean enabled = true;

	/**
	 * 放行url
	 */
	private List<String> skipUrl = new ArrayList<>();
}
