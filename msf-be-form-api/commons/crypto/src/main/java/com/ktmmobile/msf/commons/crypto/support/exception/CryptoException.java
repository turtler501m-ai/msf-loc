package com.ktmmobile.msf.commons.crypto.support.exception;

/**
 * Crypto 모듈 암복호화 처리 중 발생하는 예외
 */
public class CryptoException extends RuntimeException {

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
