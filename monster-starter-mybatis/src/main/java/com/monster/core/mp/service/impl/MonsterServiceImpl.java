package com.monster.core.mp.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.monster.core.mp.base.BaseEntity;
import com.monster.core.mp.base.BaseServiceImpl;
import com.monster.core.mp.injector.MonsterSqlMethod;
import com.monster.core.mp.mapper.MonsterMapper;
import com.monster.core.mp.service.MonsterService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

/**
 * 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型
 *
 * @param <M> mapper
 * @param <T> entity
 */
@Validated
public class MonsterServiceImpl<M extends MonsterMapper<T>, T extends BaseEntity> extends BaseServiceImpl<M, T> implements MonsterService<T> {

    @Override
    public boolean saveIgnore(T entity) {
        return SqlHelper.retBool(baseMapper.insertIgnore(entity));
    }

    @Override
    public boolean saveReplace(T entity) {
        return SqlHelper.retBool(baseMapper.replace(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveIgnoreBatch(Collection<T> entityList, int batchSize) {
        return saveBatch(entityList, batchSize, MonsterSqlMethod.INSERT_IGNORE_ONE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveReplaceBatch(Collection<T> entityList, int batchSize) {
        return saveBatch(entityList, batchSize, MonsterSqlMethod.REPLACE_ONE);
    }

    private boolean saveBatch(Collection<T> entityList, int batchSize, MonsterSqlMethod sqlMethod) {
        String sqlStatement = kteSqlStatement(sqlMethod);
        executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
        return true;
    }

    /**
     * 获取 kteSqlStatement
     *
     * @param sqlMethod ignore
     * @return sql
     */
    protected String kteSqlStatement(MonsterSqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }
}
