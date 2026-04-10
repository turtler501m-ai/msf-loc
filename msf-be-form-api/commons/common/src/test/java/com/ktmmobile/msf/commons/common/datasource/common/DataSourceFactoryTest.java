package com.ktmmobile.msf.commons.common.datasource.common;

import java.util.Map;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceItemType;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourceItemProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataSourceFactoryTest {

    private static final String GROUP_NAME = "smartform";
    private static final String URL = "jdbc:postgresql://localhost:5432/smartform";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pw";

    @Test
    void createBuildsHikariDataSourceWithMergedConfigAndItemProperties() {
        Binder binder = binder(
            entry("spring.datasource.hikari.maximum-pool-size", "15"),
            entry("spring.datasource.hikari.minimum-idle", "10"),
            entry("spring.datasource.hikari.initialization-fail-timeout", "-1"),
            entry("spring.datasource.groups.smartform.writer.hikari.maximum-pool-size", "20")
        );
        HikariConfig hikariConfig = HikariConfigBinder.bind(
            binder,
            "spring.datasource.hikari",
            "spring.datasource.groups.smartform.writer.hikari"
        );

        HikariDataSource dataSource = (HikariDataSource) DataSourceFactory.create(
            GROUP_NAME,
            DataSourceItemType.WRITER,
            item(false),
            hikariConfig
        );

        try {
            assertThat(dataSource.getPoolName()).isEqualTo("smartformWriterPool");
            assertThat(dataSource.getJdbcUrl()).isEqualTo(URL);
            assertThat(dataSource.getUsername()).isEqualTo(USERNAME);
            assertThat(dataSource.isReadOnly()).isFalse();
            assertThat(dataSource.getMaximumPoolSize()).isEqualTo(20);
            assertThat(dataSource.getMinimumIdle()).isEqualTo(10);
        } finally {
            dataSource.close();
        }
    }

    @Test
    void createRejectsNullHikariConfig() {
        assertThatThrownBy(() -> DataSourceFactory.create(
            GROUP_NAME,
            DataSourceItemType.WRITER,
            item(false),
            null
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("hikariConfig is required");
    }

    private static DataSourceItemProperties item(boolean readOnly) {
        return new DataSourceItemProperties(URL, USERNAME, PASSWORD, readOnly, new HikariConfig());
    }

    private static Binder binder(Map.Entry<String, String>... entries) {
        return new Binder(new MapConfigurationPropertySource(Map.ofEntries(entries)));
    }

    private static Map.Entry<String, String> entry(String key, String value) {
        return Map.entry(key, value);
    }
}
