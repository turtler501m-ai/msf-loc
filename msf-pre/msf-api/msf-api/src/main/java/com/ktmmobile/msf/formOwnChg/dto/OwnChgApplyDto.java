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
