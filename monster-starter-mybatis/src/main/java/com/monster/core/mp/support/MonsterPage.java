package com.monster.core.mp.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页
 *
 * @param <T> 实体类
 */
@Data
public class MonsterPage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * mybatis-plus分页模型转化
     *
     * @param page 分页实体类
     * @return KtePage<T>
     */
    public static <T> MonsterPage<T> of(IPage<T> page) {
        MonsterPage<T> monsterPage = new MonsterPage<>();
        monsterPage.setRecords(page.getRecords());
        monsterPage.setTotal(page.getTotal());
        monsterPage.setSize(page.getSize());
        monsterPage.setCurrent(page.getCurrent());
        return monsterPage;
    }
}
