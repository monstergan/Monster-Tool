package com.monster.core.oss.model;

import com.aliyun.oss.model.AppendObjectRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 追加文件返回对象
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class AppendFileResult extends MonsterFile {
    /**
     * 追加位置
     */
    private Long pointNext;
    /**
     * 请求对象
     */
    private AppendObjectRequest appendObjectRequest;
}
