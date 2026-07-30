package com.ktmmobile.msf.commons.crypto.support.processor;

import java.security.SecureRandom;
import java.util.Map;

/**
 * 신규 DB 필드 저장용 AES-GCM 문자열 암복호화 처리기
 *
 * <p>prefix + version + keyId + IV + payload 암호문 포맷
 * keyId 기반 키 교체 이후 이전 키 복호화 지원</p>
 */
public class AesGcmTextEncryptor extends AbstractAesGcmTextEncryptor {

    private static final String VERSION = "gcm1";

    private final SecureRandom secureRandom = new SecureRandom();

    public AesGcmTextEncryptor(String key, String prefix) {
        this(key, prefix, "default");
    }

    public AesGcmTextEncryptor(String key, String prefix, String keyId) {
        this(key, prefix, keyId, Map.of());
    }

    public AesGcmTextEncryptor(String key, String prefix, String keyId, Map<String, String> previousKeys) {
        super(key, prefix, keyId, previousKeys);
    }

    @Override
    protected String version() {
        return VERSION;
    }

    /** 랜덤 IV 생성 */
    @Override
    protected byte[] createIv(String plainText) {
        // 12바이트 랜덤 IV 기반 동일 평문 다른 암호문 생성
        byte[] iv = new byte[IV_LENGTH_BYTES];
        secureRandom.nextBytes(iv);
        return iv;
    }

    @Override
    public boolean isEncrypted(String value) {
        return value != null && value.startsWith(prefix);
    }

    @Override
    protected String encryptionFailedMessage() {
        return "Field encryption failed.";
    }

    @Override
    protected String decryptionFailedMessage() {
        return "Field decryption failed.";
    }

    @Override
    protected String invalidPayloadMessage() {
        return "Encrypted field payload is invalid.";
    }
}
