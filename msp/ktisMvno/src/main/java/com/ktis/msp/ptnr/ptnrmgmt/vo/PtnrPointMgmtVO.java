package com.ktis.msp.ptnr.ptnrmgmt.vo;


import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name="ptnrPointMgmtVo")
public class PtnrPointMgmtVO extends BaseVo implements Serializable {

	/** serialVersionUID **/
	private static final long serialVersionUID = 5361103943997816947L;
	
	
	/** IBSheet **/
	private String status;		/** 상태 **/
	
	
	/** 제휴포인트정보 **/
	private String partnerId;		/** 제휴사ID **/
	private String rateCd;		/** 제휴상품 **/
	private String rateNm;		/** 제휴상품명 **/
	private String billYm;		/** 청구월 **/
	private String usgYm;		/** 사용월 **/
	private String contractNum;	/** 가입계약번호 */
	private String svcCntrNo;	/** 서비스계약번호 */
	private String pointType;	/** 지급유형 */
	private String invAmt;		/** 청구금액 **/
	private String pymAmt;		/** 수납금액 **/
	private String useAmt;		/** 사용금액 **/
	private String calPoint;	/** 정산포인트 **/
	private String adjPoint;	/** 조정포인트 **/
	private String reqPoint;	/** 지급요청포인트 **/
	private String payPoint;	/** 지급포인트 **/
	private String payResult;	/** 지급결과 **/
	private String fullPayYn;	/** 완납여부 **/
	private String sendFlag;	/** 전송결과 **/
	private String searchCd;	/** 검색구분 **/
	private String searchVal;	/** 검색어 **/
	
	
	
	/** 기타정보 **/
	private String userId;		/** 사용자 **/
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}


	public String getRateCd() {
		return rateCd;
	}


	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}


	public String getRateNm() {
		return rateNm;
	}


	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}


	public String getBillYm() {
		return billYm;
	}


	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}


	public String getUsgYm() {
		return usgYm;
	}


	public void setUsgYm(String usgYm) {
		this.usgYm = usgYm;
	}


	public String getContractNum() {
		return contractNum;
	}


	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}


	public String getSvcCntrNo() {
		return svcCntrNo;
	}


	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}


	public String getPointType() {
		return pointType;
	}


	public void setPointType(String pointType) {
		this.pointType = pointType;
	}


	public String getInvAmt() {
		return invAmt;
	}


	public void setInvAmt(String invAmt) {
		this.invAmt = invAmt;
	}


	public String getPymAmt() {
		return pymAmt;
	}


	public void setPymAmt(String pymAmt) {
		this.pymAmt = pymAmt;
	}


	public String getUseAmt() {
		return useAmt;
	}


	public void setUseAmt(String useAmt) {
		this.useAmt = useAmt;
	}


	public String getCalPoint() {
		return calPoint;
	}


	public void setCalPoint(String calPoint) {
		this.calPoint = calPoint;
	}


	public String getAdjPoint() {
		return adjPoint;
	}


	public void setAdjPoint(String adjPoint) {
		this.adjPoint = adjPoint;
	}


	public String getReqPoint() {
		return reqPoint;
	}


	public void setReqPoint(String reqPoint) {
		this.reqPoint = reqPoint;
	}


	public String getPayPoint() {
		return payPoint;
	}


	public void setPayPoint(String payPoint) {
		this.payPoint = payPoint;
	}


	public String getPayResult() {
		return payResult;
	}


	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}


	public String getFullPayYn() {
		return fullPayYn;
	}


	public void setFullPayYn(String fullPayYn) {
		this.fullPayYn = fullPayYn;
	}


	public String getSendFlag() {
		return sendFlag;
	}


	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
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


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}