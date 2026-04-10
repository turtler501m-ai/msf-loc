package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class MyPageSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userType;
    private String userDivisionYn;
    private String userName;
    private String ncn;
    private String ctn;
    private String billSeqNo;//청구일련번호
    private String billMonth ;// 청구년월
    private String message;
    private String custId;
    private String modelName;
    private String contractNum;//계약번호
    private Integer menuKey;
    private String backUrl;
    private String pageNo;
    private String tabBtnIndex;
    private String subStatus; // 상태값  SUB_STATUS
    private int moCtn;
    
    private String seq;
    private String addDivCd;
    private String soc;//요금제 코드


    public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public int getMoCtn() {
		return moCtn;
	}
	public void setMoCtn(int moCtn) {
		this.moCtn = moCtn;
	}
	public String getTabBtnIndex() {
        return tabBtnIndex;
    }
    public void setTabBtnIndex(String tabBtnIndex) {
        this.tabBtnIndex = tabBtnIndex;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNcn() {
        return ncn;
    }
    public void setNcn(String ncn) {
        this.ncn = ncn;
    }
    public String getCtn() {
        return ctn;
    }
    public void setCtn(String ctn) {
        this.ctn = ctn;
    }
    public String getBillMonth() {
        return billMonth;
    }
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }
    public String getBillSeqNo() {
        return billSeqNo;
    }
    public void setBillSeqNo(String billSeqNo) {
        this.billSeqNo = billSeqNo;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    /**
     * @return the menuKey
     */
    public Integer getMenuKey() {
        return menuKey;
    }
    /**
     * @param menuKey the menuKey to set
     */
    public void setMenuKey(Integer menuKey) {
        this.menuKey = menuKey;
    }
    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }
    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    /**
     * @return the backUrl
     */
    public String getBackUrl() {
        return backUrl;
    }
    /**
     * @param backUrl the backUrl to set
     */
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    /**
     * @return the contractNum
     */
    public String getContractNum() {
        return contractNum;
    }
    /**
     * @param contractNum the contractNum to set
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getPageNo() {
        return pageNo;
    }
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getSubStatus() {
        return subStatus;
    }
    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }
	public String getUserDivisionYn() {
		return userDivisionYn;
	}
	public void setUserDivisionYn(String userDivisionYn) {
		this.userDivisionYn = userDivisionYn;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getAddDivCd() {
		return addDivCd;
	}
	public void setAddDivCd(String addDivCd) {
		this.addDivCd = addDivCd;
	}
    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }
}
