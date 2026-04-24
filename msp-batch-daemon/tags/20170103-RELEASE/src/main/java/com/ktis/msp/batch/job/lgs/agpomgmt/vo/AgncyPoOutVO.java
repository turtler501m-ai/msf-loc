package com.ktis.msp.batch.job.lgs.agpomgmt.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.BaseVo;

/**
 * @Class Name  : AgncyPoOutVO.java
 * @Description : AgncyPoOutVO Class
 * @Modification Information
 * @
 * @  수정일	  수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.11  IB		 최초생성
 *
 * @author IB
 * @since 2014.08.11
 * @version 1.0
 */

public class AgncyPoOutVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3967297767273599174L;
	
	/** 조직 */
	private String orgnId;				/** 조직ID */
	private String orgnNm;				/** 조직명 */
	
	/** 주문 */
	private String ordId;				/** 주문ID */
	private String ordTypeCd;			/** 주문유형코드 */
	private String ordStatCd;			/** 주문상태코드 */
	private String ordStatNm;			/** 주문상태코드명 */
	private String ordDt;				/** 주문일자 */
	private String orgHghrOrgnId;		/** 주문상위조직ID */
	private String ordOrgnId;			/** 주문조직ID */
	private String ordOrgnNm;			/** 주문조직명 */
	private int    ordQnty;				/** 주문수량 */
	private int    ordAmt;				/** 주문금액 */
	private int    ordUnitPric;			/** 주문단가 */
	private String ordRemark;			/** 주문참고사항 */
	private int    setQnty;				/** 배정수량 */
	private int    setAmt;				/** 배정금액 */
	private String setCnfmId;			/** 배정확정자ID */
	private String setOrgnId;			/** 배정조직ID */
	private String setOrgnNm;			/** 배정조직명 */
	private String setCnfmDt;			/** 배정일자 */
	private String inDueDt;				/** 입고희망일자 */
	
	/** 송장 */
	private String invcId;				/** 송장ID */
	private String invcDt;				/** 납품일자 */
	private String invcTypeCd;			/** 송장유형코드 */
	private String invcStatCd;			/** 송장상태코드 */
	private String invcStatNm;			/** 송장상태명 */
	private String inOrgnId;			/** 입고조직ID */
	private String inOrgnNm;			/** 입고조직명 */
	private String outOrgnId;			/** 출고조직ID */
	private String outOrgnNm;			/** 출고조직명 */
	private String invRemark;			/** 송장참고사항 */
	private String regDt;				/** 출고지시일자 */
	private String outDt;				/** 출고일자 */
	private String extDlvyInvcNo;		/** 외부배송업체송장번호 */
	
	/** 인수 */
	private String tkovrCnfmId;			/** 인수확인자ID */ 
	private String tkovrCnfmDt;			/** 인수확인일자 */ 
	private String tkovrRejctId;		/** 인수거부자ID */ 
	private String tkovrRejctDt;		/** 인수거부일자 */ 
	private String tkovrRejctRsn;		/** 인수거부사유 */ 
	
	/** 기기 */
	private String mnfctId;				/** 제조사ID */
	private String mnfctNm;				/** 제조사명 */
	private String prdtTypeCd;			/** 제품유형CD */
	private String prdtTypeNm;			/** 제품유형명 */
	private String oldYn;				/** 중고여부 */
	private String oldYnNm;				/** 중고여부명 */
	private String prdtId;				/** 제품ID */
	private String prdtNm;				/** 제품명 */
	private String prdtColrCd;			/** 색상코드 */
	private String prdtColrNm;			/** 색상명 */
	private String prdtSrlNum;			/** 제품일련번호 */
	private String codeDigit;			/** 일련번호자릿수 */

	/** 수량 */
	private int    invQnty;				/** 출고지시수량 */
	private int    regQnty;				/** 출고등록수량 */
	private int    ableQnty;			/** 출고가능수량 */
	private int    dlvrQnty;			/** 출고수량 */
	private int    outAmt;				/** 출고금액 */
	private int    outUnitPric; 		/** 출가단가 */
	
	/** 재고 */
	private int    goodStrgInvtrQnty;	/** 양품창고재고수량 */
	private int    ngStrgInvtrQnty;		/** 불량창고재고수량 */
	private int    inDueQnty;			/** 입고예정수량 */
	private int    outRsvQnty;			/** 출고예약수량 */
	
	/** 수불 */
	private String inoutId;				/** 수불ID */
	private String inoutDt;				/** 수불일자 */
	private String inoutStatCd;			/** 수불상태코드 */
	private String inoutTypeCd;			/** 수불유형코드 */
	private String inoutTypeDtlCd;		/** 수불유형상세코드 */
	private String invtrApplYn;			/** 재고반영여부 */
	private String lastInoutId; 		/** 최종수불ID */
	private String logisProcStatCd; 	/** 물류처리상태코드 */
	private long   taxbillSrlNum;		/** 세금계산서번호 */
	private String taxbillInfoApplYn;	/** 세금계산서정보적용여부 */
	private String jobCd;				/** 업무코드 */
	private String jobIndCd;			/** 업무구분코드 */

	/** 기타 */
	private String startDate;			/** 검색시작일시 */
	private String endDate;				/** 검색종료일시 */
	private String userId;				/** 사용자ID */
	private String userOrgnId;			/** 사용자조직ID */
	private String userOrgnNm;			/** 사용자조직명 */
	private String userOrgnTypeCd;		/** 사용자조직유형코드 */
	private String instructCd;			/** 지시현황코드 */
	
	// 2015-03-19, IMEI 추가
	private String imei;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

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

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getOrdTypeCd() {
		return ordTypeCd;
	}

	public void setOrdTypeCd(String ordTypeCd) {
		this.ordTypeCd = ordTypeCd;
	}

	public String getOrdStatCd() {
		return ordStatCd;
	}

	public void setOrdStatCd(String ordStatCd) {
		this.ordStatCd = ordStatCd;
	}

	public String getOrdStatNm() {
		return ordStatNm;
	}

	public void setOrdStatNm(String ordStatNm) {
		this.ordStatNm = ordStatNm;
	}

	public String getOrdDt() {
		return ordDt;
	}

	public void setOrdDt(String ordDt) {
		this.ordDt = ordDt;
	}

	public String getOrgHghrOrgnId() {
		return orgHghrOrgnId;
	}

	public void setOrgHghrOrgnId(String orgHghrOrgnId) {
		this.orgHghrOrgnId = orgHghrOrgnId;
	}

	public String getOrdOrgnId() {
		return ordOrgnId;
	}

	public void setOrdOrgnId(String ordOrgnId) {
		this.ordOrgnId = ordOrgnId;
	}

	public String getOrdOrgnNm() {
		return ordOrgnNm;
	}

	public void setOrdOrgnNm(String ordOrgnNm) {
		this.ordOrgnNm = ordOrgnNm;
	}

	public int getOrdQnty() {
		return ordQnty;
	}

	public void setOrdQnty(int ordQnty) {
		this.ordQnty = ordQnty;
	}

	public int getOrdAmt() {
		return ordAmt;
	}

	public void setOrdAmt(int ordAmt) {
		this.ordAmt = ordAmt;
	}

	public int getOrdUnitPric() {
		return ordUnitPric;
	}

	public void setOrdUnitPric(int ordUnitPric) {
		this.ordUnitPric = ordUnitPric;
	}

	public String getOrdRemark() {
		return ordRemark;
	}

	public void setOrdRemark(String ordRemark) {
		this.ordRemark = ordRemark;
	}

	public int getSetQnty() {
		return setQnty;
	}

	public void setSetQnty(int setQnty) {
		this.setQnty = setQnty;
	}

	public int getSetAmt() {
		return setAmt;
	}

	public void setSetAmt(int setAmt) {
		this.setAmt = setAmt;
	}

	public String getSetCnfmId() {
		return setCnfmId;
	}

	public void setSetCnfmId(String setCnfmId) {
		this.setCnfmId = setCnfmId;
	}

	public String getSetOrgnId() {
		return setOrgnId;
	}

	public void setSetOrgnId(String setOrgnId) {
		this.setOrgnId = setOrgnId;
	}

	public String getSetOrgnNm() {
		return setOrgnNm;
	}

	public void setSetOrgnNm(String setOrgnNm) {
		this.setOrgnNm = setOrgnNm;
	}

	public String getSetCnfmDt() {
		return setCnfmDt;
	}

	public void setSetCnfmDt(String setCnfmDt) {
		this.setCnfmDt = setCnfmDt;
	}

	public String getInDueDt() {
		return inDueDt;
	}

	public void setInDueDt(String inDueDt) {
		this.inDueDt = inDueDt;
	}

	public String getInvcId() {
		return invcId;
	}

	public void setInvcId(String invcId) {
		this.invcId = invcId;
	}

	public String getInvcDt() {
		return invcDt;
	}

	public void setInvcDt(String invcDt) {
		this.invcDt = invcDt;
	}

	public String getInvcTypeCd() {
		return invcTypeCd;
	}

	public void setInvcTypeCd(String invcTypeCd) {
		this.invcTypeCd = invcTypeCd;
	}

	public String getInvcStatCd() {
		return invcStatCd;
	}

	public void setInvcStatCd(String invcStatCd) {
		this.invcStatCd = invcStatCd;
	}

	public String getInvcStatNm() {
		return invcStatNm;
	}

	public void setInvcStatNm(String invcStatNm) {
		this.invcStatNm = invcStatNm;
	}

	public String getInOrgnId() {
		return inOrgnId;
	}

	public void setInOrgnId(String inOrgnId) {
		this.inOrgnId = inOrgnId;
	}

	public String getInOrgnNm() {
		return inOrgnNm;
	}

	public void setInOrgnNm(String inOrgnNm) {
		this.inOrgnNm = inOrgnNm;
	}

	public String getOutOrgnId() {
		return outOrgnId;
	}

	public void setOutOrgnId(String outOrgnId) {
		this.outOrgnId = outOrgnId;
	}

	public String getOutOrgnNm() {
		return outOrgnNm;
	}

	public void setOutOrgnNm(String outOrgnNm) {
		this.outOrgnNm = outOrgnNm;
	}

	public String getInvRemark() {
		return invRemark;
	}

	public void setInvRemark(String invRemark) {
		this.invRemark = invRemark;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getOutDt() {
		return outDt;
	}

	public void setOutDt(String outDt) {
		this.outDt = outDt;
	}

	public String getExtDlvyInvcNo() {
		return extDlvyInvcNo;
	}

	public void setExtDlvyInvcNo(String extDlvyInvcNo) {
		this.extDlvyInvcNo = extDlvyInvcNo;
	}

	public String getTkovrCnfmId() {
		return tkovrCnfmId;
	}

	public void setTkovrCnfmId(String tkovrCnfmId) {
		this.tkovrCnfmId = tkovrCnfmId;
	}

	public String getTkovrCnfmDt() {
		return tkovrCnfmDt;
	}

	public void setTkovrCnfmDt(String tkovrCnfmDt) {
		this.tkovrCnfmDt = tkovrCnfmDt;
	}

	public String getTkovrRejctId() {
		return tkovrRejctId;
	}

	public void setTkovrRejctId(String tkovrRejctId) {
		this.tkovrRejctId = tkovrRejctId;
	}

	public String getTkovrRejctDt() {
		return tkovrRejctDt;
	}

	public void setTkovrRejctDt(String tkovrRejctDt) {
		this.tkovrRejctDt = tkovrRejctDt;
	}

	public String getTkovrRejctRsn() {
		return tkovrRejctRsn;
	}

	public void setTkovrRejctRsn(String tkovrRejctRsn) {
		this.tkovrRejctRsn = tkovrRejctRsn;
	}

	public String getMnfctId() {
		return mnfctId;
	}

	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}

	public String getMnfctNm() {
		return mnfctNm;
	}

	public void setMnfctNm(String mnfctNm) {
		this.mnfctNm = mnfctNm;
	}

	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}

	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}

	public String getPrdtTypeNm() {
		return prdtTypeNm;
	}

	public void setPrdtTypeNm(String prdtTypeNm) {
		this.prdtTypeNm = prdtTypeNm;
	}

	public String getOldYn() {
		return oldYn;
	}

	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}

	public String getOldYnNm() {
		return oldYnNm;
	}

	public void setOldYnNm(String oldYnNm) {
		this.oldYnNm = oldYnNm;
	}

	public String getPrdtId() {
		return prdtId;
	}

	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}

	public String getPrdtNm() {
		return prdtNm;
	}

	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}

	public String getPrdtColrCd() {
		return prdtColrCd;
	}

	public void setPrdtColrCd(String prdtColrCd) {
		this.prdtColrCd = prdtColrCd;
	}

	public String getPrdtColrNm() {
		return prdtColrNm;
	}

	public void setPrdtColrNm(String prdtColrNm) {
		this.prdtColrNm = prdtColrNm;
	}

	public String getPrdtSrlNum() {
		return prdtSrlNum;
	}

	public void setPrdtSrlNum(String prdtSrlNum) {
		this.prdtSrlNum = prdtSrlNum;
	}

	public String getCodeDigit() {
		return codeDigit;
	}

	public void setCodeDigit(String codeDigit) {
		this.codeDigit = codeDigit;
	}

	public int getInvQnty() {
		return invQnty;
	}

	public void setInvQnty(int invQnty) {
		this.invQnty = invQnty;
	}

	public int getRegQnty() {
		return regQnty;
	}

	public void setRegQnty(int regQnty) {
		this.regQnty = regQnty;
	}

	public int getAbleQnty() {
		return ableQnty;
	}

	public void setAbleQnty(int ableQnty) {
		this.ableQnty = ableQnty;
	}

	public int getDlvrQnty() {
		return dlvrQnty;
	}

	public void setDlvrQnty(int dlvrQnty) {
		this.dlvrQnty = dlvrQnty;
	}

	public int getOutAmt() {
		return outAmt;
	}

	public void setOutAmt(int outAmt) {
		this.outAmt = outAmt;
	}

	public int getOutUnitPric() {
		return outUnitPric;
	}

	public void setOutUnitPric(int outUnitPric) {
		this.outUnitPric = outUnitPric;
	}

	public int getGoodStrgInvtrQnty() {
		return goodStrgInvtrQnty;
	}

	public void setGoodStrgInvtrQnty(int goodStrgInvtrQnty) {
		this.goodStrgInvtrQnty = goodStrgInvtrQnty;
	}

	public int getNgStrgInvtrQnty() {
		return ngStrgInvtrQnty;
	}

	public void setNgStrgInvtrQnty(int ngStrgInvtrQnty) {
		this.ngStrgInvtrQnty = ngStrgInvtrQnty;
	}

	public int getInDueQnty() {
		return inDueQnty;
	}

	public void setInDueQnty(int inDueQnty) {
		this.inDueQnty = inDueQnty;
	}

	public int getOutRsvQnty() {
		return outRsvQnty;
	}

	public void setOutRsvQnty(int outRsvQnty) {
		this.outRsvQnty = outRsvQnty;
	}

	public String getInoutId() {
		return inoutId;
	}

	public void setInoutId(String inoutId) {
		this.inoutId = inoutId;
	}

	public String getInoutDt() {
		return inoutDt;
	}

	public void setInoutDt(String inoutDt) {
		this.inoutDt = inoutDt;
	}

	public String getInoutStatCd() {
		return inoutStatCd;
	}

	public void setInoutStatCd(String inoutStatCd) {
		this.inoutStatCd = inoutStatCd;
	}

	public String getInoutTypeCd() {
		return inoutTypeCd;
	}

	public void setInoutTypeCd(String inoutTypeCd) {
		this.inoutTypeCd = inoutTypeCd;
	}

	public String getInoutTypeDtlCd() {
		return inoutTypeDtlCd;
	}

	public void setInoutTypeDtlCd(String inoutTypeDtlCd) {
		this.inoutTypeDtlCd = inoutTypeDtlCd;
	}

	public String getInvtrApplYn() {
		return invtrApplYn;
	}

	public void setInvtrApplYn(String invtrApplYn) {
		this.invtrApplYn = invtrApplYn;
	}

	public String getLastInoutId() {
		return lastInoutId;
	}

	public void setLastInoutId(String lastInoutId) {
		this.lastInoutId = lastInoutId;
	}

	public String getLogisProcStatCd() {
		return logisProcStatCd;
	}

	public void setLogisProcStatCd(String logisProcStatCd) {
		this.logisProcStatCd = logisProcStatCd;
	}

	public long getTaxbillSrlNum() {
		return taxbillSrlNum;
	}

	public void setTaxbillSrlNum(long taxbillSrlNum) {
		this.taxbillSrlNum = taxbillSrlNum;
	}

	public String getTaxbillInfoApplYn() {
		return taxbillInfoApplYn;
	}

	public void setTaxbillInfoApplYn(String taxbillInfoApplYn) {
		this.taxbillInfoApplYn = taxbillInfoApplYn;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserOrgnId() {
		return userOrgnId;
	}

	public void setUserOrgnId(String userOrgnId) {
		this.userOrgnId = userOrgnId;
	}

	public String getUserOrgnNm() {
		return userOrgnNm;
	}

	public void setUserOrgnNm(String userOrgnNm) {
		this.userOrgnNm = userOrgnNm;
	}

	public String getUserOrgnTypeCd() {
		return userOrgnTypeCd;
	}

	public void setUserOrgnTypeCd(String userOrgnTypeCd) {
		this.userOrgnTypeCd = userOrgnTypeCd;
	}

	public String getInstructCd() {
		return instructCd;
	}

	public void setInstructCd(String instructCd) {
		this.instructCd = instructCd;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getJobCd() {
		return jobCd;
	}

	public void setJobCd(String jobCd) {
		this.jobCd = jobCd;
	}

	public String getJobIndCd() {
		return jobIndCd;
	}

	public void setJobIndCd(String jobIndCd) {
		this.jobIndCd = jobIndCd;
	}
	
	
	
}