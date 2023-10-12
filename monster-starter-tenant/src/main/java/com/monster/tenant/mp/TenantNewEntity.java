package com.monster.tenant.mp;

import com.monster.core.mp.base.BaseNewEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户基础实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantNewEntity extends BaseNewEntity {

    /**
     * 租户ID
     */
    private String tenantId;
}
