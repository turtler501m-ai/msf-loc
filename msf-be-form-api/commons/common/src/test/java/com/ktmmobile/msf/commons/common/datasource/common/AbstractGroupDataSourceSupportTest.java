package com.ktmmobile.msf.commons.common.datasource.common;

import java.util.Map;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.mock.env.MockEnvironment;

import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceGroupType;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourceGroupProperties;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourceItemProperties;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourcesProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AbstractGroupDataSourceSupportTest {

    private static final String SMARTFORM = "smartform";
    private static final String MSP_READER = "mspReader";
    private static final String SMARTFORM_URL = "jdbc:postgresql://localhost:5432/smartform";
    private static final String MSP_URL = "jdbc:postgresql://localhost:5432/msp";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pw";

    private static final Environment ENVIRONMENT = environment(
        entry("spring.datasource.hikari.maximum-pool-size", "15"),
        entry("spring.datasource.hikari.initialization-fail-timeout", "-1"),
        entry("spring.datasource.groups.smartform.reader.hikari.maximum-pool-size", "30"),
        entry("spring.datasource.groups.msp-reader.standalone.hikari.maximum-pool-size", "10")
    );

    @Test
    void createRoutingDataSourceReturnsLazyProxy() {
        TestGroupDataSourceSupport support = support(
            groups(routingGroup(item(SMARTFORM_URL, false), item(SMARTFORM_URL, true))),
            ENVIRONMENT
        );

        DataSource dataSource = support.routing(SMARTFORM);

        assertThat(dataSource).isInstanceOf(LazyConnectionDataSourceProxy.class);
    }

    @Test
    void createStandaloneDataSourceUsesStandalonePoolName() {
        TestGroupDataSourceSupport support = support(
            groups(MSP_READER, standaloneGroup(item(MSP_URL, true))),
            ENVIRONMENT
        );

        HikariDataSource dataSource = (HikariDataSource) support.standalone(MSP_READER);
        try {
            assertThat(dataSource.getPoolName()).isEqualTo("mspReaderStandalonePool");
            assertThat(dataSource.isReadOnly()).isTrue();
            assertThat(dataSource.getMaximumPoolSize()).isEqualTo(10);
        } finally {
            dataSource.close();
        }
    }

    @Test
    void createRoutingDataSourceFailsWhenGroupIsMissing() {
        TestGroupDataSourceSupport support = support(new DataSourcesProperties(Map.of(), new HikariConfig()), ENVIRONMENT);

        assertThatThrownBy(() -> support.routing(SMARTFORM))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("spring.datasource.groups.smartform is required");
    }

    @Test
    void createRoutingDataSourceFailsWhenGroupTypeIsWrong() {
        TestGroupDataSourceSupport support = support(
            groups(SMARTFORM, standaloneGroup(item(SMARTFORM_URL, true))),
            ENVIRONMENT
        );

        assertThatThrownBy(() -> support.routing(SMARTFORM))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("spring.datasource.groups.smartform.type must be ROUTING");
    }

    @Test
    void createRoutingDataSourceFailsWhenWriterIsMissing() {
        TestGroupDataSourceSupport support = support(
            groups(SMARTFORM, routingGroup(null, item(SMARTFORM_URL, true))),
            ENVIRONMENT
        );

        assertThatThrownBy(() -> support.routing(SMARTFORM))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("spring.datasource.groups.smartform.writer is required");
    }

    @Test
    void createRoutingDataSourceFailsWhenReaderIsMissing() {
        TestGroupDataSourceSupport support = support(
            groups(SMARTFORM, routingGroup(item(SMARTFORM_URL, false), null)),
            ENVIRONMENT
        );

        assertThatThrownBy(() -> support.routing(SMARTFORM))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("spring.datasource.groups.smartform.reader is required");
    }

    @Test
    void createStandaloneDataSourceFailsWhenStandaloneItemIsMissing() {
        TestGroupDataSourceSupport support = support(
            groups(MSP_READER, standaloneGroup(null)),
            ENVIRONMENT
        );

        assertThatThrownBy(() -> support.standalone(MSP_READER))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("spring.datasource.groups.mspReader.standalone is required");
    }

    private static TestGroupDataSourceSupport support(DataSourcesProperties properties, Environment environment) {
        return new TestGroupDataSourceSupport(properties, environment);
    }

    private static DataSourcesProperties groups(DataSourceGroupProperties smartformGroup) {
        return groups(SMARTFORM, smartformGroup);
    }

    private static DataSourcesProperties groups(String groupName, DataSourceGroupProperties groupProperties) {
        return new DataSourcesProperties(Map.of(groupName, groupProperties), new HikariConfig());
    }

    private static DataSourceGroupProperties routingGroup(DataSourceItemProperties writer, DataSourceItemProperties reader) {
        return new DataSourceGroupProperties(DataSourceGroupType.ROUTING, writer, reader, null, new HikariConfig());
    }

    private static DataSourceGroupProperties standaloneGroup(DataSourceItemProperties standalone) {
        return new DataSourceGroupProperties(DataSourceGroupType.STANDALONE, null, null, standalone, new HikariConfig());
    }

    private static DataSourceItemProperties item(String url, boolean readOnly) {
        return new DataSourceItemProperties(url, USERNAME, PASSWORD, readOnly, new HikariConfig());
    }

    private static MockEnvironment environment(Map.Entry<String, String>... entries) {
        MockEnvironment environment = new MockEnvironment();
        for (Map.Entry<String, String> entry: entries) {
            environment.setProperty(entry.getKey(), entry.getValue());
        }
        return environment;
    }

    private static Map.Entry<String, String> entry(String key, String value) {
        return Map.entry(key, value);
    }

    private static final class TestGroupDataSourceSupport extends AbstractGroupDataSourceSupport {

        private TestGroupDataSourceSupport(DataSourcesProperties dataSourcesProperties, Environment environment) {
            super(dataSourcesProperties, environment);
        }

        private DataSource routing(String groupName) {
            return createRoutingDataSource(groupName);
        }

        private DataSource standalone(String groupName) {
            return createStandaloneDataSource(groupName);
        }
    }
}
