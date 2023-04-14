package com.monster.core.secure;

import com.monster.core.tool.support.Kv;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author monster gan
 */
@Data
public class MonsterUser implements Serializable {

    /**
     * 客户端id
     */
    @ApiModelProperty(hidden = true)
    private String clientId;

    /**
     * 用户id
     */
    @ApiModelProperty(hidden = true)
    private Long userId;
    /**
     * 账号
     */
    @ApiModelProperty(hidden = true)
    private String account;
    /**
     * 用户名
     */
    @ApiModelProperty(hidden = true)
    private String userName;
    /**
     * 昵称
     */
    @ApiModelProperty(hidden = true)
    private String nickName;
    /**
     * 真名
     */
    @ApiModelProperty(hidden = true)
    private String realName;
    /**
     * 租户ID
     */
    @ApiModelProperty(hidden = true)
    private String tenantId;
    /**
     * 第三方认证ID
     */
    @ApiModelProperty(hidden = true)
    private String oauthId;
    /**
     * 部门id
     */
    @ApiModelProperty(hidden = true)
    private String deptId;
    /**
     * 岗位id
     */
    @ApiModelProperty(hidden = true)
    private String postId;
    /**
     * 角色id
     */
    @ApiModelProperty(hidden = true)
    private String roleId;
    /**
     * 角色名
     */
    @ApiModelProperty(hidden = true)
    private String roleName;
    /**
     * 用户详情
     */
    @ApiModelProperty(hidden = true)
    private Kv detail;

}
