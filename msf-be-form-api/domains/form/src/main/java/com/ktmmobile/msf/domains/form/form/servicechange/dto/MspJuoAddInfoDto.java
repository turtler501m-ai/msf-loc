package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;

public class MspJuoAddInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String contractNum;	//가입계약번호
    private int dvcOfwAmnt;	//단말기출고가
    private int instOrginAmnt;	//할부원금
    private int instMnthCnt;	//할부개월수
    private String appStartDd;	//적용시작일시 -YYYYMMDDHH24MISS
    private String appEndDate;	//적용종료일시 -YYYYMMDDHH24MISS
    private int enggMnthCnt;	//약정개월수
    private String modelName;	//단말기 모델명
    private String modelId;		//단말기 모델ID

    private int monthPay;	//월납부액
    private int remainPay;	//잔여할부금액
    private int remainMonth;	//잔여할부개월  SUBSCRIBER_NO

    private String subscriberNo ;  //전화번호
    private String enggYn ;  //약정여부

    private String agentCd ;  //대리점 코드
    private String agentNm ;  //대리점 명
    private String channelCd; //채널 코드
    private String channelNm; //채널 명

    /**
     * @return the contract_num
     */
    public String getContractNum() {
        return contractNum;
    }
    /**
     * @param contract_num the contract_num to set
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    /**
     * @return the dvcOfwAmnt
     */
    public int getDvcOfwAmnt() {
        return dvcOfwAmnt;
    }
    /**
     * @param dvcOfwAmnt the dvcOfwAmnt to set
     */
    public void setDvcOfwAmnt(int dvcOfwAmnt) {
        this.dvcOfwAmnt = dvcOfwAmnt;
    }
    /**
     * @return the instOrginAmnt
     */
    public int getInstOrginAmnt() {
        return instOrginAmnt;
    }
    /**
     * @param instOrginAmnt the instOrginAmnt to set
     */
    public void setInstOrginAmnt(int instOrginAmnt) {
        this.instOrginAmnt = instOrginAmnt;
    }
    /**
     * @return the instMnthCnt
     */
    public int getInstMnthCnt() {
        return instMnthCnt;
    }
    /**
     * @param instMnthCnt the instMnthCnt to set
     */
    public void setInstMnthCnt(int instMnthCnt) {
        this.instMnthCnt = instMnthCnt;
    }
    /**
     * @return the appStartDd
     */
    public String getAppStartDd() {
        return appStartDd;
    }
    /**
     * @param appStartDd the appStartDd to set
     */
    public void setAppStartDd(String appStartDd) {
        this.appStartDd = appStartDd;
    }
    /**
     * @return the appEndDate
     */
    public String getAppEndDate() {
        return appEndDate;
    }
    /**
     * @param appEndDate the appEndDate to set
     */
    public void setAppEndDate(String appEndDate) {
        this.appEndDate = appEndDate;
    }
    /**
     * @return the enggMnthCnt
     */
    public int getEnggMnthCnt() {
        return enggMnthCnt;
    }
    /**
     * @param enggMnthCnt the enggMnthCnt to set
     */
    public void setEnggMnthCnt(int enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
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
     * @return the modelId
     */
    public String getModelId() {
        return modelId;
    }
    /**
     * @param modelId the modelId to set
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    /**
     * @return the monthPay
     */
    public int getMonthPay() {
    	if(instOrginAmnt != 0) {
            this.monthPay = instOrginAmnt / instOrginAmnt;
    	} 
        //this.monthPay = instOrginAmnt / instOrginAmnt;
        return monthPay;
    }
    /**
     * @param monthPay the monthPay to set
     */
    public void setMonthPay(int monthPay) {
        this.monthPay = monthPay;
    }
    /**
     * @return the remainPay
     */
    public int getRemainPay() {
        return remainPay;
    }
    /**
     * @param remainPay the remainPay to set
     */
    public void setRemainPay(int remainPay) {
        this.remainPay = remainPay;
    }
    /**
     * @return the remainMonth
     */
    public int getRemainMonth() {
        return remainMonth;
    }
    /**
     * @param remainMonth the remainMonth to set
     */
    public void setRemainMonth(int remainMonth) {
        this.remainMonth = remainMonth;
    }
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getEnggYn() {
		return enggYn;
	}
	public void setEnggYn(String enggYn) {
		this.enggYn = enggYn;
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
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getChannelNm() {
		return channelNm;
	}
	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}



}
