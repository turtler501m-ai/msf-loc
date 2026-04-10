package com.ktmmobile.msf.commons.common.datasource.common;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.util.Assert;

import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceGroupType;
import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceItemType;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourceGroupProperties;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourceItemProperties;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourcesProperties;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractGroupDataSourceSupport {

    private static final String PROPERTY_HIKARI = ".hikari";
    private static final String PROPERTY_DATASOURCE_HIKARI = "spring.datasource" + PROPERTY_HIKARI;
    private static final String PROPERTY_DATASOURCE_GROUPS = "spring.datasource.groups.";

    protected static final String DATASOURCE_BEAN = "DataSource";
    protected static final String TX_MANAGER_BEAN = "TransactionManager";

    protected final DataSourcesProperties dataSourcesProperties;
    protected final Environment environment;


    protected DataSource createRoutingDataSource(String groupName) {
        var routingDataSource = new RoutingDataSource(groupName,
            createWriterDataSource(groupName, createBinder(environment)),
            createReaderDataSource(groupName, createBinder(environment)));
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    private DataSource createWriterDataSource(String groupName, Binder binder) {
        var groupProps = requireGroupProps(groupName, DataSourceGroupType.ROUTING);
        var itemType = DataSourceItemType.WRITER;
        return createDateSource(groupName, groupProps, itemType, binder);
    }

    private DataSource createReaderDataSource(String groupName, Binder binder) {
        var groupProps = requireGroupProps(groupName, DataSourceGroupType.ROUTING);
        var itemType = DataSourceItemType.READER;
        return createDateSource(groupName, groupProps, itemType, binder);
    }

    protected DataSource createStandaloneDataSource(String groupName) {
        var groupProps = requireGroupProps(groupName, DataSourceGroupType.STANDALONE);
        var itemType = DataSourceItemType.STANDALONE;
        return createDateSource(groupName, groupProps, itemType, createBinder(environment));
    }

    private DataSourceGroupProperties requireGroupProps(String groupName, DataSourceGroupType expectedType) {
        var groupProps = dataSourcesProperties.groups().get(groupName);
        Assert.notNull(groupProps, PROPERTY_DATASOURCE_GROUPS + groupName + " is required");
        Assert.state(groupProps.type() == expectedType, PROPERTY_DATASOURCE_GROUPS + groupName + ".type must be " + expectedType);
        return groupProps;
    }

    private static String buildInstanceRequiredMessage(String groupName, String instanceName) {
        return String.format("%s%s.%s is required", PROPERTY_DATASOURCE_GROUPS, groupName, instanceName);
    }

    private static Binder createBinder(Environment environment) {
        return Binder.get(environment);
    }

    private static DataSource createDateSource(
        String groupName,
        DataSourceGroupProperties groupProps,
        DataSourceItemType itemType,
        Binder binder
    ) {
        var itemProps = requiredItemProps(groupName, groupProps, itemType);
        var hikariConfig = bindHikariConfig(binder, groupName, "." + itemType.getItemName());
        return DataSourceFactory.create(groupName, itemType, itemProps, hikariConfig);
    }

    private static DataSourceItemProperties requiredItemProps(String groupName, DataSourceGroupProperties groupProps, DataSourceItemType itemType) {
        var itemProps = groupProps.ofItemType(itemType);
        Assert.state(itemProps != null, buildInstanceRequiredMessage(groupName, itemType.getItemName()));
        return itemProps;
    }

    private static HikariConfig bindHikariConfig(Binder binder, String groupName, String itemName) {
        String canonicalGroupName = toCanonicalPropertyName(groupName);
        return HikariConfigBinder.bind(binder,
            PROPERTY_DATASOURCE_HIKARI,
            PROPERTY_DATASOURCE_GROUPS + canonicalGroupName + PROPERTY_HIKARI,
            PROPERTY_DATASOURCE_GROUPS + canonicalGroupName + itemName + PROPERTY_HIKARI
        );
    }

    private static String toCanonicalPropertyName(String originName) {
        return originName.replaceAll("([a-z0-9])([A-Z])", "$1-$2").toLowerCase();
    }
}
