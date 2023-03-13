package com.monster.core.launch.constant;

/**
 * 系统常量
 * <p>
 * Create on 2023/3/13 13:37
 *
 * @author monster gan
 */
public interface AppConstant {
    /**
     * 应用版本
     */
    String APPLICATION_VERSION = "1.0.1";

    /**
     * 基础包
     */
    String BASE_PACKAGES = "com.kte";

    /**
     * 应用名前缀
     */
    String APPLICATION_NAME_PREFIX = "kte-";
    /**
     * 网关模块名称
     */
    String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
    /**
     * 授权模块名称
     */
    String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";
    /**
     * 监控模块名称
     */
    String APPLICATION_ADMIN_NAME = APPLICATION_NAME_PREFIX + "admin";
    /**
     * 报表系统名称
     */
    String APPLICATION_REPORT_NAME = APPLICATION_NAME_PREFIX + "report";
    /**
     * 集群监控名称
     */
    String APPLICATION_TURBINE_NAME = APPLICATION_NAME_PREFIX + "turbine";
    /**
     * 链路追踪名称
     */
    String APPLICATION_ZIPKIN_NAME = APPLICATION_NAME_PREFIX + "zipkin";
    /**
     * websocket名称
     */
    String APPLICATION_WEBSOCKET_NAME = APPLICATION_NAME_PREFIX + "websocket";
    /**
     * 首页模块名称
     */
    String APPLICATION_DESK_NAME = APPLICATION_NAME_PREFIX + "desk";
    /**
     * 系统模块名称
     */
    String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";
    /**
     * 用户模块名称
     */
    String APPLICATION_USER_NAME = APPLICATION_NAME_PREFIX + "user";
    /**
     * 日志模块名称
     */
    String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";
    /**
     * 开发模块名称
     */
    String APPLICATION_DEVELOP_NAME = APPLICATION_NAME_PREFIX + "develop";
    /**
     * 流程设计器模块名称
     */
    String APPLICATION_FLOWDESIGN_NAME = APPLICATION_NAME_PREFIX + "flowdesign";
    /**
     * 工作流模块名称
     */
    String APPLICATION_FLOW_NAME = APPLICATION_NAME_PREFIX + "flow";
    /**
     * 资源模块名称
     */
    String APPLICATION_RESOURCE_NAME = APPLICATION_NAME_PREFIX + "resource";
    /**
     * 接口文档模块名称
     */
    String APPLICATION_SWAGGER_NAME = APPLICATION_NAME_PREFIX + "swagger";
    /**
     * 测试模块名称
     */
    String APPLICATION_TEST_NAME = APPLICATION_NAME_PREFIX + "test";
    /**
     * 演示模块名称
     */
    String APPLICATION_DEMO_NAME = APPLICATION_NAME_PREFIX + "demo";

    /**
     * 用户模块名称
     */
    String APPLICATION_PRODUCT_NAME = APPLICATION_NAME_PREFIX + "product";

    /**
     * 用户模块名称
     */
    String APPLICATION_MESSAGE_NAME = APPLICATION_NAME_PREFIX + "message";

    /**
     * 刊登模块名称
     */
    String APPLICATION_PUBLISH_NAME = APPLICATION_NAME_PREFIX + "publish";
    /**
     * 采购模块名称
     */
    String APPLICATION_PURCHASE_NAME = APPLICATION_NAME_PREFIX + "purchase";
    /**
     * 仓储模块名称
     */
    String APPLICATION_WAREHOUSE_NAME = APPLICATION_NAME_PREFIX + "warehouse";
    /**
     * MQ模块名称
     */
    String APPLICATION_RABBIT_MQ_NAME = APPLICATION_NAME_PREFIX + "rabbit-mq";

    /**
     * 刊登模块定时任务
     */
    String APPLICATION_PUBLISH_JOB = APPLICATION_NAME_PREFIX + "publish-job";

    /**
     * 产品模块定时任务
     */
    String APPLICATION_PRODUCT_JOB = APPLICATION_NAME_PREFIX + "product-job";

    /**
     * 订单模块名称
     */
    String APPLICATION_ORDER_NAME = APPLICATION_NAME_PREFIX + "order";
    /**
     * 订单模块名称
     */
    String APPLICATION_ORDER_TRANSFER_NAME = APPLICATION_NAME_PREFIX + "order-transfer";

    /**
     * 订单模块提供给Product的名称
     */
//	String APPLICATION_ORDER_PRO_NAME= APPLICATION_ORDER_NAME+ "-product";
    /**
     * 单证模块名称
     */
    String APPLICATION_DOCUMENT_NAME = APPLICATION_NAME_PREFIX + "document";

    String APPLICATION_ORDER_TASK_NAME = APPLICATION_NAME_PREFIX + "order-task";

    /**
     * 开发环境
     */
    String DEV_CODE = "dev";
    /**
     * 生产环境
     */
    String PROD_CODE = "prod";
    /**
     * 测试环境
     */
    String TEST_CODE = "test";

    /**
     * 伪生产环境
     */
    String UAT_CODE = "uat";

    /**
     * 自定义环境
     */
    String CUSTOM_CODE = "custom";

    /**
     * 代码部署于 linux 上，工作默认为 mac 和 Windows
     */
    String OS_NAME_LINUX = "LINUX";

}
