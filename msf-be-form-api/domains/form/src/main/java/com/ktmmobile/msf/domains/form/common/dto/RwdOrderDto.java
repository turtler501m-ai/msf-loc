package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class RwdOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long rwdSeq;                /* 보상서비스시퀀스 */
    private String rwdProdCd;           /* 보상서비스 코드 */
    private String resNo;               /* 예약번호 */
    private long requestKey;             /* 요청키 */
    private String contractNum;         /* 계약번호 */
    private String ifTrgtCd;            /* 연동대상코드 (CMN0250) */
    private String chnCd;               /* 가입채널코드 (CMN0249) */
    private String imei;                /* 단말고유식별번호 */
    private String imeiTwo;             /* 단말고유식별번호2 */
    private String buyPric;                /* 단말기 구입가 */
    private String fileId;              /* 이미지 파일 아이디 */
    private String fileExt;             /* 이미지 파일 확장자 */
    private String fileDir;             /* 이미지 파일 경로 */
    private String vrfyRsltCd;          /* 적격심사결과코드 (Y:적격,N:부적격,Z:심사중) */
    private String vrfyDttm;            /* 적격심사일자 */
    private String rmk;                 /* 검증결과내용 */
    private String regstId;             /* 등록자ID */
    private String regstDttm;           /* 등록일시 */
    private String rvisnId;             /* 수정자ID */
    private String rvisnDttm;           /* 수정일시 */
    private String rwdAuthInfo;         /* 자급제보상서비스 인증정보 */
    private String cstmrType;           /* 고객유형 */
    private String reqBuyType;          /* 구매유형 */

    private String rwdStatCd;           /* 보상서비스상태코드 (CMN0251) */

    private String canRsltCd;           /* 해지사유코드 */

    private String endDttm;             /* 서비스 종료 일시 */

    private String rwdProdNm;           /* 보상서비스 명 */

    private String smsRcvNo;            /* 고객 전화번호_문자발송용 */

    public long getRwdSeq() {
        return rwdSeq;
    }

    public void setRwdSeq(long rwdSeq) {
        this.rwdSeq = rwdSeq;
    }

    public String getRwdProdCd() {
        return rwdProdCd;
    }

    public void setRwdProdCd(String rwdProdCd) {
        this.rwdProdCd = rwdProdCd;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public long getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getIfTrgtCd() {
        return ifTrgtCd;
    }

    public void setIfTrgtCd(String ifTrgtCd) {
        this.ifTrgtCd = ifTrgtCd;
    }

    public String getChnCd() {
        return chnCd;
    }

    public void setChnCd(String chnCd) {
        this.chnCd = chnCd;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImeiTwo() {
        return imeiTwo;
    }

    public void setImeiTwo(String imeiTwo) {
        this.imeiTwo = imeiTwo;
    }

    public String getBuyPric() {
        return buyPric;
    }

    public void setBuyPric(String buyPric) {
        this.buyPric = buyPric;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getVrfyRsltCd() {
        return vrfyRsltCd;
    }

    public void setVrfyRsltCd(String vrfyRsltCd) {
        this.vrfyRsltCd = vrfyRsltCd;
    }

    public String getVrfyDttm() {
        return vrfyDttm;
    }

    public void setVrfyDttm(String vrfyDttm) {
        this.vrfyDttm = vrfyDttm;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public String getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(String regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public String getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getRwdAuthInfo() {
        return rwdAuthInfo;
    }

    public void setRwdAuthInfo(String rwdAuthInfo) {
        this.rwdAuthInfo = rwdAuthInfo;
    }

    public String getCstmrType() {
        return cstmrType;
    }

    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }

    public String getReqBuyType() {
        return reqBuyType;
    }

    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }

    public String getRwdStatCd() {
        return rwdStatCd;
    }

    public void setRwdStatCd(String rwdStatCd) {
        this.rwdStatCd = rwdStatCd;
    }

    public String getCanRsltCd() {
        return canRsltCd;
    }

    public void setCanRsltCd(String canRsltCd) {
        this.canRsltCd = canRsltCd;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getRwdProdNm() { return rwdProdNm; }

    public void setRwdProdNm(String rwdProdNm) { this.rwdProdNm = rwdProdNm; }

    public String getSmsRcvNo() {
        return smsRcvNo;
    }

    public void setSmsRcvNo(String smsRcvNo) {
        this.smsRcvNo = smsRcvNo;
    }
}
