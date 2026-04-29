package com.ktmmobile.msf.domains.form.form.termination.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TerminationApplyReqDto {

    private Customer customer;
    private Product product;
    private Agreement agreement;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Customer {
        private String managerCd;
        private String managerNm;
        private String agentCd;
        private String agentNm;
        private String customerType;
        private String identityCertTypeCd;
        private String identityTypeCd;
        private String identityIssuDate;
        private String identityIssuRegion;
        private String driveLicnsNo;
        private String userName;
        private String userBirthDate;
        private String selfIssuNo;
        private String cancelPhone1;
        private String cancelPhone2;
        private String cancelPhone3;
        private String afterTel1;
        private String afterTel2;
        private String afterTel3;
        private String postMethod;
        private String agencyName;
        private String cpntId;
        private String cpntNm;
        private String cntpntShopCd;
        private String cntpntShopNm;
        private String ncn;
        private String custId;

        public String getManagerCd() {
            return managerCd;
        }

        public void setManagerCd(String managerCd) {
            this.managerCd = managerCd;
        }

        public String getManagerNm() {
            return managerNm;
        }

        public void setManagerNm(String managerNm) {
            this.managerNm = managerNm;
        }

        public String getAgentCd() {
            return agentCd;
        }

        public void setAgentCd(String agentCd) {
            this.agentCd = agentCd;
        }

        public String getAgentNm() {
            return agentNm;
        }

        public void setAgentNm(String agentNm) {
            this.agentNm = agentNm;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getIdentityCertTypeCd() {
            return identityCertTypeCd;
        }

        public void setIdentityCertTypeCd(String identityCertTypeCd) {
            this.identityCertTypeCd = identityCertTypeCd;
        }

        public String getIdentityTypeCd() {
            return identityTypeCd;
        }

        public void setIdentityTypeCd(String identityTypeCd) {
            this.identityTypeCd = identityTypeCd;
        }

        public String getIdentityIssuDate() {
            return identityIssuDate;
        }

        public void setIdentityIssuDate(String identityIssuDate) {
            this.identityIssuDate = identityIssuDate;
        }

        public String getIdentityIssuRegion() {
            return identityIssuRegion;
        }

        public void setIdentityIssuRegion(String identityIssuRegion) {
            this.identityIssuRegion = identityIssuRegion;
        }

        public String getDriveLicnsNo() {
            return driveLicnsNo;
        }

        public void setDriveLicnsNo(String driveLicnsNo) {
            this.driveLicnsNo = driveLicnsNo;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserBirthDate() {
            return userBirthDate;
        }

        public void setUserBirthDate(String userBirthDate) {
            this.userBirthDate = userBirthDate;
        }

        public String getSelfIssuNo() {
            return selfIssuNo;
        }

        public void setSelfIssuNo(String selfIssuNo) {
            this.selfIssuNo = selfIssuNo;
        }

        public String getCancelPhone1() {
            return cancelPhone1;
        }

        public void setCancelPhone1(String cancelPhone1) {
            this.cancelPhone1 = cancelPhone1;
        }

        public String getCancelPhone2() {
            return cancelPhone2;
        }

        public void setCancelPhone2(String cancelPhone2) {
            this.cancelPhone2 = cancelPhone2;
        }

        public String getCancelPhone3() {
            return cancelPhone3;
        }

        public void setCancelPhone3(String cancelPhone3) {
            this.cancelPhone3 = cancelPhone3;
        }

        public String getAfterTel1() {
            return afterTel1;
        }

        public void setAfterTel1(String afterTel1) {
            this.afterTel1 = afterTel1;
        }

        public String getAfterTel2() {
            return afterTel2;
        }

        public void setAfterTel2(String afterTel2) {
            this.afterTel2 = afterTel2;
        }

        public String getAfterTel3() {
            return afterTel3;
        }

        public void setAfterTel3(String afterTel3) {
            this.afterTel3 = afterTel3;
        }

        public String getPostMethod() {
            return postMethod;
        }

        public void setPostMethod(String postMethod) {
            this.postMethod = postMethod;
        }

        public String getAgencyName() {
            return agencyName;
        }

        public void setAgencyName(String agencyName) {
            this.agencyName = agencyName;
        }

        public String getCpntId() {
            return cpntId;
        }

        public void setCpntId(String cpntId) {
            this.cpntId = cpntId;
        }

        public String getCpntNm() {
            return cpntNm;
        }

        public void setCpntNm(String cpntNm) {
            this.cpntNm = cpntNm;
        }

        public String getCntpntShopCd() {
            return cntpntShopCd;
        }

        public void setCntpntShopCd(String cntpntShopCd) {
            this.cntpntShopCd = cntpntShopCd;
        }

        public String getCntpntShopNm() {
            return cntpntShopNm;
        }

        public void setCntpntShopNm(String cntpntShopNm) {
            this.cntpntShopNm = cntpntShopNm;
        }

        public String getNcn() {
            return ncn;
        }

        public void setNcn(String ncn) {
            this.ncn = ncn;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        private String isActive;
        private String usageFee;
        private String penaltyFee;
        private String finalAmount;
        private String remainPeriod;
        private String remainAmount;
        private String memo;

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getUsageFee() {
            return usageFee;
        }

        public void setUsageFee(String usageFee) {
            this.usageFee = usageFee;
        }

        public String getPenaltyFee() {
            return penaltyFee;
        }

        public void setPenaltyFee(String penaltyFee) {
            this.penaltyFee = penaltyFee;
        }

        public String getFinalAmount() {
            return finalAmount;
        }

        public void setFinalAmount(String finalAmount) {
            this.finalAmount = finalAmount;
        }

        public String getRemainPeriod() {
            return remainPeriod;
        }

        public void setRemainPeriod(String remainPeriod) {
            this.remainPeriod = remainPeriod;
        }

        public String getRemainAmount() {
            return remainAmount;
        }

        public void setRemainAmount(String remainAmount) {
            this.remainAmount = remainAmount;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Agreement {
        private boolean agreeCheck1;
        private boolean agreeCheck2;
        private boolean agreeCheck3;

        public boolean isAgreeCheck1() {
            return agreeCheck1;
        }

        public void setAgreeCheck1(boolean agreeCheck1) {
            this.agreeCheck1 = agreeCheck1;
        }

        public boolean isAgreeCheck2() {
            return agreeCheck2;
        }

        public void setAgreeCheck2(boolean agreeCheck2) {
            this.agreeCheck2 = agreeCheck2;
        }

        public boolean isAgreeCheck3() {
            return agreeCheck3;
        }

        public void setAgreeCheck3(boolean agreeCheck3) {
            this.agreeCheck3 = agreeCheck3;
        }
    }
}
