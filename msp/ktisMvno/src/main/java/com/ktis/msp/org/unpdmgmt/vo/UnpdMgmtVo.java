package com.ktis.msp.org.unpdmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PowerTmntMgmtVo
 * @Description : 청구/수납 조회 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.07.21 김연우 최초생성
 * @
 * @author : 김연우
 * @Create Date : 2015. 7. 21.
 */
public class UnpdMgmtVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -7787087763571352166L;
	private String billYm;
	private String billItemCd;
	private String searchType;
	private String searchVal;
	private String subStatus;
	private String mcnYn;
	private String cntpntShopId;
	
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getBillItemCd() {
		return billItemCd;
	}
	public void setBillItemCd(String billItemCd) {
		this.billItemCd = billItemCd;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getMcnYn() {
		return mcnYn;
	}
	public void setMcnYn(String mcnYn) {
		this.mcnYn = mcnYn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
