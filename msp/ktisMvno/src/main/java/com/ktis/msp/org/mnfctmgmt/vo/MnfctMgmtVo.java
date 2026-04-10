package com.ktis.msp.org.mnfctmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : MnfctMgmtVo
 * @Description : 제조사 관리 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="mnfctMgmtVo")
public class MnfctMgmtVo extends BaseVo implements Serializable {

    /**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 188013984807913913L;
	
	private String serialnoFlg; /** SerialNo구분자 */
    private String email; /** 이메일 */
    private String fax; /** 팩스번호 */
    private String fax1; /** 팩스번호 */
    private String fax2; /** 팩스번호 */
    private String fax3; /** 팩스번호 */
    private String telnum; /** 전화번호 */
    private String telnum1; /** 전화번호 */
    private String telnum2; /** 전화번호 */
    private String telnum3; /** 전화번호 */
    private String mnfctYn; /** 제조사여부 */
    private String rvisnDttm; /** 수정일시 */
    private String rvisnId; /** 수정자ID */
    private String regDttm; /** 등록일시 */
    private String regId; /** 등록자ID */
    private String dtlAddr; /** 상세주소 */
    private String addr; /** 주소 */
    private String zipcd; /** 우편번호 */
    private String rprsenNm; /** 대표자명 */
    private String bizRegNum; /** 사업자등록번호 */
    private String bizRegNum1; /** 사업자등록번호 */
    private String bizRegNum2; /** 사업자등록번호 */
    private String bizRegNum3; /** 사업자등록번호 */
    private String mnfctNm; /** 제조사명 */
    private String mnfctId; /** 제조사ID */
    
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
	/**
	 * @return the serialnoFlg
	 */
	public String getSerialnoFlg() {
		return serialnoFlg;
	}


	/**
	 * @param serialnoFlg the serialnoFlg to set
	 */
	public void setSerialnoFlg(String serialnoFlg) {
		this.serialnoFlg = serialnoFlg;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}


	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}


	/**
	 * @return the telnum
	 */
	public String getTelnum() {
		return telnum;
	}


	/**
	 * @param telnum the telnum to set
	 */
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}


	public String getFax1() {
        return fax1;
    }


    public void setFax1(String fax1) {
        this.fax1 = fax1;
    }


    public String getFax2() {
        return fax2;
    }


    public void setFax2(String fax2) {
        this.fax2 = fax2;
    }


    public String getFax3() {
        return fax3;
    }


    public void setFax3(String fax3) {
        this.fax3 = fax3;
    }


    public String getTelnum1() {
        return telnum1;
    }


    public void setTelnum1(String telnum1) {
        this.telnum1 = telnum1;
    }


    public String getTelnum2() {
        return telnum2;
    }


    public void setTelnum2(String telnum2) {
        this.telnum2 = telnum2;
    }


    public String getTelnum3() {
        return telnum3;
    }


    public void setTelnum3(String telnum3) {
        this.telnum3 = telnum3;
    }


    /**
	 * @return the mnfctYn
	 */
	public String getMnfctYn() {
		return mnfctYn;
	}


	/**
	 * @param mnfctYn the mnfctYn to set
	 */
	public void setMnfctYn(String mnfctYn) {
		this.mnfctYn = mnfctYn;
	}


	/**
	 * @return the rvisnId
	 */
	public String getRvisnId() {
		return rvisnId;
	}


	/**
	 * @param rvisnId the rvisnId to set
	 */
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}



	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}


	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}


	/**
	 * @return the dtlAddr
	 */
	public String getDtlAddr() {
		return dtlAddr;
	}


	/**
	 * @param dtlAddr the dtlAddr to set
	 */
	public void setDtlAddr(String dtlAddr) {
		this.dtlAddr = dtlAddr;
	}


	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}


	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}


	/**
	 * @return the zipcd
	 */
	public String getZipcd() {
		return zipcd;
	}


	/**
	 * @param zipcd the zipcd to set
	 */
	public void setZipcd(String zipcd) {
		this.zipcd = zipcd;
	}


	/**
	 * @return the rprsenNm
	 */
	public String getRprsenNm() {
		return rprsenNm;
	}


	/**
	 * @param rprsenNm the rprsenNm to set
	 */
	public void setRprsenNm(String rprsenNm) {
		this.rprsenNm = rprsenNm;
	}


	/**
	 * @return the bizRegNum
	 */
	public String getBizRegNum() {
		return bizRegNum;
	}


	/**
	 * @param bizRegNum the bizRegNum to set
	 */
	public void setBizRegNum(String bizRegNum) {
		this.bizRegNum = bizRegNum;
	}


	public String getBizRegNum1() {
        return bizRegNum1;
    }


    public void setBizRegNum1(String bizRegNum1) {
        this.bizRegNum1 = bizRegNum1;
    }


    public String getBizRegNum2() {
        return bizRegNum2;
    }


    public void setBizRegNum2(String bizRegNum2) {
        this.bizRegNum2 = bizRegNum2;
    }


    public String getBizRegNum3() {
        return bizRegNum3;
    }


    public void setBizRegNum3(String bizRegNum3) {
        this.bizRegNum3 = bizRegNum3;
    }


    /**
	 * @return the mnfctNm
	 */
	public String getMnfctNm() {
		return mnfctNm;
	}


	/**
	 * @param mnfctNm the mnfctNm to set
	 */
	public void setMnfctNm(String mnfctNm) {
		this.mnfctNm = mnfctNm;
	}


	/**
	 * @return the mnfctId
	 */
	public String getMnfctId() {
		return mnfctId;
	}


	/**
	 * @param mnfctId the mnfctId to set
	 */
	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}


    public String getRvisnDttm() {
        return rvisnDttm;
    }


    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }


    public String getRegDttm() {
        return regDttm;
    }


    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }
    
	
}
