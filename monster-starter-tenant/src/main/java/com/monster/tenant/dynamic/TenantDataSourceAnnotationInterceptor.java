package com.monster.tenant.dynamic;

import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.monster.core.secure.utils.AuthUtil;
import com.monster.tenant.exception.TenantDataSourceException;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 租户数据源切换拦截器
 *
 * @author monster gan
 */
public class TenantDataSourceAnnotationInterceptor extends DynamicDataSourceAnnotationInterceptor {

    @Setter
    private TenantDataSourceHolder holder;

    public TenantDataSourceAnnotationInterceptor(Boolean allowedPublicOnly, DsProcessor dsProcessor) {
        super(allowedPublicOnly, dsProcessor);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            holder.handleDataSource(AuthUtil.getTenantId());
            return super.invoke(invocation);
        } catch (Exception exception) {
            throw new TenantDataSourceException(exception.getMessage());
        }
    }
}
