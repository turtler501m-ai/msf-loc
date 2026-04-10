package com.ktmmobile.mcp.usim.dto;

import java.io.Serializable;

public class UsimMspOperTypeDto implements Serializable{
	 /**
	 *
	 */
	private static final long serialVersionUID = -4000088879995556757L;
	private String salePlcyCd;
	private String operType;
	private String operName;

	public String getSalePlcyCd() {
		return salePlcyCd;
	}
	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}


}
