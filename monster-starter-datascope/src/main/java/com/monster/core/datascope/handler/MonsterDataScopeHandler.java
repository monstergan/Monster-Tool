package com.monster.core.datascope.handler;

import com.monster.core.datascope.enums.DataScopeEnum;
import com.monster.core.datascope.model.DataScopeModel;
import com.monster.core.secure.MonsterUser;
import com.monster.core.tool.constant.RoleConstant;
import com.monster.core.tool.utils.BeanUtil;
import com.monster.core.tool.utils.Func;
import com.monster.core.tool.utils.PlaceholderUtil;
import com.monster.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 默认数据权限规则
 */
@RequiredArgsConstructor
public class MonsterDataScopeHandler implements DataScopeHandler {

	private final ScopeModelHandler scopeModelHandler;

	@Override
	public String sqlCondition(String mapperId, DataScopeModel dataScope, MonsterUser MonsterUser, String originalSql) {

		//数据权限资源编号
		String code = dataScope.getResourceCode();

		//根据mapperId从数据库中获取对应模型
		DataScopeModel dataScopeDb = scopeModelHandler.getDataScopeByMapper(mapperId, MonsterUser.getRoleId());

		//mapperId配置未取到则从数据库中根据资源编号获取
		if (dataScopeDb == null && StringUtil.isNotBlank(code)) {
			dataScopeDb = scopeModelHandler.getDataScopeByCode(code);
		}

		//未从数据库找到对应配置则采用默认
		dataScope = (dataScopeDb != null) ? dataScopeDb : dataScope;

		//判断数据权限类型并组装对应Sql
		Integer scopeRule = Objects.requireNonNull(dataScope).getScopeType();
		DataScopeEnum scopeTypeEnum = DataScopeEnum.of(scopeRule);
		List<Long> ids = new ArrayList<>();
		String whereSql = "where scope.{} in ({})";
		if (DataScopeEnum.ALL == scopeTypeEnum || StringUtil.containsAny(MonsterUser.getRoleName(), RoleConstant.ADMINISTRATOR)) {
			return null;
		} else if (DataScopeEnum.CUSTOM == scopeTypeEnum) {
			whereSql = PlaceholderUtil.getDefaultResolver().resolveByMap(dataScope.getScopeValue(), BeanUtil.toMap(MonsterUser));
		} else if (DataScopeEnum.OWN == scopeTypeEnum) {
			ids.add(MonsterUser.getUserId());
		} else if (DataScopeEnum.OWN_DEPT == scopeTypeEnum) {
			ids.addAll(Func.toLongList(MonsterUser.getDeptId()));
		} else if (DataScopeEnum.OWN_DEPT_CHILD == scopeTypeEnum) {
			List<Long> deptIds = Func.toLongList(MonsterUser.getDeptId());
			ids.addAll(deptIds);
			deptIds.forEach(deptId -> {
				List<Long> deptIdList = scopeModelHandler.getDeptAncestors(deptId);
				ids.addAll(deptIdList);
			});
		}
		return StringUtil.format("select {} from ({}) scope " + whereSql, Func.toStr(dataScope.getScopeField(), "*"), originalSql, dataScope.getScopeColumn(), StringUtil.join(ids));
	}
}
