package com.ktis.msp.rsk.payTargetMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PayTargetVO
 * @Description : 
 * @author : 박준성
 * @Create Date : 2016.10.14 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="payTargetVO.java")
public class PayTargetVO extends BaseVo implements Serializable{

	private String lstComActvDate;	// 개통일
	private String contractNum;		// 계약번호
	private String openAgntCd;		// 대리점명
	private String rateNm;			// 요금제
	private String salePlcyNm;		// 판매정책
	private String operType;		// 개통유형
	private String reqBuyType;		// 구매유형
	private String subStatus;		// 상태값
	
	private String searchStartDt; 	//조회 시작일
    private String searchEndDt; 	//조회 종료일
    private String searchGbn; 		//조회구분
	private String searchName; 		//조회값
	
	public String getLstComActvDate() {
		return lstComActvDate;
	}
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getSalePlcyNm() {
		return salePlcyNm;
	}
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
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
}
