package com.ktmmobile.mcp.content.dto;

public class ReShareDataReqDto {

	private String custId; //고객번호
	private String ncn; //사용자서비스계약번호
	private String ctn; //사용자전화번호
	private String clntIp; //client IP
	private String clntUsrId; //사용자 userId
	private String retvGubunCd; //g(주기회선조회) R(받기회선조회)
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getNcn() {
		return ncn;
	}
	public void setNcn(String ncn) {
		this.ncn = ncn;
	}
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getClntIp() {
		return clntIp;
	}
	public void setClntIp(String clntIp) {
		this.clntIp = clntIp;
	}
	public String getClntUsrId() {
		return clntUsrId;
	}
	public void setClntUsrId(String clntUsrId) {
		this.clntUsrId = clntUsrId;
	}
	public String getRetvGubunCd() {
		return retvGubunCd;
	}
	public void setRetvGubunCd(String retvGubunCd) {
		this.retvGubunCd = retvGubunCd;
	}



}
