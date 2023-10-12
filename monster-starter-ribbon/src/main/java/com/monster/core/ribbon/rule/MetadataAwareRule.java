package com.monster.core.ribbon.rule;

import com.monster.core.ribbon.predicate.MetadataAwarePredicate;
import com.monster.core.ribbon.support.MonsterRibbonRuleProperties;
import com.monster.core.ribbon.utils.BeanUtil;
import com.netflix.loadbalancer.Server;
import org.springframework.util.PatternMatchUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ribbon 路由规则器
 */
public class MetadataAwareRule extends DiscoveryEnabledRule {
	public MetadataAwareRule() {
		super(MetadataAwarePredicate.INSTANCE);
	}

	@Override
	public List<Server> filterServers(List<Server> serverList) {
		MonsterRibbonRuleProperties ribbonProperties = BeanUtil.getBean(MonsterRibbonRuleProperties.class);
		List<String> priorIpPattern = ribbonProperties.getPriorIpPattern();

		// 1. 获取 ip 规则
		boolean hasPriorIpPattern = !priorIpPattern.isEmpty();
		String[] priorIpPatterns = priorIpPattern.toArray(new String[0]);

		List<Server> priorServerList = new ArrayList<>();
		for (Server server : serverList) {
			// 2. 按配置顺序排列 ip 服务
			String host = server.getHost();
			if (hasPriorIpPattern && PatternMatchUtils.simpleMatch(priorIpPatterns, host)) {
				priorServerList.add(server);
			}
		}

		// 3. 如果优先的有数据直接返回
		if (!priorServerList.isEmpty()) {
			return priorServerList;
		}

		return Collections.unmodifiableList(serverList);
	}

}
