package com.monster.core.secure;

import lombok.Data;

/**
 * Token 信息
 *
 * @author monster gan
 */
@Data
public class TokenInfo {

    /**
     * 令牌值
     */
    private String token;

    /**
     * 过期秒数
     */
    private int expire;

}
