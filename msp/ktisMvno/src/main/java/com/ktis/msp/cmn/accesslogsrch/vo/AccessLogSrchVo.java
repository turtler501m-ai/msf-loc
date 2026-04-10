package com.ktis.msp.cmn.accesslogsrch.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : UserInfoMgmtVo
 * @Description : 사용자 관리 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="accessLogSrchVo")
public class AccessLogSrchVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	private String usrId; /** 사용자 ID */
	private String attcSctnCd; /** 소속 구분 코드 */
	private String logDttm; /** 로그일자 */
	private String ipInfo; /** ip정보 */
	private String macAddr; /** mac정보 */
	private String succYn; /** 성공여부 */
	private String regstPrgm; /** 접속프로그램 */
	private String regststId; /** 접속아이디 */
	private String usrNm; /** 사용자 명 */
	private String orgnId; /** 조직ID */
	private String orgnNm; /** 조직명 */
	private String regstDttm; /** 접속일자 */
	private String searchUsrId; /** 조회사용자 id*/
	private String searchStrtDt;  /** 접속시작일자*/
	private String searchEndDt; /** 접속종료일자*/
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getAttcSctnCd() {
		return attcSctnCd;
	}
	public void setAttcSctnCd(String attcSctnCd) {
		this.attcSctnCd = attcSctnCd;
	}
	public String getLogDttm() {
		return logDttm;
	}
	public void setLogDttm(String logDttm) {
		this.logDttm = logDttm;
	}
	public String getIpInfo() {
		return ipInfo;
	}
	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public String getSuccYn() {
		return succYn;
	}
	public void setSuccYn(String succYn) {
		this.succYn = succYn;
	}
	public String getRegstPrgm() {
		return regstPrgm;
	}
	public void setRegstPrgm(String regstPrgm) {
		this.regstPrgm = regstPrgm;
	}
	public String getRegststId() {
		return regststId;
	}
	public void setRegststId(String regststId) {
		this.regststId = regststId;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
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
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getSearchUsrId() {
		return searchUsrId;
	}
	public void setSearchUsrId(String searchUsrId) {
		this.searchUsrId = searchUsrId;
	}
	public String getSearchStrtDt() {
		return searchStrtDt;
	}
	public void setSearchStrtDt(String searchStrtDt) {
		this.searchStrtDt = searchStrtDt;
	}
	public String getSearchEndDt() {
		return searchEndDt;
	}
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	    
    
	
}
