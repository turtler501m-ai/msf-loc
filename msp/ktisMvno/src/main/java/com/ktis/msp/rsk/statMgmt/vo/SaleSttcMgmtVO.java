package com.ktis.msp.rsk.statMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class SaleSttcMgmtVO extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 조회조건
	private String stdrDt;
	private String stdrTm;
	
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getStdrTm() {
		return stdrTm;
	}
	public void setStdrTm(String stdrTm) {
		this.stdrTm = stdrTm;
	}
	
	
	
}
