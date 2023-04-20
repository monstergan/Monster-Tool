package com.monster.core.ribbon.predicate;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import org.springframework.lang.Nullable;

/**
 * 过滤服务
 */
public abstract class DiscoveryEnabledPredicate extends AbstractServerPredicate {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean apply(@Nullable PredicateKey input) {
		if (input == null) {
			return false;
		}
		MetadataServer metadataServer = new MetadataServer(input.getServer());
		// 支持 metadata 进行判断
		if (metadataServer.hasMetadata()) {
			return apply(metadataServer);
		}
		return false;
	}

	/**
	 * Returns whether the specific {@link MetadataServer} matches this predicate.
	 *
	 * @param server the discovered server
	 * @return whether the server matches the predicate
	 */
	protected abstract boolean apply(MetadataServer server);
}
