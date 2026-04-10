package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class RcpCustPointVO extends BaseVo implements Serializable{

	private static final long serialVersionUID = -6510970033252597452L;
	
	private String strtDt;
    private String endDt;
    private String searchCd;
    private String searchVal;
    
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
