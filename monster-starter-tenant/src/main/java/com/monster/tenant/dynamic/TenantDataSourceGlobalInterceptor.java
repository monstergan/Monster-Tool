package com.monster.tenant.dynamic;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.monster.core.secure.utils.AuthUtil;
import com.monster.tenant.exception.TenantDataSourceException;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 租户数据源全局拦截器
 *
 * @author monster gan
 */
public class TenantDataSourceGlobalInterceptor implements MethodInterceptor {

    @Setter
    private TenantDataSourceHolder holder;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            String tenantId = AuthUtil.getTenantId();
            holder.handleDataSource(tenantId);
            DynamicDataSourceContextHolder.push(tenantId);
            return invocation.proceed();
        } catch (Exception exception) {
            throw new TenantDataSourceException(exception.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }
}
