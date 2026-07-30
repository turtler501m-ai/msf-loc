package com.ktmmobile.msf.commons.crypto.support.processor;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

/**
 * AES-GCM 계열 문자열 암복호화 공통 처리기
 *
 * <p>prefix + version + keyId + IV + payload 암호문 포맷
 * keyId 기반 이전 키 복호화 지원</p>
 */
abstract class AbstractAesGcmTextEncryptor implements TextEncryptor {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    protected static final String DELIMITER = ":";
    protected static final int IV_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;

    protected final SecretKeySpec secretKey;
    protected final String prefix;
    protected final String keyId;

    private final Map<String, SecretKeySpec> secretKeys;

    protected AbstractAesGcmTextEncryptor(
        String key,
        String prefix,
        String keyId,
        Map<String, String> previousKeys
    ) {
        this.prefix = prefix;
        this.keyId = normalizeKeyId(keyId);
        this.secretKey = new SecretKeySpec(decodeKey(key), ALGORITHM);
        this.secretKeys = createSecretKeys(this.keyId, this.secretKey, previousKeys);
    }

    /** 암호문 포맷 version */
    protected abstract String version();

    /** 알고리즘별 IV 생성 */
    protected abstract byte[] createIv(String plainText) throws GeneralSecurityException;

    /** 암호화 실패 메시지 */
    protected abstract String encryptionFailedMessage();

    /** 복호화 실패 메시지 */
    protected abstract String decryptionFailedMessage();

    /** 암호문 payload 검증 실패 메시지 */
    protected abstract String invalidPayloadMessage();

    @Override
    public String encrypt(String plainText) {
        if (plainText == null || isEncrypted(plainText)) {
            return plainText;
        }
        try {
            byte[] iv = createIv(plainText);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return prefix
                + version()
                + DELIMITER
                + keyId
                + DELIMITER
                + encodeUrl(iv)
                + DELIMITER
                + encodeUrl(encrypted);
        } catch (GeneralSecurityException e) {
            throw new CryptoException(encryptionFailedMessage(), e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        if (cipherText == null || !isEncrypted(cipherText)) {
            return cipherText;
        }
        try {
            String payload = cipherText.substring(prefix.length());
            return decryptVersioned(payload);
        } catch (IllegalArgumentException | GeneralSecurityException e) {
            throw new CryptoException(decryptionFailedMessage(), e);
        }
    }

    /** 암호문 keyId 기반 현재 키 또는 이전 키 복호화 */
    private String decryptVersioned(String payload) throws GeneralSecurityException {
        String[] parts = payload.split(DELIMITER, 4);
        if (parts.length != 4 || !version().equals(parts[0]) || parts[1].isBlank() || parts[2].isBlank() || parts[3].isBlank()) {
            throw new CryptoException(invalidPayloadMessage());
        }
        // 암호문 keyId 기준 키 로테이션 기간 복호화 지원
        return decrypt(secretKeyOf(parts[1]), decodeUrl(parts[2]), decodeUrl(parts[3]));
    }

    private String decrypt(SecretKeySpec secretKey, byte[] iv, byte[] encrypted) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
    }

    protected String versionedPrefix() {
        return prefix + version() + DELIMITER;
    }

    protected String encodeUrl(byte[] value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value);
    }

    protected byte[] decodeUrl(String value) {
        return Base64.getUrlDecoder().decode(value);
    }

    private byte[] decodeKey(String key) {
        if (key == null || key.isBlank()) {
            throw new CryptoException("crypto.field.key must be configured.");
        }
        // Base64 키와 원문 문자열 키 모두 허용
        byte[] decoded = tryBase64Decode(key);
        if (isValidKeyLength(decoded.length)) {
            return decoded;
        }
        byte[] raw = key.getBytes(StandardCharsets.UTF_8);
        if (isValidKeyLength(raw.length)) {
            return raw;
        }
        throw new CryptoException("crypto.field.key must be 16, 24, or 32 bytes. Base64 encoded keys are supported.");
    }

    private byte[] tryBase64Decode(String key) {
        try {
            return Base64.getDecoder().decode(key);
        } catch (IllegalArgumentException _) {
            return new byte[0];
        }
    }

    private boolean isValidKeyLength(int length) {
        return length == 16 || length == 24 || length == 32;
    }

    private Map<String, SecretKeySpec> createSecretKeys(
        String activeKeyId,
        SecretKeySpec activeKey,
        Map<String, String> previousKeys
    ) {
        Map<String, SecretKeySpec> keys = new HashMap<>();
        keys.put(activeKeyId, activeKey);
        previousKeys.forEach((previousKeyId, previousKey) -> {
            String normalizedKeyId = normalizeKeyId(previousKeyId);
            if (keys.containsKey(normalizedKeyId)) {
                throw new CryptoException("Duplicated crypto.field key-id: " + normalizedKeyId);
            }
            // 이전 키 복호화 전용 보관
            keys.put(normalizedKeyId, new SecretKeySpec(decodeKey(previousKey), ALGORITHM));
        });
        return Map.copyOf(keys);
    }

    private SecretKeySpec secretKeyOf(String keyId) {
        SecretKeySpec key = secretKeys.get(keyId);
        if (key == null) {
            throw new CryptoException("Crypto field key is not configured for key-id: " + keyId);
        }
        return key;
    }

    private String normalizeKeyId(String keyId) {
        if (keyId == null || keyId.isBlank()) {
            return "default";
        }
        if (keyId.contains(DELIMITER)) {
            throw new CryptoException("crypto.field.key-id must not contain ':'.");
        }
        return keyId;
    }
}
