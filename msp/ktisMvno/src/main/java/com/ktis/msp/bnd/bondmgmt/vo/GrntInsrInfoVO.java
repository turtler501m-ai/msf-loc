package com.ktis.msp.bnd.bondmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="grntInsrInfoVO")
public class GrntInsrInfoVO extends BaseVo implements Serializable {
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	
	
    private String searchStartDt;
    private String searchEndDt;
    
    private String pSearchName = "";
    private String pSearchGbn = "";
    
	// MSP_GRNT_INSR_INFO
	private String grntInsrMngmNo;
	private String contractNum;
	private String applEndDttm;
	private String applstrtDttm;
	private String prdtId;
	private String prdtSrlNum;
	private long prdtAmt;
	private String openDt;
	private long instAmt;
	private long instCnt;
	private String instPayStrtDt;
	private String instPayEndDt;
	private long grntInsrAmt;
	private String grntInsrStrtDt;
	private String grntInsrEndDt;
	private String nameChrDt;
	private String svcCntrNo;
	private String grntInsrInvYn;
	private String grntInsrInvDt;
	private long grntInsrInvAmt;
	private String grntInsrRcvDt;
	private long grntInsrRcvAmt;
	private String grntInsrinvrId;
	private long grntInsrFee;
	private long canRfndFee;
	private String regstId;
	private String regstDttm;
	private String rvisnId;
	private String rvisnDttm;
	
	// MSP_JUO_CUS_INFO
	private String customerId;
	private String customerAdrPrimaryLn;
	private String customerLinkName;
	
	// MSP_JUO_SUB_INFO
	private String subscriberNo;
	private String subStatus;
	
