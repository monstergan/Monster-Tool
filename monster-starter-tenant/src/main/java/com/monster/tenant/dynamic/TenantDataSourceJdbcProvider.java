package com.monster.tenant.dynamic;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.monster.core.tool.utils.StringUtil;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static com.monster.tenant.constant.TenantBaseConstant.TENANT_DATASOURCE_GROUP_STATEMENT;

/**
 * 租户数据源初始加载
 *
 * @author monster gan
 */
public class TenantDataSourceJdbcProvider extends AbstractJdbcDataSourceProvider {

    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;
    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    public TenantDataSourceJdbcProvider(DynamicDataSourceProperties dynamicDataSourceProperties, String driverClassName, String url, String username, String password) {
        super(driverClassName, url, username, password);
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        // 构建数据源集合
        Map<String, DataSourceProperty> map = new HashMap<>(16);
        // 构建主数据源
        DataSourceProperty masterProperty = new DataSourceProperty();
        masterProperty.setDriverClassName(driverClassName);
        masterProperty.setUrl(url);
        masterProperty.setUsername(username);
        masterProperty.setPassword(password);
        masterProperty.setDruid(dynamicDataSourceProperties.getDruid());
        map.put(dynamicDataSourceProperties.getPrimary(), masterProperty);
        // 构建yml数据源
        Map<String, DataSourceProperty> datasource = dynamicDataSourceProperties.getDatasource();
        if (!CollectionUtils.isEmpty(datasource)) {
            map.putAll(datasource);
        }
        // 构建动态数据源
        ResultSet rs = statement.executeQuery(TENANT_DATASOURCE_GROUP_STATEMENT);
        while (rs.next()) {
            String tenantId = rs.getString("tenantId");
            String driver = rs.getString("driverClass");
            String url = rs.getString("url");
            String userName = rs.getString("username");
            String passWord = rs.getString("password");
            if (StringUtil.isNoneBlank(tenantId, driver, url, userName, passWord)) {
                DataSourceProperty jdbcProperty = new DataSourceProperty();
                jdbcProperty.setDriverClassName(driver);
                jdbcProperty.setUrl(url);
                jdbcProperty.setUsername(userName);
                jdbcProperty.setPassword(passWord);
                jdbcProperty.setDruid(dynamicDataSourceProperties.getDruid());
                map.put(tenantId, jdbcProperty);
            }
        }
        return map;
    }
}
