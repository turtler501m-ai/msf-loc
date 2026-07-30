package com.ktmmobile.msf.commons.common.lock.postgres;

/**
 * PostgreSQL advisory transaction lock 관리자
 */
public interface PostgresAdvisoryLockManager {

    /**
     * 락 이름 기준 transaction-level advisory lock 획득
     *
     * @param lockName 락 이름
     */
    void lock(String lockName);

    /**
     * 락 이름 기준 transaction-level advisory lock 획득 시도
     *
     * @param lockName 락 이름
     * @return 락 획득 여부
     */
    boolean tryLock(String lockName);
}
