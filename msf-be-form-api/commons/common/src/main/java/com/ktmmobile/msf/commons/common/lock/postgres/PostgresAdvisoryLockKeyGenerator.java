package com.ktmmobile.msf.commons.common.lock.postgres;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * PostgreSQL advisory lock 이름의 64-bit key 변환기
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostgresAdvisoryLockKeyGenerator {

    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * 락 이름을 PostgreSQL bigint advisory lock key로 변환
     *
     * @param lockName 락 이름
     * @return 64-bit advisory lock key
     */
    public static long generate(String lockName) {
        if (!StringUtils.hasText(lockName)) {
            throw new IllegalArgumentException("lockName must not be blank.");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(lockName.getBytes(StandardCharsets.UTF_8));
            return ByteBuffer.wrap(hash).getLong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(HASH_ALGORITHM + " algorithm is not available.", e);
        }
    }
}
