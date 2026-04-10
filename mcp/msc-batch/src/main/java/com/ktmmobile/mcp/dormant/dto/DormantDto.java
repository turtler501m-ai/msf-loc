package com.ktmmobile.mcp.dormant.dto;

import java.io.Serializable;


public class DormantDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    private String userid;
    private String mobileNo;
    private String yearText;
    private String monthText;
    private String dateText;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getYearText() {
		return yearText;
	}
	public void setYearText(String yearText) {
		this.yearText = yearText;
	}
	public String getMonthText() {
		return monthText;
	}
	public void setMonthText(String monthText) {
		this.monthText = monthText;
	}
	public String getDateText() {
		return dateText;
	}
	public void setDateText(String dateText) {
		this.dateText = dateText;
	}
    
    
}
