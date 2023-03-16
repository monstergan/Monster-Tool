package com.monster.core.context.props;

import com.monster.core.launch.constant.TokenConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(MonsterContextProperties.PREFIX)
public class MonsterContextProperties {

	/**
	 * 配置前缀
	 */
	public static final String PREFIX = "kte.context";
	/**
	 * 上下文传递的 headers 信息
	 */
	private Headers headers = new Headers();

	/**
	 * 获取跨服务的请求头
	 *
	 * @return 请求头列表
	 */
	public List<String> getCrossHeaders() {
		List<String> headerList = new ArrayList<>();
		headerList.add(headers.getRequestId());
		headerList.add(headers.getAccountId());
		headerList.add(headers.getTenantId());
		headerList.addAll(headers.getAllowed());
		return headerList;
	}

	@Getter
	@Setter
	public static class Headers {
		/**
		 * 请求id，默认：Kte-RequestId
		 */
		private String requestId = "Kte-RequestId";
		/**
		 * 用于 聚合层 向调用层传递用户信息 的请求头，默认：Kte-AccountId
		 */
		private String accountId = "Kte-AccountId";
		/**
		 * 用于 聚合层 向调用层传递租户id 的请求头，默认：Kte-TenantId
		 */
		private String tenantId = "Kte-TenantId";
		/**
		 * 自定义 RestTemplate 和 Feign 透传到下层的 Headers 名称列表
		 */
		private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "authorization", "Authorization", TokenConstant.HEADER.toLowerCase(), TokenConstant.HEADER);
	}

}
