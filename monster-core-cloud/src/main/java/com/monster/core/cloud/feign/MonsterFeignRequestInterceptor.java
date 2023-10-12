package com.monster.core.cloud.feign;

import com.monster.core.tool.constant.MonsterConstant;
import com.monster.core.tool.utils.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

/**
 * feign 传递Request header
 * Create by monster gan on 2023/3/13 14:45
 */
public class MonsterFeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpHeaders headers = ThreadLocalUtil.get(MonsterConstant.CONTEXT_KEY);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((key, values) ->
                    values.forEach(value -> requestTemplate.header(key, value))
            );
        }
    }
}
