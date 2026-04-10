package com.ktmmobile.mcp.content.dto;

public class MyCombinationReqDto {

	private String custId;	//고객번호
	private String ncn;		//사용자 서비스계약번호
	private String ctn; 	//사용자 전화번호
	private String clntIp;  //Client IP
	private String reqSvc; 	 //선택한 회선에 전화번호
	private String contractNum; //가입계약번호
	private String combiChkYn; //결합 사전체크여부


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
	public String getCombiChkYn() {
		return combiChkYn;
	}
	public void setCombiChkYn(String combiChkYn) {
		this.combiChkYn = combiChkYn;
	}
	public String getReqSvc() {
		return reqSvc;
	}
	public void setReqSvc(String reqSvc) {
		this.reqSvc = reqSvc;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}




}
