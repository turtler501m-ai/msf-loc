/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.pps.orgmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;



/**  
 * @Class Name : PpsOrgMgmtVO.java
 * @Description : OrgMgmtVO Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.13   장익준           최초생성
 * 
 * @author 장익준
 * @since 2014.08.13
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsOrgMgmtVO")
public class PpsOrgMgmtVO extends BaseVo implements Serializable {
	
	/** 조직유형 */
	final public static String ORG_TYPE_CD_HDOFC	= "10";//본사조직
	final public static String ORG_TYPE_CD_AGNCY	= "20";//대리점
	final public static String ORG_TYPE_CD_SALE_AGNCY	= "30";//판매점
	
	final public static String ORG_LVL_CD_AGNCY	= "10";//대리점
	final public static String ORG_LVL_CD_SALE_AGNCY	= "20";//판매점
	
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3255423502191666933L;
	
	private String orgnSeq; /** 조직이력 Seq */
    private String orgnId; /** 조직 ID */
    private String orgnNm; /** 조직명 */
    private String typeCd; /** 유형코드 */
    private String orgnLvlCd; /** 조직레벨코드 */
    private String applStrtDt; /** 적용시작일자 */
    private String applCmpltDt; /** 적용종료일자 */
    private String openCrdtLoanYn; /** 개통여신여부 */
    private String openCrdtLoanStrtDt; /** 개통여신시작일자 */
    private String taxbillYn; /** 세금계산서발행여부 */
    private String cmsnPrvdYn; /** 수수료지급여부 */
    private String hndstInvtrYn; /** 단말재고여부 */
    private String bizTypeCd; /** 사업자구분 */
    private String bizRegNum; /** 사업자등록번호 */
    private String bizRegNum1; /** 사업자등록번호 */
    private String bizRegNum2; /** 사업자등록번호 */
    private String bizRegNum3; /** 사업자등록번호 */
    private String corpRegNum; /** 법인등록번호 */
    private String corpRegNum1; /** 법인등록번호 */
    private String corpRegNum2; /** 법인등록번호 */
    private String bizCat; /** 업종 */
    private String bizCon; /** 업태 */
    private String rprsenNm; /** 대표자명 */
    private String rprsenRrnum; /** 대표자주민번호 */
    private String rprsenRrnum1; /** 대표자주민번호 */
    private String rprsenRrnum2; /** 대표자주민번호 */
    private String telnum; /** 전화번호 */
    private String telnum1; /** 전화번호 */
    private String telnum2; /** 전화번호 */
    private String telnum3; /** 전화번호 */
    private String fax; /** 팩스번호 */
    private String fax1; /** 팩스번호 */
    private String fax2; /** 팩스번호 */
    private String fax3; /** 팩스번호 */
    private String oldFax; /** 팩스번호 */
    private String email; /** 이메일 */
    private String zipcd; /** 우편번호 */
    private String addr1; /** 주소1 */
    private String addr2; /** 주소2 */
    private String respnPrsnId; /** 담당자ID */
    private String respnPrsnNum; /** 담당자전화번호 */
    private String respnPrsnNm; /** 담당자명 */
    private String respnPrsnMblphn; /** 담당자휴대폰번호 */
    private String respnPrsnEmail; /** 담당자이메일 */
    private String hndstOrdYn; /** 단말주문여부 */
    private String orgnStatCd; /** 조직상태코드 */
    private String tmntDt; /** 해지일자 */
    private String typeDtlCd; /** 유형상세코드 */
    private String hghrOrgnCd; /** 상위조직코드 */
    private String hghrOrgnNm; /** 상위조직명 */
    private String orgnGrd; /** 조직등급 */
    private String closeYn; /** 정리점여부 */
    private String logisCnterYn; /** 물류센터여부 */
    private String typeNm;  /**유형코드명 */
    private String orgnLvlNm;   /**조직레벨코드명 */
    private String bizTypeNm;   /**사업자구분명 */
    private String orgnStatNm;   /**조직상태명 */
    private String typeDtlNm;   /**유형상세코드명 */
    private String regId; /** 등록자명 */
    private String regDttm; /** 등록일자 */
    private String rvisnId; /** 수정자명 */
    private String rvisnNm; /** 수정자명 */
    private String rvisnDttm; /** 수정일자 */
    private String tempOrgId; /** 임시 조직 ID */
    private String ktOrgId; /** KT 조직 ID */
    private String typeDtlCd1; /** 유형상세1 */
    private String typeDtlCd2; /** 유형상세2 */
    private String typeDtlCd3; /** 유형상세3 */
    private String typeDtlCdNm1; /** 유형상세1 */
    private String typeDtlCdNm2; /** 유형상세2 */
    private String typeDtlCdNm3; /** 유형상세3 */
    private String userOrgnLvlCd; /**로그인 사용자의 조직레벨코드*/
    private String id;
    private String xmlkidsBo; 
    private boolean xmlkids; 

    private String salePlcyCd; /** 판매정책코드 */
    
	private String virAccountId;/** 가상계좌번호  */
	private String virBankNm; /** 가상계좌은행명 */

    @Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   
    
    
	/**
	 * @return the salePlcyCd
	 */
	public String getSalePlcyCd() {
		return salePlcyCd;
	}




	/**
	 * @param salePlcyCd the salePlcyCd to set
	 */
	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}




	/**
	 * @return the typeDtlCd1
	 */
	public String getTypeDtlCd1() {
		return typeDtlCd1;
	}




	/**
	 * @param typeDtlCd1 the typeDtlCd1 to set
	 */
	public void setTypeDtlCd1(String typeDtlCd1) {
		this.typeDtlCd1 = typeDtlCd1;
	}




	/**
	 * @return the typeDtlCd2
	 */
	public String getTypeDtlCd2() {
		return typeDtlCd2;
	}




	/**
	 * @param typeDtlCd2 the typeDtlCd2 to set
	 */
	public void setTypeDtlCd2(String typeDtlCd2) {
		this.typeDtlCd2 = typeDtlCd2;
	}




	/**
	 * @return the typeDtlCd3
	 */
	public String getTypeDtlCd3() {
		return typeDtlCd3;
	}




	/**
	 * @param typeDtlCd3 the typeDtlCd3 to set
	 */
	public void setTypeDtlCd3(String typeDtlCd3) {
		this.typeDtlCd3 = typeDtlCd3;
	}




	/**
	 * @return the oldFax
	 */
	public String getOldFax() {
		return oldFax;
	}



	/**
	 * @param oldFax the oldFax to set
	 */
	public void setOldFax(String oldFax) {
		this.oldFax = oldFax;
	}



	/**
	 * @return the ktOrgId
	 */
	public String getKtOrgId() {
		return ktOrgId;
	}



	/**
	 * @param ktOrgId the ktOrgId to set
	 */
	public void setKtOrgId(String ktOrgId) {
		this.ktOrgId = ktOrgId;
	}



	/**
	 * @return the tempOrgId
	 */
	public String getTempOrgId() {
		return tempOrgId;
	}



	/**
	 * @param tempOrgId the tempOrgId to set
	 */
	public void setTempOrgId(String tempOrgId) {
		this.tempOrgId = tempOrgId;
	}



	/**
	 * @return the hghrOrgnNm
	 */
	public String getHghrOrgnNm() {
		return hghrOrgnNm;
	}




	/**
	 * @param hghrOrgnNm the hghrOrgnNm to set
	 */
	public void setHghrOrgnNm(String hghrOrgnNm) {
		this.hghrOrgnNm = hghrOrgnNm;
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




	public String getRvisnNm() {
        return rvisnNm;
    }




    public void setRvisnNm(String rvisnNm) {
        this.rvisnNm = rvisnNm;
    }




    /**
	 * @return the rvisnDttm
	 */
	public String getRvisnDttm() {
		return rvisnDttm;
	}




	/**
	 * @param rvisnDttm the rvisnDttm to set
	 */
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}




	/**
	 * @return the regDttm
	 */
	public String getRegDttm() {
		return regDttm;
	}




	/**
	 * @param regDttm the regDttm to set
	 */
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
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
	 * @return the orgnSeq
	 */
	public String getOrgnSeq() {
		return orgnSeq;
	}



	/**
	 * @param orgnSeq the orgnSeq to set
	 */
	public void setOrgnSeq(String orgnSeq) {
		this.orgnSeq = orgnSeq;
	}



	/**
	 * @return the logisCnterYn
	 */
	public String getLogisCnterYn() {
		return logisCnterYn;
	}

	/**
	 * @param logisCnterYn the logisCnterYn to set
	 */
	public void setLogisCnterYn(String logisCnterYn) {
		this.logisCnterYn = logisCnterYn;
	}

	/**
	 * @return the closeYn
	 */
	public String getCloseYn() {
		return closeYn;
	}

	/**
	 * @param closeYn the closeYn to set
	 */
	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	/**
	 * @return the orgnGrd
	 */
	public String getOrgnGrd() {
		return orgnGrd;
	}

	/**
	 * @param orgnGrd the orgnGrd to set
	 */
	public void setOrgnGrd(String orgnGrd) {
		this.orgnGrd = orgnGrd;
	}

	/**
	 * @return the hghrOrgnCd
	 */
	public String getHghrOrgnCd() {
		return hghrOrgnCd;
	}

	/**
	 * @param hghrOrgnCd the hghrOrgnCd to set
	 */
	public void setHghrOrgnCd(String hghrOrgnCd) {
		this.hghrOrgnCd = hghrOrgnCd;
	}

	/**
	 * @return the typeDtlCd
	 */
	public String getTypeDtlCd() {
		return typeDtlCd;
	}

	/**
	 * @param typeDtlCd the typeDtlCd to set
	 */
	public void setTypeDtlCd(String typeDtlCd) {
		this.typeDtlCd = typeDtlCd;
	}

	/**
	 * @return the tmntDt
	 */
	public String getTmntDt() {
		return tmntDt;
	}

	/**
	 * @param tmntDt the tmntDt to set
	 */
	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
	}

	/**
	 * @return the orgnStatCd
	 */
	public String getOrgnStatCd() {
		return orgnStatCd;
	}

	/**
	 * @param orgnStatCd the orgnStatCd to set
	 */
	public void setOrgnStatCd(String orgnStatCd) {
		this.orgnStatCd = orgnStatCd;
	}

	/**
	 * @return the hndstOrdYn
	 */
	public String getHndstOrdYn() {
		return hndstOrdYn;
	}

	/**
	 * @param hndstOrdYn the hndstOrdYn to set
	 */
	public void setHndstOrdYn(String hndstOrdYn) {
		this.hndstOrdYn = hndstOrdYn;
	}

	/**
	 * @return the respnPrsnEmail
	 */
	public String getRespnPrsnEmail() {
		return respnPrsnEmail;
	}

	/**
	 * @param respnPrsnEmail the respnPrsnEmail to set
	 */
	public void setRespnPrsnEmail(String respnPrsnEmail) {
		this.respnPrsnEmail = respnPrsnEmail;
	}

	/**
	 * @return the respnPrsnMblphn
	 */
	public String getRespnPrsnMblphn() {
		return respnPrsnMblphn;
	}

	/**
	 * @param respnPrsnMblphn the respnPrsnMblphn to set
	 */
	public void setRespnPrsnMblphn(String respnPrsnMblphn) {
		this.respnPrsnMblphn = respnPrsnMblphn;
	}

	/**
	 * @return the respnPrsnNum
	 */
	public String getRespnPrsnNum() {
		return respnPrsnNum;
	}

	/**
	 * @param respnPrsnNum the respnPrsnNum to set
	 */
	public void setRespnPrsnNum(String respnPrsnNum) {
		this.respnPrsnNum = respnPrsnNum;
	}

	
	public String getRespnPrsnNm() {
        return respnPrsnNm;
    }




    public void setRespnPrsnNm(String respnPrsnNm) {
        this.respnPrsnNm = respnPrsnNm;
    }




    /**
	 * @return the respnPrsnId
	 */
	public String getRespnPrsnId() {
		return respnPrsnId;
	}

	/**
	 * @param respnPrsnId the respnPrsnId to set
	 */
	public void setRespnPrsnId(String respnPrsnId) {
		this.respnPrsnId = respnPrsnId;
	}

	
	/**
	 * @return the addr2
	 */
	public String getAddr2() {
		return addr2;
	}

	/**
	 * @param addr2 the addr2 to set
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	/**
	 * @return the addr1
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * @param addr1 the addr1 to set
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
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

	/**
	 * @return the rprsenRrnum
	 */
	public String getRprsenRrnum() {
		return rprsenRrnum;
	}

	/**
	 * @param rprsenRrnum the rprsenRrnum to set
	 */
	public void setRprsenRrnum(String rprsenRrnum) {
		this.rprsenRrnum = rprsenRrnum;
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
	 * @return the bizCon
	 */
	public String getBizCon() {
		return bizCon;
	}

	/**
	 * @param bizCon the bizCon to set
	 */
	public void setBizCon(String bizCon) {
		this.bizCon = bizCon;
	}

	/**
	 * @return the bizCat
	 */
	public String getBizCat() {
		return bizCat;
	}

	/**
	 * @param bizCat the bizCat to set
	 */
	public void setBizCat(String bizCat) {
		this.bizCat = bizCat;
	}

	/**
	 * @return the corpRegNum
	 */
	public String getCorpRegNum() {
		return corpRegNum;
	}

	/**
	 * @param corpRegNum the corpRegNum to set
	 */
	public void setCorpRegNum(String corpRegNum) {
		this.corpRegNum = corpRegNum;
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

	/**
	 * @return the bizTypeCd
	 */
	public String getBizTypeCd() {
		return bizTypeCd;
	}

	/**
	 * @param bizTypeCd the bizTypeCd to set
	 */
	public void setBizTypeCd(String bizTypeCd) {
		this.bizTypeCd = bizTypeCd;
	}

	/**
	 * @return the hndstInvtrYn
	 */
	public String getHndstInvtrYn() {
		return hndstInvtrYn;
	}

	/**
	 * @param hndstInvtrYn the hndstInvtrYn to set
	 */
	public void setHndstInvtrYn(String hndstInvtrYn) {
		this.hndstInvtrYn = hndstInvtrYn;
	}

	/**
	 * @return the cmsnPrvdYn
	 */
	public String getCmsnPrvdYn() {
		return cmsnPrvdYn;
	}

	/**
	 * @param cmsnPrvdYn the cmsnPrvdYn to set
	 */
	public void setCmsnPrvdYn(String cmsnPrvdYn) {
		this.cmsnPrvdYn = cmsnPrvdYn;
	}

	/**
	 * @return the taxbillYn
	 */
	public String getTaxbillYn() {
		return taxbillYn;
	}

	/**
	 * @param taxbillYn the taxbillYn to set
	 */
	public void setTaxbillYn(String taxbillYn) {
		this.taxbillYn = taxbillYn;
	}

	/**
	 * @return the openCrdtLoanStrtDt
	 */
	public String getOpenCrdtLoanStrtDt() {
		return openCrdtLoanStrtDt;
	}

	/**
	 * @param openCrdtLoanStrtDt the openCrdtLoanStrtDt to set
	 */
	public void setOpenCrdtLoanStrtDt(String openCrdtLoanStrtDt) {
		this.openCrdtLoanStrtDt = openCrdtLoanStrtDt;
	}

	/**
	 * @return the openCrdtLoanYn
	 */
	public String getOpenCrdtLoanYn() {
		return openCrdtLoanYn;
	}

	/**
	 * @param openCrdtLoanYn the openCrdtLoanYn to set
	 */
	public void setOpenCrdtLoanYn(String openCrdtLoanYn) {
		this.openCrdtLoanYn = openCrdtLoanYn;
	}

	/**
	 * @return the applCmpltDt
	 */
	public String getApplCmpltDt() {
		return applCmpltDt;
	}

	/**
	 * @param applCmpltDt the applCmpltDt to set
	 */
	public void setApplCmpltDt(String applCmpltDt) {
		this.applCmpltDt = applCmpltDt;
	}

	/**
	 * @return the applStrtDt
	 */
	public String getApplStrtDt() {
		return applStrtDt;
	}

	/**
	 * @param applStrtDt the applStrtDt to set
	 */
	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}

	/**
	 * @return the orgnLvlCd
	 */
	public String getOrgnLvlCd() {
		return orgnLvlCd;
	}

	/**
	 * @param orgnLvlCd the orgnLvlCd to set
	 */
	public void setOrgnLvlCd(String orgnLvlCd) {
		this.orgnLvlCd = orgnLvlCd;
	}

	/**
	 * @return the typeCd
	 */
	public String getTypeCd() {
		return typeCd;
	}

	/**
	 * @param typeCd the typeCd to set
	 */
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	/**
	 * @return the orgnNm
	 */
	public String getOrgnNm() {
		return orgnNm;
	}

	/**
	 * @param orgnNm the orgnNm to set
	 */
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}

	/**
	 * @return the orgnId
	 */
	public String getOrgnId() {
		return orgnId;
	}

	/**
	 * @param orgnId the orgnId to set
	 */
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

    public String getTypeNm() {
        return typeNm;
    }

    public void setTypeNm(String typeNm) {
        this.typeNm = typeNm;
    }

    public String getOrgnLvlNm() {
        return orgnLvlNm;
    }

    public void setOrgnLvlNm(String orgnLvlNm) {
        this.orgnLvlNm = orgnLvlNm;
    }

    public String getBizTypeNm() {
        return bizTypeNm;
    }

    public void setBizTypeNm(String bizTypeNm) {
        this.bizTypeNm = bizTypeNm;
    }

    public String getOrgnStatNm() {
        return orgnStatNm;
    }

    public void setOrgnStatNm(String orgnStatNm) {
        this.orgnStatNm = orgnStatNm;
    }

    public String getTypeDtlNm() {
        return typeDtlNm;
    }

    public void setTypeDtlNm(String typeDtlNm) {
        this.typeDtlNm = typeDtlNm;
    }




    public String getTypeDtlCdNm1() {
        return typeDtlCdNm1;
    }




    public void setTypeDtlCdNm1(String typeDtlCdNm1) {
        this.typeDtlCdNm1 = typeDtlCdNm1;
    }




    public String getTypeDtlCdNm2() {
        return typeDtlCdNm2;
    }




    public void setTypeDtlCdNm2(String typeDtlCdNm2) {
        this.typeDtlCdNm2 = typeDtlCdNm2;
    }




    public String getTypeDtlCdNm3() {
        return typeDtlCdNm3;
    }




    public void setTypeDtlCdNm3(String typeDtlCdNm3) {
        this.typeDtlCdNm3 = typeDtlCdNm3;
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




    public String getCorpRegNum1() {
        return corpRegNum1;
    }




    public void setCorpRegNum1(String corpRegNum1) {
        this.corpRegNum1 = corpRegNum1;
    }




    public String getCorpRegNum2() {
        return corpRegNum2;
    }




    public void setCorpRegNum2(String corpRegNum2) {
        this.corpRegNum2 = corpRegNum2;
    }




    public String getRprsenRrnum1() {
        return rprsenRrnum1;
    }




    public void setRprsenRrnum1(String rprsenRrnum1) {
        this.rprsenRrnum1 = rprsenRrnum1;
    }




    public String getRprsenRrnum2() {
        return rprsenRrnum2;
    }




    public void setRprsenRrnum2(String rprsenRrnum2) {
        this.rprsenRrnum2 = rprsenRrnum2;
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




    public String getUserOrgnLvlCd() {
        return userOrgnLvlCd;
    }




    public void setUserOrgnLvlCd(String userOrgnLvlCd) {
        this.userOrgnLvlCd = userOrgnLvlCd;
    }




    public String getId() {
        return id;
    }




    public void setId(String id) {
        this.id = id;
    }




    public String getXmlkidsBo() {
        return xmlkidsBo;
    }




    public void setXmlkidsBo(String xmlkidsBo) {
        this.xmlkidsBo = xmlkidsBo;
    }




    public boolean isXmlkids() {
        return xmlkids;
    }




    public void setXmlkids(boolean xmlkids) {
        this.xmlkids = xmlkids;
    }




	public String getVirAccountId() {
		return virAccountId;
	}




	public void setVirAccountId(String virAccountId) {
		this.virAccountId = virAccountId;
	}




	public String getVirBankNm() {
		return virBankNm;
	}




	public void setVirBankNm(String virBankNm) {
		this.virBankNm = virBankNm;
	}



    
    
}
