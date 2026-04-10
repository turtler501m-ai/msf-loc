package com.ktis.msp.pps.cardmgmt.vo;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;
/**
 * @Class Name : PpsPinSummaryOpenVo
 * @Description : 핀개통내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsPinSummaryOpenVo")
public class PpsPinSummaryOpenVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	

	/**
	 * PIN개통고유번호:number(20)
	 */
	private int openSeq;

	/**
	 * 시작관리번호:varchar2(20)
	 */
	private String startMngCode;

	/**
	 * 종료관리번호:varchar2(20)
	 */
	private String endMngCode;

	/**
	 * 개통개수:number(20)
	 */
	private int openCount;

	/**
	 * 액면가액합계:number(20)
	 */
	private int pinChargeSum;

	/**
	 * 출고가격합계(회계반영금액합계, 할인율 적용됨):number(20)
	 */
	private int agentChargeSum;

	/**
	 * 개통관리자:varchar2(20)
	 */
	private String openAdminId;
    /**
     * 개통관리자명
     */
	private String openAdminNm;

	/**
	 * 개통일자:date(0)
	 */
	private String openDate;

	/**
	 * 개통대리점구분(A=일반대리점, C=딜러점개통):varchar2(1)
	 */
	private String openAgentType;
	/**
	 * 개통대리점구분명
	 */
	private String openAgentTypeNm;

	/**
	 * 일반대리점코드:varchar2(20)
	 */
	private String openAgentId;

 
	/**
	 * 대리점명
	 */
	private String openAgentNm;

	/**
	 * 카드딜러코드:varchar2(20)
	 */
	private String openDealerId;
	/**
	 * 카드딜러명
	 */
	private String openDealerNm;

	/**
	 * 수납금액:number(20)
	 */
	private int payAmt;

	/**
	 * 수납여부:varchar2(1)
	 */
	private String payFlag;
	/**
	 * 수납여부명
	 */
	private String payFlagNm;

	/**
	 * 수납일자:date(0)
	 */
	private String payDate;

	/**
	 * 등록일자:date(0)
	 */
	private String recordDate;

	/**
	 * 비고:varchar2(1000)
	 */
	private String remark;
	
	/**
	 * 관리번호 시작~종료
	 */
	private String mngCodeStr;

	/**
	 * 프로시저 리턴 코드 
	 */
	private String retCode;

	/**
	 * 프로시저 메시지 
	 */
	private String retMsg;


	/**
	 * 취소여부
	 */
	private String cancelFlag;
	/**
	 * 취소여부명
	 */
	private String cancelFlagNm;
	
	/**
	 * 삭제일자
	 */
	private String cancelDate;
		
	/**
	 * 반품SEQ
	 */
	private int stopSeq;
	
	/**
	 * PIN개통시 할인율
	 */
	private int pinDisRate;
	
	/**
	 * 수정사용자명
	 */
	private String updNmStr;
	
	/**
	 * PIN 무료지급여부 (Y:무료지급)
	 */
	private String freeFlag;
	
	/**
	 * 핀무료지급명
	 */
	private String freeFlagNm;
	
	
	
	


	/**
	 * @return the openSeq
	 */
	public int getOpenSeq() {
		return openSeq;
	}






	/**
	 * @param openSeq the openSeq to set
	 */
	public void setOpenSeq(int openSeq) {
		this.openSeq = openSeq;
	}






	/**
	 * @return the startMngCode
	 */
	public String getStartMngCode() {
		return startMngCode;
	}






	/**
	 * @param startMngCode the startMngCode to set
	 */
	public void setStartMngCode(String startMngCode) {
		this.startMngCode = startMngCode;
	}






	/**
	 * @return the endMngCode
	 */
	public String getEndMngCode() {
		return endMngCode;
	}






	/**
	 * @param endMngCode the endMngCode to set
	 */
	public void setEndMngCode(String endMngCode) {
		this.endMngCode = endMngCode;
	}






	/**
	 * @return the openCount
	 */
	public int getOpenCount() {
		return openCount;
	}






	/**
	 * @param openCount the openCount to set
	 */
	public void setOpenCount(int openCount) {
		this.openCount = openCount;
	}






	/**
	 * @return the pinChargeSum
	 */
	public int getPinChargeSum() {
		return pinChargeSum;
	}






	/**
	 * @param pinChargeSum the pinChargeSum to set
	 */
	public void setPinChargeSum(int pinChargeSum) {
		this.pinChargeSum = pinChargeSum;
	}






	/**
	 * @return the agentChargeSum
	 */
	public int getAgentChargeSum() {
		return agentChargeSum;
	}






	/**
	 * @param agentChargeSum the agentChargeSum to set
	 */
	public void setAgentChargeSum(int agentChargeSum) {
		this.agentChargeSum = agentChargeSum;
	}






	/**
	 * @return the openAdminId
	 */
	public String getOpenAdminId() {
		return openAdminId;
	}






	/**
	 * @param openAdminId the openAdminId to set
	 */
	public void setOpenAdminId(String openAdminId) {
		this.openAdminId = openAdminId;
	}






	/**
	 * @return the openDate
	 */
	public String getOpenDate() {
		return openDate;
	}






	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}






	/**
	 * @return the openAgentType
	 */
	public String getOpenAgentType() {
		return openAgentType;
	}






	/**
	 * @param openAgentType the openAgentType to set
	 */
	public void setOpenAgentType(String openAgentType) {
		this.openAgentType = openAgentType;
	}






	/**
	 * @return the openAgentId
	 */
	public String getOpenAgentId() {
		return openAgentId;
	}






	/**
	 * @param openAgentId the openAgentId to set
	 */
	public void setOpenAgentId(String openAgentId) {
		this.openAgentId = openAgentId;
	}






	/**
	 * @return the openDealerId
	 */
	public String getOpenDealerId() {
		return openDealerId;
	}






	/**
	 * @param openDealerId the openDealerId to set
	 */
	public void setOpenDealerId(String openDealerId) {
		this.openDealerId = openDealerId;
	}






	/**
	 * @return the payAmt
	 */
	public int getPayAmt() {
		return payAmt;
	}






	/**
	 * @param payAmt the payAmt to set
	 */
	public void setPayAmt(int payAmt) {
		this.payAmt = payAmt;
	}






	/**
	 * @return the payFlag
	 */
	public String getPayFlag() {
		return payFlag;
	}






	/**
	 * @param payFlag the payFlag to set
	 */
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}






	/**
	 * @return the payDate
	 */
	public String getPayDate() {
		return payDate;
	}






	/**
	 * @param payDate the payDate to set
	 */
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}






	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}






	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}






	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}






	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}






	/**
	 * @return the mngCodeStr
	 */
	public String getMngCodeStr() {
		return mngCodeStr;
	}






	/**
	 * @param mngCodeStr the mngCodeStr to set
	 */
	public void setMngCodeStr(String mngCodeStr) {
		this.mngCodeStr = mngCodeStr;
	}






	/**
	 * @return openAdminNm
	 */
	public String getOpenAdminNm() {
		return openAdminNm;
	}






	/**
	 * @param openAdminNm セットする openAdminNm
	 */
	public void setOpenAdminNm(String openAdminNm) {
		this.openAdminNm = openAdminNm;
	}






	/**
	 * @return openAgentTypeNm
	 */
	public String getOpenAgentTypeNm() {
		return openAgentTypeNm;
	}






	/**
	 * @param openAgentTypeNm セットする openAgentTypeNm
	 */
	public void setOpenAgentTypeNm(String openAgentTypeNm) {
		this.openAgentTypeNm = openAgentTypeNm;
	}






	/**
	 * @return openAgentNm
	 */
	public String getOpenAgentNm() {
		return openAgentNm;
	}






	/**
	 * @param openAgentNm セットする openAgentNm
	 */
	public void setOpenAgentNm(String openAgentNm) {
		this.openAgentNm = openAgentNm;
	}






	/**
	 * @return openDealerNm
	 */
	public String getOpenDealerNm() {
		return openDealerNm;
	}






	/**
	 * @param openDealerNm セットする openDealerNm
	 */
	public void setOpenDealerNm(String openDealerNm) {
		this.openDealerNm = openDealerNm;
	}






	/**
	 * @return payFlagNm
	 */
	public String getPayFlagNm() {
		return payFlagNm;
	}






	/**
	 * @param payFlagNm セットする payFlagNm
	 */
	public void setPayFlagNm(String payFlagNm) {
		this.payFlagNm = payFlagNm;
	}






	/**
	 * @return retCode
	 */
	public String getRetCode() {
		return retCode;
	}






	/**
	 * @param retCode セットする retCode
	 */
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}






	/**
	 * @return retMsg
	 */
	public String getRetMsg() {
		return retMsg;
	}






	/**
	 * @param retMsg セットする retMsg
	 */
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}



	


	/**
	 * @return the cancelFlag
	 */
	public String getCancelFlag() {
		return cancelFlag;
	}






	/**
	 * @param cancelFlag the cancelFlag to set
	 */
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}






	/**
	 * @return the cancelFlagNm
	 */
	public String getCancelFlagNm() {
		return cancelFlagNm;
	}






	/**
	 * @param cancelFlagNm the cancelFlagNm to set
	 */
	public void setCancelFlagNm(String cancelFlagNm) {
		this.cancelFlagNm = cancelFlagNm;
	}






	/**
	 * @return the cancelDate
	 */
	public String getCancelDate() {
		return cancelDate;
	}






	/**
	 * @param cancelDate the cancelDate to set
	 */
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}






	/**
	 * @return the stopSeq
	 */
	public int getStopSeq() {
		return stopSeq;
	}






	/**
	 * @param stopSeq the stopSeq to set
	 */
	public void setStopSeq(int stopSeq) {
		this.stopSeq = stopSeq;
	}






	/**
	 * @return the pinDisRate
	 */
	public int getPinDisRate() {
		return pinDisRate;
	}






	/**
	 * @param pinDisRate the pinDisRate to set
	 */
	public void setPinDisRate(int pinDisRate) {
		this.pinDisRate = pinDisRate;
	}






	/**
	 * @return the updNmStr
	 */
	public String getUpdNmStr() {
		return updNmStr;
	}






	/**
	 * @param updNmStr the updNmStr to set
	 */
	public void setUpdNmStr(String updNmStr) {
		this.updNmStr = updNmStr;
	}

	/**
	 * @return the freeFlag
	 */
	public String getFreeFlag() {
		return freeFlag;
	}






	/**
	 * @param freeFlag the freeFlag to set
	 */
	public void setFreeFlag(String freeFlag) {
		this.freeFlag = freeFlag;
	}






	/**
	 * @return the freeFlagNm
	 */
	public String getFreeFlagNm() {
		return freeFlagNm;
	}






	/**
	 * @param freeFlagNm the freeFlagNm to set
	 */
	public void setFreeFlagNm(String freeFlagNm) {
		this.freeFlagNm = freeFlagNm;
	}






	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
