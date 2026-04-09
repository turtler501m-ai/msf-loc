package com.ktmmobile.msf.common.mplatform.vo;


public class MpBkInfoDto {
	private String remainSusCnt;		//시작일자
	private String colSusDays;			//종료일자
	private String remainOgDays;		//고객정지일수 - 단위: 건수
	private String csaActivityRsnDesc;	//구분 - 예)고객요청-발신정지

	public String getRemainSusCnt() {
		return remainSusCnt;
	}
	public void setRemainSusCnt(String remainSusCnt) {
		this.remainSusCnt = remainSusCnt;
	}
	public String getcolSusDays() {
		return colSusDays;
	}
	public void setColSusDays(String colSusDays) {
		this.colSusDays = colSusDays;
	}
	public String getRemainOgDays() {
		return remainOgDays;
	}
	public void setRemainOgDays(String remainOgDays) {
		this.remainOgDays = remainOgDays;
	}
	public String getCsaActivityRsnDesc() {
		return csaActivityRsnDesc;
	}
	public void setCsaActivityRsnDesc(String csaActivityRsnDesc) {
		this.csaActivityRsnDesc = csaActivityRsnDesc;
	}
}
