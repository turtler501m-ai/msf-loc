package com.ktis.msp.pps.hdofccustmgmt.vo;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Tue Sep 16 10:46:44 JST 2014
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : MspRateMstVo
 * @Description : 요금제 내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="MspRateMstVo")
public class MspRateMstVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "MSP_RATE_MST";
	
	/**
	 * RATE_CD:varchar2(10) <Primary Key>
	 */
	private String rateCd;
	

	/**
	 * APPL_END_DT:varchar2(8) 
	 */
	private String applEndDt;

	/**
	 * APPL_STRT_DT:varchar2(8)
	 */
	private String applStrtDt;

	/**
	 * RATE_NM:varchar2(50)
	 */
	private String rateNm;

	/**
	 * RATE_GRP_CD:varchar2(10)
	 */
	private String rateGrpCd;

	/**
	 * PAY_CL_CD:varchar2(10)
	 */
	private String payClCd;

	/**
	 * RATE_TYPE:varchar2(10)
	 */
	private String rateType;

	/**
	 * DATA_TYPE:varchar2(10)
	 */
	private String dataType;

	/**
	 * BASE_AMT:number(10)
	 */
	private int baseAmt;

	/**
	 * FREE_CALL_CL_CD:char(1)
	 */
	private String freeCallClCd;

	/**
	 * FREE_CALL_CNT:varchar2(50)
	 */
	private String freeCallCnt;

	/**
	 * NW_IN_CALL_CNT:varchar2(50)
	 */
	private String nwInCallCnt;

	/**
	 * NW_OUT_CALL_CNT:varchar2(50)
	 */
	private String nwOutCallCnt;

	/**
	 * FREE_SMS_CNT:varchar2(50)
	 */
	private String freeSmsCnt;

	/**
	 * FREE_DATA_CNT:varchar2(50)
	 */
	private String freeDataCnt;

	/**
	 * RMK:varchar2(4000)
	 */
	private String rmk;

	/**
	 * REGST_ID:varchar2(10)
	 */
	private String regstId;

	/**
	 * REGST_DTTM:date
	 */
	private String regstDttm;

	/**
	 * RVISN_ID:varchar2(10)
	 */
	private String rvisnId;

	/**
	 * RVISN_DTTM:date
	 */
	private String rvisnDttm;

	/**
	 * ONLINE_TYPE_CD:varchar2(1)
	 */
	private String onlineTypeCd;

	/**
	 * AL_FLAG:varchar2(1)
	 */
	private String alFlag;
	
	/**
	 * MONTHLY_FEE:number(0)
	 */
	private int monthlyFee;
	
	
	
	

	/**
	 * @return the rateCd
	 */
	public String getRateCd() {
		return rateCd;
	}

	/**
	 * @param rateCd the rateCd to set
	 */
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}

	/**
	 * @return the applEndDt
	 */
	public String getApplEndDt() {
		return applEndDt;
	}

	/**
	 * @param applEndDt the applEndDt to set
	 */
	public void setApplEndDt(String applEndDt) {
		this.applEndDt = applEndDt;
	}

	/**
	 * @return the applStrtDt
	 */
	public String getApplStrtDt() {
		return applStrtDt;
	}

	/**
	 * @param applStrtDt the applStrtDt to set
	 */
	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}

	/**
	 * @return the rateNm
	 */
	public String getRateNm() {
		return rateNm;
	}

	/**
	 * @param rateNm the rateNm to set
	 */
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}

	/**
	 * @return the rateGrpCd
	 */
	public String getRateGrpCd() {
		return rateGrpCd;
	}

	/**
	 * @param rateGrpCd the rateGrpCd to set
	 */
	public void setRateGrpCd(String rateGrpCd) {
		this.rateGrpCd = rateGrpCd;
	}

	/**
	 * @return the payClCd
	 */
	public String getPayClCd() {
		return payClCd;
	}

	/**
	 * @param payClCd the payClCd to set
	 */
	public void setPayClCd(String payClCd) {
		this.payClCd = payClCd;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the baseAmt
	 */
	public int getBaseAmt() {
		return baseAmt;
	}

	/**
	 * @param baseAmt the baseAmt to set
	 */
	public void setBaseAmt(int baseAmt) {
		this.baseAmt = baseAmt;
	}

	/**
	 * @return the freeCallClCd
	 */
	public String getFreeCallClCd() {
		return freeCallClCd;
	}

	/**
	 * @param freeCallClCd the freeCallClCd to set
	 */
	public void setFreeCallClCd(String freeCallClCd) {
		this.freeCallClCd = freeCallClCd;
	}

	/**
	 * @return the freeCallCnt
	 */
	public String getFreeCallCnt() {
		return freeCallCnt;
	}

	/**
	 * @param freeCallCnt the freeCallCnt to set
	 */
	public void setFreeCallCnt(String freeCallCnt) {
		this.freeCallCnt = freeCallCnt;
	}

	/**
	 * @return the nwInCallCnt
	 */
	public String getNwInCallCnt() {
		return nwInCallCnt;
	}

	/**
	 * @param nwInCallCnt the nwInCallCnt to set
	 */
	public void setNwInCallCnt(String nwInCallCnt) {
		this.nwInCallCnt = nwInCallCnt;
	}

	/**
	 * @return the nwOutCallCnt
	 */
	public String getNwOutCallCnt() {
		return nwOutCallCnt;
	}

	/**
	 * @param nwOutCallCnt the nwOutCallCnt to set
	 */
	public void setNwOutCallCnt(String nwOutCallCnt) {
		this.nwOutCallCnt = nwOutCallCnt;
	}

	/**
	 * @return the freeSmsCnt
	 */
	public String getFreeSmsCnt() {
		return freeSmsCnt;
	}

	/**
	 * @param freeSmsCnt the freeSmsCnt to set
	 */
	public void setFreeSmsCnt(String freeSmsCnt) {
		this.freeSmsCnt = freeSmsCnt;
	}

	/**
	 * @return the freeDataCnt
	 */
	public String getFreeDataCnt() {
		return freeDataCnt;
	}

	/**
	 * @param freeDataCnt the freeDataCnt to set
	 */
	public void setFreeDataCnt(String freeDataCnt) {
		this.freeDataCnt = freeDataCnt;
	}

	/**
	 * @return the rmk
	 */
	public String getRmk() {
		return rmk;
	}

	/**
	 * @param rmk the rmk to set
	 */
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	/**
	 * @return the regstId
	 */
	public String getRegstId() {
		return regstId;
	}

	/**
	 * @param regstId the regstId to set
	 */
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}

	/**
	 * @return the regstDttm
	 */
	public String getRegstDttm() {
		return regstDttm;
	}

	/**
	 * @param regstDttm the regstDttm to set
	 */
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}

	/**
	 * @return the rvisnId
	 */
	public String getRvisnId() {
		return rvisnId;
	}

	/**
	 * @param rvisnId the rvisnId to set
	 */
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}

	/**
	 * @return the rvisnDttm
	 */
	public String getRvisnDttm() {
		return rvisnDttm;
	}

	/**
	 * @param rvisnDttm the rvisnDttm to set
	 */
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}

	/**
	 * @return the onlineTypeCd
	 */
	public String getOnlineTypeCd() {
		return onlineTypeCd;
	}

	/**
	 * @param onlineTypeCd the onlineTypeCd to set
	 */
	public void setOnlineTypeCd(String onlineTypeCd) {
		this.onlineTypeCd = onlineTypeCd;
	}

	/**
	 * @return the alFlag
	 */
	public String getAlFlag() {
		return alFlag;
	}

	/**
	 * @param alFlag the alFlag to set
	 */
	public void setAlFlag(String alFlag) {
		this.alFlag = alFlag;
	}
	
	/**
	 * @return the monthlyFee
	 */
	public int getMonthlyFee() {
		return monthlyFee;
	}

	/**
	 * @param monthlyFee the monthlyFee to set
	 */
	public void setMonthlyFee(int monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
