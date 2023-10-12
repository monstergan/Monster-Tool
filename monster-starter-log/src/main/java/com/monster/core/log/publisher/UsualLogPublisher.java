package com.monster.core.log.publisher;


import com.monster.core.log.constant.EventConstant;
import com.monster.core.log.event.UsualLogEvent;
import com.monster.core.log.model.LogUsual;
import com.monster.core.log.utils.LogAbstractUtil;
import com.monster.core.tool.utils.SpringUtil;
import com.monster.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志信息事件发送
 */
public class UsualLogPublisher {

	public static void publishEvent(String level, String id, String data) {
		HttpServletRequest request = WebUtil.getRequest();
		LogUsual logUsual = new LogUsual();
		logUsual.setLogLevel(level);
		logUsual.setLogId(id);
		logUsual.setLogData(data);
		Thread thread = Thread.currentThread();
		StackTraceElement[] trace = thread.getStackTrace();
		if (trace.length > 3) {
			logUsual.setMethodClass(trace[3].getClassName());
			logUsual.setMethodName(trace[3].getMethodName());
		}
		LogAbstractUtil.addRequestInfoToLog(request, logUsual);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logUsual);
		SpringUtil.publishEvent(new UsualLogEvent(event));
	}

}
