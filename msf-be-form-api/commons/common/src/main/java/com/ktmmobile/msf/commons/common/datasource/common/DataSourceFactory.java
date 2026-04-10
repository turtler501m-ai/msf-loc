package com.ktmmobile.msf.commons.common.datasource.common;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceItemType;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourceItemProperties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataSourceFactory {

    public static DataSource create(
        String groupName,
        DataSourceItemType dataSourceItem,
        DataSourceItemProperties itemProperties,
        HikariConfig hikariConfig
    ) {
        Assert.notNull(itemProperties, "itemProperties is required");
        Assert.notNull(hikariConfig, "hikariConfig is required");

        String poolName = groupName + dataSourceItem.getRoleName() + "Pool";
        hikariConfig.setJdbcUrl(itemProperties.url());
        hikariConfig.setUsername(itemProperties.username());
        hikariConfig.setPassword(itemProperties.password());
        hikariConfig.setReadOnly(itemProperties.readOnly());
        hikariConfig.setPoolName(poolName);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        logHikariConfig(poolName, hikariConfig);
        return dataSource;
    }

    private static void logHikariConfig(String poolName, HikariConfig hikariConfig) {
        int maximumPoolSize = hikariConfig.getMaximumPoolSize();
        int minimumIdle = hikariConfig.getMinimumIdle() == -1 ? maximumPoolSize : hikariConfig.getMinimumIdle();
        log.info("{} - minSize:{}, maxSize:{}", poolName, minimumIdle, maximumPoolSize);
        // log.info("{} - idleTimeout:{}", poolName, hikariConfig.getIdleTimeout());
    }
}
