package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class BenefitSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String cntrNo;
    private String pointYyyy;
    private String pointMm;
    private String pointTrtCd;
    
    
    
	public String getPointTrtCd() {
		return pointTrtCd;
	}
	public void setPointTrtCd(String pointTrtCd) {
		this.pointTrtCd = pointTrtCd;
	}
	public String getCntrNo() {
		return cntrNo;
	}
	public void setCntrNo(String cntrNo) {
		this.cntrNo = cntrNo;
	}

	
	public String getPointYyyy() {
		return pointYyyy;
	}
	public void setPointYyyy(String pointYyyy) {
		this.pointYyyy = pointYyyy;
	}
	public String getPointMm() {
		return pointMm;
	}
	public void setPointMm(String pointMm) {
		this.pointMm = pointMm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    


    
}
