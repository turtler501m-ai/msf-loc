package com.ktmmobile.msf.commons.common.datasource.common;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HikariConfigBinderTest {

    private static final String COMMON_HIKARI = "spring.datasource.hikari";
    private static final String SMARTFORM_HIKARI = "spring.datasource.groups.smartform.hikari";
    private static final String SMARTFORM_WRITER_HIKARI = "spring.datasource.groups.smartform.writer.hikari";
    private static final String MSP_READER_HIKARI = "spring.datasource.groups.msp-reader.hikari";
    private static final String MSP_READER_STANDALONE_HIKARI = "spring.datasource.groups.msp-reader.standalone.hikari";

    @Test
    void bindAppliesOverridesInCommonGroupItemOrder() {
        Binder binder = binder(
            entry(COMMON_HIKARI + ".minimum-idle", "15"),
            entry(COMMON_HIKARI + ".maximum-pool-size", "15"),
            entry(COMMON_HIKARI + ".connection-timeout", "2700"),
            entry(COMMON_HIKARI + ".validation-timeout", "2100"),
            entry(COMMON_HIKARI + ".keepalive-time", "30000"),
            entry(COMMON_HIKARI + ".leak-detection-threshold", "59000"),
            entry(COMMON_HIKARI + ".max-lifetime", "120000"),
            entry(COMMON_HIKARI + ".data-source-properties.cachePrepStmts", "true"),
            entry(SMARTFORM_HIKARI + ".minimum-idle", "12"),
            entry(SMARTFORM_HIKARI + ".data-source-properties.prepStmtCacheSize", "500"),
            entry(SMARTFORM_WRITER_HIKARI + ".minimum-idle", "20"),
            entry(SMARTFORM_WRITER_HIKARI + ".maximum-pool-size", "20"),
            entry(SMARTFORM_WRITER_HIKARI + ".validation-timeout", "500"),
            entry(SMARTFORM_WRITER_HIKARI + ".data-source-properties.useServerPrepStmts", "true")
        );

        var merged = HikariConfigBinder.bind(
            binder,
            COMMON_HIKARI,
            SMARTFORM_HIKARI,
            SMARTFORM_WRITER_HIKARI
        );

        assertThat(merged.getMinimumIdle()).isEqualTo(20);
        assertThat(merged.getMaximumPoolSize()).isEqualTo(20);
        assertThat(merged.getConnectionTimeout()).isEqualTo(2700L);
        assertThat(merged.getValidationTimeout()).isEqualTo(500L);
        assertThat(merged.getKeepaliveTime()).isEqualTo(30000L);
        assertThat(merged.getLeakDetectionThreshold()).isEqualTo(59000L);
        assertThat(merged.getMaxLifetime()).isEqualTo(120000L);
        assertThat(merged.getDataSourceProperties().getProperty("cachePrepStmts")).isEqualTo("true");
        assertThat(merged.getDataSourceProperties().getProperty("prepStmtCacheSize")).isEqualTo("500");
        assertThat(merged.getDataSourceProperties().getProperty("useServerPrepStmts")).isEqualTo("true");
    }

    @Test
    void bindDoesNotOverrideCommonValuesWithEmptyLowerLevelConfigs() {
        Binder binder = binder(
            entry(COMMON_HIKARI + ".minimum-idle", "15"),
            entry(COMMON_HIKARI + ".maximum-pool-size", "15"),
            entry(COMMON_HIKARI + ".connection-timeout", "2700"),
            entry(COMMON_HIKARI + ".validation-timeout", "2100"),
            entry(COMMON_HIKARI + ".keepalive-time", "30000"),
            entry(COMMON_HIKARI + ".leak-detection-threshold", "59000"),
            entry(COMMON_HIKARI + ".max-lifetime", "120000")
        );

        var merged = HikariConfigBinder.bind(
            binder,
            COMMON_HIKARI,
            SMARTFORM_HIKARI,
            SMARTFORM_WRITER_HIKARI
        );

        assertThat(merged.getMinimumIdle()).isEqualTo(15);
        assertThat(merged.getMaximumPoolSize()).isEqualTo(15);
        assertThat(merged.getConnectionTimeout()).isEqualTo(2700L);
        assertThat(merged.getValidationTimeout()).isEqualTo(2100L);
        assertThat(merged.getKeepaliveTime()).isEqualTo(30000L);
        assertThat(merged.getLeakDetectionThreshold()).isEqualTo(59000L);
        assertThat(merged.getMaxLifetime()).isEqualTo(120000L);
        assertThat(merged.isReadOnly()).isFalse();
    }

    @Test
    void bindSupportsCamelCaseGroupNameByUsingCanonicalPrefix() {
        Binder binder = binder(
            entry(COMMON_HIKARI + ".maximum-pool-size", "15"),
            entry(MSP_READER_HIKARI + ".maximum-pool-size", "20"),
            entry(MSP_READER_STANDALONE_HIKARI + ".maximum-pool-size", "25")
        );

        var merged = HikariConfigBinder.bind(
            binder,
            COMMON_HIKARI,
            MSP_READER_HIKARI,
            MSP_READER_STANDALONE_HIKARI
        );

        assertThat(merged.getMaximumPoolSize()).isEqualTo(25);
    }

    @Test
    void bindRejectsNullBinder() {
        assertThatThrownBy(() -> HikariConfigBinder.bind(null, COMMON_HIKARI))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("binder is required");
    }

    private static Binder binder(Map.Entry<String, String>... entries) {
        return new Binder(new MapConfigurationPropertySource(Map.ofEntries(entries)));
    }

    private static Map.Entry<String, String> entry(String key, String value) {
        return Map.entry(key, value);
    }
}
