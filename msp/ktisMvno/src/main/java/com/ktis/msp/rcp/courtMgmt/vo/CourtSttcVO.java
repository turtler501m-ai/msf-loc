package com.ktis.msp.rcp.courtMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class CourtSttcVO extends BaseVo implements Serializable{

	private static final long serialVersionUID = -6510970033252597452L;
	
	private String strtDt;
	private String endDt;
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
