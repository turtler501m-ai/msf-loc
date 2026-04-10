package com.ktis.msp.rcp.billMgmt.vo;
import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class BillMgmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 검색어
	private String customerId;		// 고객ID
	private String billYm;			// 청구월
	private String subscriberNo;	// 전화번호
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	
}
