package com.ktmmobile.msf.commons.common.datasource.common;

import java.util.Map;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceItemType;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private final String groupName;

    public RoutingDataSource(String groupName, DataSource writerDataSource, DataSource readerDataSource) {
        this.groupName = groupName;
        setTargetDataSources(Map.of(
            DataSourceItemType.WRITER, writerDataSource,
            DataSourceItemType.READER, readerDataSource
        ));
        setDefaultTargetDataSource(writerDataSource);
        afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            return DataSourceItemType.WRITER;
        }

        DataSourceItemType target = DataSourceItemType.WRITER;
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            target = DataSourceItemType.READER;
        }

        log.debug("{} Routing: {}", groupName, target);
        return target;
    }
}
