package com.ktmmobile.mcp.usermanage.dto;

public class McpUserCertDto {

    // MCP_USER
    private String userId;
    private String userNm;

    private String mobileNo;
    private String birthday;
    private String userDivision;

    // MCP_USER_CNTR_MNG
    private String contractNum;
    private String subStatus;
    private String subLinkName;
    private String subscriberNo;
    private String repNo;
    private String modelName;
    private String modelId;
    private String sysRdate;
    private String srmNo;
    
    private String customerId;
    private String customerType;
    private String svcCntrNo;


    public String getSrmNo() {
        return srmNo;
    }
    public void setSrmNo(String srmNo) {
        this.srmNo = srmNo;
    }
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    public String getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(String sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getRepNo() {
        return repNo;
    }
    public void setRepNo(String repNo) {
        this.repNo = repNo;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getSubStatus() {
        return subStatus;
    }
    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }
    public String getSubLinkName() {
        return subLinkName;
    }
    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
    }
    public String getSubscriberNo() {
        return subscriberNo;
    }
    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserNm() {
        return userNm;
    }
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getUserDivision() {
        return userDivision;
    }
    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getUserDivisionTxt() {
        if ("00".equals(userDivision)) {
            return "회원";
        } else if ("01".equals(userDivision)) {
            return "정회원";
        } else {
            return userDivision;
        }

    }
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }
}
