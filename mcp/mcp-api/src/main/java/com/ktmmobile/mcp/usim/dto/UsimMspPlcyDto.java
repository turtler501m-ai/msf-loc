package com.ktmmobile.mcp.usim.dto;

import java.io.Serializable;

public class UsimMspPlcyDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -8744453331981713365L;

	/** 판매정책코드 */
	private String salePlcyCd;

	/** 판매정책명 */
	private String salePlcyNm;

	public String getSalePlcyCd() {
		return salePlcyCd;
	}

	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}

	public String getSalePlcyNm() {
		return salePlcyNm;
	}

	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}
}
