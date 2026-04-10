package com.ktmmobile.mcp.common.dto;

import java.util.Date;

public class NaverDto {

    private String clientId;
    private String clientSecret;
    private String code;
    private String state;
    private String redirectURI;

    private String accessToken;
    private String tokenType;

    private String txId;
    private String id;

    private String platform;
    private String callbackPageUrl;
    private String callCenterNo;
    private String remoteAddress;
    private String name;
    private Date sysRdate;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCallbackPageUrl() {
        return callbackPageUrl;
    }
    public void setCallbackPageUrl(String callbackPageUrl) {
        this.callbackPageUrl = callbackPageUrl;
    }
    public String getCallCenterNo() {
        return callCenterNo;
    }
    public void setCallCenterNo(String callCenterNo) {
        this.callCenterNo = callCenterNo;
    }
    public String getRemoteAddress() {
        return remoteAddress;
    }
    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTxId() {
        return txId;
    }
    public void setTxId(String txId) {
        this.txId = txId;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getRedirectURI() {
        return redirectURI;
    }
    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }



}
