package com.ktmmobile.mcp.appform.dto;

import java.io.Serializable;
import java.util.Date;

public class FrndInviUsimDto implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private String name;
	private String phone;
	private String commendId;
	private String agree;
	private String frndName;
	private String frndPhone;
	private String frndPost;
	private String frndAddr;
	private String frndAddrDtl;
	private String sysRdate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCommendId() {
		return commendId;
	}
	public void setCommendId(String commendId) {
		this.commendId = commendId;
	}
	public String getAgree() {
		return agree;
	}
	public void setAgree(String agree) {
		this.agree = agree;
	}
	public String getFrndName() {
		return frndName;
	}
	public void setFrndName(String frndName) {
		this.frndName = frndName;
	}
	public String getFrndPhone() {
		return frndPhone;
	}
	public void setFrndPhone(String frndPhone) {
		this.frndPhone = frndPhone;
	}
	public String getFrndPost() {
		return frndPost;
	}
	public void setFrndPost(String frndPost) {
		this.frndPost = frndPost;
	}
	public String getFrndAddr() {
		return frndAddr;
	}
	public void setFrndAddr(String frndAddr) {
		this.frndAddr = frndAddr;
	}
	public String getFrndAddrDtl() {
		return frndAddrDtl;
	}
	public void setFrndAddrDtl(String frndAddrDtl) {
		this.frndAddrDtl = frndAddrDtl;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
