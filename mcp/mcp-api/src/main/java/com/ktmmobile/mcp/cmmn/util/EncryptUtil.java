package com.ktmmobile.mcp.cmmn.util;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.ACE_256_ENC_EXCEPTION;
import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;

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

    private static final String SALT_KEY = "2013070198765432";

    /** aes256Key */
    private static final String AEC_256_KEY ="S3QxMTQ2NTQ1MDExNElTMzc3NjQ5NDkwMTBNb2JpbGU=";
    private static final String AEC_256__SALT_KEY ="AAAAAAAAAAAAAAAAAAAAAA==";




    /**
     * <pre>
     * 설명     : SHA-512 해시 알고리즘을 이용한 암화화 처리 메소드 주로 고객이입력한 패스워드 암호촤 처리시 사용.
     * @param msg 암호화 대상 메시지
     * @return 128자리 16진수 스트링문자열
     * </pre>
     */
    public static String sha512HexEnc(String msg) {
        String encMsg = "";

        if (msg != null && !msg.equals("")) {
            encMsg = DigestUtils.sha512Hex(msg + SALT_KEY) ;
        }
        return encMsg ;
    }



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
            throw new McpCommonJsonException(COMMON_EXCEPTION);
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
            throw new McpCommonJsonException(ACE_256_ENC_EXCEPTION);
        } catch (NoSuchPaddingException e) {
            throw new McpCommonJsonException(ACE_256_ENC_EXCEPTION);
        } catch (InvalidKeyException e) {
            throw new McpCommonJsonException(ACE_256_ENC_EXCEPTION);
        } catch (InvalidAlgorithmParameterException e) {
            throw new McpCommonJsonException(ACE_256_ENC_EXCEPTION);
        } catch (IllegalBlockSizeException e) {
            throw new McpCommonJsonException(ACE_256_ENC_EXCEPTION);
        } catch (BadPaddingException e) {
            throw new McpCommonJsonException(ACE_256_ENC_EXCEPTION);
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
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return plainText;
    }

    public static byte[] ace256Dec(byte[] text) throws CryptoException {
        SecretKey secureKey = new SecretKeySpec(Base64.decodeBase64(AEC_256_KEY), "AES");
        byte[] data = null;
        
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(2, secureKey, new IvParameterSpec(Base64.decodeBase64(AEC_256__SALT_KEY)));

            data = c.doFinal(text);
        /*    
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
        */
            
        } catch (NoSuchAlgorithmException e) {
            throw new McpCommonJsonException(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new McpCommonJsonException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new McpCommonJsonException(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new McpCommonJsonException(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new McpCommonJsonException(e.getMessage());
        } catch (BadPaddingException e) {
            throw new McpCommonJsonException(e.getMessage());
        } catch (Exception e) {
            throw new McpCommonJsonException(e.getMessage());
        }
        
        return data;
    }
}