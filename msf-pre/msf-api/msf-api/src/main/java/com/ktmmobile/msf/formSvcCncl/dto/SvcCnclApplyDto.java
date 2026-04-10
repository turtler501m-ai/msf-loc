package com.ktmmobile.msf.formSvcCncl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 서비스해지 신청서 등록 요청 DTO.
 * 설계서 S104030101 동의/완료 단계 처리.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvcCnclApplyDto {

    private String agencyCode;
    private CustomerFormDto customerForm;
    private ProductFormDto productForm;

    public String getAgencyCode() { return agencyCode; }
    public void setAgencyCode(String agencyCode) { this.agencyCode = agencyCode; }
    public CustomerFormDto getCustomerForm() { return customerForm; }
    public void setCustomerForm(CustomerFormDto customerForm) { this.customerForm = customerForm; }
    public ProductFormDto getProductForm() { return productForm; }
    public void setProductForm(ProductFormDto productForm) { this.productForm = productForm; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerFormDto {
        private String custType;
        private String name;
        private String phone;
        private Boolean phoneAuthCompleted;
        private String email;
        private String emailDomain;
        private String post;
        private String address;
        private String addressDtl;
        private String ncn;
        private String custId;

        public String getCustType() { return custType; }
        public void setCustType(String custType) { this.custType = custType; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Boolean getPhoneAuthCompleted() { return phoneAuthCompleted; }
        public void setPhoneAuthCompleted(Boolean phoneAuthCompleted) { this.phoneAuthCompleted = phoneAuthCompleted; }
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
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductFormDto {
        /** kt 재사용/skt 사용/lgt 사용/기타 (설계서 S104020101) */
        private String useType;
        /** 잔여사용요금 */
        private Long remainCharge;
        /** 위약금 */
        private Long penalty;
        /** 단말기 분납 잔액 */
        private Long installmentRemain;
        /** 해지사유 */
        private String cancelReason;
        /** 메모 */
        private String memo;

        public String getUseType() { return useType; }
        public void setUseType(String useType) { this.useType = useType; }
        public Long getRemainCharge() { return remainCharge; }
        public void setRemainCharge(Long remainCharge) { this.remainCharge = remainCharge; }
        public Long getPenalty() { return penalty; }
        public void setPenalty(Long penalty) { this.penalty = penalty; }
        public Long getInstallmentRemain() { return installmentRemain; }
        public void setInstallmentRemain(Long installmentRemain) { this.installmentRemain = installmentRemain; }
        public String getCancelReason() { return cancelReason; }
        public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
        public String getMemo() { return memo; }
        public void setMemo(String memo) { this.memo = memo; }
    }
}
