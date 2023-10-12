package com.monster.core.sms;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.monster.core.redis.cache.RedisUtils;
import com.monster.core.sms.model.SmsCode;
import com.monster.core.sms.model.SmsData;
import com.monster.core.sms.model.SmsResponse;
import com.monster.core.sms.props.SmsProperties;
import com.monster.core.tool.utils.Func;
import com.monster.core.tool.utils.StringPool;
import com.monster.core.tool.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;

/**
 * 腾讯云短信发送类
 */
@AllArgsConstructor
public class TencentSmsTemplate implements SmsTemplate {

	private static final int SUCCESS = 0;
	private static final String NATION_CODE = "86";

	private final SmsProperties smsProperties;
	private final SmsMultiSender smsSender;
	private final RedisUtils redisUtils;


	@Override
	public SmsResponse sendMessage(SmsData smsData, Collection<String> phones) {
		try {
			Collection<String> values = smsData.getParams().values();
			String[] params = StringUtil.toStringArray(values);
			SmsMultiSenderResult senderResult = smsSender.sendWithParam(
				NATION_CODE,
				StringUtil.toStringArray(phones),
				Func.toInt(smsProperties.getTemplateId()),
				params,
				smsProperties.getSignName(),
				StringPool.EMPTY, StringPool.EMPTY
			);
			return new SmsResponse(senderResult.result == SUCCESS, senderResult.result, senderResult.toString());
		} catch (HTTPException | IOException e) {
			e.printStackTrace();
			return new SmsResponse(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public SmsCode sendValidate(SmsData smsData, String phone) {
		SmsCode smsCode = new SmsCode();
		boolean temp = sendSingle(smsData, phone);
		if (temp && StringUtil.isNotBlank(smsData.getKey())) {
			String id = StringUtil.randomUUID();
			String value = smsData.getParams().get(smsData.getKey());
			redisUtils.setEx(cacheKey(phone, id), value, Duration.ofMinutes(30));
			smsCode.setId(id).setValue(value);
		} else {
			smsCode.setSuccess(Boolean.FALSE);
		}
		return smsCode;
	}

	@Override
	public boolean validateMessage(SmsCode smsCode) {
		String id = smsCode.getId();
		String value = smsCode.getValue();
		String phone = smsCode.getPhone();
		String cache = redisUtils.get(cacheKey(phone, id));
		if (StringUtil.isNotBlank(value) && StringUtil.equalsIgnoreCase(cache, value)) {
			redisUtils.del(cacheKey(phone, id));
			return true;
		}
		return false;
	}

}
