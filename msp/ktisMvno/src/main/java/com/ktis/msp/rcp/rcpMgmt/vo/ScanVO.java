package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="scanVO")
public class ScanVO extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	private String requestKey;
	private String userId;
	private String scanPath;
	private String scanUrl;
	
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getScanPath() {
		return scanPath;
	}
	public void setScanPath(String scanPath) {
		this.scanPath = scanPath;
	}
	public String getScanUrl() {
		return scanUrl;
	}
	public void setScanUrl(String scanUrl) {
		this.scanUrl = scanUrl;
	}
	

}
