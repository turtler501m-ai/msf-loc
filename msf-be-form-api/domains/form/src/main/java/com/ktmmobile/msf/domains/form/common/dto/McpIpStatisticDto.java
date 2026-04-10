package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;


/**
 * @Class Name : McpIpStatisticDto
 * @Description : 사용자 IP 를 MCP_IP_STATISTIC 테이블로 저장하는 Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 6.
 */
public class McpIpStatisticDto  implements Serializable  {

    private static final long serialVersionUID = 1L;


    private String parameter;//접근 파라미터
    private String accessIp;//접근 아이피
    private String sysDt;//입력일(년월일)
    private String sysRdate;//입력일
    private String accessUrl;//접근 URL
    private String userid;//접근자
    private String prcsMdlInd;
    private String prcsSbst;
    private String trtmRsltSmst;

    private String loginDivCd;
    private String loginSeq;
    private String platformCd;
    private String menuSeq;
    private String urlSeq;

    private String rateResChgSeq;
    private String svcCntrNo;//서비스계약번호
    private String mobileNo; //모바일번호
    private String eventCode;//이벤트코드
    private String resChgRateCd; //예약변경요금제코드
    private String param; //파라미터
    private String resChgDate; //예약변경일
    private String resChgApyDate; //예약변경신청일자
    private String trtMdlDiv;//처리모듈구분
    private String globalNo; //글로벌넘버
    private String batchRsltCd; //성공 00000 실패 9999
    private String cretIp; //생성일시

    private String befChgRateCd ; //BEF_CHG_RATE_CD      VARCHAR2 (10) NULL, //변경전요금제코드
    private int befChgRateAmnt = 0; //BEF_CHG_RATE_AMNT    NUMBER (15) NULL   //변경전요금제금액

    public String getPrcsMdlInd() {
        if (StringUtils.isBlank(prcsMdlInd) ) {
            return "";
        }
        return prcsMdlInd;
    }
    public void setPrcsMdlInd(String prcsMdlInd) {
        this.prcsMdlInd = prcsMdlInd;
    }
    public String getPrcsSbst() {
        if (StringUtils.isBlank(prcsSbst) ) {
            return "";
        }
        return prcsSbst;
    }
    public void setPrcsSbst(String prcsSbst) {
        this.prcsSbst = prcsSbst;
    }
    public String getTrtmRsltSmst() {
        if (StringUtils.isBlank(trtmRsltSmst) ) {
            return "";
        }
        return trtmRsltSmst;
    }
    public void setTrtmRsltSmst(String trtmRsltSmst) {
        this.trtmRsltSmst = trtmRsltSmst;
    }
    /**
     * @return the parameter
     */
    public String getParameter() {
        if (StringUtils.isBlank(parameter) ) {
            return "";
        }
        return parameter;
    }
    /**
     * @param parameter the parameter to set
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    /**
     * @return the ip
     */
    public String getIp() {
        if (StringUtils.isBlank(accessIp) ) {
            return "";
        }
        return accessIp;
    }
    /**
     * @param ip the ip to set
     */
    public void setIp(String accessIp) {
        this.accessIp = accessIp;
    }
    /**
     * @return the sysRdate
     */
    public String getSysRdate() {
        if (StringUtils.isBlank(sysRdate) ) {
            return "";
        }
        return sysRdate;
    }
    /**
     * @param sysRdate the sysRdate to set
     */
    public void setSysRdate(String sysRdate) {
        this.sysRdate = sysRdate;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        if (StringUtils.isBlank(accessUrl) ) {
            return "";
        }
        return accessUrl;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
    /**
     * @return the userid
     */
    public String getUserid() {
        if (StringUtils.isBlank(userid) ) {
            return "";
        }
        return userid;
    }
    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSysDt() {
        if (StringUtils.isBlank(sysDt) ) {
            return "";
        }
        return sysDt;
    }
    public void setSysDt(String sysDt) {
        this.sysDt = sysDt;
    }
    public String getAccessIp() {
        return accessIp;
    }
    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
    public String getAccessUrl() {
        return accessUrl;
    }
    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
    public String getLoginDivCd() {
        return loginDivCd;
    }
    public void setLoginDivCd(String loginDivCd) {
        this.loginDivCd = loginDivCd;
    }
    public String getLoginSeq() {
        return loginSeq;
    }
    public void setLoginSeq(String loginSeq) {
        this.loginSeq = loginSeq;
    }
    public String getPlatformCd() {
        return platformCd;
    }
    public void setPlatformCd(String platformCd) {
        this.platformCd = platformCd;
    }
    public String getMenuSeq() {
        return menuSeq;
    }
    public void setMenuSeq(String menuSeq) {
        this.menuSeq = menuSeq;
    }
    public String getUrlSeq() {
        return urlSeq;
    }
    public void setUrlSeq(String urlSeq) {
        this.urlSeq = urlSeq;
    }
    public String getRateResChgSeq() {
        return rateResChgSeq;
    }
    public void setRateResChgSeq(String rateResChgSeq) {
        this.rateResChgSeq = rateResChgSeq;
    }
    public String getSvcCntrNo() {
        return svcCntrNo;
    }
    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getEventCode() {
        return eventCode;
    }
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
    public String getResChgRateCd() {
        return resChgRateCd;
    }
    public void setResChgRateCd(String resChgRateCd) {
        this.resChgRateCd = resChgRateCd;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
    public String getResChgApyDate() {
        return resChgApyDate;
    }
    public void setResChgApyDate(String resChgApyDate) {
        this.resChgApyDate = resChgApyDate;
    }
    public String getTrtMdlDiv() {
        return trtMdlDiv;
    }
    public void setTrtMdlDiv(String trtMdlDiv) {
        this.trtMdlDiv = trtMdlDiv;
    }
    public String getGlobalNo() {
        return globalNo;
    }
    public void setGlobalNo(String globalNo) {
        this.globalNo = globalNo;
    }
    public String getBatchRsltCd() {
        return batchRsltCd;
    }
    public void setBatchRsltCd(String batchRsltCd) {
        this.batchRsltCd = batchRsltCd;
    }
    public String getCretIp() {
        return cretIp;
    }
    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }
    public String getResChgDate() {
        return resChgDate;
    }
    public void setResChgDate(String resChgDate) {
        this.resChgDate = resChgDate;
    }
    public String getBefChgRateCd() {
        return befChgRateCd;
    }
    public void setBefChgRateCd(String befChgRateCd) {
        this.befChgRateCd = befChgRateCd;
    }
    public int getBefChgRateAmnt() {
        return befChgRateAmnt;
    }
    public void setBefChgRateAmnt(int befChgRateAmnt) {
        this.befChgRateAmnt = befChgRateAmnt;
    }
}
