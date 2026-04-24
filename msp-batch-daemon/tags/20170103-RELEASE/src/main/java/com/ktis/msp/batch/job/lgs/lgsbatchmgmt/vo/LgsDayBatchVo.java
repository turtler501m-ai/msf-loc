package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

/**
 * @Class Name  : LgsDayBatchVo.java
 * @Description : LgsDayBatchVo Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.24     IB      최초생성
 *
 * @author IB
 * @since 2014. 08.24
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

public class LgsDayBatchVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 2833433042138170138L;
	
    private String orgnId;				/** 조직ID */
    private String stdDt;				/** 기준일자 */
    private String befStdDt;			/** 기준일자 */
    private String prdtId;				/** 제품ID */
    private String oldYn;				/** 신품여부 */
  
    private String userId;				/** 사용자ID */
    private String mnfctId;				/** 공급사ID */
    private String mnfctNm;				/** 공급사명   */
    
    
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getStdDt() {
		return stdDt;
	}
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}
	public String getBefStdDt() {
		return befStdDt;
	}
	public void setBefStdDt(String befStdDt) {
		this.befStdDt = befStdDt;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getOldYn() {
		return oldYn;
	}
	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMnfctId() {
		return mnfctId;
	}
	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}
	public String getMnfctNm() {
		return mnfctNm;
	}
	public void setMnfctNm(String mnfctNm) {
		this.mnfctNm = mnfctNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
	
}
