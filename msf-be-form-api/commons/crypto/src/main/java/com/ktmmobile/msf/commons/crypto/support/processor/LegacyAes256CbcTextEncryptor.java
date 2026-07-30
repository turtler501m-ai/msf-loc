package com.ktmmobile.msf.commons.crypto.support.processor;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

/**
 * 레거시 AES-256-CBC 암복호화 호환 처리기
 *
 * <p>AES/CBC/PKCS5Padding + Base64 포맷 사용</p>
 */
@SuppressWarnings({
    "java:S3329", // 레거시 암호문 호환용 고정 IV 사용
    "java:S5542"  // 레거시 암호문 호환용 AES/CBC/PKCS5Padding 유지
})
public class LegacyAes256CbcTextEncryptor implements TextEncryptor {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH_BYTES = 16;

    private final SecretKeySpec secretKey;
    private final IvParameterSpec ivParameterSpec;

    /** 레거시 AES 키/IV 기반 암복호화기 초기화 */
    public LegacyAes256CbcTextEncryptor(String key, String iv) {
        this.secretKey = new SecretKeySpec(decodeKey(key), ALGORITHM);
        this.ivParameterSpec = new IvParameterSpec(decodeIv(iv));
    }

    /** 고정 IV 기반 Base64 암호문 생성 */
    @Override
    public String encrypt(String plainText) {
        if (plainText == null) {
            return null;
        }
        if (plainText.isBlank()) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 기존 MSP/MCP 동일 고정 IV 사용
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec); // NOSONAR
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (GeneralSecurityException e) {
            throw new CryptoException("Legacy AES-256-CBC field encryption failed.", e);
        }
    }

    /** 레거시 AES-CBC Base64 암호문 복호화 */
    @Override
    public String decrypt(String cipherText) {
        if (cipherText == null) {
            return null;
        }
        if (cipherText.isBlank()) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException | GeneralSecurityException e) {
            throw new CryptoException("Legacy AES-256-CBC field decryption failed.", e);
        }
    }

    private byte[] decodeKey(String key) {
        if (key == null || key.isBlank()) {
            throw new CryptoException("crypto.field.legacy-aes256.key must be configured.");
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
        throw new CryptoException("crypto.field.legacy-aes256.key must be 16, 24, or 32 bytes. Base64 encoded keys are supported.");
    }

    private byte[] decodeIv(String iv) {
        if (iv == null || iv.isBlank()) {
            throw new CryptoException("crypto.field.legacy-aes256.iv must be configured.");
        }
        // Base64 IV와 16바이트 원문 문자열 IV 모두 허용
        byte[] decoded = tryBase64Decode(iv);
        if (decoded.length == IV_LENGTH_BYTES) {
            return decoded;
        }
        byte[] raw = iv.getBytes(StandardCharsets.UTF_8);
        if (raw.length == IV_LENGTH_BYTES) {
            return raw;
        }
        throw new CryptoException("crypto.field.legacy-aes256.iv must be 16 bytes. Base64 encoded IVs are supported.");
    }

    private byte[] tryBase64Decode(String value) {
        try {
            return Base64.getDecoder().decode(value);
        } catch (IllegalArgumentException _) {
            return new byte[0];
        }
    }

    private boolean isValidKeyLength(int length) {
        return length == 16 || length == 24 || length == 32;
    }
}
