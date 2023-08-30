package com.monster.core.log.event;

import com.monster.core.launch.props.MonsterProperties;
import com.monster.core.launch.server.ServerInfo;
import com.monster.core.log.constant.EventConstant;
import com.monster.core.log.feign.ILogClient;
import com.monster.core.log.model.LogError;
import com.monster.core.log.utils.LogAbstractUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听错误日志事件
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {

    private final ILogClient logService;
    private final ServerInfo serverInfo;
    private final MonsterProperties monsterProperties;

    @Async
    @Order
    @EventListener(ErrorLogEvent.class)
    public void saveErrorLog(ErrorLogEvent event) {
        Map<String, Object> source = (Map<String, Object>) event.getSource();
        LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
        LogAbstractUtil.addOtherInfoToLog(logError, monsterProperties, serverInfo);
        logService.saveErrorLog(logError);
    }

}
