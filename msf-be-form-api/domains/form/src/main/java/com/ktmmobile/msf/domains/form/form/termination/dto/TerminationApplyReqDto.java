package com.ktmmobile.msf.domains.form.form.termination.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TerminationApplyReqDto {

    // Request payload (screen)
    private Customer customer;
    private Product product;
    private Agreement agreement;

    // Persist payload (MSF_REQUEST_CANCEL)
    private Long requestKey;
    private String cretIp;
    private String cretDt;
    private String cretId;
    private String amdIp;
    private String amdDt;
    private String amdId;
    private String managerCd;
    private String agentCd;
    private String shopCd;
    private String shopNm;
    private String realShopNm;
    private String cpntId;
    private String cntpntShopCd;
    private String operTypeCd;
    private String cstmrTypeCd;
    private String cstmrNm;
    private String cancelMobileNo;
    private String receiveMobileNo;
    private String contractNum;
    private String scanId;
    private String authInfo;
    private String identityTypeCd;
    private String identityIssuDate;
    private String identityIssuRegion;
    private String driveLicnsNo;
    private String receiveWayCd;
    private String cancelUseCompanyCd;
    private Long payAmt;
    private Long pnltAmt;
    private Long lastSumAmt;
    private Integer instamtMnthCnt;
    private Long instamtMnthAmt;
    private String benefitAgreeYn;
    private String clauseCntrDelYn;
    private String etcAgreeYn;
    private String memo;
    private String regDate;
    private String regstId;
    private String regDt;
    private String procCd;
    private String recYn;
    private String resCd;
    private String resMsg;
    private String resNo;
    private String appFormYn;
    private String appFormXmlYn;
    private String fileNm;
    private String fileMaskNm;

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

    public Long getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(Long requestKey) {
        this.requestKey = requestKey;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public String getCretDt() {
        return cretDt;
    }

    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdIp() {
        return amdIp;
    }

    public void setAmdIp(String amdIp) {
        this.amdIp = amdIp;
    }

    public String getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public String getManagerCd() {
        return managerCd;
    }

    public void setManagerCd(String managerCd) {
        this.managerCd = managerCd;
    }

    public String getAgentCd() {
        return agentCd;
    }

    public void setAgentCd(String agentCd) {
        this.agentCd = agentCd;
    }

    public String getShopCd() {
        return shopCd;
    }

    public void setShopCd(String shopCd) {
        this.shopCd = shopCd;
    }

    public String getShopNm() {
        return shopNm;
    }

    public void setShopNm(String shopNm) {
        this.shopNm = shopNm;
    }

    public String getRealShopNm() {
        return realShopNm;
    }

    public void setRealShopNm(String realShopNm) {
        this.realShopNm = realShopNm;
    }

    public String getCpntId() {
        return cpntId;
    }

    public void setCpntId(String cpntId) {
        this.cpntId = cpntId;
    }

    public String getCntpntShopCd() {
        return cntpntShopCd;
    }

    public void setCntpntShopCd(String cntpntShopCd) {
        this.cntpntShopCd = cntpntShopCd;
    }

    public String getOperTypeCd() {
        return operTypeCd;
    }

    public void setOperTypeCd(String operTypeCd) {
        this.operTypeCd = operTypeCd;
    }

    public String getCstmrTypeCd() {
        return cstmrTypeCd;
    }

    public void setCstmrTypeCd(String cstmrTypeCd) {
        this.cstmrTypeCd = cstmrTypeCd;
    }

    public String getCancelMobileNo() {
        return cancelMobileNo;
    }

    public void setCancelMobileNo(String cancelMobileNo) {
        this.cancelMobileNo = cancelMobileNo;
    }

    public String getCstmrNm() {
        return cstmrNm;
    }

    public void setCstmrNm(String cstmrNm) {
        this.cstmrNm = cstmrNm;
    }

    public String getReceiveMobileNo() {
        return receiveMobileNo;
    }

    public void setReceiveMobileNo(String receiveMobileNo) {
        this.receiveMobileNo = receiveMobileNo;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
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

    public String getReceiveWayCd() {
        return receiveWayCd;
    }

    public void setReceiveWayCd(String receiveWayCd) {
        this.receiveWayCd = receiveWayCd;
    }

    public String getCancelUseCompanyCd() {
        return cancelUseCompanyCd;
    }

    public void setCancelUseCompanyCd(String cancelUseCompanyCd) {
        this.cancelUseCompanyCd = cancelUseCompanyCd;
    }

    public Long getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(Long payAmt) {
        this.payAmt = payAmt;
    }

    public Long getPnltAmt() {
        return pnltAmt;
    }

    public void setPnltAmt(Long pnltAmt) {
        this.pnltAmt = pnltAmt;
    }

    public Long getLastSumAmt() {
        return lastSumAmt;
    }

    public void setLastSumAmt(Long lastSumAmt) {
        this.lastSumAmt = lastSumAmt;
    }

    public Integer getInstamtMnthCnt() {
        return instamtMnthCnt;
    }

    public void setInstamtMnthCnt(Integer instamtMnthCnt) {
        this.instamtMnthCnt = instamtMnthCnt;
    }

    public Long getInstamtMnthAmt() {
        return instamtMnthAmt;
    }

    public void setInstamtMnthAmt(Long instamtMnthAmt) {
        this.instamtMnthAmt = instamtMnthAmt;
    }

    public String getBenefitAgreeYn() {
        return benefitAgreeYn;
    }

    public void setBenefitAgreeYn(String benefitAgreeYn) {
        this.benefitAgreeYn = benefitAgreeYn;
    }

    public String getClauseCntrDelYn() {
        return clauseCntrDelYn;
    }

    public void setClauseCntrDelYn(String clauseCntrDelYn) {
        this.clauseCntrDelYn = clauseCntrDelYn;
    }

    public String getEtcAgreeYn() {
        return etcAgreeYn;
    }

    public void setEtcAgreeYn(String etcAgreeYn) {
        this.etcAgreeYn = etcAgreeYn;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getProcCd() {
        return procCd;
    }

    public void setProcCd(String procCd) {
        this.procCd = procCd;
    }

    public String getRecYn() {
        return recYn;
    }

    public void setRecYn(String recYn) {
        this.recYn = recYn;
    }

    public String getResCd() {
        return resCd;
    }

    public void setResCd(String resCd) {
        this.resCd = resCd;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getAppFormYn() {
        return appFormYn;
    }

    public void setAppFormYn(String appFormYn) {
        this.appFormYn = appFormYn;
    }

    public String getAppFormXmlYn() {
        return appFormXmlYn;
    }

    public void setAppFormXmlYn(String appFormXmlYn) {
        this.appFormXmlYn = appFormXmlYn;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileMaskNm() {
        return fileMaskNm;
    }

    public void setFileMaskNm(String fileMaskNm) {
        this.fileMaskNm = fileMaskNm;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Customer {
        private String managerCd;
        private String agentCd;
        private String customerType;
        private String userName;
        private String userBirthDate;
        private String cancelPhone1;
        private String cancelPhone2;
        private String cancelPhone3;
        private String afterTel1;
        private String afterTel2;
        private String afterTel3;
        private String postMethod;
        private String agencyName;
        private String ncn;
        private String custId;

        public String getManagerCd() {
            return managerCd;
        }

        public void setManagerCd(String managerCd) {
            this.managerCd = managerCd;
        }

        public String getAgentCd() {
            return agentCd;
        }

        public void setAgentCd(String agentCd) {
            this.agentCd = agentCd;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
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
