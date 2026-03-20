package com.ktmmobile.msf.formOwnChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 명의변경 신청서 등록 요청 (TO-BE 단일 서식).
 * 파일명 규칙: dto.서비스명칭Dto.java (10.서식지프로젝트.md)
 * 프론트 store(ident_form) 구조와 맞춤. NMCP_NFL_CHG_TRNS/TRNSFE 저장 시 매핑용.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnChgApplyDto {

    /** 대리점 코드 */
    private String agencyCode;

    /** 고객(양도고객·양수고객·요금제·약관) 정보 */
    private CustomerFormDto customerForm;

    /** 상품(USIM·납부·명세서·메모) 정보 */
    private ProductFormDto productForm;

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public CustomerFormDto getCustomerForm() {
        return customerForm;
    }

    public void setCustomerForm(CustomerFormDto customerForm) {
        this.customerForm = customerForm;
    }

    public ProductFormDto getProductForm() {
        return productForm;
    }

    public void setProductForm(ProductFormDto productForm) {
        this.productForm = productForm;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerFormDto {
        private String custType;
        private String visitType;
        private String name;
        private String birthDate;
        private String corporateNo;
        private String bizNo;
        private String representativeName;
        private Boolean phoneAuthCompleted;
        private String phone;
        private String email;
        private String emailDomain;
        private String post;
        private String address;
        private String addressDtl;
        private String ncn;
        private String custId;
        private String ratePlanCode;
        private Boolean termsAgreed;
        private TransfereeDto transferee;
        /** 양도인 미납연락처 */
        private String etcMobile;
        /** 양도인 미성년자 법정대리인 이름 */
        private String grMinorAgentName;
        /** 양도인 미성년자 법정대리인 관계 */
        private String grMinorAgentRelation;
        /** 양도인 미성년자 법정대리인 주민번호 */
        private String grMinorAgentRrn;
        /** 양도인 미성년자 법정대리인 연락처 */
        private String grMinorAgentTel;
        /** 양도인 미성년자 법정대리인 동의여부 */
        private String grMinorAgentAgrmYn;

        public String getCustType() { return custType; }
        public void setCustType(String custType) { this.custType = custType; }
        public String getVisitType() { return visitType; }
        public void setVisitType(String visitType) { this.visitType = visitType; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getBirthDate() { return birthDate; }
        public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
        public String getCorporateNo() { return corporateNo; }
        public void setCorporateNo(String corporateNo) { this.corporateNo = corporateNo; }
        public String getBizNo() { return bizNo; }
        public void setBizNo(String bizNo) { this.bizNo = bizNo; }
        public String getRepresentativeName() { return representativeName; }
        public void setRepresentativeName(String representativeName) { this.representativeName = representativeName; }
        public Boolean getPhoneAuthCompleted() { return phoneAuthCompleted; }
        public void setPhoneAuthCompleted(Boolean phoneAuthCompleted) { this.phoneAuthCompleted = phoneAuthCompleted; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getEmailDomain() { return emailDomain; }
        public void setEmailDomain(String emailDomain) { this.emailDomain = emailDomain; }
        public String getPost() { return post; }
        public void setPost(String post) { this.post = post; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getAddressDtl() { return addressDtl; }
        public void setAddressDtl(String addressDtl) { this.addressDtl = addressDtl; }
        public String getNcn() { return ncn; }
        public void setNcn(String ncn) { this.ncn = ncn; }
        public String getCustId() { return custId; }
        public void setCustId(String custId) { this.custId = custId; }
        public String getRatePlanCode() { return ratePlanCode; }
        public void setRatePlanCode(String ratePlanCode) { this.ratePlanCode = ratePlanCode; }
        public Boolean getTermsAgreed() { return termsAgreed; }
        public void setTermsAgreed(Boolean termsAgreed) { this.termsAgreed = termsAgreed; }
        public TransfereeDto getTransferee() { return transferee; }
        public void setTransferee(TransfereeDto transferee) { this.transferee = transferee; }
        public String getEtcMobile() { return etcMobile; }
        public void setEtcMobile(String etcMobile) { this.etcMobile = etcMobile; }
        public String getGrMinorAgentName() { return grMinorAgentName; }
        public void setGrMinorAgentName(String grMinorAgentName) { this.grMinorAgentName = grMinorAgentName; }
        public String getGrMinorAgentRelation() { return grMinorAgentRelation; }
        public void setGrMinorAgentRelation(String grMinorAgentRelation) { this.grMinorAgentRelation = grMinorAgentRelation; }
        public String getGrMinorAgentRrn() { return grMinorAgentRrn; }
        public void setGrMinorAgentRrn(String grMinorAgentRrn) { this.grMinorAgentRrn = grMinorAgentRrn; }
        public String getGrMinorAgentTel() { return grMinorAgentTel; }
        public void setGrMinorAgentTel(String grMinorAgentTel) { this.grMinorAgentTel = grMinorAgentTel; }
        public String getGrMinorAgentAgrmYn() { return grMinorAgentAgrmYn; }
        public void setGrMinorAgentAgrmYn(String grMinorAgentAgrmYn) { this.grMinorAgentAgrmYn = grMinorAgentAgrmYn; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransfereeDto {
        private String custType;
        private String name;
        private String residentNo;
        private String corporateNo;
        private String representativeName;
        private String contactPhone;
        private String email;
        private String post;
        private String address;
        private String addressDtl;
        private String idDocType;
        /** 신분증 발급일/만료일 */
        private String idDocIssuExprDt;
        /** 신분증 발급번호 */
        private String idDocIssuNum;
        /** 외국인 국적 */
        private String foreignerNation;

        // 약관동의 필드 (ASIS clause* 대응)
        /** 개인정보 수집동의 */
        private String clausePriCollectFlag;
        /** 개인정보 제공동의 */
        private String clausePriOfferFlag;
        /** 본인확인 필수동의 */
        private String clauseEssCollectFlag;
        /** 신용정보 조회동의 */
        private String clauseConfidenceFlag;
        /** 광고성 정보 제공동의 */
        private String clausePriAdFlag;
        /** 제휴사 서비스 동의 */
        private String clauseJehuFlag;
        /** 개인정보 삭제 동의 (양도인용) */
        private String clauseCntrDelFlag;
        /** 개인정보 수집동의(일반) */
        private String personalInfoCollectAgree;
        /** 제3자 제공동의(M모바일) */
        private String othersTrnsAgree;
        /** 제3자 제공동의(KT) */
        private String othersTrnsKtAgree;
        /** 제3자 광고수신동의 */
        private String othersAdReceiveAgree;
        /** 본인확인 조회동의 */
        private String selfInqryAgrmYn;

        // 납부방법 (ASIS reqPay* 대응)
        /** 납부방법 (계좌이체/카드) */
        private String reqPayType;
        /** 은행코드 */
        private String reqBank;
        /** 계좌번호 */
        private String reqAccountNumber;
        /** 카드사 */
        private String reqCardCompany;
        /** 카드번호 */
        private String reqCardNo;
        /** 카드유효기간 년 */
        private String reqCardYy;
        /** 카드유효기간 월 */
        private String reqCardMm;
        /** 명세서 발송코드 */
        private String billSendCode;

        // 가입정보 변경 여부
        private String reqInfoChgYn;
        /** 요금제코드 */
        private String soc;
        /** 요금제명 */
        private String socNm;
        /** 제휴상품유형 */
        private String jehuProdType;

        // 미성년자 법정대리인 (ASIS minorAgent* 대응)
        /** 법정대리인 이름 */
        private String minorAgentName;
        /** 법정대리인 관계 */
        private String minorAgentRelation;
        /** 법정대리인 주민번호 */
        private String minorAgentRrn;
        /** 법정대리인 연락처 */
        private String minorAgentTel;
        /** 법정대리인 동의여부 */
        private String minorAgentAgrmYn;

        public String getCustType() { return custType; }
        public void setCustType(String custType) { this.custType = custType; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getResidentNo() { return residentNo; }
        public void setResidentNo(String residentNo) { this.residentNo = residentNo; }
        public String getCorporateNo() { return corporateNo; }
        public void setCorporateNo(String corporateNo) { this.corporateNo = corporateNo; }
        public String getRepresentativeName() { return representativeName; }
        public void setRepresentativeName(String representativeName) { this.representativeName = representativeName; }
        public String getContactPhone() { return contactPhone; }
        public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPost() { return post; }
        public void setPost(String post) { this.post = post; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getAddressDtl() { return addressDtl; }
        public void setAddressDtl(String addressDtl) { this.addressDtl = addressDtl; }
        public String getIdDocType() { return idDocType; }
        public void setIdDocType(String idDocType) { this.idDocType = idDocType; }
        public String getIdDocIssuExprDt() { return idDocIssuExprDt; }
        public void setIdDocIssuExprDt(String idDocIssuExprDt) { this.idDocIssuExprDt = idDocIssuExprDt; }
        public String getIdDocIssuNum() { return idDocIssuNum; }
        public void setIdDocIssuNum(String idDocIssuNum) { this.idDocIssuNum = idDocIssuNum; }
        public String getForeignerNation() { return foreignerNation; }
        public void setForeignerNation(String foreignerNation) { this.foreignerNation = foreignerNation; }
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
        public String getClauseCntrDelFlag() { return clauseCntrDelFlag; }
        public void setClauseCntrDelFlag(String clauseCntrDelFlag) { this.clauseCntrDelFlag = clauseCntrDelFlag; }
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
        public String getBillSendCode() { return billSendCode; }
        public void setBillSendCode(String billSendCode) { this.billSendCode = billSendCode; }
        public String getReqInfoChgYn() { return reqInfoChgYn; }
        public void setReqInfoChgYn(String reqInfoChgYn) { this.reqInfoChgYn = reqInfoChgYn; }
        public String getSoc() { return soc; }
        public void setSoc(String soc) { this.soc = soc; }
        public String getSocNm() { return socNm; }
        public void setSocNm(String socNm) { this.socNm = socNm; }
        public String getJehuProdType() { return jehuProdType; }
        public void setJehuProdType(String jehuProdType) { this.jehuProdType = jehuProdType; }
        public String getMinorAgentName() { return minorAgentName; }
        public void setMinorAgentName(String minorAgentName) { this.minorAgentName = minorAgentName; }
        public String getMinorAgentRelation() { return minorAgentRelation; }
        public void setMinorAgentRelation(String minorAgentRelation) { this.minorAgentRelation = minorAgentRelation; }
        public String getMinorAgentRrn() { return minorAgentRrn; }
        public void setMinorAgentRrn(String minorAgentRrn) { this.minorAgentRrn = minorAgentRrn; }
        public String getMinorAgentTel() { return minorAgentTel; }
        public void setMinorAgentTel(String minorAgentTel) { this.minorAgentTel = minorAgentTel; }
        public String getMinorAgentAgrmYn() { return minorAgentAgrmYn; }
        public void setMinorAgentAgrmYn(String minorAgentAgrmYn) { this.minorAgentAgrmYn = minorAgentAgrmYn; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductFormDto {
        private String usimType;
        private String usimNo;
        private String usimOption;
        private String usimPayType;
        private String rateNote;
        private String payMethod;
        private String statementType;
        private String memo;
        /** AS-IS MC0 연동용: 명의변경 사유 코드 (예: RCMCMCN=실사용자 명변). 미입력 시 서버에서 "RCMCMCN" 적용 */
        private String mcnStatRsnCd;
        /** AS-IS MC0 연동용: USIM 승계 여부 (Y/N). 미입력 시 서버에서 "Y" 적용 */
        private String usimSuccYn;

        public String getUsimType() { return usimType; }
        public void setUsimType(String usimType) { this.usimType = usimType; }
        public String getUsimNo() { return usimNo; }
        public void setUsimNo(String usimNo) { this.usimNo = usimNo; }
        public String getUsimOption() { return usimOption; }
        public void setUsimOption(String usimOption) { this.usimOption = usimOption; }
        public String getUsimPayType() { return usimPayType; }
        public void setUsimPayType(String usimPayType) { this.usimPayType = usimPayType; }
        public String getRateNote() { return rateNote; }
        public void setRateNote(String rateNote) { this.rateNote = rateNote; }
        public String getPayMethod() { return payMethod; }
        public void setPayMethod(String payMethod) { this.payMethod = payMethod; }
        public String getStatementType() { return statementType; }
        public void setStatementType(String statementType) { this.statementType = statementType; }
        public String getMemo() { return memo; }
        public void setMemo(String memo) { this.memo = memo; }
        public String getMcnStatRsnCd() { return mcnStatRsnCd; }
        public void setMcnStatRsnCd(String mcnStatRsnCd) { this.mcnStatRsnCd = mcnStatRsnCd; }
        public String getUsimSuccYn() { return usimSuccYn; }
        public void setUsimSuccYn(String usimSuccYn) { this.usimSuccYn = usimSuccYn; }
    }
}
