/**
 * 
 */
package com.ktis.msp.batch.job.org.prmtmgmt.vo;

import com.ktis.msp.base.BaseVo;

/**
 * @author 82103
 *
 */
public class EventCodePrmtVO extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String srchStrtDt;
	private String srchEndDt;
	private String srchEventCd;

	// 엑셀다운로드 로그
	private String userId;
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/

	public String getSrchStrtDt() {
		return srchStrtDt;
	}

	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String getSrchEventCd() {
		return srchEventCd;
	}

	public void setSrchEventCd(String srchEventCd) {
		this.srchEventCd = srchEventCd;
	}

	public String getDwnldRsn() {
		return dwnldRsn;
	}

	public void setDwnldRsn(String dwnldRsn) {
		this.dwnldRsn = dwnldRsn;
	}

	public String getExclDnldId() {
		return exclDnldId;
	}

	public void setExclDnldId(String exclDnldId) {
		this.exclDnldId = exclDnldId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
