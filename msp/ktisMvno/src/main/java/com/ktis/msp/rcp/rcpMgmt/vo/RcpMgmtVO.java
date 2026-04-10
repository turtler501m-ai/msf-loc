package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rcpMgmtVO")
public class RcpMgmtVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3317078644517603370L;
	
	private String requestKey;
	private String sysRdate;
	
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
}
