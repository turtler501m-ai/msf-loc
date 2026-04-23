package com.ktis.mcpif.mPlatform.event;

import static com.ktis.mcpif.common.KisaSeedUtil.encryptValue;

public abstract class BaseRequest implements EventRequest {
    protected EventCode eventCode;
    protected String encryptYn;

    private String clntIp;
    private String clntUsrId;
    private String custId;
    private String ncn;
    private String ctn;

    public String getClntIp() {
        return clntIp;
    }

    @Override
    public EventCode getEventCode() {
        return eventCode;
    }

    @Override
    public void setEventCode(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    public String getEncryptYn() {
        return encryptYn;
    }

    public void setEncryptYn(String encryptYn) {
        this.encryptYn = encryptYn;
    }

    public void setClntIp(String clntIp) {
        this.clntIp = clntIp;
    }

    public String getClntUsrId() {
        return clntUsrId;
    }

    public void setClntUsrId(String clntUsrId) {
        this.clntUsrId = clntUsrId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getNcn() {
        return ncn;
    }

    public void setNcn(String ncn) {
        this.ncn = ncn;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public abstract String toBodyContentXml();

    protected StringBuilder toSelfCareInXml() {
        return new StringBuilder()
                .append("<selfCareIn>")
                .append("<clntIp>").append(this.clntIp).append("</clntIp>")
                .append("<clntUsrId>").append(this.clntUsrId).append("</clntUsrId>")
                .append("<custId>").append(this.custId).append("</custId>")
                .append("<ncn>").append(this.ncn).append("</ncn>")
                .append("<ctn>").append(encryptValue(this.ctn, this.encryptYn)).append("</ctn>")
                .append("</selfCareIn>");
    }
}
