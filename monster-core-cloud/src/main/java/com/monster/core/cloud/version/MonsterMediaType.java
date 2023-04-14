package com.monster.core.cloud.version;

import lombok.Getter;
import org.springframework.http.MediaType;

/**
 * Create by monster gan on 2023/3/13 14:56
 */
@Getter
public class MonsterMediaType {
	private static final String MEDIA_TYPE_TEMP = "application/vnd.%s.%s+json";

	private final String appName = "kte";
	private final String version;
	private final MediaType mediaType;

	public MonsterMediaType(String version) {
		this.version = version;
		this.mediaType = MediaType.valueOf(String.format(MEDIA_TYPE_TEMP, appName, version));
	}

	@Override
	public String toString() {
		return mediaType.toString();
	}
}
