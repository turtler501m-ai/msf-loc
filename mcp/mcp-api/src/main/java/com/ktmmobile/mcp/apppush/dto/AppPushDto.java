package com.ktmmobile.mcp.apppush.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppPushDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(AppPushDto.class);

    /*앱푸시 어드민 발송일자*/
    private String pushSndDd;

    /*앱푸시 어드민 제목*/
    private String pushTitle;

    /*앱푸시 어드민 내용*/
    private String pushMsg;

    /*앱푸시 어드민 링크*/
    private String pushLink;

    /*앱푸시 어드민 이전통신사*/
    private String prevTelCode;

    /*앱푸시 어드민 발송현황*/
    private String pushStat;

    /*앱푸시 성공 건수*/
    private long successCount;

    /*앱푸시 실패 건수*/
    private long failCount;

    /*앱푸시 어드민 등록아이디*/
    private String cretId;

    /*앱푸시 어드민 수정아이디*/
    private String amdId;

    /*앱푸시 어드민 등록일시*/
    private Date cretDt;

    /*앱푸시 어드민 수정일시*/
    private Date amdDt;

    /*앱푸시 어드민 등록 아이피*/
    private String rip;

    /*앱푸시 어드민 이전통신사 리스트*/
    private List<String> prevTelCodeList;

    public String getPushSndDd() {
        return pushSndDd;
    }
    public void setPushSndDd(String pushSndDd) {
        this.pushSndDd = pushSndDd;
    }
    public String getPushTitle() {
        return pushTitle;
    }
    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }
    public String getPushMsg() {
        return pushMsg;
    }
    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }
    public String getPushLink() {
        return pushLink;
    }
    public void setPushLink(String pushLink) {
        this.pushLink = pushLink;
    }
    public String getPrevTelCode() {
        return prevTelCode;
    }
    public void setPrevTelCode(String prevTelCode) {
        this.prevTelCode = prevTelCode;
        this.prevTelCodeList = (prevTelCode == null || prevTelCode.isEmpty()) ? Collections.emptyList(): Arrays.asList(prevTelCode.split(","));
    }
    public String getPushStat() {
        return pushStat;
    }
    public void setPushStat(String pushStat) {
        this.pushStat = pushStat;
    }
    public long getSuccessCount() {
        return successCount;
    }
    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }
    public long getFailCount() {
        return failCount;
    }
    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public Date getCretDt() {
        return cretDt;
    }
    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }
    public Date getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }
    public String getRip() {
        return rip;
    }
    public void setRip(String rip) {
        this.rip = rip;
    }
    public List<String> getPrevTelCodeList() {
        return prevTelCodeList;
    }
}
