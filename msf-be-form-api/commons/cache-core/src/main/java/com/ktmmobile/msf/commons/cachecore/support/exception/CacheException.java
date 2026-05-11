package com.ktmmobile.msf.commons.cachecore.support.exception;

/**
 * cache-core 전용 예외
 */
public class CacheException extends RuntimeException {

    /**
     * 메시지 기반 예외 생성
     *
     * @param message 예외 메시지
     */
    public CacheException(String message) {
        super(message);
    }

    /**
     * 메시지와 원인 기반 예외 생성
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * RuntimeException을 CacheException으로 변환
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     * @return CacheException
     */
    public static CacheException wrap(String message, Throwable cause) {
        if (cause instanceof CacheException cacheException) {
            return cacheException;
        }
        return new CacheException(message, cause);
    }
}
