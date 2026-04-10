package com.ktis.msp.m2m.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SmsTemplateReqVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8271462041358511928L;
	
	private String searchOrgnId;		// 관리부서
	private String searchWorkType;		// 업무구분
	private String searchTemplateNm;	// 템플릿명
	
	public String getSearchOrgnId() {
		return searchOrgnId;
	}
	public void setSearchOrgnId(String searchOrgnId) {
		this.searchOrgnId = searchOrgnId;
	}
	public String getSearchWorkType() {
		return searchWorkType;
	}
	public void setSearchWorkType(String searchWorkType) {
		this.searchWorkType = searchWorkType;
	}
	public String getSearchTemplateNm() {
		return searchTemplateNm;
	}
	public void setSearchTemplateNm(String searchTemplateNm) {
		this.searchTemplateNm = searchTemplateNm;
	}
	
}
