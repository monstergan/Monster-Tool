package com.monster.core.log.utils;

import com.monster.core.launch.props.MonsterProperties;
import com.monster.core.launch.server.ServerInfo;
import com.monster.core.log.model.LogAbstract;
import com.monster.core.secure.utils.AuthUtil;
import com.monster.core.tool.constant.MonsterConstant;
import com.monster.core.tool.utils.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Log 相关工具
 */
public class LogAbstractUtil {
    public LogAbstractUtil() {
    }

    /**
     * 向log中添加补齐request的信息
     *
     * @param request     请求
     * @param logAbstract 日志基础类
     */
    public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
        if (ObjectUtil.isNotEmpty(request)) {
            logAbstract.setTenantId(Func.toStrWithEmpty(AuthUtil.getTenantId(), MonsterConstant.ADMIN_TENANT_ID));
            logAbstract.setRemoteIp(WebUtil.getIP(request));
            logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
            logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
            logAbstract.setMethod(request.getMethod());
            logAbstract.setParams(WebUtil.getRequestContent(request));
            logAbstract.setCreateBy(AuthUtil.getUserAccount(request));
        }
    }

    /**
     * 向log中添加补齐其他的信息（eg：kte、server等）
     *
     * @param logAbstract   日志基础类
     * @param monsterProperties 配置信息
     * @param serverInfo    服务信息
     */
    public static void addOtherInfoToLog(LogAbstract logAbstract, MonsterProperties monsterProperties, ServerInfo serverInfo) {
        logAbstract.setServiceId(monsterProperties.getName());
        logAbstract.setServerHost(serverInfo.getHostName());
        logAbstract.setServerIp(serverInfo.getIpWithPort());
        logAbstract.setEnv(monsterProperties.getEnv());
        logAbstract.setCreateTime(DateUtil.now());
        if (logAbstract.getParams() == null) {
            logAbstract.setParams(StringPool.EMPTY);
        }
    }
}
