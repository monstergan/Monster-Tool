package com.monster.core.datascope.handler;

import com.monster.core.datascope.model.DataScopeModel;
import com.monster.core.secure.MonsterUser;


public interface DataScopeHandler {
	/**
	 * 获取过滤sql
	 *
	 * @param mapperId    数据查询类
	 * @param dataScope   数据权限类
	 * @param kteUser     当前用户信息
	 * @param originalSql 原始Sql
	 * @return sql
	 */
	String sqlCondition(String mapperId, DataScopeModel dataScope, MonsterUser kteUser, String originalSql);

}
