package com.ktmmobile.msf.system.common.dto;

import java.io.Serializable;
import java.util.Map;

public class JsonReturnDto implements Serializable  {

    private static final long serialVersionUID = 1L;


    private String message;
    private String returnCode;
    private Map<String,Object> resultMap;
    private Object result;
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return the returnCode
     */
    public String getReturnCode() {
        return returnCode;
    }
    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    /**
     * @return the result
     */
    public Object getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }
    public Map<String, Object> getResultMap() {
        return resultMap;
    }
    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }



}
