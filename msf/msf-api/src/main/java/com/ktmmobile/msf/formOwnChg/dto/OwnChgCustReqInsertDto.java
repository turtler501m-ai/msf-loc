package com.ktmmobile.msf.formOwnChg.dto;

/**
 * 명의변경 신청서 DB 저장용 flat DTO.
 * ASIS MyNameChgReqDto 대응. MSF_CUST_REQUEST_MST + MSF_REQUEST_NAME_CHG 2테이블 INSERT에 사용.
 * (TOBE DDL에서 ASIS 3테이블(MST+NAME_CHG+AGENT)이 2테이블(MST+NAME_CHG)로 통합됨)
 */
public class OwnChgCustReqInsertDto {

    // ──── 공통 ────
    private String agencyCode;      // 대리점코드

    // ──── MSF_CUST_REQUEST_MST (양도인 마스터) ────
    private Long custReqSeq;        // REQUEST_KEY
    private String reqType;         // 고정: "NC"
    private String userid;          // 로그인 사용자ID
    private String grCstmrName;     // 양도인 이름
    private String mobileNo;        // 명변 대상 번호
    private String grCstmrNativeRrn;// 양도인 주민번호
    private String contractNum;     // 계약번호(ncn)
    private String grCstmrType;     // 양도인 고객유형 (NM=미성년)
    private String grOnlineAuthType;
    private String grOnlineAuthInfo;
    private String etcMobile;       // 미납 연락처

    // ──── MSF_CUST_REQUEST_NAME_CHG (양수인 상세) ────
    private String cstmrName;       // 양수인 이름
    private String cstmrType;       // 양수인 고객유형 (NM=미성년)
    private String cstmrNativeRrn;  // 양수인 주민번호
    private String onlineAuthType;
    private String onlineAuthInfo;
    // 약관동의
    private String clauseCntrDelFlag;
    private String clausePriCollectFlag;
    private String clausePriOfferFlag;
    private String clauseEssCollectFlag;
    private String clauseConfidenceFlag;
    private String clausePriAdFlag;
    private String clauseJehuFlag;
    private String personalInfoCollectAgree;
    private String othersTrnsAgree;
    private String othersTrnsKtAgree;
    private String othersAdReceiveAgree;
    private String selfInqryAgrmYn;
    // 가입정보
    private String reqInfoChgYn;
    private String soc;             // 요금제코드
    private String socNm;           // 요금제명
    // 주소
    private String cstmrPost;
    private String cstmrAddr;
    private String cstmrAddrDtl;
    // 명세서/메일
    private String cstmrBillSendCode;
    private String cstmrMail;
    // 납부방법
    private String reqPayType;
    private String reqBank;
    private String reqAccountNumber;
    private String reqCardCompany;
    private String reqCardNo;
    private String reqCardYy;
    private String reqCardMm;
    // 신분증
    private String selfCertType;
    private String selfIssuExprDt;
    private String selfIssuNum;
    // 양수인 연락처 (3분할)
    private String cstmrReceiveTelFn;
    private String cstmrReceiveTelMn;
    private String cstmrReceiveTelRn;
    // MC0 연동
    private String mcnStatRsnCd;    // 고정: "RCMCMCN"
    private String usimSuccYn;      // 고정: "Y"
    private String iccId;
    private String mcnResNo;        // 예약번호
    // 외국인
    private String cstmrForeignerNation;
    // 제휴
    private String jehuProdType;
    // 법정대리인 연락처
    private String agntTelNo;
    // 처리코드
    private String procCd;          // 고정: "RQ"

    // ──── MSF_CUST_REQ_NAME_CHG_AGENT (미성년자 법정대리인) ────
    // 양수인 법정대리인
    private String minorAgentName;
    private String minorAgentRrn;
    private String minorAgentRelation;
    private String minorAgentTel;
    private String minorAgentAgrmYn;
    // 양도인 법정대리인
    private String grMinorAgentName;
    private String grMinorAgentRrn;
    private String grMinorAgentRelation;
    private String grMinorAgentTel;
    private String grMinorAgentAgrmYn;

