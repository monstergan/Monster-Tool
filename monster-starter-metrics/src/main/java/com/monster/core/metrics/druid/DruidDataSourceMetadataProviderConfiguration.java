package com.monster.core.metrics.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid 数据源元数据提供者配置
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DruidDataSource.class)
public class DruidDataSourceMetadataProviderConfiguration {

    @Bean
    public DataSourcePoolMetadataProvider druidDataSourceMetadataProvider() {
        return (dataSource) -> {
            DruidDataSource druidDataSource = DataSourceUnwrapper.unwrap(dataSource, DruidDataSource.class);
            if (druidDataSource != null) {
                return new DruidDataSourcePoolMetadata(druidDataSource);
            }
            return null;
        };
    }

}
