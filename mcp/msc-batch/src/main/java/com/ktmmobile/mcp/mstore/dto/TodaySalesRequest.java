package com.ktmmobile.mcp.mstore.dto;

public class TodaySalesRequest {
    private String cmpyNo;
    private String authCode;
    private String devcTpCd;

    public TodaySalesRequest() {}

    public TodaySalesRequest(String cmpyNo, String authCode, String devcTpCd) {
        this.cmpyNo = cmpyNo;
        this.authCode = authCode;
        this.devcTpCd = devcTpCd;
    }

    public String getCmpyNo() {
        return cmpyNo;
    }

    public void setCmpyNo(String cmpyNo) {
        this.cmpyNo = cmpyNo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getDevcTpCd() {
        return devcTpCd;
    }

    public void setDevcTpCd(String devcTpCd) {
        this.devcTpCd = devcTpCd;
    }
}
