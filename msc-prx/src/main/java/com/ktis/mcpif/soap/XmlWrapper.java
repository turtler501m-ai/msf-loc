package com.ktis.mcpif.soap;

import com.ktis.mcpif.mPlatform.event.EventCode;

public class XmlWrapper {
    private final String appEntrPrsnId;
    private final String appAgncCd;
    private final String appSendDateTime;
    private final String appNstepUserId;
    private final String bodyContent;
    private final EventCode eventCode;

    public XmlWrapper(XmlWrapperParameter xmlWrapperParameter) {
        this.appEntrPrsnId = xmlWrapperParameter.getAppEntrPrsnId();
        this.appAgncCd = xmlWrapperParameter.getAppAgncCd();
        this.eventCode = xmlWrapperParameter.getEventCode();
        this.appSendDateTime = xmlWrapperParameter.getAppSendDateTime();
        this.appNstepUserId = xmlWrapperParameter.getAppNstepUserId();
        this.bodyContent = xmlWrapperParameter.getBodyContent();
    }

    public String buildXml() {
        return new StringBuilder()
                .append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
                .append("xmlns:").append(this.eventCode.getNamespace().getPrefix()).append("=\"").append(this.eventCode.getNamespace().getUri()).append("\">")
                .append("<soapenv:Header/>")
                .append("<soapenv:Body>")
                .append("<").append(eventCode.getNamespace().getPrefix()).append(":").append(eventCode.getInfo()).append(">")
                .append("<").append(eventCode.getVo()).append(">")
                .append(this.bizHeader())
                .append(this.commHeader())
                .append(bodyContent)
                .append("</").append(eventCode.getVo()).append(">")
                .append("</").append(eventCode.getNamespace().getPrefix()).append(":").append(eventCode.getInfo()).append(">")
                .append("</soapenv:Body>")
                .append("</soapenv:Envelope>")
                .toString();
    }

    private StringBuilder bizHeader() {
        return new StringBuilder()
                .append("<bizHeader>")
                .append("<appEntrPrsnId>").append(this.appEntrPrsnId).append("</appEntrPrsnId>")
                .append("<appAgncCd>").append(this.appAgncCd).append("</appAgncCd>")
                .append("<appEventCd>").append(this.eventCode.name()).append("</appEventCd>")
                .append("<appSendDateTime>").append(this.appSendDateTime).append("</appSendDateTime>")
                .append("<appRecvDateTime></appRecvDateTime>")
                .append("<appLgDateTime></appLgDateTime>")
                .append("<appNstepUserId>").append(this.appNstepUserId).append("</appNstepUserId>")
                .append("<appOrderId></appOrderId>")
                .append("</bizHeader>");
    }

    private StringBuilder commHeader() {
        return new StringBuilder()
                .append("<commHeader>")
                .append("<globalNo>").append(this.appNstepUserId).append(this.appSendDateTime).append("</globalNo>")
                .append("<encYn></encYn>")
                .append("<responseType></responseType>")
                .append("<responseCode></responseCode>")
                .append("<responseLogcd></responseLogcd>")
                .append("<responseTitle></responseTitle>")
                .append("<responseBasic></responseBasic>")
                .append("<langCode></langCode>")
                .append("<filler></filler>")
                .append("</commHeader>");
    }
}
