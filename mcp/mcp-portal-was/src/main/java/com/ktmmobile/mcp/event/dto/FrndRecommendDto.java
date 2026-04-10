package com.ktmmobile.mcp.event.dto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class FrndRecommendDto {

	/** 추천 아이디 */
	private String commendId;

	/** 계약번호(NCN)  */
	private String contractNum;

	/** 개통방법코드 01 바로배송유심  02 편의점 유심 03 오픈마켓 유심 04 eSIM 셀프 */
	private String openMthdCd= "";
	/** 추천요금제 코드1 */
	private String commendSocCode01 ="";
	/** 추천요금제 코드2 */
	private String commendSocCode02="";
	/** 추천요금제 코드3 */
	private String commendSocCode03="";
	/** 링크유형코드 01 기본 추천  02 올인원 */
	private String linkTypeCd;

	private String sysRdate; // 생성일
	private int requestKey; // MCP_REQUEST FK
	private String resNo; // 예약등록_예약번호
	private String applyYn; // 혜택 적용여부 (Y/ N)
	private String applyDate; // 적용일자
	private String rvisnDttm; // 수정일시

	private String cretIp;
	private String cretId;


	
	public String getCommendId() {
		return commendId;
	}
	public void setCommendId(String commendId) {
		this.commendId = commendId;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public int getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(int requestKey) {
		this.requestKey = requestKey;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getApplyYn() {
		return applyYn;
	}
	public void setApplyYn(String applyYn) {
		this.applyYn = applyYn;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}


	public String getOpenMthdCd() {
		return openMthdCd;
	}

	public void setOpenMthdCd(String openMthdCd) {
		this.openMthdCd = openMthdCd;
	}

	public String getCommendSocCode01() {
		return commendSocCode01;
	}

	public void setCommendSocCode01(String commendSocCode01) {
		this.commendSocCode01 = commendSocCode01;
	}

	public String getCommendSocCode02() {
		return commendSocCode02;
	}

	public void setCommendSocCode02(String commendSocCode02) {
		this.commendSocCode02 = commendSocCode02;
	}

	public String getCommendSocCode03() {
		return commendSocCode03;
	}

	public void setCommendSocCode03(String commendSocCode03) {
		this.commendSocCode03 = commendSocCode03;
	}

	public String getLinkTypeCd() {
		return linkTypeCd;
	}

	public void setLinkTypeCd(String linkTypeCd) {
		this.linkTypeCd = linkTypeCd;
	}

	public String getCretIp() {
		return cretIp;
	}

	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}

	public String getCretId() {
		return cretId;
	}

	public void setCretId(String cretId) {
		this.cretId = cretId;
	}

	public String getEventLink() {
		return "/event/frndRecommendView.do?recommend="+commendId ;
	}
}
