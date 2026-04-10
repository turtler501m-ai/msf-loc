package com.ktmmobile.mcp.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.common.controller.OcrController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SuppressWarnings("unused")
public class Aes256 {

    private static final Logger logger = LoggerFactory.getLogger(Aes256.class);
    private final byte[] key; // 암/복호화 Key 값
    private final byte[] iv; // 암/복호화 IV 값

    public Aes256(String key, String iv){
        this.key = Base64.getDecoder().decode(key);
        this.iv = Base64.getDecoder().decode(iv);
    }

    /**
     * AES 암호화 후 Base64 인코딩
     * @param plainText 암호화 할 값(평문)
     * @return 인코딩 텍스트
     */
    public String encryptAndEncodeByBase64(String plainText){
        byte[] cipherText = encrypt(plainText);

        if(cipherText == null) return null;
        return encodeBase64(cipherText);
    }

    /**
     * Base64 인코딩
     * @param bytes 인코딩할 Bytes
     * @return 인코딩 테스트
     */
    public String encodeBase64(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * AES 암호화
     * @param plainText 암호화할 문자열(평문)
     * @return 암호화된 bytes
     */
    public byte[] encrypt(String plainText){
        byte[] cipherText;
        try {
            Cipher aes = Cipher.getInstance("AES/CTR/NoPadding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            aes.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), ivSpec);
            cipherText = aes.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            logger.error("Exception e : {}", e.getMessage());
            cipherText = null;
        }

        return cipherText;
    }

    /**
     * Aes 복호화
     * @param base64Encoded 복호화할 base64로 인코딩된 문자열
     * @return 복호화된 bytes
     */
    public byte[] decrypt(String base64Encoded){
        byte[] cipherText;
        try {
            byte[] cryptoTxt = Base64.getDecoder().decode(base64Encoded);
            Cipher aes = Cipher.getInstance("AES/CTR/NoPadding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            aes.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), ivSpec);
            cipherText = aes.doFinal(cryptoTxt);
        }catch (Exception e){
            logger.error("Exception e : {}", e.getMessage());
            cipherText = null;
        }

        return cipherText;
    }
}
