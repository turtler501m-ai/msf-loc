package com.ktis.msp.org.usimmgmt.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : UsimMgmtVO
 * @Description : 유심관리 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="usimMgmtVO")
public class UsimMgmtVO extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 1383510499751369252L;
	
	//private String seq;
	private String orgnId; 		/** 조직ID */
	private String orgnNm; 		/** 조직명 */
	private String usimNo; 		/** 유심번호 */
	private String agentNm;		/** 대리점 */
	private String reqInDay;	/** 신청일자 */
	private String regstId; 	/** 등록자ID */
	private String regstDttm; 	/** 등록일자 */
	private String rvisnId; 	/** 수정자ID */
	private String rvisnDttm; 	/** 수정일자 */
	private String evntSeq;		/** 이벤트번호 */
	private String evntCd;		/** 이벤트유형코드 */
	private String evntNm;		/** 이벤트유형 */
	private String evntRegstId;	/** 이벤트등록자ID */
	private String evntDttm;	/** 이벤트발생일자 */
	private String deliveryDttm; /*배송일자*/
	private String orderNum;	/*주문번호*/
	
	private String fileName;
	
	private String searchFromDt;
	private String searchToDt;
	private String lstComActvDate;
	private String contractNum;
	private String status;
	private String sessionUserId;
	
	List<String> arrSeq;	
    List<UsimMgmtVO> items;
    
    //2021.01.21 [SRM21011583814] M전산 제휴유심관리 內 단말기코드 항목 추가요청   단말ID,모델코드 추가 
    private String usimPrdtId;		/* 모델ID */
    private String usimPrdtCode;		/* 모델코드*/    
    
//	public String getSeq() {
//		return seq;
//	}
//	public void setSeq(String seq) {
//		this.seq = seq;
//	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getUsimNo() {
		return usimNo;
	}
	public void setUsimNo(String usimNo) {
		this.usimNo = usimNo;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	public String getReqInDay() {
		return reqInDay;
	}
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getEvntSeq() {
		return evntSeq;
	}
	public void setEvntSeq(String evntSeq) {
		this.evntSeq = evntSeq;
	}
	public String getEvntCd() {
		return evntCd;
	}
	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}
	public String getEvntNm() {
		return evntNm;
	}
	public void setEvntNm(String evntNm) {
		this.evntNm = evntNm;
	}
	public String getEvntRegstId() {
		return evntRegstId;
	}
	public void setEvntRegstId(String evntRegstId) {
		this.evntRegstId = evntRegstId;
	}
	public String getEvntDttm() {
		return evntDttm;
	}
	public void setEvntDttm(String evntDttm) {
		this.evntDttm = evntDttm;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSessionUserId() {
		return sessionUserId;
	}
	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}
	public List<String> getArrSeq() {
		return arrSeq;
	}
	public void setArrSeq(List<String> arrSeq) {
		this.arrSeq = arrSeq;
	}
	public List<UsimMgmtVO> getItems() {
		return items;
	}
	public void setItems(List<UsimMgmtVO> items) {
		this.items = items;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDeliveryDttm() {
		return deliveryDttm;
	}
	public void setDeliveryDttm(String deliveryDttm) {
		this.deliveryDttm = deliveryDttm;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getUsimPrdtId() {
		return usimPrdtId;
	}
	public void setUsimPrdtId(String usimPrdtId) {
		this.usimPrdtId = usimPrdtId;
	}
	public String getUsimPrdtCode() {
		return usimPrdtCode;
	}
	public void setUsimPrdtCode(String usimPrdtCode) {
		this.usimPrdtCode = usimPrdtCode;
	}

}
