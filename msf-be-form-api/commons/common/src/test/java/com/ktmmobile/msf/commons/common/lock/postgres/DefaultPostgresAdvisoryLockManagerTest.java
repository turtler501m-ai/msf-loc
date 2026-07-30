package com.ktmmobile.msf.commons.common.lock.postgres;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultPostgresAdvisoryLockManagerTest {

    @AfterEach
    void tearDown() {
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
        TransactionSynchronizationManager.setActualTransactionActive(false);
    }

    @Test
    void lockRequiresActiveTransaction() {
        DefaultPostgresAdvisoryLockManager lockManager = new DefaultPostgresAdvisoryLockManager(mock(JdbcOperations.class));

        assertThatThrownBy(() -> lockManager.lock("sample"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("active transaction");
    }

    @Test
    void lockRequiresReadWriteTransaction() {
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);

        DefaultPostgresAdvisoryLockManager lockManager = new DefaultPostgresAdvisoryLockManager(mock(JdbcOperations.class));

        assertThatThrownBy(() -> lockManager.lock("sample"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("read-write transaction");
    }

    @Test
    void tryLockReturnsPostgresFunctionResult() {
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);

        JdbcOperations jdbcOperations = mock(JdbcOperations.class);
        DefaultPostgresAdvisoryLockManager lockManager = new DefaultPostgresAdvisoryLockManager(jdbcOperations);
        long lockKey = PostgresAdvisoryLockKeyGenerator.generate("sample");

        when(jdbcOperations.queryForObject("SELECT PG_TRY_ADVISORY_XACT_LOCK(?)", Boolean.class, lockKey))
            .thenReturn(true);

        assertThat(lockManager.tryLock("sample")).isTrue();
    }
}
