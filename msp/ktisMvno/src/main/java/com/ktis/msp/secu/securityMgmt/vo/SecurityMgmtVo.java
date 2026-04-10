package com.ktis.msp.secu.securityMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SecurityMgmtVo
 * @Description : 보안점검 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2019.01.17 
 * @
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="SecurityMgmtVo")
public class SecurityMgmtVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	private String logDttm; /** 접속일시 */
	private String succYn; /** 성공실패여부 */
	private String usrId; /** 사용자ID */
	private String usrNm; /** 사용자명 */
	private String orgnId; /** 조직ID */
	private String orgnNm; /** 조직명 */
	private String fileDnDt; /** 파일다운로드일시 */
	private String dwnldRsb; /** 파일다운로드사유 */
	private String menuNm; /** 메뉴명 */
	private String fileNm; /** 메뉴명 */
	private String attcSctnNm; /** 계정구분 */
	private String rvisnDttm; /** 수정일자 */
	private String regstDttm; /** 생성일자 */
	private String lastLoginDt; /** 마지막로그인일자 */
	private String delYn; 	/** 삭제여부 */
	private String usgYn;	/** 사용여부 */
	private String status;  /** 상태구분 */
	
	
    public String getDelYn() {
		return delYn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getUsgYn() {
		return usgYn;
	}
	public void setUsgYn(String usgYn) {
		this.usgYn = usgYn;
	}
	public String getLogDttm() {
        return logDttm;
    }
    public void setLogDttm(String logDttm) {
        this.logDttm = logDttm;
    }
    public String getSuccYn() {
        return succYn;
    }
    public void setSuccYn(String succYn) {
        this.succYn = succYn;
    }
    public String getUsrId() {
        return usrId;
    }
    public void setUsrId(String usrId) {
        this.usrId = usrId;
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
    public String getFileDnDt() {
        return fileDnDt;
    }
    public void setFileDnDt(String fileDnDt) {
        this.fileDnDt = fileDnDt;
    }
    public String getDwnldRsb() {
        return dwnldRsb;
    }
    public void setDwnldRsb(String dwnldRsb) {
        this.dwnldRsb = dwnldRsb;
    }
    public String getMenuNm() {
        return menuNm;
    }
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }
    public String getFileNm() {
        return fileNm;
    }
    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }
    public String getAttcSctnNm() {
        return attcSctnNm;
    }
    public void setAttcSctnNm(String attcSctnNm) {
        this.attcSctnNm = attcSctnNm;
    }
    public String getRvisnDttm() {
        return rvisnDttm;
    }
    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }
    public String getRegstDttm() {
        return regstDttm;
    }
    public void setRegstDttm(String regstDttm) {
        this.regstDttm = regstDttm;
    }
    public String getLastLoginDt() {
        return lastLoginDt;
    }
    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

}
