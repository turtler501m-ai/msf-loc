package com.ktis.msp.rcp.statsMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class AcenReceptionVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String srchStrtDt;
	private String srchEndDt;
	private String compStrtDt;
	private String compEndDt;
	private String compUseYn;
	private String cntpntShopId;
	private String docType;
	private String searchGbn;
	private String searchName;
	private String receiveType;
	private String status;
	private String contractNum;
	private String seq;
	private String subStatus;

	public String getSrchStrtDt() {
		return srchStrtDt;
	}

	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String getCompStrtDt() {
		return compStrtDt;
	}

	public void setCompStrtDt(String compStrtDt) {
		this.compStrtDt = compStrtDt;
	}

	public String getCompEndDt() {
		return compEndDt;
	}

	public void setCompEndDt(String compEndDt) {
		this.compEndDt = compEndDt;
	}

	public String getCompUseYn() {
		return compUseYn;
	}

	public void setCompUseYn(String compUseYn) {
		this.compUseYn = compUseYn;
	}

	public String getCntpntShopId() {
		return cntpntShopId;
	}

	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
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

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
}
