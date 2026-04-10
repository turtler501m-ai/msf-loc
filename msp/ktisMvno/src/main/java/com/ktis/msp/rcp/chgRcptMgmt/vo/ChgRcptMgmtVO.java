package com.ktis.msp.rcp.chgRcptMgmt.vo;
import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class ChgRcptMgmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 검색어
	private String strtDt;		// 조회시작일자
	private String endDt;		// 조회종료일자
	private String tskTp;		// 업무유형
	private String searchClCd;	// 검색구분
	private String searchVal;	// 검색어
	private String procAgntCd;	// 처리대리점코드
	//
	private String contractNum;		// 계약번호
	private String subscriberNo;	// 전화번호
	private String scanId;			// 스캔ID
	private String custNm;			// 고객명
	private String pppo;			// 선후불구분
	private String subStatus;		// 계약상태
	private String openDt;			// 개통일자
	private String rmk;				// 처리내용
	
	private String searchSubscriberNo;	// 검색전화번호
	private String openAgntCd;			// 개통대리점코드
	private String payClNm;				// 선후불
	private String subStatusNm;			// 계약상태
	private String tskNm;				// 업무유형(직접입력)
	private String newScanId;			// 스캔하기로 얻은 스캔ID
	private String scanMstYn;
	
	// SRM18072675707, 단체보험 추가
	private String insrCd;
	private String clauseInsuranceFlag;
	private Integer seqNum;
	
	private String searchGb;
	private String searchGbVal;
	
	// 휴대폰안심보험
	private String insrProdCd;
	private String clauseInsrProdFlag;
	
	public String getStrtDt() {
		return strtDt;
	}
	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getTskTp() {
		return tskTp;
	}
	public void setTskTp(String tskTp) {
		this.tskTp = tskTp;
	}
	public String getSearchClCd() {
		return searchClCd;
	}
	public void setSearchClCd(String searchClCd) {
		this.searchClCd = searchClCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getPppo() {
		return pppo;
	}
	public void setPppo(String pppo) {
		this.pppo = pppo;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getProcAgntCd() {
		return procAgntCd;
	}
	public void setProcAgntCd(String procAgntCd) {
		this.procAgntCd = procAgntCd;
	}
	public String getSearchSubscriberNo() {
		return searchSubscriberNo;
	}
	public void setSearchSubscriberNo(String searchSubscriberNo) {
		this.searchSubscriberNo = searchSubscriberNo;
	}
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getPayClNm() {
		return payClNm;
	}
	public void setPayClNm(String payClNm) {
		this.payClNm = payClNm;
	}
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getTskNm() {
		return tskNm;
	}
	public void setTskNm(String tskNm) {
		this.tskNm = tskNm;
	}
	public String getNewScanId() {
		return newScanId;
	}
	public void setNewScanId(String newScanId) {
		this.newScanId = newScanId;
	}
	public String getScanMstYn() {
		return scanMstYn;
	}
	public void setScanMstYn(String scanMstYn) {
		this.scanMstYn = scanMstYn;
	}
	public String getInsrCd() {
		return insrCd;
	}
	public void setInsrCd(String insrCd) {
		this.insrCd = insrCd;
	}
	public String getClauseInsuranceFlag() {
		return clauseInsuranceFlag;
	}
	public void setClauseInsuranceFlag(String clauseInsuranceFlag) {
		this.clauseInsuranceFlag = clauseInsuranceFlag;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	public String getSearchGbVal() {
		return searchGbVal;
	}
	public void setSearchGbVal(String searchGbVal) {
		this.searchGbVal = searchGbVal;
	}
	public String getInsrProdCd() {
		return insrProdCd;
	}
	public void setInsrProdCd(String insrProdCd) {
		this.insrProdCd = insrProdCd;
	}
	public String getClauseInsrProdFlag() {
		return clauseInsrProdFlag;
	}
	public void setClauseInsrProdFlag(String clauseInsrProdFlag) {
		this.clauseInsrProdFlag = clauseInsrProdFlag;
	}
	
}
