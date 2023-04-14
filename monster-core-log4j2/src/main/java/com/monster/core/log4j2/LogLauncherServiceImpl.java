package com.monster.core.log4j2;


import com.monster.core.auto.service.AutoService;
import com.monster.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 日志启动器
 * <p>
 * @author monster gan
 */
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {

    @Override
    public void launcher(SpringApplicationBuilder builder, String appName, String profile,
                         boolean isLocalDev) {
        System.setProperty("logging.config", String.format("classpath:log/log4j2_%s.xml", profile));
        // 非本地 将 全部的 System.err 和 System.out 替换为log
        if (!isLocalDev) {
            System.setOut(LogPrintStream.out());
            System.setErr(LogPrintStream.err());
        }
    }


}
