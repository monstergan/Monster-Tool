package com.monster.core.log.utils;


import com.monster.core.tool.utils.StringPool;

import java.util.Properties;

/**
 * Elk配置工具
 *
 * @author monster gan
 */
public class ElkPropsUtil {

    /**
     * 获取elk服务地址
     *
     * @return 服务地址
     * @author monster gan
     */
    public static String getDestination() {
        Properties properties = System.getProperties();
        return properties.getProperty("monster.log.elk.destination", StringPool.EMPTY);
    }
}
