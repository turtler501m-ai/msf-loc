package com.ktmmobile.msf.commons.common.lock.postgres;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * PostgreSQL advisory transaction lock 기본 Manager
 */
public class DefaultPostgresAdvisoryLockManager implements PostgresAdvisoryLockManager {

    private static final String LOCK_SQL = "SELECT PG_ADVISORY_XACT_LOCK(?)";
    private static final String TRY_LOCK_SQL = "SELECT PG_TRY_ADVISORY_XACT_LOCK(?)";

    private final JdbcOperations jdbcOperations;

    public DefaultPostgresAdvisoryLockManager(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /** 락 이름 기준 transaction-level advisory lock 획득 */
    @Override
    public void lock(String lockName) {
        lockByKey(PostgresAdvisoryLockKeyGenerator.generate(lockName));
    }

    /** 락 이름 기준 transaction-level advisory lock 획득 시도 */
    @Override
    public boolean tryLock(String lockName) {
        return tryLockByKey(PostgresAdvisoryLockKeyGenerator.generate(lockName));
    }

    private void lockByKey(long lockKey) {
        validateTransaction();
        jdbcOperations.query(LOCK_SQL, ps -> ps.setLong(1, lockKey), rs -> null);
    }

    private boolean tryLockByKey(long lockKey) {
        validateTransaction();
        Boolean locked = jdbcOperations.queryForObject(TRY_LOCK_SQL, Boolean.class, lockKey);
        return Boolean.TRUE.equals(locked);
    }

    private void validateTransaction() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new IllegalStateException("PostgreSQL advisory lock requires an active transaction.");
        }
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            throw new IllegalStateException("PostgreSQL advisory lock requires a read-write transaction.");
        }
    }
}