    // ──── Getters / Setters ────

    public String getAgencyCode() { return agencyCode; }
    public void setAgencyCode(String agencyCode) { this.agencyCode = agencyCode; }
    public Long getCustReqSeq() { return custReqSeq; }
    public void setCustReqSeq(Long custReqSeq) { this.custReqSeq = custReqSeq; }
    public String getReqType() { return reqType; }
    public void setReqType(String reqType) { this.reqType = reqType; }
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    public String getGrCstmrName() { return grCstmrName; }
    public void setGrCstmrName(String grCstmrName) { this.grCstmrName = grCstmrName; }
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public String getGrCstmrNativeRrn() { return grCstmrNativeRrn; }
    public void setGrCstmrNativeRrn(String grCstmrNativeRrn) { this.grCstmrNativeRrn = grCstmrNativeRrn; }
    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }
    public String getGrCstmrType() { return grCstmrType; }
    public void setGrCstmrType(String grCstmrType) { this.grCstmrType = grCstmrType; }
    public String getGrOnlineAuthType() { return grOnlineAuthType; }
    public void setGrOnlineAuthType(String grOnlineAuthType) { this.grOnlineAuthType = grOnlineAuthType; }
    public String getGrOnlineAuthInfo() { return grOnlineAuthInfo; }
    public void setGrOnlineAuthInfo(String grOnlineAuthInfo) { this.grOnlineAuthInfo = grOnlineAuthInfo; }
    public String getEtcMobile() { return etcMobile; }
    public void setEtcMobile(String etcMobile) { this.etcMobile = etcMobile; }

    public String getCstmrName() { return cstmrName; }
    public void setCstmrName(String cstmrName) { this.cstmrName = cstmrName; }
    public String getCstmrType() { return cstmrType; }
    public void setCstmrType(String cstmrType) { this.cstmrType = cstmrType; }
    public String getCstmrNativeRrn() { return cstmrNativeRrn; }
    public void setCstmrNativeRrn(String cstmrNativeRrn) { this.cstmrNativeRrn = cstmrNativeRrn; }
    public String getOnlineAuthType() { return onlineAuthType; }
    public void setOnlineAuthType(String onlineAuthType) { this.onlineAuthType = onlineAuthType; }
    public String getOnlineAuthInfo() { return onlineAuthInfo; }
    public void setOnlineAuthInfo(String onlineAuthInfo) { this.onlineAuthInfo = onlineAuthInfo; }
    public String getClauseCntrDelFlag() { return clauseCntrDelFlag; }
    public void setClauseCntrDelFlag(String clauseCntrDelFlag) { this.clauseCntrDelFlag = clauseCntrDelFlag; }
    public String getClausePriCollectFlag() { return clausePriCollectFlag; }
    public void setClausePriCollectFlag(String clausePriCollectFlag) { this.clausePriCollectFlag = clausePriCollectFlag; }
    public String getClausePriOfferFlag() { return clausePriOfferFlag; }
    public void setClausePriOfferFlag(String clausePriOfferFlag) { this.clausePriOfferFlag = clausePriOfferFlag; }
    public String getClauseEssCollectFlag() { return clauseEssCollectFlag; }
    public void setClauseEssCollectFlag(String clauseEssCollectFlag) { this.clauseEssCollectFlag = clauseEssCollectFlag; }
    public String getClauseConfidenceFlag() { return clauseConfidenceFlag; }
    public void setClauseConfidenceFlag(String clauseConfidenceFlag) { this.clauseConfidenceFlag = clauseConfidenceFlag; }
    public String getClausePriAdFlag() { return clausePriAdFlag; }
    public void setClausePriAdFlag(String clausePriAdFlag) { this.clausePriAdFlag = clausePriAdFlag; }
    public String getClauseJehuFlag() { return clauseJehuFlag; }
    public void setClauseJehuFlag(String clauseJehuFlag) { this.clauseJehuFlag = clauseJehuFlag; }
    public String getPersonalInfoCollectAgree() { return personalInfoCollectAgree; }
    public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) { this.personalInfoCollectAgree = personalInfoCollectAgree; }
    public String getOthersTrnsAgree() { return othersTrnsAgree; }
    public void setOthersTrnsAgree(String othersTrnsAgree) { this.othersTrnsAgree = othersTrnsAgree; }
    public String getOthersTrnsKtAgree() { return othersTrnsKtAgree; }
    public void setOthersTrnsKtAgree(String othersTrnsKtAgree) { this.othersTrnsKtAgree = othersTrnsKtAgree; }
    public String getOthersAdReceiveAgree() { return othersAdReceiveAgree; }
    public void setOthersAdReceiveAgree(String othersAdReceiveAgree) { this.othersAdReceiveAgree = othersAdReceiveAgree; }
    public String getSelfInqryAgrmYn() { return selfInqryAgrmYn; }
    public void setSelfInqryAgrmYn(String selfInqryAgrmYn) { this.selfInqryAgrmYn = selfInqryAgrmYn; }
    public String getReqInfoChgYn() { return reqInfoChgYn; }
    public void setReqInfoChgYn(String reqInfoChgYn) { this.reqInfoChgYn = reqInfoChgYn; }
    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }
    public String getSocNm() { return socNm; }
    public void setSocNm(String socNm) { this.socNm = socNm; }
    public String getCstmrPost() { return cstmrPost; }
    public void setCstmrPost(String cstmrPost) { this.cstmrPost = cstmrPost; }
    public String getCstmrAddr() { return cstmrAddr; }
    public void setCstmrAddr(String cstmrAddr) { this.cstmrAddr = cstmrAddr; }
    public String getCstmrAddrDtl() { return cstmrAddrDtl; }
    public void setCstmrAddrDtl(String cstmrAddrDtl) { this.cstmrAddrDtl = cstmrAddrDtl; }
    public String getCstmrBillSendCode() { return cstmrBillSendCode; }
    public void setCstmrBillSendCode(String cstmrBillSendCode) { this.cstmrBillSendCode = cstmrBillSendCode; }
    public String getCstmrMail() { return cstmrMail; }
    public void setCstmrMail(String cstmrMail) { this.cstmrMail = cstmrMail; }
    public String getReqPayType() { return reqPayType; }
    public void setReqPayType(String reqPayType) { this.reqPayType = reqPayType; }
    public String getReqBank() { return reqBank; }
    public void setReqBank(String reqBank) { this.reqBank = reqBank; }
    public String getReqAccountNumber() { return reqAccountNumber; }
    public void setReqAccountNumber(String reqAccountNumber) { this.reqAccountNumber = reqAccountNumber; }
    public String getReqCardCompany() { return reqCardCompany; }
    public void setReqCardCompany(String reqCardCompany) { this.reqCardCompany = reqCardCompany; }
    public String getReqCardNo() { return reqCardNo; }
    public void setReqCardNo(String reqCardNo) { this.reqCardNo = reqCardNo; }
    public String getReqCardYy() { return reqCardYy; }
    public void setReqCardYy(String reqCardYy) { this.reqCardYy = reqCardYy; }
    public String getReqCardMm() { return reqCardMm; }
    public void setReqCardMm(String reqCardMm) { this.reqCardMm = reqCardMm; }
    public String getSelfCertType() { return selfCertType; }
    public void setSelfCertType(String selfCertType) { this.selfCertType = selfCertType; }
    public String getSelfIssuExprDt() { return selfIssuExprDt; }
    public void setSelfIssuExprDt(String selfIssuExprDt) { this.selfIssuExprDt = selfIssuExprDt; }
    public String getSelfIssuNum() { return selfIssuNum; }
    public void setSelfIssuNum(String selfIssuNum) { this.selfIssuNum = selfIssuNum; }
    public String getCstmrReceiveTelFn() { return cstmrReceiveTelFn; }
    public void setCstmrReceiveTelFn(String cstmrReceiveTelFn) { this.cstmrReceiveTelFn = cstmrReceiveTelFn; }
    public String getCstmrReceiveTelMn() { return cstmrReceiveTelMn; }
    public void setCstmrReceiveTelMn(String cstmrReceiveTelMn) { this.cstmrReceiveTelMn = cstmrReceiveTelMn; }
    public String getCstmrReceiveTelRn() { return cstmrReceiveTelRn; }
    public void setCstmrReceiveTelRn(String cstmrReceiveTelRn) { this.cstmrReceiveTelRn = cstmrReceiveTelRn; }
    public String getMcnStatRsnCd() { return mcnStatRsnCd; }
    public void setMcnStatRsnCd(String mcnStatRsnCd) { this.mcnStatRsnCd = mcnStatRsnCd; }
    public String getUsimSuccYn() { return usimSuccYn; }
    public void setUsimSuccYn(String usimSuccYn) { this.usimSuccYn = usimSuccYn; }
    public String getIccId() { return iccId; }
    public void setIccId(String iccId) { this.iccId = iccId; }
    public String getMcnResNo() { return mcnResNo; }
    public void setMcnResNo(String mcnResNo) { this.mcnResNo = mcnResNo; }
    public String getCstmrForeignerNation() { return cstmrForeignerNation; }
    public void setCstmrForeignerNation(String cstmrForeignerNation) { this.cstmrForeignerNation = cstmrForeignerNation; }
    public String getJehuProdType() { return jehuProdType; }
    public void setJehuProdType(String jehuProdType) { this.jehuProdType = jehuProdType; }
    public String getAgntTelNo() { return agntTelNo; }
    public void setAgntTelNo(String agntTelNo) { this.agntTelNo = agntTelNo; }
    public String getProcCd() { return procCd; }
    public void setProcCd(String procCd) { this.procCd = procCd; }

    public String getMinorAgentName() { return minorAgentName; }
    public void setMinorAgentName(String minorAgentName) { this.minorAgentName = minorAgentName; }
    public String getMinorAgentRrn() { return minorAgentRrn; }
    public void setMinorAgentRrn(String minorAgentRrn) { this.minorAgentRrn = minorAgentRrn; }
    public String getMinorAgentRelation() { return minorAgentRelation; }
    public void setMinorAgentRelation(String minorAgentRelation) { this.minorAgentRelation = minorAgentRelation; }
    public String getMinorAgentTel() { return minorAgentTel; }
    public void setMinorAgentTel(String minorAgentTel) { this.minorAgentTel = minorAgentTel; }
    public String getMinorAgentAgrmYn() { return minorAgentAgrmYn; }
    public void setMinorAgentAgrmYn(String minorAgentAgrmYn) { this.minorAgentAgrmYn = minorAgentAgrmYn; }
    public String getGrMinorAgentName() { return grMinorAgentName; }
    public void setGrMinorAgentName(String grMinorAgentName) { this.grMinorAgentName = grMinorAgentName; }
    public String getGrMinorAgentRrn() { return grMinorAgentRrn; }
    public void setGrMinorAgentRrn(String grMinorAgentRrn) { this.grMinorAgentRrn = grMinorAgentRrn; }
    public String getGrMinorAgentRelation() { return grMinorAgentRelation; }
    public void setGrMinorAgentRelation(String grMinorAgentRelation) { this.grMinorAgentRelation = grMinorAgentRelation; }
    public String getGrMinorAgentTel() { return grMinorAgentTel; }
    public void setGrMinorAgentTel(String grMinorAgentTel) { this.grMinorAgentTel = grMinorAgentTel; }
    public String getGrMinorAgentAgrmYn() { return grMinorAgentAgrmYn; }
    public void setGrMinorAgentAgrmYn(String grMinorAgentAgrmYn) { this.grMinorAgentAgrmYn = grMinorAgentAgrmYn; }
}
