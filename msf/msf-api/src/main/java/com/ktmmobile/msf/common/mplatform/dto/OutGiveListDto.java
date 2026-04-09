package com.ktmmobile.msf.common.mplatform.dto;

import java.io.Serializable;



/**
*
* 데이터 주고받기 결합 상태 조회
* x86
*
*/
public class OutGiveListDto  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String custNm; 	  	//고객명
	private String rcvSvcNo;    //받기요금제번호(결합회선)
	private String tgtCustNm;   //받기회선 고객명
	private String rcvProdNm; 	//받기회선 요금제명
	private String efctStDt ;   //결합일시
	private String efctFnsDt;   //해지일시
	private String bindStatus;  //결합상태
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getRcvSvcNo() {
		return rcvSvcNo;
	}
	public void setRcvSvcNo(String rcvSvcNo) {
		this.rcvSvcNo = rcvSvcNo;
	}
	public String getTgtCustNm() {
		return tgtCustNm;
	}
	public void setTgtCustNm(String tgtCustNm) {
		this.tgtCustNm = tgtCustNm;
	}
	public String getRcvProdNm() {
		return rcvProdNm;
	}
	public void setRcvProdNm(String rcvProdNm) {
		this.rcvProdNm = rcvProdNm;
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
