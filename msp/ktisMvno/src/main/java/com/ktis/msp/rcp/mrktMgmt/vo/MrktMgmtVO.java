package com.ktis.msp.rcp.mrktMgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class MrktMgmtVO extends BaseVo implements Serializable  {

	/** 검색구분 */
	private String searchCd;
	private String searchVal;
	private String searchAgrYn;

	/** 발송이력 */
	private String sendYm;
	private String searchFromDt;
	private String searchToDt;
	private String searchSendDiv;
	private String searchSendYn;
	private String sendStDt;
	private String sendEdDt;

	/** 마케팅동의조회 */
	private String contractNum;
	private String agrYn;
	private String strtDttm;
	private String endDttm;

	private String personalInfoCollectAgree;
	private String othersTrnsAgree;
	private String MrktRvisnDttm;
	private String PersonalInfoRvisnDttm;
	private String OthersTrnsRvisnDttm;
	private String othersTrnsKtAgree;			//2025.07.30 신규 추가 컬럼: 제공을 위한 제3자 제공 동의(KT)
	private String othersTrnsKtRvisnDttm;		//2025.07.30 신규 추가 컬럼: 제공을 위한 제3자 제공 동의(KT) 변경일시
	private String othersAdReceiveAgree;		//2025.07.30 신규 추가 컬럼: 제3자 제공관련 광고 수신 동의
	private String othersAdReceiveRvisnDttm;	//2025.07.30 신규 추가 컬럼: 제3자 제공관련 광고 수신 동의 변경일시

	private String fileName;

	List<MrktMgmtVO> items;

	/** Y41 연동 */
    private String svcCntrNo;
	private String subscriberNo;
	private String customerId;

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getAgrYn() {
		return agrYn;
	}
	public void setAgrYn(String agrYn) {
		this.agrYn = agrYn;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getEndDttm() {
		return endDttm;
	}
	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}
	public String getSendYm() {
		return sendYm;
	}
	public void setSendYm(String sendYm) {
		this.sendYm = sendYm;
	}
	public String getSearchCd() {
		return searchCd;
	}
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

	public String getSearchAgrYn() {
		return searchAgrYn;
	}

	public void setSearchAgrYn(String searchAgrYn) {
		this.searchAgrYn = searchAgrYn;
	}

	public String getSearchFromDt() {
		return searchFromDt;
	}
	public void setSearchFromDt(String searchFromDt) {
		this.searchFromDt = searchFromDt;
	}
	public String getSearchToDt() {
		return searchToDt;
	}
	public void setSearchToDt(String searchToDt) {
		this.searchToDt = searchToDt;
	}
	public String getSearchSendDiv() {
		return searchSendDiv;
	}
	public void setSearchSendDiv(String searchSendDiv) {
		this.searchSendDiv = searchSendDiv;
	}
	public String getSearchSendYn() {
		return searchSendYn;
	}
	public void setSearchSendYn(String searchSendYn) {
		this.searchSendYn = searchSendYn;
	}
	public List<MrktMgmtVO> getItems() {
		return items;
	}
	public void setItems(List<MrktMgmtVO> items) {
		this.items = items;
	}
	public String getSendStDt() {
		return sendStDt;
	}
	public void setSendStDt(String sendStDt) {
		this.sendStDt = sendStDt;
	}
	public String getSendEdDt() {
		return sendEdDt;
	}
	public void setSendEdDt(String sendEdDt) {
		this.sendEdDt = sendEdDt;
	}
	public String getPersonalInfoCollectAgree() {
		return personalInfoCollectAgree;
	}
	public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) {
		this.personalInfoCollectAgree = personalInfoCollectAgree;
	}
	public String getOthersTrnsAgree() {
		return othersTrnsAgree;
	}
	public void setOthersTrnsAgree(String othersTrnsAgree) {
		this.othersTrnsAgree = othersTrnsAgree;
	}
	public String getMrktRvisnDttm() {
		return MrktRvisnDttm;
	}
	public void setMrktRvisnDttm(String mrktRvisnDttm) {
		MrktRvisnDttm = mrktRvisnDttm;
	}
	public String getPersonalInfoRvisnDttm() {
		return PersonalInfoRvisnDttm;
	}
	public void setPersonalInfoRvisnDttm(String personalInfoRvisnDttm) {
		PersonalInfoRvisnDttm = personalInfoRvisnDttm;
	}
	public String getOthersTrnsRvisnDttm() {
		return OthersTrnsRvisnDttm;
	}
	public void setOthersTrnsRvisnDttm(String othersTrnsRvisnDttm) {
		OthersTrnsRvisnDttm = othersTrnsRvisnDttm;
	}

	public String getOthersTrnsKtAgree() {
		return othersTrnsKtAgree;
	}

	public void setOthersTrnsKtAgree(String othersTrnsKtAgree) {
		this.othersTrnsKtAgree = othersTrnsKtAgree;
	}

	public String getOthersTrnsKtRvisnDttm() {
		return othersTrnsKtRvisnDttm;
	}

	public void setOthersTrnsKtRvisnDttm(String othersTrnsKtRvisnDttm) {
		this.othersTrnsKtRvisnDttm = othersTrnsKtRvisnDttm;
	}

	public String getOthersAdReceiveAgree() {
		return othersAdReceiveAgree;
	}

	public void setOthersAdReceiveAgree(String othersAdReceiveAgree) {
		this.othersAdReceiveAgree = othersAdReceiveAgree;
	}

	public String getOthersAdReceiveRvisnDttm() {
		return othersAdReceiveRvisnDttm;
	}

	public void setOthersAdReceiveRvisnDttm(String othersAdReceiveRvisnDttm) {
		this.othersAdReceiveRvisnDttm = othersAdReceiveRvisnDttm;
	}

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }

    public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
