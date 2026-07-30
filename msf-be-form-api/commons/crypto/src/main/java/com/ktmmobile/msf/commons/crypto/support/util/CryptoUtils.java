package com.ktmmobile.msf.commons.crypto.support.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;
import com.ktmmobile.msf.commons.crypto.support.processor.TextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.TextEncryptorRegistry;

/**
 * 모듈 전반 문자열 암복호화 정적 유틸리티
 *
 * <p>Spring Bean 주입이 어려운 legacy 코드용 {@link TextEncryptorRegistry} 정적 접근점</p>
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoUtils {

    private static TextEncryptorRegistry textEncryptors;

    /** 단일 기본 암호화기 테스트 초기화 */
    public static void initialize(TextEncryptor textEncryptor) {
        initialize(
            new TextEncryptorRegistry(java.util.Map.of(FieldCryptoAlgorithm.AES_GCM, textEncryptor))
        );
    }

    /** 알고리즘별 암호화기 registry 초기화 */
    public static void initialize(TextEncryptorRegistry textEncryptors) {
        CryptoUtils.textEncryptors = textEncryptors;
    }

    /** 원본 문자열 암호화 */
    public static String encrypt(String plainText) {
        return encrypt(plainText, FieldCryptoAlgorithm.AES_GCM);
    }

    /** 지정 알고리즘으로 원본 문자열 암호화 */
    public static String encrypt(String plainText, FieldCryptoAlgorithm algorithm) {
        return requireTextEncryptors().get(algorithm).encrypt(plainText);
    }

    /** 암호화 문자열 복호화 */
    public static String decrypt(String cipherText) {
        return decrypt(cipherText, FieldCryptoAlgorithm.AES_GCM);
    }

    /** 지정 알고리즘으로 암호화 문자열 복호화 */
    public static String decrypt(String cipherText, FieldCryptoAlgorithm algorithm) {
        return requireTextEncryptors().get(algorithm).decrypt(cipherText);
    }

    /** 암호화 문자열 여부 확인 */
    public static boolean isEncrypted(String value) {
        return isEncrypted(value, FieldCryptoAlgorithm.AES_GCM);
    }

    /** 지정 알고리즘의 암호화 문자열 여부 확인 */
    public static boolean isEncrypted(String value, FieldCryptoAlgorithm algorithm) {
        return requireTextEncryptors().get(algorithm).isEncrypted(value);
    }

    private static TextEncryptorRegistry requireTextEncryptors() {
        if (textEncryptors == null) {
            throw new CryptoException("CryptoUtils has not been initialized.");
        }
        return textEncryptors;
    }
}
