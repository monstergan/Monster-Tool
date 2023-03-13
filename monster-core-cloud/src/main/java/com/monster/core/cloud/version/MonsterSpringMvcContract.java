package com.monster.core.cloud.version;

import com.monster.core.cloud.annotation.ApiVersion;
import com.monster.core.cloud.annotation.UrlVersion;
import com.kte.core.tool.utils.StringPool;
import com.kte.core.tool.utils.StringUtil;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Create by monster gan on 2023/3/13 14:57
 */
public class MonsterSpringMvcContract extends SpringMvcContract {

    public MonsterSpringMvcContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService) {
        super(annotatedParameterProcessors, conversionService);
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        if (methodAnnotation instanceof RequestMapping || methodAnnotation.annotationType().isAnnotationPresent(RequestMapping.class)) {
            Class<?> targetType = method.getDeclaringClass();
            // url 上的版本，优先获取方法上的版本
            UrlVersion urlVersion = AnnotatedElementUtils.findMergedAnnotation(method, UrlVersion.class);
            // 再次尝试类上的版本
            if (urlVersion == null || StringUtil.isBlank(urlVersion.value())) {
                urlVersion = AnnotatedElementUtils.findMergedAnnotation(targetType, UrlVersion.class);
            }
            if (urlVersion != null && StringUtil.isNotBlank(urlVersion.value())) {
                String versionUrl = "/" + urlVersion.value();
                data.template().uri(versionUrl);
            }

            // 注意：在父类之前 添加 url版本，在父类之后，处理 Media Types 版本
            super.processAnnotationOnMethod(data, methodAnnotation, method);

            // 处理 Media Types 版本信息
            ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
            // 再次尝试类上的版本
            if (apiVersion == null || StringUtil.isBlank(apiVersion.value())) {
                apiVersion = AnnotatedElementUtils.findMergedAnnotation(targetType, ApiVersion.class);
            }
            if (apiVersion != null && StringUtil.isNotBlank(apiVersion.value())) {
                MonsterMediaType monsterMediaType = new MonsterMediaType(apiVersion.value());
                data.template().header(HttpHeaders.ACCEPT, monsterMediaType.toString());
            }
        }
    }

    /**
     * 参考：https://gist.github.com/rmfish/0ed59a9af6c05157be2a60c9acea2a10
     *
     * @param annotations 注解
     * @param paramIndex  参数索引
     * @return 是否 http 注解
     */
    @Override
    protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
        boolean httpAnnotation = super.processAnnotationsOnParameter(data, annotations, paramIndex);
        // 在 springMvc 中如果是 Get 请求且参数中是对象 没有声明为@RequestBody 则默认为 Param
        if (!httpAnnotation && StringPool.GET.equals(data.template().method().toUpperCase())) {
            for (Annotation parameterAnnotation : annotations) {
                if (!(parameterAnnotation instanceof RequestBody)) {
                    return false;
                }
            }
            data.queryMapIndex(paramIndex);
            return true;
        }
        return httpAnnotation;
    }
}
