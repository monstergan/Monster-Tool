package com.monster.core.swagger;

import com.monster.core.auto.service.AutoService;
import com.monster.core.launch.constant.AppConstant;
import com.monster.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Properties;

/**
 * 初始化Swagger配置
 */
@AutoService(LauncherService.class)
public class SwaggerLauncherServiceImpl implements LauncherService {
    @Override
    public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
        Properties props = System.getProperties();
        if (profile.equals(AppConstant.PROD_CODE)) {
            props.setProperty("knife4j.production", "true");
        }
        props.setProperty("knife4j.enable", "true");
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
