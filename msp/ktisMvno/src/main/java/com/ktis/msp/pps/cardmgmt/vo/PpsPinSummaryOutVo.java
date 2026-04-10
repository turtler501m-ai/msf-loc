package com.ktis.msp.pps.cardmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;
/**
 * @Class Name : PpsPinSummaryOutVo
 * @Description : 핀출고내역 VO
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
@XmlRootElement(name="ppsPinSummaryOutVo")
public class PpsPinSummaryOutVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	

	/**
	 * PIN출고고유번호:number(20)
	 */
	private int outSeq;

	/**
	 * 시작관리번호:varchar2(20)
	 */
	private String startMngCode;

	/**
	 * 종료관리번호:varchar2(20)
	 */
	private String endMngCode;

	/**
	 * 출고개수:number(20)
	 */
	private int outCount;

	/**
	 * 출고관리자:varchar2(20)
	 */
	private String outAdminId;
	/**
	 * 출고관리자명
	 */
	private String outAdminNm;

	/**
	 * 출고일자:date(0)
	 */
	private String outDate;

	/**
	 * 출고대리점구분(A=일반대리점, C=딜러점개통):varchar2(1)
	 */
	private String outAgentType;

	/**
	 * 출고대리점코드:varchar2(20)
	 */
	private String outAgentId;
	/**
	 * 출고대리점명
	 */
	private String outAgentNm;

	/**
	 * 출고카드딜러코드:varchar2(20)
	 */
	private String outDealerId;
	/**
	 * 출고카드딜러명
	 */
	private String outDealerNm;

	/**
	 * 등록일자:date(0)
	 */
	private String recordDate;

	/**
	 * 비고:varchar2(1000)
	 */
	private String remark;
	
	
	/**
	 * 관리번호 시작~끝
	 */
	private String mngCodeStr;
	
	/**
	 * 취소여부
	 */
	private String cancelFlag;
	/**
	 * 취소여부명
	 */
	private String cancelFlagNm;
	
	
	/**
	 * 취소일자
	 */
	private String cancelDate;
	
	/**
	 * PIN 무료지급여부 (Y:무료지급)
	 */
	private String freeFlag;
	
	/**
	 * 핀무료지급명
	 */
	private String freeFlagNm;
	


	/**
	 * @return the outSeq
	 */
	public int getOutSeq() {
		return outSeq;
	}






	/**
	 * @param outSeq the outSeq to set
	 */
	public void setOutSeq(int outSeq) {
		this.outSeq = outSeq;
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
	 * @return the outCount
	 */
	public int getOutCount() {
		return outCount;
	}






	/**
	 * @param outCount the outCount to set
	 */
	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}






	/**
	 * @return the outAdminId
	 */
	public String getOutAdminId() {
		return outAdminId;
	}






	/**
	 * @param outAdminId the outAdminId to set
	 */
	public void setOutAdminId(String outAdminId) {
		this.outAdminId = outAdminId;
	}






	/**
	 * @return the outAdminNm
	 */
	public String getOutAdminNm() {
		return outAdminNm;
	}






	/**
	 * @param outAdminNm the outAdminNm to set
	 */
	public void setOutAdminNm(String outAdminNm) {
		this.outAdminNm = outAdminNm;
	}






	/**
	 * @return the outDate
	 */
	public String getOutDate() {
		return outDate;
	}






	/**
	 * @param outDate the outDate to set
	 */
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}






	/**
	 * @return the outAgentType
	 */
	public String getOutAgentType() {
		return outAgentType;
	}






	/**
	 * @param outAgentType the outAgentType to set
	 */
	public void setOutAgentType(String outAgentType) {
		this.outAgentType = outAgentType;
	}






	/**
	 * @return the outAgentId
	 */
	public String getOutAgentId() {
		return outAgentId;
	}






	/**
	 * @param outAgentId the outAgentId to set
	 */
	public void setOutAgentId(String outAgentId) {
		this.outAgentId = outAgentId;
	}






	/**
	 * @return the outAgentNm
	 */
	public String getOutAgentNm() {
		return outAgentNm;
	}






	/**
	 * @param outAgentNm the outAgentNm to set
	 */
	public void setOutAgentNm(String outAgentNm) {
		this.outAgentNm = outAgentNm;
	}






	/**
	 * @return the outDealerId
	 */
	public String getOutDealerId() {
		return outDealerId;
	}






	/**
	 * @param outDealerId the outDealerId to set
	 */
	public void setOutDealerId(String outDealerId) {
		this.outDealerId = outDealerId;
	}






	/**
	 * @return the outDealerNm
	 */
	public String getOutDealerNm() {
		return outDealerNm;
	}






	/**
	 * @param outDealerNm the outDealerNm to set
	 */
	public void setOutDealerNm(String outDealerNm) {
		this.outDealerNm = outDealerNm;
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
