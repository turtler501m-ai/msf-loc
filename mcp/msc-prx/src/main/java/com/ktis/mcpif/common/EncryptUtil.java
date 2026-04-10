package com.ktis.mcpif.common;

import com.ktds.crypto.exception.CryptoException;
import com.ktis.mcpif.common.exception.McpCommonException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;



/**
 * <pre>
 * 파일명   : EncryptUtil.java
 * 날짜     : 2016. 01. 05.
 * 작성자   : ywc
 * 설명     :  암호화 관련 유틸성 클래스
 *        -SHA-256(단방향 암호화 처리 ,복호화처리안됨),주로 패스워드 암호화에 이용
 * </pre>
 */
public class EncryptUtil {

    /** aes256Key */
    private static final String AEC_256_KEY ="S3QxMTQ2NTQ1MDExNElTMzc3NjQ5NDkwMTBNb2JpbGU=";
    private static final String AEC_256__SALT_KEY ="AAAAAAAAAAAAAAAAAAAAAA==";

    /** msg **/
    public static final String COMMON_EXCEPTION = "서비스 처리중 오류가 발생 하였습니다.";
    public static final String ACE_256_ENC_EXCEPTION = "암호화(ace256Enc) 오류 발생 ";

    /**
     * <pre>
     * 설명     :  AES256Encryptor  DBMSEnc
     * @param  :
     * @return :
     * </pre>
     */
    public static String ace256Enc(String text)  {
        byte[] data = null;
        try {
            data = ace256Enc(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }
        return new String(Base64.encodeBase64(data));
    }

    public static byte[] ace256Enc(byte[] text)  {
        SecretKey secureKey = new SecretKeySpec(Base64.decodeBase64(AEC_256_KEY),"AES");

        byte[] data = null;
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(1, secureKey,new IvParameterSpec(Base64.decodeBase64(AEC_256__SALT_KEY)));
            data = c.doFinal(text);
        } catch (NoSuchAlgorithmException e) {
            throw new McpCommonException(ACE_256_ENC_EXCEPTION);
        } catch (NoSuchPaddingException e) {
            throw new McpCommonException(ACE_256_ENC_EXCEPTION);
        } catch (InvalidKeyException e) {
            throw new McpCommonException(ACE_256_ENC_EXCEPTION);
        } catch (InvalidAlgorithmParameterException e) {
            throw new McpCommonException(ACE_256_ENC_EXCEPTION);
        } catch (IllegalBlockSizeException e) {
            throw new McpCommonException(ACE_256_ENC_EXCEPTION);
        } catch (BadPaddingException e) {
            throw new McpCommonException(ACE_256_ENC_EXCEPTION);
        }
        return data;
    }


    /**
     * <pre>
     * 설명     :  AES256Decryptor  DBMSDec
     * @param  :
     * @return :
     * </pre>
     */
    public static String ace256Dec(String text) throws CryptoException {
        if (StringUtils.isBlank(text)) {
            return "";
        }

        byte[] data = Base64.decodeBase64(text.getBytes());
        data = ace256Dec(data);
        String plainText = "";
        try {
            plainText = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }
        return plainText;
    }

    public static byte[] ace256Dec(byte[] text) throws CryptoException {
        SecretKey secureKey = new SecretKeySpec(Base64.decodeBase64(AEC_256_KEY),"AES");
        byte[] data = null;
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(2, secureKey,new IvParameterSpec(Base64.decodeBase64(AEC_256__SALT_KEY)));

            data = c.doFinal(text);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new CryptoException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new CryptoException(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new CryptoException(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException(e.getMessage());
        } catch (BadPaddingException e) {
            throw new CryptoException(e.getMessage());
        } catch (Exception e) {
            throw new CryptoException(e.getMessage());
        }
        return data;
    }

    public static String ace256NoPaddingEnc(String key, String iv, String textStr)  {
        byte[] ivArry = Base64.decodeBase64(iv);
        byte[] keyArry = Base64.decodeBase64(key);
        byte[] dataText;
        try {
            Cipher aes = Cipher.getInstance("AES/CTR/NoPadding");
            IvParameterSpec ivSpec = new IvParameterSpec(ivArry);

            aes.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArry, "AES"), ivSpec);
            dataText = aes.doFinal(textStr.getBytes("utf-8"));
        }catch (Exception e){
            e.printStackTrace();
            dataText = null;
        }
        String encodeText = null;
        if (dataText != null) {
            encodeText = Base64.encodeBase64String(dataText);
        }
        return dataText == null ? "" : encodeText;
    }

    public static String ace256NoPaddingDec(String key, String iv, String textStr) throws InvalidAlgorithmParameterException, InvalidKeyException {

        byte[] ivArry = Base64.decodeBase64(iv);
        byte[] keyArry = Base64.decodeBase64(key);

        byte[] data = Base64.decodeBase64(textStr.getBytes());
        String plainText = "";
        SecretKey secureKey = new SecretKeySpec(Base64.decodeBase64(key),"AES");
        try {
            Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
            c.init(2, secureKey,new IvParameterSpec(Base64.decodeBase64(iv )));
            try {
                data = c.doFinal(data);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } finally {
            if (data != null) {
                try {
                    plainText = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new McpCommonException(COMMON_EXCEPTION);
                }
            }
        }
        return plainText;

    }
}
