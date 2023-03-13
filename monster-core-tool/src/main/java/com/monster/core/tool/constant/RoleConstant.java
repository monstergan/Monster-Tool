package com.monster.core.tool.constant;

/**
 * 系统默认角色
 * <p>
 * Create on 2023/3/13 10:46
 *
 * @author monster gan
 */
public class RoleConstant {

    public static final String ADMINISTRATOR = "administrator";

    public static final String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ADMINISTRATOR + "')";

    public static final String ADMIN = "admin";

    public static final String HR = "行政人事经理";

    public static final String HR2 = "行政专员";

    public static final String HR3 = "行政助理";

    public static final String HAS_ROLE_ADMIN = "hasAnyRole('" + ADMINISTRATOR + "', '" + ADMIN + "')";

    public static final String USER = "user";

    public static final String HAS_ROLE_USER = "hasRole('" + USER + "')";

    public static final String TEST = "test";

    public static final String HAS_ROLE_TEST = "hasRole('" + TEST + "')";

    public static final String HAS_ROLE_ADMIN_HR = "hasAnyRole('" + ADMINISTRATOR + "', '" + ADMIN + "','" + HR + "','" + HR2 + "','" + HR3 + "')";

}
