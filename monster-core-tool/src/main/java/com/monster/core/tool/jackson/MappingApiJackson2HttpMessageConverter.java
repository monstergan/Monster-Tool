package com.monster.core.tool.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * MappingApiJackson2HttpMessageConverter
 * <p>
 * Create on 2023/3/13 11:13
 *
 * @author monster gan
 */
public class MappingApiJackson2HttpMessageConverter extends AbstractReadWriteJackson2HttpMessageConverter {

	@Nullable
	private String jsonPrefix;

	public MappingApiJackson2HttpMessageConverter(ObjectMapper objectMapper, MonsterJacksonProperties properties) {
		super(objectMapper, initWriteObjectMapper(objectMapper, properties), initMediaType(properties));
	}

	private static List<MediaType> initMediaType(MonsterJacksonProperties properties) {
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(new MediaType("application", "*+json"));
		// 支持 text 文本，用于报文签名
		if (Boolean.TRUE.equals(properties.getSupportTextPlain())) {
			supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		}
		return supportedMediaTypes;
	}

	private static ObjectMapper initWriteObjectMapper(ObjectMapper readObjectMapper, MonsterJacksonProperties properties) {
		// 拷贝 readObjectMapper
		ObjectMapper writeObjectMapper = readObjectMapper.copy();
		// 大数字 转 字符串
		if (Boolean.TRUE.equals(properties.getBigNumToString())) {
			writeObjectMapper.registerModules(MonsterNumberModule.INSTANCE);
		}
		// null 处理
		if (Boolean.TRUE.equals(properties.getNullToEmpty())) {
			writeObjectMapper.setSerializerFactory(writeObjectMapper.getSerializerFactory().withSerializerModifier(new MonsterBeanSerializerModifier()));
			writeObjectMapper.getSerializerProvider().setNullValueSerializer(MonsterBeanSerializerModifier.NullJsonSerializers.STRING_JSON_SERIALIZER);
		}
		return writeObjectMapper;
	}

	@Override
	protected void writeInternal(@NonNull Object object, @Nullable Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		// 如果是字符串，直接写出
		if (object instanceof String) {
			Charset defaultCharset = this.getDefaultCharset();
			Charset charset = defaultCharset == null ? StandardCharsets.UTF_8 : defaultCharset;
			StreamUtils.copy((String) object, charset, outputMessage.getBody());
		} else {
			super.writeInternal(object, type, outputMessage);
		}
	}

	/**
	 * Specify a custom prefix to use for this view's JSON output.
	 * Default is none.
	 *
	 * @param jsonPrefix jsonPrefix
	 * @see #setPrefixJson
	 */
	public void setJsonPrefix(@Nullable String jsonPrefix) {
		this.jsonPrefix = jsonPrefix;
	}

	/**
	 * Indicate whether the JSON output by this view should be prefixed with ")]}', ". Default is false.
	 * <p>Prefixing the JSON string in this manner is used to help prevent JSON Hijacking.
	 * The prefix renders the string syntactically invalid as a script so that it cannot be hijacked.
	 * This prefix should be stripped before parsing the string as JSON.
	 *
	 * @param prefixJson prefixJson
	 * @see #setJsonPrefix
	 */
	public void setPrefixJson(boolean prefixJson) {
		this.jsonPrefix = (prefixJson ? ")]}', " : null);
	}

	@Override
	protected void writePrefix(@NonNull JsonGenerator generator, @NonNull Object object) throws IOException {
		if (this.jsonPrefix != null) {
			generator.writeRaw(this.jsonPrefix);
		}
	}
}
