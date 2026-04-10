package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

/**
 * @Class Name : MspPlcyOperTypeDto
 * @Description : MSP 가입정보 조회
 * @author : ant
 * @Create Date : 2016. 03. 02.
 */
public class MspPlcyOperTypeDto implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5634005226362486604L;

	/** 판매정책코드 */
    private String salePlcyCd;

    /** 가입유형 코드 */
    private String operType;

    /** 가입유형 이름 */
    private String operName;

    private String orgnId;

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

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
