package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class RcpSelfSuffVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = 3044976242494530140L;

	private String strtDt;
	private String endDt;
	private String searchCd;
	private String searchVal;
	private String paramPhoneStateCode;
	private String paramUsimStateCode;
	private String paramPstate;
	
	private String requestKey;
	private String phoneStateCode;
	private String usimStateCode;
	private String pstate;
	
	private String FileName;
    List<RcpSelfSuffVO> items;

    private int reqSrLeng;
    private String reqSrNo;
    private String resNo;
    private String tbNm;
    private String dlvryNo;
    private String tbCd;
    private String reqStateCd;
	
	public int getReqSrLeng() {
		return reqSrLeng;
	}
	public void setReqSrLeng(int reqSrLeng) {
		this.reqSrLeng = reqSrLeng;
	}
	public String getParamPhoneStateCode() {
		return paramPhoneStateCode;
	}
	public void setParamPhoneStateCode(String paramPhoneStateCode) {
		this.paramPhoneStateCode = paramPhoneStateCode;
	}
	public String getParamUsimStateCode() {
		return paramUsimStateCode;
	}
	public void setParamUsimStateCode(String paramUsimStateCode) {
		this.paramUsimStateCode = paramUsimStateCode;
	}
	public String getParamPstate() {
		return paramPstate;
	}
	public void setParamPstate(String paramPstate) {
		this.paramPstate = paramPstate;
	}
	public String getPhoneStateCode() {
		return phoneStateCode;
	}
	public void setPhoneStateCode(String phoneStateCode) {
		this.phoneStateCode = phoneStateCode;
	}
	public String getUsimStateCode() {
		return usimStateCode;
	}
	public void setUsimStateCode(String usimStateCode) {
		this.usimStateCode = usimStateCode;
	}
	public String getReqStateCd() {
		return reqStateCd;
	}
	public void setReqStateCd(String reqStateCd) {
		this.reqStateCd = reqStateCd;
	}
	public String getTbCd() {
		return tbCd;
	}
	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}
	public List<RcpSelfSuffVO> getItems() {
		return items;
	}
	public void setItems(List<RcpSelfSuffVO> items) {
		this.items = items;
	}
	public String getReqSrNo() {
		return reqSrNo;
	}
	public void setReqSrNo(String reqSrNo) {
		this.reqSrNo = reqSrNo;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getTbNm() {
		return tbNm;
	}
	public void setTbNm(String tbNm) {
		this.tbNm = tbNm;
	}
	public String getDlvryNo() {
		return dlvryNo;
	}
	public void setDlvryNo(String dlvryNo) {
		this.dlvryNo = dlvryNo;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getPstate() {
		return pstate;
	}
	public void setPstate(String pstate) {
		this.pstate = pstate;
	}
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
	
}
