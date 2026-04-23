package com.ktis.mcpif.mPlatform.event;

public interface EventRequest {
    String toBodyContentXml();

    void setEventCode(EventCode eventCode);

    EventCode getEventCode();

    void setEncryptYn(String encryptYn);

    String getEncryptYn();

    void setClntIp(String clntIp);

    void setClntUsrId(String clntUsrId);
}
