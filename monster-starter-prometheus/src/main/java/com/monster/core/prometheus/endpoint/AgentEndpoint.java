package com.monster.core.prometheus.endpoint;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.monster.core.auto.annotation.AutoIgnore;
import com.monster.core.prometheus.data.Agent;
import com.monster.core.prometheus.data.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * consul agent api
 */
@AutoIgnore
@RestController
@RequiredArgsConstructor
public class AgentEndpoint {
	private final NacosDiscoveryProperties properties;

	@GetMapping(value = "/v1/agent/self", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Agent getNodes() {
		Config config = Config.builder().dataCenter(properties.getGroup()).build();
		return Agent.builder().config(config).build();
	}

}
