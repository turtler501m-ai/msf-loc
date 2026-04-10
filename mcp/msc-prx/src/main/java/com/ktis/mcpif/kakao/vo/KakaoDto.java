package com.ktis.mcpif.kakao.vo;

import java.io.Serializable;

public class KakaoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userName; // 고객명
    private String phone_No;    // 고객 전화번호
    private String birthday;    // 고객생년월일
    private String certifyType;  // 카카오인증 상품코드
    private String txId;    //전자서명원문접수번호
    private String result; //서명요쳥 결과여부
    private String status;
    private String request_type;
    private String data;
    private String createdAt;
    private String expiredAt;


    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getExpiredAt() {
        return expiredAt;
    }
}

