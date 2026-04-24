package com.ktis.msp.batch.job.rcp.rcpmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class RcpNowDlvryUsimVO extends BaseVo {

	private String requestKey;
	private String resNo                 ;    /* 예약번호                 */ 
	private String reqUsimSn             ;    /* 유심번호                   */ 
	private String dstaddr               ;    /* sms 전송할 전화번호                 */ 
	private String openExpDate        ;    /* 개통완료기한 날짜                    */ 
	private String text            ;    /* 문자내용                 */
	private String dlvryType		;		/*배송타입 (02:바로배송 / 04:제휴유심보유 - 제휴신청서 작성시 유심미보유 선택)*/
	private String cntpntShopNm		;		/*배송타입 (02:바로배송 / 04:제휴유심보유 - 제휴신청서 작성시 유심미보유 선택)*/

	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getReqUsimSn() {
		return reqUsimSn;
	}
	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}
	public String getDstaddr() {
		return dstaddr;
	}
	public void setDstaddr(String dstaddr) {
		this.dstaddr = dstaddr;
	}
	public String getOpenExpDate() {
		return openExpDate;
	}
	public void setOpenExpDate(String openExpDate) {
		this.openExpDate = openExpDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getDlvryType() {
		return dlvryType;
	}

	public void setDlvryType(String dlvryType) {
		this.dlvryType = dlvryType;
	}

	public String getCntpntShopNm() {
		return cntpntShopNm;
	}

	public void setCntpntShopNm(String cntpntShopNm) {
		this.cntpntShopNm = cntpntShopNm;
	}
}
