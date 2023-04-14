package com.monster.core.log.feign;

import com.monster.core.log.model.LogApi;
import com.monster.core.log.model.LogError;
import com.monster.core.log.model.LogUsual;
import com.monster.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 日志fallback
 */
@Slf4j
@Component
public class LogClientFallback implements ILogClient {

	@Override
	public R<Boolean> saveUsualLog(LogUsual log) {
		return R.fail("usual log send fail");
	}

	@Override
	public R<Boolean> saveApiLog(LogApi log) {
		return R.fail("api log send fail");
	}

	@Override
	public R<Boolean> saveErrorLog(LogError log) {
		return R.fail("error log send fail");
	}
}
