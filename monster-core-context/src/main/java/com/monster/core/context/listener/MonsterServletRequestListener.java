package com.monster.core.context.listener;


import com.monster.core.context.MonsterHttpHeadersGetter;
import com.monster.core.context.props.MonsterContextProperties;
import com.monster.core.tool.constant.MonsterConstant;
import com.monster.core.tool.utils.StringUtil;
import com.monster.core.tool.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求监听器
 *
 * @author monster
 */
@RequiredArgsConstructor
public class MonsterServletRequestListener implements ServletRequestListener {
    private final MonsterContextProperties contextProperties;
    private final MonsterHttpHeadersGetter httpHeadersGetter;

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        // MDC 获取透传的 变量
        MonsterContextProperties.Headers headers = contextProperties.getHeaders();
        String requestId = request.getHeader(headers.getRequestId());
        if (StringUtil.isNotBlank(requestId)) {
            MDC.put(MonsterConstant.MDC_REQUEST_ID_KEY, requestId);
        }
        String accountId = request.getHeader(headers.getAccountId());
        if (StringUtil.isNotBlank(accountId)) {
            MDC.put(MonsterConstant.MDC_ACCOUNT_ID_KEY, accountId);
        }
        String tenantId = request.getHeader(headers.getTenantId());
        if (StringUtil.isNotBlank(tenantId)) {
            MDC.put(MonsterConstant.MDC_TENANT_ID_KEY, tenantId);
        }
        // 处理 context，直接传递 request，因为 spring 中的尚未初始化完成
        HttpHeaders httpHeaders = httpHeadersGetter.get(request);
        ThreadLocalUtil.put(MonsterConstant.CONTEXT_KEY, httpHeaders);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        // 会话销毁时，清除上下文
        ThreadLocalUtil.clear();
        // 会话销毁时，清除 mdc
        MDC.remove(MonsterConstant.MDC_REQUEST_ID_KEY);
        MDC.remove(MonsterConstant.MDC_ACCOUNT_ID_KEY);
        MDC.remove(MonsterConstant.MDC_TENANT_ID_KEY);
    }
}
