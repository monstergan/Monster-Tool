package com.monster.core.tool.api;

import java.io.Serializable;

/**
 * 业务代码接口
 * <p>
 * Create on 2023/3/12 22:21
 *
 * @author monster gan
 */
public interface IResultCode extends Serializable {

    /**
     * 获取消息
     *
     * @return 消息
     */
    String getMessage();

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getCode();

}
