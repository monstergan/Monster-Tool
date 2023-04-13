package com.monster.core.secure.handler;

import com.monster.core.cache.utils.CacheUtil;
import com.monster.core.secure.MonsterUser;
import com.monster.core.secure.utils.AuthUtil;
import com.monster.core.tool.utils.Func;
import com.monster.core.tool.utils.StringPool;
import com.monster.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.monster.core.cache.constant.CacheConstant.SYS_CACHE;
import static com.monster.core.secure.constant.PermissionConstant.permissionAllStatement;
import static com.monster.core.secure.constant.PermissionConstant.permissionStatement;


/**
 * 默认授权校验类
 */
@AllArgsConstructor
public class MonsterPermissionHandler implements IPermissionHandler {

    private static final String SCOPE_CACHE_CODE = "apiScope:code:";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean permissionAll() {
        HttpServletRequest request = WebUtil.getRequest();
        MonsterUser user = AuthUtil.getUser();
        if (request == null || user == null) {
            return false;
        }
        String uri = request.getRequestURI();
        List<String> paths = permissionPath(user.getRoleId());
        if (paths.size() == 0) {
            return false;
        }
        return paths.stream().anyMatch(uri::contains);
    }

    @Override
    public boolean hasPermission(String permission) {
        HttpServletRequest request = WebUtil.getRequest();
        MonsterUser user = AuthUtil.getUser();
        if (request == null || user == null) {
            return false;
        }
        List<String> codes = permissionCode(permission, user.getRoleId());
        return codes.size() != 0;
    }

    /**
     * 获取接口权限地址
     *
     * @param roleId 角色id
     * @return permissions
     */
    private List<String> permissionPath(String roleId) {
        List<String> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, roleId, List.class);
        if (permissions == null) {
            List<Long> roleIds = Func.toLongList(roleId);
            permissions = jdbcTemplate.queryForList(permissionAllStatement(roleIds.size()), roleIds.toArray(), String.class);
            CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, roleId, permissions);
        }
        return permissions;
    }

    /**
     * 获取接口权限信息
     *
     * @param permission 权限编号
     * @param roleId     角色id
     * @return permissions
     */
    private List<String> permissionCode(String permission, String roleId) {
        List<String> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, permission + StringPool.COLON + roleId, List.class);
        if (permissions == null) {
            List<Object> args = new ArrayList<>(Collections.singletonList(permission));
            List<Long> roleIds = Func.toLongList(roleId);
            args.addAll(roleIds);
            permissions = jdbcTemplate.queryForList(permissionStatement(roleIds.size()), args.toArray(), String.class);
            CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, permission + StringPool.COLON + roleId, permissions);
        }
        return permissions;
    }
}
