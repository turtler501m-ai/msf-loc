package com.ktis.msp.insr.insrMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class InsrCmpnVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1699848593531918268L;
	
	private String searchFromDt;
	private String searchToDt;
	private String searchCd;
	private String searchVal;
	private String subscriberNo;
	private String subLinkName;
	private String contractNum;
	private String insrProdCd;
	private String insrProdNm;
	private String strtDttm;
	private String cmpnType;
	private String cmpnTypeNm;
	private String modelId;
	private String modelNm;
	private String intmSrlNo;
	private String insrMngmNo;
	private String acdntNo;
	private String acdntRegDt;
	private String acdntDt;
	private String acdntTp;
	private String cmpnCnfmDt;
	private String cmpnLmtAmt;
	private String rmnCmpnAmt;
	private String cmpnAcmlAmt;
	private String rmnCmpnLmtAmt;
	private String rcptAmt;
	private String custBrdnAmt;
	private String realCmpnAmt;
	private String bankNm;
	private String acntNo;
	private String dpstNm;
	private String outUnitPric;
	private String overAmt;
	private String usimAmt;
	private String cmpnModelNm;
	private String cmpnModelColor;
	private String cmpnCtn;
	private String payType;
	private String payTypeNm;
	private String rvisnId;
	private String rvisnDttm;
	private String vrfyRsltNm;
	private String payAmt;
	private String payYn;
	private String vacBankCd;
	private String vacBankNm;
	private String vacId;
	private String payDttm;
	private String resNo;
	private String reqPhoneSn;
	private String dlvryNo;
	private String tbCd;
	private String tbNm;
	private String insrCmpnNum;
	private String cmpnTrtmTypeCd;
	private String rmk;
	
	private String opCode;
	private String gubun;
	private String expireDt;
	private String remark;
	private String retCd;
	private String retMsg;
	private String oVacBankCd;
	private String oVacId;
	
	/* 해지미처리목록 추가용 */
	private String subStatusNm; //계약 상태
	private String canRsltCd; //해지요청사유
	private String rsltMsg; //해지요청결과
	private String procDt; //해지요청일자
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
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
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getSubLinkName() {
		return subLinkName;
	}
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getInsrProdCd() {
		return insrProdCd;
	}
	public void setInsrProdCd(String insrProdCd) {
		this.insrProdCd = insrProdCd;
	}
	public String getInsrProdNm() {
		return insrProdNm;
	}
	public void setInsrProdNm(String insrProdNm) {
		this.insrProdNm = insrProdNm;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getCmpnType() {
		return cmpnType;
	}
	public void setCmpnType(String cmpnType) {
		this.cmpnType = cmpnType;
	}
	public String getCmpnTypeNm() {
		return cmpnTypeNm;
	}
	public void setCmpnTypeNm(String cmpnTypeNm) {
		this.cmpnTypeNm = cmpnTypeNm;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelNm() {
		return modelNm;
	}
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	public String getIntmSrlNo() {
		return intmSrlNo;
	}
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}
	public String getInsrMngmNo() {
		return insrMngmNo;
	}
	public void setInsrMngmNo(String insrMngmNo) {
		this.insrMngmNo = insrMngmNo;
	}
	public String getAcdntNo() {
		return acdntNo;
	}
	public void setAcdntNo(String acdntNo) {
		this.acdntNo = acdntNo;
	}
	public String getAcdntRegDt() {
		return acdntRegDt;
	}
	public void setAcdntRegDt(String acdntRegDt) {
		this.acdntRegDt = acdntRegDt;
	}
	public String getAcdntDt() {
		return acdntDt;
	}
	public void setAcdntDt(String acdntDt) {
		this.acdntDt = acdntDt;
	}
	public String getAcdntTp() {
		return acdntTp;
	}
	public void setAcdntTp(String acdntTp) {
		this.acdntTp = acdntTp;
	}
	public String getCmpnCnfmDt() {
		return cmpnCnfmDt;
	}
	public void setCmpnCnfmDt(String cmpnCnfmDt) {
		this.cmpnCnfmDt = cmpnCnfmDt;
	}
	public String getCmpnLmtAmt() {
		return cmpnLmtAmt;
	}
	public void setCmpnLmtAmt(String cmpnLmtAmt) {
		this.cmpnLmtAmt = cmpnLmtAmt;
	}
	public String getRmnCmpnAmt() {
		return rmnCmpnAmt;
	}
	public void setRmnCmpnAmt(String rmnCmpnAmt) {
		this.rmnCmpnAmt = rmnCmpnAmt;
	}
	public String getCmpnAcmlAmt() {
		return cmpnAcmlAmt;
	}
	public void setCmpnAcmlAmt(String cmpnAcmlAmt) {
		this.cmpnAcmlAmt = cmpnAcmlAmt;
	}
	public String getRmnCmpnLmtAmt() {
		return rmnCmpnLmtAmt;
	}
	public void setRmnCmpnLmtAmt(String rmnCmpnLmtAmt) {
		this.rmnCmpnLmtAmt = rmnCmpnLmtAmt;
	}
	public String getRcptAmt() {
		return rcptAmt;
	}
	public void setRcptAmt(String rcptAmt) {
		this.rcptAmt = rcptAmt;
	}
	public String getCustBrdnAmt() {
		return custBrdnAmt;
	}
	public void setCustBrdnAmt(String custBrdnAmt) {
		this.custBrdnAmt = custBrdnAmt;
	}
	public String getRealCmpnAmt() {
		return realCmpnAmt;
	}
	public void setRealCmpnAmt(String realCmpnAmt) {
		this.realCmpnAmt = realCmpnAmt;
	}
	public String getBankNm() {
		return bankNm;
	}
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	public String getAcntNo() {
		return acntNo;
	}
	public void setAcntNo(String acntNo) {
		this.acntNo = acntNo;
	}
	public String getDpstNm() {
		return dpstNm;
	}
	public void setDpstNm(String dpstNm) {
		this.dpstNm = dpstNm;
	}
	public String getOutUnitPric() {
		return outUnitPric;
	}
	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}
	public String getOverAmt() {
		return overAmt;
	}
	public void setOverAmt(String overAmt) {
		this.overAmt = overAmt;
	}
	public String getUsimAmt() {
		return usimAmt;
	}
	public void setUsimAmt(String usimAmt) {
		this.usimAmt = usimAmt;
	}
	public String getCmpnModelNm() {
		return cmpnModelNm;
	}
	public void setCmpnModelNm(String cmpnModelNm) {
		this.cmpnModelNm = cmpnModelNm;
	}
	public String getCmpnModelColor() {
		return cmpnModelColor;
	}
	public void setCmpnModelColor(String cmpnModelColor) {
		this.cmpnModelColor = cmpnModelColor;
	}
	public String getCmpnCtn() {
		return cmpnCtn;
	}
	public void setCmpnCtn(String cmpnCtn) {
		this.cmpnCtn = cmpnCtn;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayTypeNm() {
		return payTypeNm;
	}
	public void setPayTypeNm(String payTypeNm) {
		this.payTypeNm = payTypeNm;
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
	public String getVrfyRsltNm() {
		return vrfyRsltNm;
	}
	public void setVrfyRsltNm(String vrfyRsltNm) {
		this.vrfyRsltNm = vrfyRsltNm;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getPayYn() {
		return payYn;
	}
	public void setPayYn(String payYn) {
		this.payYn = payYn;
	}
	public String getVacBankCd() {
		return vacBankCd;
	}
	public void setVacBankCd(String vacBankCd) {
		this.vacBankCd = vacBankCd;
	}
	public String getVacBankNm() {
		return vacBankNm;
	}
	public void setVacBankNm(String vacBankNm) {
		this.vacBankNm = vacBankNm;
	}
	public String getVacId() {
		return vacId;
	}
	public void setVacId(String vacId) {
		this.vacId = vacId;
	}
	public String getPayDttm() {
		return payDttm;
	}
	public void setPayDttm(String payDttm) {
		this.payDttm = payDttm;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getReqPhoneSn() {
		return reqPhoneSn;
	}
	public void setReqPhoneSn(String reqPhoneSn) {
		this.reqPhoneSn = reqPhoneSn;
	}
	public String getDlvryNo() {
		return dlvryNo;
	}
	public void setDlvryNo(String dlvryNo) {
		this.dlvryNo = dlvryNo;
	}
	public String getTbCd() {
		return tbCd;
	}
	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}
	public String getTbNm() {
		return tbNm;
	}
	public void setTbNm(String tbNm) {
		this.tbNm = tbNm;
	}
	public String getInsrCmpnNum() {
		return insrCmpnNum;
	}
	public void setInsrCmpnNum(String insrCmpnNum) {
		this.insrCmpnNum = insrCmpnNum;
	}
	public String getCmpnTrtmTypeCd() {
		return cmpnTrtmTypeCd;
	}
	public void setCmpnTrtmTypeCd(String cmpnTrtmTypeCd) {
		this.cmpnTrtmTypeCd = cmpnTrtmTypeCd;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getExpireDt() {
		return expireDt;
	}
	public void setExpireDt(String expireDt) {
		this.expireDt = expireDt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRetCd() {
		return retCd;
	}
	public void setRetCd(String retCd) {
		this.retCd = retCd;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getoVacBankCd() {
		return oVacBankCd;
	}
	public void setoVacBankCd(String oVacBankCd) {
		this.oVacBankCd = oVacBankCd;
	}
	public String getoVacId() {
		return oVacId;
	}
	public void setoVacId(String oVacId) {
		this.oVacId = oVacId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getCanRsltCd() {
		return canRsltCd;
	}
	public void setCanRsltCd(String canRsltCd) {
		this.canRsltCd = canRsltCd;
	}
	public String getRsltMsg() {
		return rsltMsg;
	}
	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}
	public String getProcDt() {
		return procDt;
	}
	public void setProcDt(String procDt) {
		this.procDt = procDt;
	}
}
