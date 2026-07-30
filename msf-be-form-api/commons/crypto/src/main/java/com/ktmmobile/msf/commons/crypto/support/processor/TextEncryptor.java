package com.ktmmobile.msf.commons.crypto.support.processor;

/**
 * 문자열 암복호화 정책 구현체
 */
public interface TextEncryptor {

    /** 원본 문자열 암호화 */
    String encrypt(String plainText);

    /** 암호화 문자열 복호화 */
    String decrypt(String cipherText);

    /** 암호화 문자열 여부 확인 */
    default boolean isEncrypted(String value) {
        return false;
    }
}
