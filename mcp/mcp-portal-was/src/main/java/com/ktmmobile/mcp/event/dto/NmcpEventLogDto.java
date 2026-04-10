package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;

public class NmcpEventLogDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String eventCd;
	private String eventVal;
	private String eventResult;
	private String eventStatus;
	private String userid;
	private Date regstDttm;

	public String getEventCd() {
		return eventCd;
	}
	public void setEventCd(String eventCd) {
		this.eventCd = eventCd;
	}
	public String getEventVal() {
		return eventVal; 
	}
	public void setEventVal(String eventVal) {
		this.eventVal = eventVal;
	}
	public String getEventResult() {
		return eventResult;
	}
	public void setEventResult(String eventResult) {
		this.eventResult = eventResult;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(Date regstDttm) {
		this.regstDttm = regstDttm;
	}

}
