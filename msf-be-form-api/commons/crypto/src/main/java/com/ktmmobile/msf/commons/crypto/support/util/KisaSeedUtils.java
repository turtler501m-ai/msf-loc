package com.ktmmobile.msf.commons.crypto.support.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

/**
 * MSP PRX 레거시 KISA SEED-CBC 문자열 암복호화 유틸리티
 *
 * <p>PRX adapter, Jackson deserializer, 필드 암복호화 어댑터 공통 SEED 설정 공유</p>
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class KisaSeedUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final Object SEED_SPEC_LOCK = new Object();
    private static KisaSeedSpec seedSpec;

    /** 애플리케이션 설정 기반 SEED-CBC 키와 IV 초기화 */
    public static void initialize(String key, String base64Iv) {
        if (key == null || key.isBlank()) {
            throw new CryptoException("crypto.kisa-seed.key must be configured.");
        }
        byte[] decodedIv = decodeIv(base64Iv);
        if (decodedIv.length != 16) {
            throw new CryptoException("crypto.kisa-seed.iv must be 16 bytes. Base64 encoded IV is supported.");
        }
        synchronized (SEED_SPEC_LOCK) {
            seedSpec = new KisaSeedSpec(key, decodedIv);
        }
    }

    /** 문자열을 MSP PRX 레거시 SEED-CBC 방식으로 암호화 */
    public static String encrypt(String value) {
        return encrypt(value, DEFAULT_CHARSET);
    }

    /** 문자열을 지정 문자셋 기준으로 MSP PRX 레거시 SEED-CBC 방식 암호화 */
    public static String encrypt(String value, Charset charset) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        try {
            byte[] plainBytes = value.getBytes(charset);
            KisaSeedSpec seedSpec = requireSeedSpec();
            // 기존 MSP PRX 암호문 호환용 KISA SEED-CBC 직접 호출
            byte[] encrypted = KisaSeedCbc.SEED_CBC_Encrypt(
                seedSpec.key().getBytes(charset),
                seedSpec.ivSpec(),
                plainBytes,
                0,
                plainBytes.length
            );
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new CryptoException("KISA SEED encryption failed.", e);
        }
    }

    /** 암호화 문자열을 MSP PRX 레거시 SEED-CBC 방식으로 복호화 */
    public static String decrypt(String value) {
        return decrypt(value, DEFAULT_CHARSET);
    }

    /** 암호화 문자열을 지정 문자셋 기준으로 MSP PRX 레거시 SEED-CBC 방식 복호화 */
    public static String decrypt(String value, Charset charset) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        try {
            byte[] encrypted = Base64.getDecoder().decode(value);
            KisaSeedSpec seedSpec = requireSeedSpec();
            // PRX 규격 Base64 암호문과 raw bytes 복호화
            byte[] decrypted = KisaSeedCbc.SEED_CBC_Decrypt(
                seedSpec.key().getBytes(charset),
                seedSpec.ivSpec(),
                encrypted,
                0,
                encrypted.length
            );
            return new String(decrypted, charset);
        } catch (Exception e) {
            throw new CryptoException("KISA SEED decryption failed.", e);
        }
    }

    /** encryptYn이 Y이면 암호화하고, 아니면 원본 유지 */
    public static String encryptValue(String value, String encryptYn) {
        if ("Y".equals(encryptYn)) {
            return nullToEmpty(encrypt(value));
        }
        return nullToEmpty(value);
    }

    private static KisaSeedSpec requireSeedSpec() {
        synchronized (SEED_SPEC_LOCK) {
            if (seedSpec == null) {
                throw new CryptoException("KisaSeedUtils has not been initialized.");
            }
            return seedSpec;
        }
    }

    private static byte[] decodeIv(String base64Iv) {
        if (base64Iv == null || base64Iv.isBlank()) {
            throw new CryptoException("crypto.kisa-seed.iv must be configured.");
        }
        try {
            return Base64.getDecoder().decode(base64Iv);
        } catch (IllegalArgumentException e) {
            throw new CryptoException("crypto.kisa-seed.iv must be Base64 encoded.", e);
        }
    }

    private static String nullToEmpty(String value) {
        if (value == null || "null".equals(value)) {
            return "";
        }
        return value;
    }

    private record KisaSeedSpec(String key, byte[] ivSpec) {

        private KisaSeedSpec {
            // mutable IV 배열 저장 반환 시 방어 복사
            ivSpec = ivSpec.clone();
        }

        @Override
        public byte[] ivSpec() {
            return ivSpec.clone();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof KisaSeedSpec(String otherKey, byte[] otherIvSpec))) {
                return false;
            }
            return key.equals(otherKey) && Arrays.equals(ivSpec, otherIvSpec);
        }

        @Override
        public int hashCode() {
            return 31 * key.hashCode() + Arrays.hashCode(ivSpec);
        }

        @Override
        public String toString() {
            return "KisaSeedSpec[key=******, ivSpec=" + Arrays.toString(ivSpec) + "]";
        }
    }
}
