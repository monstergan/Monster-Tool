package com.monster.core.mongo.config;

import com.monster.core.mongo.converter.DBObjectToJsonNodeConverter;
import com.monster.core.mongo.converter.JsonNodeToDocumentConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * mongo 配置
 */
@Configuration(proxyBeanMethods = false)
public class MongoConfiguration {

	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>(2);
		converters.add(DBObjectToJsonNodeConverter.INSTANCE);
		converters.add(JsonNodeToDocumentConverter.INSTANCE);
		return new MongoCustomConversions(converters);
	}
}
