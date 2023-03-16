package com.monster.core.context;


import com.monster.core.context.props.MonsterContextProperties;
import com.monster.core.tool.utils.StringUtil;
import com.monster.core.tool.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.function.Function;

import static com.monster.core.tool.constant.MonsterConstant.CONTEXT_KEY;

/**
 * 上下文，跨线程失效
 *
 * @author monster
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MonsterServletContext implements MonsterContext {
    private final MonsterContextProperties contextProperties;
    private final MonsterHttpHeadersGetter httpHeadersGetter;

    @Nullable
    @Override
    public String getRequestId() {
        return get(contextProperties.getHeaders().getRequestId());
    }

    @Nullable
    @Override
    public String getAccountId() {
        return get(contextProperties.getHeaders().getAccountId());
    }

    @Nullable
    @Override
    public String getTenantId() {
        return get(contextProperties.getHeaders().getTenantId());
    }

    @Nullable
    @Override
    public String get(String ctxKey) {
        HttpHeaders headers = ThreadLocalUtil.getIfAbsent(CONTEXT_KEY, httpHeadersGetter::get);
        if (headers == null || headers.isEmpty()) {
            return null;
        }
        return headers.getFirst(ctxKey);
    }

    @Nullable
    @Override
    public <T> T get(String ctxKey, Function<String, T> function) {
        String ctxValue = get(ctxKey);
        if (StringUtil.isBlank(ctxValue)) {
            return null;
        }
        return function.apply(ctxKey);
    }
}
