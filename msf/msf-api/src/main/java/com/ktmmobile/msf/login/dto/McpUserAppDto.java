package com.ktmmobile.msf.login.dto;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.ACE_APP_DECRYPT_EXCEPTION;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.util.AppEncryptUtils;
import com.ktmmobile.msf.system.common.xss.RequestWrapper;

public class McpUserAppDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String passWord;

    public String getUserId() {

        if (StringUtils.isBlank(userId)) {
            return "";
        } else {
            try {
                return RequestWrapper.cleanXSS(AppEncryptUtils.aesDec(userId));
            } catch(IllegalArgumentException e) {
                throw new McpCommonException(ACE_APP_DECRYPT_EXCEPTION + "[IllegalArgumentException]");
            } catch (Exception e) {
                throw new McpCommonException(ACE_APP_DECRYPT_EXCEPTION);
            }
        }

    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassWord() {

        if (StringUtils.isBlank(passWord)) {
            return "";
        } else {
            try {
                return RequestWrapper.cleanXSS(AppEncryptUtils.aesDec(passWord));
            } catch(IllegalArgumentException e) {
                throw new McpCommonException(ACE_APP_DECRYPT_EXCEPTION + "[IllegalArgumentException]");
            }  catch (Exception e) {
                throw new McpCommonException(ACE_APP_DECRYPT_EXCEPTION);
            }
        }
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }






}
