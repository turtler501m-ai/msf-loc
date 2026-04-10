package com.ktmmobile.mcp.alarm.dto;

import java.io.Serializable;


/**
 * @Class Name : AlarmDto
 * @Description : 알림
 *
 * @author : 
 * @Create Date : 
 */
public class AlarmDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String notiDivCd;
	private String notiApyDate;
	private String notiTrtSttusCd;
	private String notiSndDt;
	private String notiWayCd;
	private String notiCd;
	private String cretIp;
	private String cretDt;
	private String cretId;
	private String amdIp;
	private String amdDt;
	private String amdId;
	private String nm;
	private String mobileNo;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNotiDivCd() {
		return notiDivCd;
	}
	public void setNotiDivCd(String notiDivCd) {
		this.notiDivCd = notiDivCd;
	}
	public String getNotiApyDate() {
		return notiApyDate;
	}
	public void setNotiApyDate(String notiApyDate) {
		this.notiApyDate = notiApyDate;
	}
	public String getNotiTrtSttusCd() {
		return notiTrtSttusCd;
	}
	public void setNotiTrtSttusCd(String notiTrtSttusCd) {
		this.notiTrtSttusCd = notiTrtSttusCd;
	}
	public String getNotiSndDt() {
		return notiSndDt;
	}
	public void setNotiSndDt(String notiSndDt) {
		this.notiSndDt = notiSndDt;
	}
	public String getNotiWayCd() {
		return notiWayCd;
	}
	public void setNotiWayCd(String notiWayCd) {
		this.notiWayCd = notiWayCd;
	}
	public String getNotiCd() {
		return notiCd;
	}
	public void setNotiCd(String notiCd) {
		this.notiCd = notiCd;
	}
	public String getCretIp() {
		return cretIp;
	}
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
		
}
   