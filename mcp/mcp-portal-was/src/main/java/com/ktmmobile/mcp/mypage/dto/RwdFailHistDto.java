package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;


public class RwdFailHistDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long histSeq;    /* 순번 */
    private long requestKey;  /* 요청 키 */
    private String resNo;  /* 예약번호 */
    private String contractNum;  /* 계약 번호 */
    private String sysDate;  /* 등록일시 */
    private String userId;  /* 회원 아이디 */
    private String failRsn;  /* 실패 이유 */
    private String accessUrl;  /* 접속 URL */
    private String accessIp;  /* 접속 IP */


    public long getHistSeq() {
        return histSeq;
    }

    public void setHistSeq(long histSeq) {
        this.histSeq = histSeq;
    }

    public long getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFailRsn() {
        return failRsn;
    }

    public void setFailRsn(String failRsn) {
        this.failRsn = failRsn;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

}
