package com.ktis.mcpif.soap;

import com.ktis.mcpif.mPlatform.event.EventCode;

public class XmlWrapperParameter {
    private String appEntrPrsnId;
    private String appAgncCd;
    private String appSendDateTime;
    private String appNstepUserId;
    private String bodyContent;

    private EventCode eventCode;

    public String getAppEntrPrsnId() {
        return appEntrPrsnId;
    }

    public void setAppEntrPrsnId(String appEntrPrsnId) {
        this.appEntrPrsnId = appEntrPrsnId;
    }

    public String getAppAgncCd() {
        return appAgncCd;
    }

    public void setAppAgncCd(String appAgncCd) {
        this.appAgncCd = appAgncCd;
    }

    public String getAppSendDateTime() {
        return appSendDateTime;
    }

    public void setAppSendDateTime(String appSendDateTime) {
        this.appSendDateTime = appSendDateTime;
    }

    public String getAppNstepUserId() {
        return appNstepUserId;
    }

    public void setAppNstepUserId(String appNstepUserId) {
        this.appNstepUserId = appNstepUserId;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    public EventCode getEventCode() {
        return eventCode;
    }

    public void setEventCode(EventCode eventCode) {
        this.eventCode = eventCode;
    }
}
