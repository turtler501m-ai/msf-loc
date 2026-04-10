package com.ktmmobile.mcp.appform.dto;

import java.io.Serializable;

public class EsimDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private String eid;
    private String imei1;
    private String imei2;
    private int uploadPhoneSrlNo;
    private String resultCode;
    private String intmSrlNo;
    private String resultMsg;
    private String rstCd;

    // Y12 output
    private String moveTlcmIndCd; // 이동텔레콤구분코드
    private String moveCmncGnrtIndCd; // 이동통신세대구분코드
    private String moveCd; // 이동텔레콤구분코드
    // 나이스 인증
    private String cstmrName;
    private String cstmrNativeRrn01;
    private String modelId;
    private String modelNm;
    private String cstmrMobile;

    //또 다른 거시기 Y14 호출 정보
    private String modelIdOther;
    private String modelNmOther;
    private String intmSrlNoOther;


    // Y15 input
    private String wrkjobDivCd; //작업구분코드
    private String wifiMacAdr; //MAC ID
    private String intmEtcPurpDivCd; //기기기타용도구분코드
    private String euiccId; //eIccId
    private String trtDivCd; //처리구분코드
    private String cmpnCdIfVal; //CEIR 연동 사업자식별코드명
    private String birthday; //생년월일
    private String sexDiv; //성별
    private String ctn; //전화번호


    public String getIntmSrlNoOther() {
        return intmSrlNoOther;
    }
    public void setIntmSrlNoOther(String intmSrlNoOther) {
        this.intmSrlNoOther = intmSrlNoOther;
    }
    public String getMoveCd() {
        return moveCd;
    }
    public void setMoveCd(String moveCd) {
        this.moveCd = moveCd;
    }
    public String getRstCd() {
        return rstCd;
    }
    public void setRstCd(String rstCd) {
        this.rstCd = rstCd;
    }
    public String getMoveTlcmIndCd() {
        return moveTlcmIndCd;
    }
    public void setMoveTlcmIndCd(String moveTlcmIndCd) {
        this.moveTlcmIndCd = moveTlcmIndCd;
    }
    public String getMoveCmncGnrtIndCd() {
        return moveCmncGnrtIndCd;
    }
    public void setMoveCmncGnrtIndCd(String moveCmncGnrtIndCd) {
        this.moveCmncGnrtIndCd = moveCmncGnrtIndCd;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    public String getModelNm() {
        return modelNm;
    }
    public void setModelNm(String modelNm) {
        this.modelNm = modelNm;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    public String getCstmrNativeRrn01() {
        return cstmrNativeRrn01;
    }
    public void setCstmrNativeRrn01(String cstmrNativeRrn01) {
        this.cstmrNativeRrn01 = cstmrNativeRrn01;
    }
    public String getIntmSrlNo() {
        return intmSrlNo;
    }
    public void setIntmSrlNo(String intmSrlNo) {
        this.intmSrlNo = intmSrlNo;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public int getUploadPhoneSrlNo() {
        return uploadPhoneSrlNo;
    }
    public void setUploadPhoneSrlNo(int uploadPhoneSrlNo) {
        this.uploadPhoneSrlNo = uploadPhoneSrlNo;
    }
    public String getWrkjobDivCd() {
        return wrkjobDivCd;
    }
    public void setWrkjobDivCd(String wrkjobDivCd) {
        this.wrkjobDivCd = wrkjobDivCd;
    }

    public String getWifiMacAdr() {
        return wifiMacAdr;
    }
    public void setWifiMacAdr(String wifiMacAdr) {
        this.wifiMacAdr = wifiMacAdr;
    }
    public String getIntmEtcPurpDivCd() {
        return intmEtcPurpDivCd;
    }
    public void setIntmEtcPurpDivCd(String intmEtcPurpDivCd) {
        this.intmEtcPurpDivCd = intmEtcPurpDivCd;
    }
    public String getEuiccId() {
        return euiccId;
    }
    public void setEuiccId(String euiccId) {
        this.euiccId = euiccId;
    }
    public String getTrtDivCd() {
        return trtDivCd;
    }
    public void setTrtDivCd(String trtDivCd) {
        this.trtDivCd = trtDivCd;
    }
    public String getCmpnCdIfVal() {
        return cmpnCdIfVal;
    }
    public void setCmpnCdIfVal(String cmpnCdIfVal) {
        this.cmpnCdIfVal = cmpnCdIfVal;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getSexDiv() {
        return sexDiv;
    }
    public void setSexDiv(String sexDiv) {
        this.sexDiv = sexDiv;
    }
    public String getCtn() {
        return ctn;
    }
    public void setCtn(String ctn) {
        this.ctn = ctn;
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
    public String getModelIdOther() {
        return modelIdOther;
    }
    public void setModelIdOther(String modelIdOther) {
        this.modelIdOther = modelIdOther;
    }
    public String getModelNmOther() {
        return modelNmOther;
    }
    public void setModelNmOther(String modelNmOther) {
        this.modelNmOther = modelNmOther;
    }
    public String getCstmrMobile() {
        return cstmrMobile;
    }
    public void setCstmrMobile(String cstmrMobile) {
        this.cstmrMobile = cstmrMobile;
    }



}
