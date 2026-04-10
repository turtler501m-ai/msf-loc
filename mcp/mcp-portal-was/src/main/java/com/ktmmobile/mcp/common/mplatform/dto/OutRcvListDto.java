package com.ktmmobile.mcp.common.mplatform.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.ktmmobile.mcp.common.util.XmlParse;

/**
*
* 데이터 주고받기 결합 상태 조회
* x86
*
*/
public class OutRcvListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	// 조회구분코드 R일경우
	private String giveCustNm; //주기회선 고객명
	private String giveProdNm; //주기회선 요금제명
	private String giveSvcNo;  //주기회선 번호
	private String rcvCustNm; //받기회선 고객명
	private String rcvProdNm; //받기회선 요금제명
	private String rcvSvcNo;  //받기회선 번호
	private String efctStDt;  //결합일시
	private String efctFnsDt; //해지일시
	private String bindStatus; 	//결합상태
	public String getGiveCustNm() {
		return giveCustNm;
	}
	public void setGiveCustNm(String giveCustNm) {
		this.giveCustNm = giveCustNm;
	}
	public String getGiveProdNm() {
		return giveProdNm;
	}
	public void setGiveProdNm(String giveProdNm) {
		this.giveProdNm = giveProdNm;
	}
	public String getGiveSvcNo() {
		return giveSvcNo;
	}
	public void setGiveSvcNo(String giveSvcNo) {
		this.giveSvcNo = giveSvcNo;
	}
	public String getRcvCustNm() {
		return rcvCustNm;
	}
	public void setRcvCustNm(String rcvCustNm) {
		this.rcvCustNm = rcvCustNm;
	}
	public String getRcvProdNm() {
		return rcvProdNm;
	}
	public void setRcvProdNm(String rcvProdNm) {
		this.rcvProdNm = rcvProdNm;
	}
	public String getRcvSvcNo() {
		return rcvSvcNo;
	}
	public void setRcvSvcNo(String rcvSvcNo) {
		this.rcvSvcNo = rcvSvcNo;
	}
	public String getEfctStDt() {
		return efctStDt;
	}
	public void setEfctStDt(String efctStDt) {
		this.efctStDt = efctStDt;
	}
	public String getEfctFnsDt() {
		return efctFnsDt;
	}
	public void setEfctFnsDt(String efctFnsDt) {
		this.efctFnsDt = efctFnsDt;
	}
	public String getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}


}
