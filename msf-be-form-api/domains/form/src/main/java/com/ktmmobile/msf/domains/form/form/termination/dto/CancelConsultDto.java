package com.ktmmobile.msf.domains.form.form.termination.dto;

import java.io.Serializable;
import java.util.Date;

public class CancelConsultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custReqSeq;          // 해지 상담 일련번호
    private String cstmrName;         // 고객명
    private String contractNum;       // 계약번호
    private String onlineAuthType;    // 본인인증 방법
    private String onlineAuthInfo;    // 본인인증 정보
    private String reqSeq;
    private String resSeq;
    private String cstmrType;
    private String cstmrNativeRrn;
    private String receiveMobileNo;   // 연락받을 번호
    private String cancelMobileNo;    // 해지신청 번호
    private String survey1Cd;         // 설문 1
    private String survey1Text;       // 설문 1 답변
    private String survey2Cd;         // 설문 2
    private String survey2Text;       // 설문 2
    private Integer surveyScore;      // 만족도
    private String surveySuggestion;  // 개선사항
    private String procCd;            // 처리결과
    private String memo;              // 메모
    private String scanId;            // 스캔 아이디
    private String sysRdateDd;        // 등록일
    private String rip;               // 등록아이피
    private String regstId;           // 등록자 ID
    private Date regstDttm;           // 등록일시
    private String rvisnId;           // 수정자 ID
    private Date rvisnDttm;           // 수정일시

    public String getCustReqSeq() {
        return custReqSeq;
    }
    public void setCustReqSeq(String custReqSeq) {
        this.custReqSeq = custReqSeq;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getOnlineAuthType() {
        return onlineAuthType;
    }
    public void setOnlineAuthType(String onlineAuthType) {
        this.onlineAuthType = onlineAuthType;
    }
    public String getOnlineAuthInfo() {
        return onlineAuthInfo;
    }
    public void setOnlineAuthInfo(String onlineAuthInfo) {
        this.onlineAuthInfo = onlineAuthInfo;
    }
    public String getReqSeq() {
        return reqSeq;
    }
    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }
    public String getResSeq() {
        return resSeq;
    }
    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }
    public String getCstmrType() {
        return cstmrType;
    }
    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }
    public String getCstmrNativeRrn() {
        return cstmrNativeRrn;
    }
    public void setCstmrNativeRrn(String cstmrNativeRrn) {
        this.cstmrNativeRrn = cstmrNativeRrn;
    }
    public String getReceiveMobileNo() {
        return receiveMobileNo;
    }
    public void setReceiveMobileNo(String receiveMobileNo) {
        this.receiveMobileNo = receiveMobileNo;
    }
    public String getCancelMobileNo() {
        return cancelMobileNo;
    }
    public void setCancelMobileNo(String cancelMobileNo) {
        this.cancelMobileNo = cancelMobileNo;
    }
    public String getSurvey1Cd() {
        return survey1Cd;
    }
    public void setSurvey1Cd(String survey1Cd) {
        this.survey1Cd = survey1Cd;
    }
    public String getSurvey1Text() {
        return survey1Text;
    }
    public void setSurvey1Text(String survey1Text) {
        this.survey1Text = survey1Text;
    }
    public String getSurvey2Cd() {
        return survey2Cd;
    }
    public void setSurvey2Cd(String survey2Cd) {
        this.survey2Cd = survey2Cd;
    }
    public String getSurvey2Text() {
        return survey2Text;
    }
    public void setSurvey2Text(String survey2Text) {
        this.survey2Text = survey2Text;
    }
    public Integer getSurveyScore() {
        return surveyScore;
    }
    public void setSurveyScore(Integer surveyScore) {
        this.surveyScore = surveyScore;
    }
    public String getSurveySuggestion() {
        return surveySuggestion;
    }
    public void setSurveySuggestion(String surveySuggestion) {
        this.surveySuggestion = surveySuggestion;
    }
    public String getProcCd() {
        return procCd;
    }
    public void setProcCd(String procCd) {
        this.procCd = procCd;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getScanId() {
        return scanId;
    }
    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
    public String getSysRdateDd() {
        return sysRdateDd;
    }
    public void setSysRdateDd(String sysRdateDd) {
        this.sysRdateDd = sysRdateDd;
    }
    public String getRip() {
        return rip;
    }
    public void setRip(String rip) {
        this.rip = rip;
    }
    public String getRegstId() {
        return regstId;
    }
    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }
    public Date getRegstDttm() {
        return regstDttm;
    }
    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }
    public String getRvisnId() {
        return rvisnId;
    }
    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }
    public Date getRvisnDttm() {
        return rvisnDttm;
    }
    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    // -------------------------------------------------------------------------
    // X18 잔여요금·위약금 조회 요청/응답 DTO
    // -------------------------------------------------------------------------

    public static class RemainChargeReqDto {
        private String ncn;     // 계약번호
        private String ctn;     // 휴대폰번호
        private String custId;  // 고객ID

        public String getNcn() { return ncn; }
        public void setNcn(String ncn) { this.ncn = ncn; }
        public String getCtn() { return ctn; }
        public void setCtn(String ctn) { this.ctn = ctn; }
        public String getCustId() { return custId; }
        public void setCustId(String custId) { this.custId = custId; }
    }

    public static class RemainChargeResVO {
        private boolean success;
        private String message;
        private String searchDay;   // 조회 날짜
        private String searchTime;  // 조회 기간
        private String sumAmt;      // 당월요금계
        private java.util.List<FareItem> items;

        /** requestView 위약금 블록 데이터 (X54·X16·mspAddInfo) */
        private TerminationSettlementDto settlement;

        public static class FareItem {
            private String gubun;    // 요금 항목명
            private String payment;  // 요금 금액

            public String getGubun() { return gubun; }
            public void setGubun(String gubun) { this.gubun = gubun; }
            public String getPayment() { return payment; }
            public void setPayment(String payment) { this.payment = payment; }
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getSearchDay() { return searchDay; }
        public void setSearchDay(String searchDay) { this.searchDay = searchDay; }
        public String getSearchTime() { return searchTime; }
        public void setSearchTime(String searchTime) { this.searchTime = searchTime; }
        public String getSumAmt() { return sumAmt; }
        public void setSumAmt(String sumAmt) { this.sumAmt = sumAmt; }
        public java.util.List<FareItem> getItems() { return items; }
        public void setItems(java.util.List<FareItem> items) { this.items = items; }
        public TerminationSettlementDto getSettlement() { return settlement; }
        public void setSettlement(TerminationSettlementDto settlement) { this.settlement = settlement; }
    }

    /**
     * requestView Try 블록 2(위약금 정산)에 해당하는 데이터 클래스.
     * X54(스폰서/위약금), X16(잔여 할부금), mspAddInfo(할부원금) 결과를 담는다.
     */
    public static class TerminationSettlementDto {
        private boolean prePayment;         // 선불 요금제 여부 (true 이면 나머지 필드 미조회)

        /* --- X54: 스폰서/위약금 (MpMoscSpnsrItgInfoInVO) --- */
        private String saleEngtNm;              // 스폰서 유형명
        private String saleEngtOptnCd;          // 스폰서 유형 옵션 코드 (KD/PM)
        private String trmnForecBprmsAmt;       // (해지 시) 예상 위약금
        private String chageDcAmt;              // 요금할인(월)
        private String rtrnAmtAndChageDcAmt;    // 반환금(요금할인)
        private String chageDcAmtSuprtRtrnAmt;  // 요금할인(지원금) 반환금
        private String ktSuprtPenltAmt;         // 위약금(공시지원금)
        private String storSuprtPenltAmt;       // 위약금(추가지원금)
        private String engtAplyStDate;          // 가입일
        private String engtExpirPamDate;        // 만료예정일
        private String engtRmndDate;            // 잔여약정기간

        /* --- X16: 요금 상세 — 잔여 할부금 (MpFarMonDetailInfoDto) --- */
        private String installmentAmt;          // 잔여 할부 금액
        private String totalNoOfInstall;        // 잔여 개월
        private String installmentYN;           // 할부 여부

        /* --- mspAddInfo: 할부 원금 (MspJuoAddInfoDto) --- */
        private int instOrginAmnt;              // 할부원금
        private int instMnthCnt;               // 할부개월수
        private int remainPay;                  // 잔여 할부금액
        private int remainMonth;               // 잔여 할부개월
        private String modelName;               // 단말기 모델명

        public boolean isPrePayment() { return prePayment; }
        public void setPrePayment(boolean prePayment) { this.prePayment = prePayment; }
        public String getSaleEngtNm() { return saleEngtNm; }
        public void setSaleEngtNm(String saleEngtNm) { this.saleEngtNm = saleEngtNm; }
        public String getSaleEngtOptnCd() { return saleEngtOptnCd; }
        public void setSaleEngtOptnCd(String saleEngtOptnCd) { this.saleEngtOptnCd = saleEngtOptnCd; }
        public String getTrmnForecBprmsAmt() { return trmnForecBprmsAmt; }
        public void setTrmnForecBprmsAmt(String trmnForecBprmsAmt) { this.trmnForecBprmsAmt = trmnForecBprmsAmt; }
        public String getChageDcAmt() { return chageDcAmt; }
        public void setChageDcAmt(String chageDcAmt) { this.chageDcAmt = chageDcAmt; }
        public String getRtrnAmtAndChageDcAmt() { return rtrnAmtAndChageDcAmt; }
        public void setRtrnAmtAndChageDcAmt(String rtrnAmtAndChageDcAmt) { this.rtrnAmtAndChageDcAmt = rtrnAmtAndChageDcAmt; }
        public String getChageDcAmtSuprtRtrnAmt() { return chageDcAmtSuprtRtrnAmt; }
        public void setChageDcAmtSuprtRtrnAmt(String chageDcAmtSuprtRtrnAmt) { this.chageDcAmtSuprtRtrnAmt = chageDcAmtSuprtRtrnAmt; }
        public String getKtSuprtPenltAmt() { return ktSuprtPenltAmt; }
        public void setKtSuprtPenltAmt(String ktSuprtPenltAmt) { this.ktSuprtPenltAmt = ktSuprtPenltAmt; }
        public String getStorSuprtPenltAmt() { return storSuprtPenltAmt; }
        public void setStorSuprtPenltAmt(String storSuprtPenltAmt) { this.storSuprtPenltAmt = storSuprtPenltAmt; }
        public String getEngtAplyStDate() { return engtAplyStDate; }
        public void setEngtAplyStDate(String engtAplyStDate) { this.engtAplyStDate = engtAplyStDate; }
        public String getEngtExpirPamDate() { return engtExpirPamDate; }
        public void setEngtExpirPamDate(String engtExpirPamDate) { this.engtExpirPamDate = engtExpirPamDate; }
        public String getEngtRmndDate() { return engtRmndDate; }
        public void setEngtRmndDate(String engtRmndDate) { this.engtRmndDate = engtRmndDate; }
        public String getInstallmentAmt() { return installmentAmt; }
        public void setInstallmentAmt(String installmentAmt) { this.installmentAmt = installmentAmt; }
        public String getTotalNoOfInstall() { return totalNoOfInstall; }
        public void setTotalNoOfInstall(String totalNoOfInstall) { this.totalNoOfInstall = totalNoOfInstall; }
        public String getInstallmentYN() { return installmentYN; }
        public void setInstallmentYN(String installmentYN) { this.installmentYN = installmentYN; }
        public int getInstOrginAmnt() { return instOrginAmnt; }
        public void setInstOrginAmnt(int instOrginAmnt) { this.instOrginAmnt = instOrginAmnt; }
        public int getInstMnthCnt() { return instMnthCnt; }
        public void setInstMnthCnt(int instMnthCnt) { this.instMnthCnt = instMnthCnt; }
        public int getRemainPay() { return remainPay; }
        public void setRemainPay(int remainPay) { this.remainPay = remainPay; }
        public int getRemainMonth() { return remainMonth; }
        public void setRemainMonth(int remainMonth) { this.remainMonth = remainMonth; }
        public String getModelName() { return modelName; }
        public void setModelName(String modelName) { this.modelName = modelName; }
    }

}