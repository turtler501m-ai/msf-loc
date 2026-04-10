package com.ktmmobile.mcp.push.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class PushSndProcTrgtDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pushSndProcSno;	//푸쉬발송처리일련번호
    private String pushSndSetSno;	//푸쉬발송설정일련번호	실시간발송 이력 위해 관리
    private String userId;			//고객 ID
    private String appOsTp;			//단말기OS유형	A:Android, I:IOS
    private String procSt;			//처리상태	o 코드값(Y : 발송 / N : 대기 / X : 오류)
    private String sndProcDt;		//발송처리일자
    private String sndTitle = "";		//발송제목
    private String sndMsg = "";			//발송메시지
    private String pushImgUrl = "";		//푸쉬이미지경로	NAS 이미지 경로
    private String linkUrl = "";			//링크URL
    private int sndCnt;			//발송횟수	default: 1 , 최대 5회 재발송
    private String sndTestYn;		//발송테스트여부	o 코드값(Y : 테스트발송 / N : 실제발송)

    private String udid;
    private String os;
    private String sendYn;
    private String lastAccessDt;
    private String currentStat;
    private int minCnt;
    private int maxCnt;
    private int pushSeq;
    private List<String> tokens;

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

    private List<String> prevTelCodeList;

    public String getPushSndProcSno() {
        return pushSndProcSno;
    }
    public void setPushSndProcSno(String pushSndProcSno) {
        this.pushSndProcSno = pushSndProcSno;
    }
    public String getPushSndSetSno() {
        return pushSndSetSno;
    }
    public void setPushSndSetSno(String pushSndSetSno) {
        this.pushSndSetSno = pushSndSetSno;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAppOsTp() {
        return appOsTp;
    }
    public void setAppOsTp(String appOsTp) {
        this.appOsTp = appOsTp;
    }
    public String getProcSt() {
        return procSt;
    }
    public void setProcSt(String procSt) {
        this.procSt = procSt;
    }
    public String getSndProcDt() {
        return sndProcDt;
    }
    public void setSndProcDt(String sndProcDt) {
        this.sndProcDt = sndProcDt;
    }
    public String getSndTitle() {
        return sndTitle;
    }
    public void setSndTitle(String sndTitle) {
        this.sndTitle = sndTitle;
    }
    public String getSndMsg() {
        return sndMsg;
    }
    public void setSndMsg(String sndMsg) {
        this.sndMsg = sndMsg;
    }
    public String getPushImgUrl() {
        return pushImgUrl;
    }
    public void setPushImgUrl(String pushImgUrl) {
        this.pushImgUrl = pushImgUrl;
    }
    public String getLinkUrl() {
        return linkUrl;
    }
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
    public String getSndTestYn() {
        return sndTestYn;
    }
    public void setSndTestYn(String sndTestYn) {
        this.sndTestYn = sndTestYn;
    }
    public String getUdid() {
        return udid;
    }
    public void setUdid(String udid) {
        this.udid = udid;
    }
    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public String getSendYn() {
        return sendYn;
    }
    public void setSendYn(String sendYn) {
        this.sendYn = sendYn;
    }
    public String getLastAccessDt() {
        return lastAccessDt;
    }
    public void setLastAccessDt(String lastAccessDt) {
        this.lastAccessDt = lastAccessDt;
    }
    public void setSndCnt(int sndCnt) {
        this.sndCnt = sndCnt;
    }
    public int getSndCnt() {
        return sndCnt;
    }

    public void setCurrentStat(String currentStat) {
        this.currentStat = currentStat;
    }

    public String getCurrentStat() {
        return currentStat;
    }

    public void setMinCnt(int minCnt) {
        this.minCnt = minCnt;
    }

    public int getMinCnt() {
        return minCnt;
    }

    public void setMaxCnt(int maxCnt) {
        this.maxCnt = maxCnt;
    }

    public int getMaxCnt() {
        return maxCnt;
    }

    public void setPushSeq(int pushSeq) {
        this.pushSeq = pushSeq;
    }

    public int getPushSeq() {
        return pushSeq;
    }
    public List<String> getTokens() {
        return tokens;
    }
    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
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
