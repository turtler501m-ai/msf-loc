package com.ktmmobile.msf.commons.crypto.support.processor;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Map;
import javax.crypto.Mac;

/**
 * 동등 조건 검색 컬럼용 AES-GCM 문자열 암복호화 처리기
 *
 * <p>평문과 키 기반 결정적 IV 생성
 * 같은 평문 같은 암호문 생성
 * 검색 조건과 저장 값 동일 암호화 용도</p>
 */
public class SearchableAesGcmTextEncryptor extends AbstractAesGcmTextEncryptor {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String VERSION = "sgcm1";

    public SearchableAesGcmTextEncryptor(String key, String prefix, String keyId) {
        this(key, prefix, keyId, Map.of());
    }

    public SearchableAesGcmTextEncryptor(
        String key,
        String prefix,
        String keyId,
        Map<String, String> previousKeys
    ) {
        super(key, prefix, keyId, previousKeys);
    }

    @Override
    protected String version() {
        return VERSION;
    }

    @Override
    public boolean isEncrypted(String value) {
        return value != null && value.startsWith(versionedPrefix());
    }

    /** 결정적 IV 생성 */
    @Override
    protected byte[] createIv(String plainText) throws GeneralSecurityException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(secretKey);
        byte[] digest = mac.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        // HMAC 결과 앞 12바이트 기반 결정적 GCM IV
        return java.util.Arrays.copyOf(digest, IV_LENGTH_BYTES);
    }

    @Override
    protected String encryptionFailedMessage() {
        return "Searchable field encryption failed.";
    }

    @Override
    protected String decryptionFailedMessage() {
        return "Searchable field decryption failed.";
    }

    @Override
    protected String invalidPayloadMessage() {
        return "Searchable encrypted field payload is invalid.";
    }
}
