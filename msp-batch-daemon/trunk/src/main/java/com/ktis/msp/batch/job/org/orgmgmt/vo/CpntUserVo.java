/**
 * 
 */
package com.ktis.msp.batch.job.org.orgmgmt.vo;

import com.ktis.msp.base.BaseVo;

/**
 * @author 82103
 *
 */
public class CpntUserVo extends BaseVo {
	private String orgnId;			//조직ID
	private String orgnNm;			//조직명
	private String orgnSubstrNm;	//조직명(50자 이상시 길이 제한된 조직명)
	private String hghrOrgnCd;		//상위조직코드
	private String mngrFileNm;		//대리점경영자이력 생성 	파일명 KIS_OG_AGNCY_MNGR_HST_
	private String agntFileNm;		//대리점 정보생성 		파일명 KIS_OG_AGNCY_BAS_
	private String mngrMphonNo;		//전화번호 MNGR_MPHON_NO
	private String grpId;			//그룹ID
	private String bizRegNum;		//사업자번호
	private String userId;			//영업전산 계정 ID
	private String pass;			//초기password	
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getOrgnSubstrNm() {
		return orgnSubstrNm;
	}
	public void setOrgnSubstrNm(String orgnSubstrNm) {
		this.orgnSubstrNm = orgnSubstrNm;
	}
	public String getHghrOrgnCd() {
		return hghrOrgnCd;
	}
	public void setHghrOrgnCd(String hghrOrgnCd) {
		this.hghrOrgnCd = hghrOrgnCd;
	}
	public String getMngrFileNm() {
		return mngrFileNm;
	}
	public void setMngrFileNm(String mngrFileNm) {
		this.mngrFileNm = mngrFileNm;
	}
	public String getAgntFileNm() {
		return agntFileNm;
	}
	public void setAgntFileNm(String agntFileNm) {
		this.agntFileNm = agntFileNm;
	}
	public String getMngrMphonNo() {
		return mngrMphonNo;
	}
	public void setMngrMphonNo(String mngrMphonNo) {
		this.mngrMphonNo = mngrMphonNo;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getBizRegNum() {
		return bizRegNum;
	}
	public void setBizRegNum(String bizRegNum) {
		this.bizRegNum = bizRegNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
