package com.ktmmobile.mcp.appform.dto;

import java.io.Serializable;
import java.util.Date;

public class McpUploadPhoneInfoDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private int uploadPhoneSrlNo;
    private String reqModelName;
    private String modelId;
    private String reqPhoneSn;
    private String reqUsimSn;
    private String eid;
    private String imei1;
    private String imei2;
    private String uploadPhoneImg;
    private String accessIp;
    private String userId;
    private String sysDt;
    private Date sysRdate;

    /** 모 회선 계약번호*/
    private String prntsContractNo;

    /** 이동텔레콤구분코드
     * Y: 자급제
     * N: SKT/LG/KT 3사
     * */
    private String moveTlcmIndCd;

    /** 이동통신세대구분코드 3:3G,4:LTE,5:5G*/
    private String moveCmncGnrtIndCd;



    /** R:대기,S:성공,F:실패,A:신청서완료 */
    private String rstCd;
    private String evntCd; // Y14, Y15 이벤트 코드 값
    private String rsltCd; // M플랫폼 연동 결과 코드
    private String rsltYn; // 연동 처리 결과
    private String rsltMsg; // 연동 처리 메시지


    public String getEvntCd() {
        return evntCd;
    }
    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }
    public String getRsltCd() {
        return rsltCd;
    }
    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }
    public String getRsltYn() {
        return rsltYn;
    }
    public void setRsltYn(String rsltYn) {
        this.rsltYn = rsltYn;
    }
    public String getRsltMsg() {
        return rsltMsg;
    }
    public void setRsltMsg(String rsltMsg) {
        this.rsltMsg = rsltMsg;
    }
    public String getRstCd() {
        return rstCd;
    }
    public void setRstCd(String rstCd) {
        this.rstCd = rstCd;
    }


    public String getMoveCmncGnrtIndCd() {
        return moveCmncGnrtIndCd;
    }
    public void setMoveCmncGnrtIndCd(String moveCmncGnrtIndCd) {
        this.moveCmncGnrtIndCd = moveCmncGnrtIndCd;
    }
    public String getMoveTlcmIndCd() {
        return moveTlcmIndCd;
    }
    public void setMoveTlcmIndCd(String moveTlcmIndCd) {
        this.moveTlcmIndCd = moveTlcmIndCd;
    }
    public int getUploadPhoneSrlNo() {
        return uploadPhoneSrlNo;
    }
    public void setUploadPhoneSrlNo(int uploadPhoneSrlNo) {
        this.uploadPhoneSrlNo = uploadPhoneSrlNo;
    }
    public String getReqModelName() {
        return reqModelName;
    }
    public void setReqModelName(String reqModelName) {
        this.reqModelName = reqModelName;
    }
    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    public String getReqPhoneSn() {
        return reqPhoneSn;
    }
    public void setReqPhoneSn(String reqPhoneSn) {
        this.reqPhoneSn = reqPhoneSn;
    }
    public String getReqUsimSn() {
        return reqUsimSn;
    }
    public void setReqUsimSn(String reqUsimSn) {
        this.reqUsimSn = reqUsimSn;
    }
    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
    }
    public String getImei1() {
        return imei1;
    }
    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }
    public String getImei2() {
        return imei2;
    }
    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }
    public String getUploadPhoneImg() {
        return uploadPhoneImg;
    }
    public void setUploadPhoneImg(String uploadPhoneImg) {
        this.uploadPhoneImg = uploadPhoneImg;
    }
    public String getAccessIp() {
        return accessIp;
    }
    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getSysDt() {
        return sysDt;
    }
    public void setSysDt(String sysDt) {
        this.sysDt = sysDt;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }


    public String getPrntsContractNo() {
        return prntsContractNo;
    }
    public void setPrntsContractNo(String prntsContractNo) {
        this.prntsContractNo = prntsContractNo;
    }







}