	// CMN_INTM_MDL
	private String prdtNm;
	private String prdtCode;
	
	
	/**
	 * @return the grntInsrMngmNo
	 */
	public String getGrntInsrMngmNo() {
		return grntInsrMngmNo;
	}
	/**
	 * @param grntInsrMngmNo the grntInsrMngmNo to set
	 */
	public void setGrntInsrMngmNo(String grntInsrMngmNo) {
		this.grntInsrMngmNo = grntInsrMngmNo;
	}
	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}
	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	/**
	 * @return the applEndDttm
	 */
	public String getApplEndDttm() {
		return applEndDttm;
	}
	/**
	 * @param applEndDttm the applEndDttm to set
	 */
	public void setApplEndDttm(String applEndDttm) {
		this.applEndDttm = applEndDttm;
	}
	/**
	 * @return the applstrtDttm
	 */
	public String getApplstrtDttm() {
		return applstrtDttm;
	}
	/**
	 * @param applstrtDttm the applstrtDttm to set
	 */
	public void setApplstrtDttm(String applstrtDttm) {
		this.applstrtDttm = applstrtDttm;
	}
	/**
	 * @return the prdtId
	 */
	public String getPrdtId() {
		return prdtId;
	}
	/**
	 * @param prdtId the prdtId to set
	 */
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	/**
	 * @return the prdtSrlNum
	 */
	public String getPrdtSrlNum() {
		return prdtSrlNum;
	}
	/**
	 * @param prdtSrlNum the prdtSrlNum to set
	 */
	public void setPrdtSrlNum(String prdtSrlNum) {
		this.prdtSrlNum = prdtSrlNum;
	}
	/**
	 * @return the prdtAmt
	 */
	public long getPrdtAmt() {
		return prdtAmt;
	}
	/**
	 * @param prdtAmt the prdtAmt to set
	 */
	public void setPrdtAmt(long prdtAmt) {
		this.prdtAmt = prdtAmt;
	}
	/**
	 * @return the openDt
	 */
	public String getOpenDt() {
		return openDt;
	}
	/**
	 * @param openDt the openDt to set
	 */
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	/**
	 * @return the instAmt
	 */
	public long getInstAmt() {
		return instAmt;
	}
	/**
	 * @param instAmt the instAmt to set
	 */
	public void setInstAmt(long instAmt) {
		this.instAmt = instAmt;
	}
	/**
	 * @return the instCnt
	 */
	public long getInstCnt() {
		return instCnt;
	}
	/**
	 * @param instCnt the instCnt to set
	 */
	public void setInstCnt(long instCnt) {
		this.instCnt = instCnt;
	}
	/**
	 * @return the instPayStrtDt
	 */
	public String getInstPayStrtDt() {
		return instPayStrtDt;
	}
	/**
	 * @param instPayStrtDt the instPayStrtDt to set
	 */
	public void setInstPayStrtDt(String instPayStrtDt) {
		this.instPayStrtDt = instPayStrtDt;
	}
	/**
	 * @return the instPayEndDt
	 */
	public String getInstPayEndDt() {
		return instPayEndDt;
	}
	/**
	 * @param instPayEndDt the instPayEndDt to set
	 */
	public void setInstPayEndDt(String instPayEndDt) {
		this.instPayEndDt = instPayEndDt;
	}
	/**
	 * @return the grntInsrAmt
	 */
	public long getGrntInsrAmt() {
		return grntInsrAmt;
	}
	/**
	 * @param grntInsrAmt the grntInsrAmt to set
	 */
	public void setGrntInsrAmt(long grntInsrAmt) {
		this.grntInsrAmt = grntInsrAmt;
	}
	/**
	 * @return the grntInsrStrtDt
	 */
	public String getGrntInsrStrtDt() {
		return grntInsrStrtDt;
	}
	/**
	 * @param grntInsrStrtDt the grntInsrStrtDt to set
	 */
	public void setGrntInsrStrtDt(String grntInsrStrtDt) {
		this.grntInsrStrtDt = grntInsrStrtDt;
	}
	/**
	 * @return the grntInsrEndDt
	 */
	public String getGrntInsrEndDt() {
		return grntInsrEndDt;
	}
	/**
	 * @param grntInsrEndDt the grntInsrEndDt to set
	 */
	public void setGrntInsrEndDt(String grntInsrEndDt) {
		this.grntInsrEndDt = grntInsrEndDt;
	}
	/**
	 * @return the nameChrDt
	 */
	public String getNameChrDt() {
		return nameChrDt;
	}
	/**
	 * @param nameChrDt the nameChrDt to set
	 */
	public void setNameChrDt(String nameChrDt) {
		this.nameChrDt = nameChrDt;
	}
	/**
	 * @return the svcCntrNo
	 */
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	/**
	 * @param svcCntrNo the svcCntrNo to set
	 */
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	/**
	 * @return the grntInsrInvYn
	 */
	public String getGrntInsrInvYn() {
		return grntInsrInvYn;
	}
	/**
	 * @param grntInsrInvYn the grntInsrInvYn to set
	 */
	public void setGrntInsrInvYn(String grntInsrInvYn) {
		this.grntInsrInvYn = grntInsrInvYn;
	}
	/**
	 * @return the grntInsrInvDt
	 */
	public String getGrntInsrInvDt() {
		return grntInsrInvDt;
	}
	/**
	 * @param grntInsrInvDt the grntInsrInvDt to set
	 */
	public void setGrntInsrInvDt(String grntInsrInvDt) {
		this.grntInsrInvDt = grntInsrInvDt;
	}
	/**
	 * @return the grntInsrInvAmt
	 */
	public long getGrntInsrInvAmt() {
		return grntInsrInvAmt;
	}
	/**
	 * @param grntInsrInvAmt the grntInsrInvAmt to set
	 */
	public void setGrntInsrInvAmt(long grntInsrInvAmt) {
		this.grntInsrInvAmt = grntInsrInvAmt;
	}
	/**
	 * @return the grntInsrRcvDt
	 */
	public String getGrntInsrRcvDt() {
		return grntInsrRcvDt;
	}
	/**
	 * @param grntInsrRcvDt the grntInsrRcvDt to set
	 */
	public void setGrntInsrRcvDt(String grntInsrRcvDt) {
		this.grntInsrRcvDt = grntInsrRcvDt;
	}
	/**
	 * @return the grntInsrRcvAmt
	 */
	public long getGrntInsrRcvAmt() {
		return grntInsrRcvAmt;
	}
	/**
	 * @param grntInsrRcvAmt the grntInsrRcvAmt to set
	 */
	public void setGrntInsrRcvAmt(long grntInsrRcvAmt) {
		this.grntInsrRcvAmt = grntInsrRcvAmt;
	}
	/**
	 * @return the grntInsrinvrId
	 */
	public String getGrntInsrinvrId() {
		return grntInsrinvrId;
	}
	/**
	 * @param grntInsrinvrId the grntInsrinvrId to set
	 */
	public void setGrntInsrinvrId(String grntInsrinvrId) {
		this.grntInsrinvrId = grntInsrinvrId;
	}
	/**
	 * @return the grntInsrFee
	 */
	public long getGrntInsrFee() {
		return grntInsrFee;
	}
	/**
	 * @param grntInsrFee the grntInsrFee to set
	 */
	public void setGrntInsrFee(long grntInsrFee) {
		this.grntInsrFee = grntInsrFee;
	}
	/**
	 * @return the canRfndFee
	 */
	public long getCanRfndFee() {
		return canRfndFee;
	}
	/**
	 * @param canRfndFee the canRfndFee to set
	 */
	public void setCanRfndFee(long canRfndFee) {
		this.canRfndFee = canRfndFee;
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
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the customerAdrPrimaryLn
	 */
	public String getCustomerAdrPrimaryLn() {
		return customerAdrPrimaryLn;
	}
	/**
	 * @param customerAdrPrimaryLn the customerAdrPrimaryLn to set
	 */
	public void setCustomerAdrPrimaryLn(String customerAdrPrimaryLn) {
		this.customerAdrPrimaryLn = customerAdrPrimaryLn;
	}
	/**
	 * @return the customerLinkName
	 */
	public String getCustomerLinkName() {
		return customerLinkName;
	}
	/**
	 * @param customerLinkName the customerLinkName to set
	 */
	public void setCustomerLinkName(String customerLinkName) {
		this.customerLinkName = customerLinkName;
	}
	/**
	 * @return the subscriberNo
	 */
	public String getSubscriberNo() {
		return subscriberNo;
	}
	/**
	 * @param subscriberNo the subscriberNo to set
	 */
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}
	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	/**
	 * @return the prdtNm
	 */
	public String getPrdtNm() {
		return prdtNm;
	}
	/**
	 * @param prdtNm the prdtNm to set
	 */
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	/**
	 * @return the prdtCode
	 */
	public String getPrdtCode() {
		return prdtCode;
	}
	/**
	 * @param prdtCode the prdtCode to set
	 */
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}
	/**
	 * @return the searchStartDt
	 */
	public String getSearchStartDt() {
		return searchStartDt;
	}
	/**
	 * @param searchStartDt the searchStartDt to set
	 */
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	/**
	 * @return the searchEndDt
	 */
	public String getSearchEndDt() {
		return searchEndDt;
	}
	/**
	 * @param searchEndDt the searchEndDt to set
	 */
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	/**
	 * @return the pSearchName
	 */
	public String getpSearchName() {
		return pSearchName;
	}
	/**
	 * @param pSearchName the pSearchName to set
	 */
	public void setpSearchName(String pSearchName) {
		this.pSearchName = pSearchName;
	}
	/**
	 * @return the pSearchGbn
	 */
	public String getpSearchGbn() {
		return pSearchGbn;
	}
	/**
	 * @param pSearchGbn the pSearchGbn to set
	 */
	public void setpSearchGbn(String pSearchGbn) {
		this.pSearchGbn = pSearchGbn;
	}
	
	
	
}
