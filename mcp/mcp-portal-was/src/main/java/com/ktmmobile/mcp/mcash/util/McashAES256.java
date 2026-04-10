package com.ktmmobile.mcp.mcash.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class McashAES256 {
    public static String alg = "AES/CBC/PKCS5Padding";
    private static String key;
    private static  String iv; // 16byte

    @Value("${mcash.aes256.key}")
    public void setKey(String key){
        this.key = key;
    }

    @Value("${mcash.aes256.iv}")
    public void setIv(String iv){
        this.iv = iv;
    }

    public static String encrypt(String text) throws GeneralSecurityException,UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.encodeBase64String(encrypted);
    }

    public static String decrypt(String cipherText) throws GeneralSecurityException,UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decrypted = cipher.doFinal(Base64.decodeBase64(cipherText));
        return new String(decrypted, "UTF-8");
    }
}
