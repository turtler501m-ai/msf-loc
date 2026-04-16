package com.ktmmobile.msf.commons.common.datasource.common;

import javax.sql.DataSource;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    private static final List<HikariDataSource> CREATED_DATA_SOURCES = new CopyOnWriteArrayList<>();

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
        CREATED_DATA_SOURCES.add(dataSource);
        logHikariConfig(poolName, hikariConfig);
        return dataSource;
    }

    public static List<HikariDataSource> getCreatedDataSources() {
        return List.copyOf(CREATED_DATA_SOURCES);
    }

    private static void logHikariConfig(String poolName, HikariConfig hikariConfig) {
        int maximumPoolSize = hikariConfig.getMaximumPoolSize();
        int minimumIdle = hikariConfig.getMinimumIdle() == -1 ? maximumPoolSize : hikariConfig.getMinimumIdle();
        log.info("{} - minSize:{}, maxSize:{}", poolName, minimumIdle, maximumPoolSize);
        // log.info("{} - idleTimeout:{}", poolName, hikariConfig.getIdleTimeout());
    }
}
