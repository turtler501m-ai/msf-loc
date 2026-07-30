package com.ktmmobile.msf.commons.crypto.support.processor;

import java.util.EnumMap;
import java.util.Map;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

/**
 * 필드별 암복호화 알고리즘 {@link TextEncryptor} 제공
 *
 * <p>processor, util, MyBatis 경로 공통 암복호화 정책 registry</p>
 */
public class TextEncryptorRegistry {

    private final Map<FieldCryptoAlgorithm, TextEncryptor> textEncryptors;

    /** 설정 암호화기 목록 enum map 복사 */
    public TextEncryptorRegistry(Map<FieldCryptoAlgorithm, TextEncryptor> textEncryptors) {
        this.textEncryptors = new EnumMap<>(FieldCryptoAlgorithm.class);
        this.textEncryptors.putAll(textEncryptors);
    }

    /** 신규 필드 암호화 기본 알고리즘 */
    public TextEncryptor defaultEncryptor() {
        return get(FieldCryptoAlgorithm.AES_GCM);
    }

    /** 지정 알고리즘의 암복호화 처리기 */
    public TextEncryptor get(FieldCryptoAlgorithm algorithm) {
        TextEncryptor textEncryptor = textEncryptors.get(algorithm);
        if (textEncryptor == null) {
            throw new CryptoException("TextEncryptor is not configured for algorithm: " + algorithm);
        }
        return textEncryptor;
    }
}
