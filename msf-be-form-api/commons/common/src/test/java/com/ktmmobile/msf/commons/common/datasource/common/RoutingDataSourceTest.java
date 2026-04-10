package com.ktmmobile.msf.commons.common.datasource.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceItemType;

import static org.assertj.core.api.Assertions.assertThat;

class RoutingDataSourceTest {

    @AfterEach
    void tearDown() {
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
        TransactionSynchronizationManager.setActualTransactionActive(false);
    }

    @Test
    void determineCurrentLookupKeyReturnsWriterByDefault() {
        TestRoutingDataSource routingDataSource = new TestRoutingDataSource("smartform");

        assertThat(routingDataSource.currentLookupKey()).isEqualTo(DataSourceItemType.WRITER);
    }

    @Test
    void determineCurrentLookupKeyReturnsReaderForReadOnlyTransaction() {
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);

        TestRoutingDataSource routingDataSource = new TestRoutingDataSource("smartform");

        assertThat(routingDataSource.currentLookupKey()).isEqualTo(DataSourceItemType.READER);
    }

    private static final class TestRoutingDataSource extends RoutingDataSource {

        private TestRoutingDataSource(String groupName) {
            super(groupName, new StubDataSource(), new StubDataSource());
        }

        private Object currentLookupKey() {
            return determineCurrentLookupKey();
        }
    }

    private static final class StubDataSource extends AbstractDataSource {

        @Override
        public java.sql.Connection getConnection() {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.sql.Connection getConnection(String username, String password) {
            throw new UnsupportedOperationException();
        }
    }
}
