package com.ktmmobile.mcp.fath.dto;

import java.io.Serializable;

public class FathDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** uuid */
    private String uuid;
    /** 포탈URL수신번호 */
    private String smsRcvTelNo;
    /** 예약번호 */
    private String resNo;
    private String operType;
    /** 포탈URL */
    private String url;
    /** URL만료일자 */
    private String expirDt;

    /** 트랜잭션 아이디 (MIS고정문자열(3) + 통신사구분(2) + YYYYMMDD24hhmmss(14) + 순번(5) + 점검비트(1)) */
    private String fathTransacId;

    /** 사업자코드 */
    private String slsCmpcCd;

    /** 신분증 종류 (I: 주민등록증, D: 운전면허증) */
    private String retvCdVal;

    /** 고객명 (예: 홍길동) */
    private String custNm;

    /** 주민등록번호 (예: xxxxxxxxxxxxx) */
    private String custIdfyNo;

    /** 발급일자 (yyyyMMdd) */
    private String issDateVal;

    /** 운전면허 번호 (신분증 종류 “D”인 경우 전달) */
    private String driveLicnsNo;

    /** 신분증 사진(고객 얼굴사진) (Base64 인코딩) */
    private String idcardPhotoImg;

    /** 신분증 사본 (Base64 인코딩) — 실물: OCR 스캔 이미지 / 모바일: 확인증 이미지 / 스캐너: 전달 안 함 */
    private String idcardCopiesImg;

    /** 모바일 신분증 QR (PASS: MPM-QR / 행안부: A2A Profile) */
    private String mblIdcardQrImg;

    /** 신원확인 수단 (SDK / WEB / PASS / MOBILEID) */
    private String idcardConfWay;

    /** 거리제한 여부 (Y: 제한 초과 / N: 가능) */
    private String distRsrtnYn;

    /** 안면인증 처리 단계 코드 (예: 07 고정값) */
    private String fathProgrStepCd;

    /** 안면인증 완료통지 일시 */
    private String fathCmpltNtfyDt;

    /** 안면인증 URL 요청 일시 */
    private String fathUrlRqtDt;

    /** 안면인증 결과 코드 (예: 0000 성공, 0001 실패) */
    private String fathResltCd;

    /** 안면인증 결과 내용 */
    private String fathResltSbst;

    /** 안면인증 요청자 아이디 (예: 91298974) */
    private String fathRqtrId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSmsRcvTelNo() {
        return smsRcvTelNo;
    }

    public void setSmsRcvTelNo(String smsRcvTelNo) {
        this.smsRcvTelNo = smsRcvTelNo;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpirDt() {
        return expirDt;
    }

    public void setExpirDt(String expirDt) {
        this.expirDt = expirDt;
    }

    public String getFathTransacId() {
        return fathTransacId;
    }

    public void setFathTransacId(String fathTransacId) {
        this.fathTransacId = fathTransacId;
    }

    public String getSlsCmpcCd() {
        return slsCmpcCd;
    }

    public void setSlsCmpcCd(String slsCmpcCd) {
        this.slsCmpcCd = slsCmpcCd;
    }

    public String getRetvCdVal() {
        return retvCdVal;
    }

    public void setRetvCdVal(String retvCdVal) {
        this.retvCdVal = retvCdVal;
    }

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public String getCustIdfyNo() {
        return custIdfyNo;
    }

    public void setCustIdfyNo(String custIdfyNo) {
        this.custIdfyNo = custIdfyNo;
    }

    public String getIssDateVal() {
        return issDateVal;
    }

    public void setIssDateVal(String issDateVal) {
        this.issDateVal = issDateVal;
    }

    public String getDriveLicnsNo() {
        return driveLicnsNo;
    }

    public void setDriveLicnsNo(String driveLicnsNo) {
        this.driveLicnsNo = driveLicnsNo;
    }

    public String getIdcardPhotoImg() {
        return idcardPhotoImg;
    }

    public void setIdcardPhotoImg(String idcardPhotoImg) {
        this.idcardPhotoImg = idcardPhotoImg;
    }

    public String getIdcardCopiesImg() {
        return idcardCopiesImg;
    }

    public void setIdcardCopiesImg(String idcardCopiesImg) {
        this.idcardCopiesImg = idcardCopiesImg;
    }

    public String getMblIdcardQrImg() {
        return mblIdcardQrImg;
    }

    public void setMblIdcardQrImg(String mblIdcardQrImg) {
        this.mblIdcardQrImg = mblIdcardQrImg;
    }

    public String getIdcardConfWay() {
        return idcardConfWay;
    }

    public void setIdcardConfWay(String idcardConfWay) {
        this.idcardConfWay = idcardConfWay;
    }

    public String getDistRsrtnYn() {
        return distRsrtnYn;
    }

    public void setDistRsrtnYn(String distRsrtnYn) {
        this.distRsrtnYn = distRsrtnYn;
    }

    public String getFathProgrStepCd() {
        return fathProgrStepCd;
    }

    public void setFathProgrStepCd(String fathProgrStepCd) {
        this.fathProgrStepCd = fathProgrStepCd;
    }

    public String getFathCmpltNtfyDt() {
        return fathCmpltNtfyDt;
    }

    public void setFathCmpltNtfyDt(String fathCmpltNtfyDt) {
        this.fathCmpltNtfyDt = fathCmpltNtfyDt;
    }

    public String getFathUrlRqtDt() {
        return fathUrlRqtDt;
    }

    public void setFathUrlRqtDt(String fathUrlRqtDt) {
        this.fathUrlRqtDt = fathUrlRqtDt;
    }

    public String getFathResltCd() {
        return fathResltCd;
    }

    public void setFathResltCd(String fathResltCd) {
        this.fathResltCd = fathResltCd;
    }

    public String getFathResltSbst() {
        return fathResltSbst;
    }

    public void setFathResltSbst(String fathResltSbst) {
        this.fathResltSbst = fathResltSbst;
    }

    public String getFathRqtrId() {
        return fathRqtrId;
    }

    public void setFathRqtrId(String fathRqtrId) {
        this.fathRqtrId = fathRqtrId;
    }
    
}
