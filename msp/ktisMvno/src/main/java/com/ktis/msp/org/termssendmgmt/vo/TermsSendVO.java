package com.ktis.msp.org.termssendmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class TermsSendVO extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String contractNum;
	private String trgtYm;
	private String name;
	private String typeCd;
	private String resultYn;
	private String mobileNo;
	private String email;
	private String typeCdNm;
	private String subStat;
	private String subStatNm;
	private String openDt;
	private String tmntDt;
	private String seqNum;

    private String sendYn;	/** LMS처리여부*/
    private String sendRsn;	/** LMS처리결과*/
    
    
	public String getSendYn() {
		return sendYn;
	}
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	public String getSendRsn() {
		return sendRsn;
	}
	public void setSendRsn(String sendRsn) {
		this.sendRsn = sendRsn;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getTrgtYm() {
		return trgtYm;
	}
	public void setTrgtYm(String trgtYm) {
		this.trgtYm = trgtYm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getResultYn() {
		return resultYn;
	}
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTypeCdNm() {
		return typeCdNm;
	}
	public void setTypeCdNm(String typeCdNm) {
		this.typeCdNm = typeCdNm;
	}
	public String getSubStat() {
		return subStat;
	}
	public void setSubStat(String subStat) {
		this.subStat = subStat;
	}
	public String getSubStatNm() {
		return subStatNm;
	}
	public void setSubStatNm(String subStatNm) {
		this.subStatNm = subStatNm;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getTmntDt() {
		return tmntDt;
	}
	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
}
