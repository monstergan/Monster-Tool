package com.monster.core.mp.plugins;

import com.monster.core.mp.intercept.QueryInterceptor;
import com.monster.core.tool.utils.ObjectUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 查询拦截器执行器
 */
public class QueryInterceptorExecutor {

    /**
     * 执行查询拦截器
     */
    static void exec(QueryInterceptor[] interceptors, Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws Throwable {
        if (ObjectUtil.isEmpty(interceptors)) {
            return;
        }
        for (QueryInterceptor interceptor : interceptors) {
            interceptor.intercept(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }
    }

}
