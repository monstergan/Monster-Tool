package com.monster.core.log.publisher;


import com.monster.core.log.annotation.ApiLog;
import com.monster.core.log.constant.EventConstant;
import com.monster.core.log.event.ApiLogEvent;
import com.monster.core.log.model.LogApi;
import com.monster.core.log.utils.LogAbstractUtil;
import com.kte.core.tool.constant.KteConstant;
import com.kte.core.tool.utils.SpringUtil;
import com.kte.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType(KteConstant.LOG_NORMAL_TYPE);
		logApi.setTitle(apiLog.value());
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);
		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
