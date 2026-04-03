package com.ktmmobile.msf.system.common.util;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.ACE_APP_DECRYPT_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.ACE_APP_ENC_EXCEPTION;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.system.common.exception.McpCommonException;

@Component
public class AppEncryptUtils {


    private static final String ALGO = "AES";
    private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    private static String iv ="0102030405060708";
    private static String aesLock;


    @Value("${aes.app.aesLock}")
    public void setAesLock(String value) {
        aesLock = value;
    }


    public static String getAesLock() {
        try {
            return EncryptUtil.ace256Dec(aesLock);
        } catch (CryptoException e) {
            return "" ;
        }

    }

    /**
     * <pre>
     * 설명     : AES 방식을 이용해서 입력받은 문자열을 암호화 처리한다.
     *            BASE64 Encoding  에따른 '+' 기호 치환 문제가 있기때문에
     *            ajax 요청이나 ,get 방식일경우 + 기호의 공백문자 치환을 처리해줘야된다.(urlencoding 처리)
     *            form submit 에 의한 post 전송일경우 상관없음
     * @param data 처리할 문자열
     * @return 암호화된 문자열
     * </pre>
     */
    public static String aesEnc(String data) {
        try {
            if (StringUtils.isBlank(data)) {
                return data;
            }

            String aesLockDesc = getAesLock();
            Key key = new SecretKeySpec(aesLockDesc.getBytes("8859_1"), ALGO);  ///<======
            byte[] ivBytes = iv.getBytes("UTF-8");
            Cipher c = Cipher.getInstance(AES_CBC_PKCS5);
            c.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
            byte[] encVal = c.doFinal(data.getBytes("utf-8"));
            String encryptedValue = StringUtil.encodeBase64String(encVal);
            return encryptedValue;


        } catch (Exception e) {
            throw new McpCommonException(ACE_APP_ENC_EXCEPTION);
        }
    }

    /**
     * <pre>
     * 설명     : AES방식으로 암호회된 문자열을 복원한다.
     *            BASE64 Encoding  에따른 '+' 기호 치환 문제가 있기때문에
     *            ajax 요청이나 ,get 방식일경우  기호의 공백문자 치환을 처리해줘야된다.(urlencoding 처리)
     *            form submit 에 의한 post 전송일경우 상관없음
     * @param encryptedData 암호화된 난수 문자열
     * @return 복원된 문자열 값
     * </pre>
     */
    public static String aesDec(String encryptedDataParam) {
        try {
            if (StringUtils.isBlank(encryptedDataParam)) {
                return encryptedDataParam;
            }

            String encryptedData = StringUtil.decodeXssTag(encryptedDataParam);
            encryptedData = encryptedData.replace(" ", "+");

            String aesLockDesc = getAesLock();


            Key key = new SecretKeySpec(aesLockDesc.getBytes("8859_1"), ALGO);///<======
            key = new SecretKeySpec(aesLockDesc.getBytes("8859_1"), ALGO);///<======
            byte[] ivBytes = iv.getBytes("UTF-8");
            Cipher c = Cipher.getInstance(AES_CBC_PKCS5);
            c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));

            byte[] decordedValue = StringUtil.decodeBase64(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue, "utf-8");
            return decryptedValue;
        } catch (Exception e) {
            throw new McpCommonException(ACE_APP_DECRYPT_EXCEPTION);
        }
    }






}
