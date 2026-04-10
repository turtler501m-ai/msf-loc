package com.ktmmobile.mcp.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class KakaoWebhookDto implements Serializable {
    private static final long serialVersionUID = 0x1L;

    @JsonProperty("CHAT_TYPE")
    private  String chatType;

    @JsonProperty("HASH_CHAT_ID")
    private  String hashChatId;

    @JsonProperty("TEMPLATE_ID")
    private  String templateId;
    private  String strUetSeq;

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getHashChatId() {
        return hashChatId;
    }

    public void setHashChatId(String hashChatId) {
        this.hashChatId = hashChatId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStrUetSeq() {
        return strUetSeq;
    }

    public void setStrUetSeq(String strUetSeq) {
        this.strUetSeq = strUetSeq;
    }


}
