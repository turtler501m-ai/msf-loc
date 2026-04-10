package com.ktmmobile.mcp.common.util;

import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

public class EncryptUtil {

	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	private static final String AEC_256_KEY ="S3QxMTQ2NTQ1MDExNElTMzc3NjQ5NDkwMTBNb2JpbGU=";
	private static final String AEC_256__SALT_KEY ="AAAAAAAAAAAAAAAAAAAAAA==";

	public static String ace256Enc(String text)  {

		if (StringUtils.isBlank(text)) {
			return "";
		}

		byte[] data = null;
		try {
			data = ace256Enc(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new McpCommonJsonException("-1", ACE_256_ENC_EXCEPTION);
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
			throw new McpCommonJsonException("-1", ACE_256_ENC_EXCEPTION);
		} catch (NoSuchPaddingException e) {
			throw new McpCommonJsonException("-1", ACE_256_ENC_EXCEPTION);
		} catch (InvalidKeyException e) {
			throw new McpCommonJsonException("-1", ACE_256_ENC_EXCEPTION);
		} catch (InvalidAlgorithmParameterException e) {
			throw new McpCommonJsonException("-1", ACE_256_ENC_EXCEPTION);
		} catch (IllegalBlockSizeException e) {
			throw new McpCommonJsonException("-1", ACE_256_ENC_EXCEPTION);
		} catch (BadPaddingException e) {
			throw new McpCommonJsonException("-1", ACE_256_ENC_EXCEPTION);
		}
		return data;
	}


	public static String ace256Dec(String text) {
		if (StringUtils.isBlank(text)) {
			return "";
		}

		byte[] data = Base64.decodeBase64(text.getBytes());
		data = ace256Dec(data);
		String plainText = "";
		try {
			plainText = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		}
		return plainText;
	}

	public static byte[] ace256Dec(byte[] text) {
		SecretKey secureKey = new SecretKeySpec(Base64.decodeBase64(AEC_256_KEY),
			"AES");
		byte[] data = null;
		try {
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(2, secureKey,
				new IvParameterSpec(Base64.decodeBase64(AEC_256__SALT_KEY)));

			data = c.doFinal(text);
		} catch (NoSuchAlgorithmException e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		} catch (NoSuchPaddingException e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		} catch (InvalidKeyException e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		} catch (InvalidAlgorithmParameterException e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		} catch (IllegalBlockSizeException e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		} catch (BadPaddingException e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		} catch (Exception e) {
			throw new McpCommonJsonException("-2", ACE_256_DECRYPT_EXCEPTION);
		}
		return data;
	}

    public static String sha256Hash(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new McpCommonJsonException("-1", COMMON_EXCEPTION);
        }
        byte[] hashBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
