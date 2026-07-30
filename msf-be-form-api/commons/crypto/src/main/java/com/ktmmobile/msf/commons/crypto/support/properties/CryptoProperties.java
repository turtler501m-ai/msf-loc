package com.ktmmobile.msf.commons.crypto.support.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 암복호화 설정 프로퍼티
 *
 * @param field DB 필드 암복호화 설정
 * @param kisaSeed MSP PRX 레거시 KISA SEED-CBC 암복호화 설정
 */
@ConfigurationProperties(prefix = "crypto")
public record CryptoProperties(
    Field field,
    KisaSeed kisaSeed
) {

    public CryptoProperties {
        field = field == null ? new Field(false, null, null, List.of(), null, null) : field;
        kisaSeed = kisaSeed == null ? new KisaSeed(null, null) : kisaSeed;
    }

    /**
     * DB 필드 암복호화 설정
     *
     * @param enabled 필드 암복호화 활성화 여부
     * @param key AES 암복호화 키
     * @param keyId 암호문에 기록할 AES 키 식별자
     * @param previousKeys 이전 AES-GCM 키 목록
     * @param prefix 암호화 문자열 식별 prefix
     * @param legacyAes256 레거시 AES-256 암복호화 호환 설정
     */
    public record Field(
        boolean enabled,
        String key,
        String keyId,
        List<AesGcmKey> previousKeys,
        String prefix,
        LegacyAes256 legacyAes256
    ) {

        private static final String DEFAULT_PREFIX = "ENC:";

        public Field {
            keyId = keyId == null || keyId.isBlank() ? "default" : keyId;
            previousKeys = previousKeys == null ? List.of() : List.copyOf(previousKeys);
            prefix = prefix == null || prefix.isBlank() ? DEFAULT_PREFIX : prefix;
            legacyAes256 = legacyAes256 == null ? new LegacyAes256(null, null) : legacyAes256;
        }
    }

    /**
     * 이전 AES-GCM 키 설정
     *
     * @param keyId AES-GCM 키 식별자
     * @param key AES-GCM 암복호화 키
     */
    public record AesGcmKey(String keyId, String key) {

        public boolean configured() {
            return keyId != null && !keyId.isBlank() && key != null && !key.isBlank();
        }
    }

    /**
     * 레거시 AES-256 암복호화 호환 설정
     *
     * @param key 레거시 AES 키
     * @param iv 레거시 AES IV
     */
    public record LegacyAes256(String key, String iv) {

        public boolean configured() {
            return key != null && !key.isBlank() && iv != null && !iv.isBlank();
        }
    }

    /**
     * MSP PRX 레거시 KISA SEED-CBC 암복호화 설정
     *
     * @param key SEED 암복호화 키
     * @param iv SEED IV, Base64 인코딩 문자열
     */
    public record KisaSeed(String key, String iv) {

        public boolean configured() {
            return key != null && !key.isBlank() && iv != null && !iv.isBlank();
        }
    }
}
