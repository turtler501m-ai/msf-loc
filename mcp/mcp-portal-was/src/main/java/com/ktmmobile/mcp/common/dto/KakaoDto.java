package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;

public class KakaoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name; // 고객명
    private String phone_No;    // 고객 전화번호
    private String birthday;    // 고객생년월일
    private String certifyType;  // 카카오인증 상품코드
    private String txId;    //전자서명원문접수번호
    private String result; //서명요쳥 결과여부
    private String status; // 서명 상태
    private String request_type; // 서명요청명 ex) '본인인증요청'
    private String data;    // 임의의 난수
    private String msg; // 결과 
    private String createdAt; // 서명 요청 일시
    private String viewedAt; // 사용자의 서명 요청 확인 일시
    private String completedAt; // 사용자의 서명 완료 일시
    private String expiredAt; // 서명 만료 일시
    private String partnerNotifiedAt; // 기관에서 서명 검증 Api 전달받은 일시
    private String requestToken;
    private String connInfo;
    
    

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setCertifyType(String certifyType) {
        this.certifyType = certifyType;
    }

    public String getCertifyType() {
        return certifyType;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getTxId() {
        return txId;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }

    public String getViewedAt() {
        return viewedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setPartnerNotifiedAt(String partnerNotifiedAt) {
        this.partnerNotifiedAt = partnerNotifiedAt;
    }

    public String getPartnerNotifiedAt() {
        return partnerNotifiedAt;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setConnInfo(String connInfo) {
        this.connInfo = connInfo;
    }

    public String getConnInfo() {
        return connInfo;
    }
}

