package com.monster.tenant;

import com.monster.core.tool.utils.RandomType;
import com.monster.core.tool.utils.StringUtil;

/**
 * 租户id生成器
 *
 * @author monster gan
 */
public class MonsterTenantId implements TenantId {

    @Override
    public String generate() {
        return StringUtil.random(6, RandomType.INT);
    }

}
