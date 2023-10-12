package com.monster.tenant.aspect;

import com.monster.tenant.MonsterTenantHolder;
import com.monster.tenant.annotation.TenantIgnore;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 自定义租户切面
 *
 * @author monster gan
 */
@Slf4j
@Aspect
public class MonsterTenantAspect {

    @Around("@annotation(tenantIgnore)")
    public Object around(ProceedingJoinPoint point, TenantIgnore tenantIgnore) throws Throwable {
        try {
            //开启忽略
            MonsterTenantHolder.setIgnore(Boolean.TRUE);
            //执行方法
            return point.proceed();
        } finally {
            //关闭忽略
            MonsterTenantHolder.clear();
        }
    }
}
