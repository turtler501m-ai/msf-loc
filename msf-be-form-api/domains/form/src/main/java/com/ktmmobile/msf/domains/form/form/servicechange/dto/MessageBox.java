package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MessageBox implements Serializable {

    private static final long serialVersionUID = 1L;

    public static enum MessageType {
        DEFAULT, CLOSE_POPUP, HISTORY_BACK
    }

    private String message = "";
    private String url = "";
    private final Map<String, Object> param = new HashMap<String, Object>();
    private MessageType messageType = MessageType.DEFAULT;

    public void putParam(String key, Object obj){
        param.put(key, obj);
    }

}
