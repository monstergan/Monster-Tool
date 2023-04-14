package com.monster.core.cloud.feign;

import com.kte.core.tool.constant.KteConstant;
import com.kte.core.tool.utils.ThreadLocalUtil;
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
        HttpHeaders headers = ThreadLocalUtil.get(KteConstant.CONTEXT_KEY);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((key, values) ->
                    values.forEach(value -> requestTemplate.header(key, value))
            );
        }
    }
}
