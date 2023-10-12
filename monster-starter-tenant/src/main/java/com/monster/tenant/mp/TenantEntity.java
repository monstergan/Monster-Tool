package com.monster.tenant.mp;

import com.monster.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户基础实体类
 *
 * @author monster gan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    /**
     * 租户ID
     */
    private String tenantId;
}
