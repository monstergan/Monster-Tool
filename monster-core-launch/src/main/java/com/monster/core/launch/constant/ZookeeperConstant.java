package com.monster.core.launch.constant;

/**
 * zookeeper配置
 * <p>
 * Create on 2023/3/13 13:42
 *
 * @author monster gan
 */
public interface ZookeeperConstant {

    /**
     * zookeeper id
     */
    String ZOOKEEPER_ID = "zk";

    /**
     * zookeeper connect string
     */
    String ZOOKEEPER_CONNECT_STRING = "127.0.0.1:2181";

    /**
     * zookeeper address
     */
    String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_CONNECT_STRING;

    /**
     * zookeeper root
     */
    String ZOOKEEPER_ROOT = "/kte-services";

}
