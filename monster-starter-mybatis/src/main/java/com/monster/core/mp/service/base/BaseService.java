package com.monster.core.mp.service.base;

import com.monster.core.mp.base.BaseNewEntity;
import com.monster.core.secure.MonsterUser;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Base设置接口
 */
@Service
public class BaseService {

    /**
     * 设置baseEntity值
     *
     * @param param       实体
     * @param MonsterUser 用户
     */
    public static <T extends BaseNewEntity> void setBaseParam(T param, MonsterUser MonsterUser) {
        Date now = new Date();
        param.setCreateTime(now);
        param.setCreateUser(MonsterUser.getUserId());
        param.setCreateUserName(MonsterUser.getNickName());
        setBaseUpdateParam(param, MonsterUser, now);
    }

    /**
     * 设置baseEntity update属性
     *
     * @param param       实体
     * @param MonsterUser 用户
     * @param now         当前时间
     */
    public static <T extends BaseNewEntity> void setBaseUpdateParam(T param, MonsterUser MonsterUser, Date now) {
        param.setUpdateTime(now);
        param.setUpdateUser(MonsterUser.getUserId());
        param.setUpdateUserName(MonsterUser.getNickName());
    }
}
