package com.ktmmobile.mcp.point.service;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.ACE_256_ENC_EXCEPTION;

@Component
public class MstoreAES256 {
	
	private static final Logger logger = LoggerFactory.getLogger(MstoreAES256.class);
	
	static {
		fixKeyLength(); 
	}
    
    public static String encrypt(String strToEncrypt, String myKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");   
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(myKey.getBytes("UTF-8"), "AES"));
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        }
        return null;
    }
    
    public static String decrypt(String strToDecrypt,String myKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(myKey.getBytes("UTF-8"), "AES"));
            return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
	public static void fixKeyLength() {
	    String errorString = "Failed manually overriding key-length permissions.";
	    int newMaxKeyLength;
	    try {
	        if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
	            Class<?> c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
	            Constructor<?> con = c.getDeclaredConstructor();
	            con.setAccessible(true);
	            Object allPermissionCollection = con.newInstance();
	            Field f = c.getDeclaredField("all_allowed");
	            f.setAccessible(true);
	            f.setBoolean(allPermissionCollection, true);

	            c = Class.forName("javax.crypto.CryptoPermissions");
	            con = c.getDeclaredConstructor();
	            con.setAccessible(true);
	            Object allPermissions = con.newInstance();
	            f = c.getDeclaredField("perms");
	            f.setAccessible(true);
	            ((Map<String, Object>) f.get(allPermissions)).put("*", allPermissionCollection);

	            c = Class.forName("javax.crypto.JceSecurityManager");
	            f = c.getDeclaredField("defaultPolicy");
	            f.setAccessible(true);
	            Field mf = Field.class.getDeclaredField("modifiers");
	            mf.setAccessible(true);
	            mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
	            f.set(null, allPermissions);

	            newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
	        }
	    } catch (Exception e) {
			throw new McpCommonException(ACE_256_ENC_EXCEPTION);
	    }
	    if (newMaxKeyLength < 256)
	        throw new McpCommonException(errorString); // hack failed
	}

}
