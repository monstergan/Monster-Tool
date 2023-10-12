package com.monster.core.mp.injector;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.monster.core.mp.injector.methods.InsertIgnore;
import com.monster.core.mp.injector.methods.Replace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * sql 注入器
 */
public class MonsterSqlInjector extends DefaultSqlInjector {

	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
		List<AbstractMethod> methodList = new ArrayList<>();
		methodList.add(new InsertIgnore());
		methodList.add(new Replace());
		methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
		methodList.addAll(super.getMethodList(mapperClass));
		return Collections.unmodifiableList(methodList);
	}
}
