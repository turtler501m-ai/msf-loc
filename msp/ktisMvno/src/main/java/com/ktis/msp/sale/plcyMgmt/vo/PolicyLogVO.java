package com.ktis.msp.sale.plcyMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class PolicyLogVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3011390047395777159L;
	
	private String salePlcyCd;
	private String procId;
	private String plcyEventCd;
	private String strtDttm;
	private String endDttm;
	private String remrk;
	private String procNm;
	private String plcyEventNm;
	
	public String getSalePlcyCd() {
		return salePlcyCd;
	}
	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getPlcyEventCd() {
		return plcyEventCd;
	}
	public void setPlcyEventCd(String plcyEventCd) {
		this.plcyEventCd = plcyEventCd;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getEndDttm() {
		return endDttm;
	}
	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}
	public String getRemrk() {
		return remrk;
	}
	public void setRemrk(String remrk) {
		this.remrk = remrk;
	}
	public String getProcNm() {
		return procNm;
	}
	public void setProcNm(String procNm) {
		this.procNm = procNm;
	}
	public String getPlcyEventNm() {
		return plcyEventNm;
	}
	public void setPlcyEventNm(String plcyEventNm) {
		this.plcyEventNm = plcyEventNm;
	}
	
}
