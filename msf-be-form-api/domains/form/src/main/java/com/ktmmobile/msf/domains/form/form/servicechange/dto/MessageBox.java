package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.HashMap;
import java.io.Serializable;
import java.util.Map;

public class MessageBox implements Serializable {

    private static final long serialVersionUID = 1L;


    public static enum MessageType {
        DEFAULT, CLOSE_POPUP, HISTORY_BACK
    }

    private String message = "";
    private String url = "";
    private final Map<String, Object> param;
    private MessageType messageType = MessageType.DEFAULT;

    /**
     *
     */
    public MessageBox() {
        this.param = new HashMap<String, Object>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the param
     */
    public Map<String, Object> getParam() {
        return param;
    }

    /**
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void putParam(String key, Object obj){
        param.put(key, obj);
    }

}
