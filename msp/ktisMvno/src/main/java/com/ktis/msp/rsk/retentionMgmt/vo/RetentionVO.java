package com.ktis.msp.rsk.retentionMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;


/**
 * @Class Name : CanRejoinVO
 * @Description : 
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.08.31  박준성 최초생성
 * @
 * @author : 박준성
 * @Create Date : 2016.08.31 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="retentionVO.java")
public class RetentionVO  extends BaseVo implements Serializable {

	private String contractNum;
	private String subLinkName;
	private String userSsn;
	private String reqByType; //
	private String subscriberNo;
	private String lstComActvDate; //
	private String openAgntCd; //
	private String modelName;
	private String intmSrlNo;
	private String cContractNum;
	private String cSubLinkName;
	private String cUserSsn;
	private String cReqByType;
	private String cSubscriberNo;
	private String cLstComActvDate;
	private String cOpenAgntCd;
	private String cModelName;
	private String cIntmSrlNo;
	private String cSubStatusDate;
	private String cSubStatusRsnCode;
	private String cUseDay;
	private String subStatus; //상태
	private String subStatusRsnCode; //해지사유
	
	
	private String searchStartDt; //조회 시작일
    private String searchEndDt; //조회 종료일
    private String searchGbn; //조회구분
	private String searchName; //조회값
	
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
    public String getSubStatusRsnCode() {
		return subStatusRsnCode;
	}
	public void setSubStatusRsnCode(String subStatusRsnCode) {
		this.subStatusRsnCode = subStatusRsnCode;
	}
	public String getSearchStartDt() {
		return searchStartDt;
	}
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	public String getSearchEndDt() {
		return searchEndDt;
	}
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
    public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
    
    
    
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSubLinkName() {
		return subLinkName;
	}
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}
	public String getUserSsn() {
		return userSsn;
	}
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}
	public String getReqByType() {
		return reqByType;
	}
	public void setReqByType(String reqByType) {
		this.reqByType = reqByType;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getLstComActvDate() {
		return lstComActvDate;
	}
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getIntmSrlNo() {
		return intmSrlNo;
	}
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}
	public String getcContractNum() {
		return cContractNum;
	}
	public void setcContractNum(String cContractNum) {
		this.cContractNum = cContractNum;
	}
	public String getcSubLinkName() {
		return cSubLinkName;
	}
	public void setcSubLinkName(String cSubLinkName) {
		this.cSubLinkName = cSubLinkName;
	}
	public String getcUserSsn() {
		return cUserSsn;
	}
	public void setcUserSsn(String cUserSsn) {
		this.cUserSsn = cUserSsn;
	}
	public String getcReqByType() {
		return cReqByType;
	}
	public void setcReqByType(String cReqByType) {
		this.cReqByType = cReqByType;
	}
	public String getcSubscriberNo() {
		return cSubscriberNo;
	}
	public void setcSubscriberNo(String cSubscriberNo) {
		this.cSubscriberNo = cSubscriberNo;
	}
	public String getcLstComActvDate() {
		return cLstComActvDate;
	}
	public void setcLstComActvDate(String cLstComActvDate) {
		this.cLstComActvDate = cLstComActvDate;
	}
	public String getcOpenAgntCd() {
		return cOpenAgntCd;
	}
	public void setcOpenAgntCd(String cOpenAgntCd) {
		this.cOpenAgntCd = cOpenAgntCd;
	}
	public String getcModelName() {
		return cModelName;
	}
	public void setcModelName(String cModelName) {
		this.cModelName = cModelName;
	}
	public String getcIntmSrlNo() {
		return cIntmSrlNo;
	}
	public void setcIntmSrlNo(String cIntmSrlNo) {
		this.cIntmSrlNo = cIntmSrlNo;
	}
	public String getcSubStatusDate() {
		return cSubStatusDate;
	}
	public void setcSubStatusDate(String cSubStatusDate) {
		this.cSubStatusDate = cSubStatusDate;
	}
	public String getcSubStatusRsnCode() {
		return cSubStatusRsnCode;
	}
	public void setcSubStatusRsnCode(String cSubStatusRsnCode) {
		this.cSubStatusRsnCode = cSubStatusRsnCode;
	}
	public String getcUseDay() {
		return cUseDay;
	}
	public void setcUseDay(String cUseDay) {
		this.cUseDay = cUseDay;
	}
	
	
	
}
