package com.monster.tenant;

/**
 * 租户id生成器
 * @author monster
 */
public interface TenantId {

    /**
     * 生成自定义租户id
     *
     * @return tenantId
     */
    String generate();
}
