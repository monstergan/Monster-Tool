package com.monster.core.prometheus.service;

import com.monster.core.prometheus.data.ChangeItem;
import com.monster.core.prometheus.data.Service;
import com.monster.core.prometheus.data.ServiceHealth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Supplier;

/**
 * Returns Services and List of Service with its last changed
 */
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {
	private static final String[] NO_SERVICE_TAGS = new String[0];
	private final DiscoveryClient discoveryClient;

	private static <T> Mono<ChangeItem<T>> returnDeferred(long waitMillis, Long index, Supplier<T> fn) {
		return Mono.just(new ChangeItem<>(fn.get(), System.currentTimeMillis()));
	}

	public Mono<ChangeItem<Map<String, String[]>>> getServiceNames(long waitMillis, Long index) {
		return returnDeferred(waitMillis, index, () -> {
			List<String> services = discoveryClient.getServices();
			Set<String> set = new HashSet<>(services);
			Map<String, String[]> result = new HashMap<>();
			for (String item : set) {
				result.put(item, NO_SERVICE_TAGS);
			}
			return result;
		});
	}

	public Mono<ChangeItem<List<Service>>> getService(String appName, long waitMillis, Long index) {
		return returnDeferred(waitMillis, index, () -> {
			List<ServiceInstance> instances = discoveryClient.getInstances(appName);
			List<Service> list = new ArrayList<>();
			if (instances == null || instances.isEmpty()) {
				return Collections.emptyList();
			}
			Set<ServiceInstance> instSet = new HashSet<>(instances);
			for (ServiceInstance instance : instSet) {
				Service service = Service.builder()
					.address(instance.getHost())
					.node(instance.getServiceId())
					.serviceAddress(instance.getHost())
					.servicePort(instance.getPort())
					.serviceName(instance.getServiceId())
					.serviceId(instance.getHost() + ":" + instance.getPort())
					.nodeMeta(Collections.emptyMap())
					.serviceMeta(instance.getMetadata())
					.serviceTags(Collections.emptyList())
					.build();
				list.add(service);
			}
			return list;
		});
	}

	public ServiceHealth getServiceHealth(Service instanceInfo) {
		String address = instanceInfo.getAddress();
		ServiceHealth.Node node = ServiceHealth.Node.builder()
			.name(instanceInfo.getServiceName())
			.address(address)
			.meta(Collections.emptyMap())
			.build();
		ServiceHealth.Service service = ServiceHealth.Service.builder()
			.id(instanceInfo.getServiceId())
			.name(instanceInfo.getServiceName())
			.tags(Collections.emptyList())
			.address(address)
			.meta(instanceInfo.getServiceMeta())
			.port(instanceInfo.getServicePort())
			.build();
		ServiceHealth.Check check = ServiceHealth.Check.builder()
			.node(instanceInfo.getServiceName())
			.checkId("service:" + instanceInfo.getServiceId())
			.name("Service '" + instanceInfo.getServiceId() + "' check")
			// nacos 实时性很高，可认定为健康
			.status("UP")
			.build();
		return ServiceHealth.builder()
			.node(node)
			.service(service)
			.checks(Collections.singletonList(check))
			.build();
	}
}
