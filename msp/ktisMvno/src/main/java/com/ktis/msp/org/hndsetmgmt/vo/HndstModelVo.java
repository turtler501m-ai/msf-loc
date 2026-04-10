package com.ktis.msp.org.hndsetmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : HndstModelVo
 * @Description : 제품 관리 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="hndstModelVo")
public class HndstModelVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 5308570473897596773L;

	
    private String prdtId; /** 제품ID */
    private String prdtNm; /** 제품명 */
    private String formVrifyNum; /** 형식검사번호 */
    private String formVrifyDt; /** 형식검사일자 */
    private String prdtDt; /** 제품단종일자 */
    private String optCpcty; /** 출력용량 */
    private String wapSptYn; /** WAP지원여부 */
    private String regId; /** 등록자ID */
    private String regDttm; /** 등록일시 */
    private String rvisnId; /** 수정자ID */
    private String mnfctId; /** 제조사ID */
    private String rvisnDttm; /** 수정일시 */
    private String prdtStcnCd; /** 제품구분코드 */
    private String simId; /** SIM ID */
    private String mycpnyPrvdSctnCd; /** 자사지급구분코드 */
    private String prdtPrposCd; /** 제품용도코드 */
    private String inDt; /** 입고일자 */
    private String ordCntrDt; /** 발주계약일자 */
    private String formVrifyYn; /** 형식검사여부 */
    private String prdtColrCd; /** 제품색상코드 */
    private String oldYn; /** 중고여부 */
    private String prdtTypeCd; /** 제품유형코드 */
    private String prdtStcnCdNm;
    private String mycpnyPrvdSctnCdNm;
    private String prdtPrposCdNm;
    private String prdtColrCdNm;
    private String prdtTypeCdNm;
    private String mnfctNm;
    private String oldYnNm;
    private String prdtLnchDt; /** 제품 출시일 */
    private String inUnitPric; /** 입고단가 */
    private String outUnitPric; /** 출고단가 */
    private String ktPrdtId; /**KT 모델ID */
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	
	

	/**
	 * @return the ktPrdtId
	 */
	public String getKtPrdtId() {
		return ktPrdtId;
	}




	/**
	 * @param ktPrdtId the ktPrdtId to set
	 */
	public void setKtPrdtId(String ktPrdtId) {
		this.ktPrdtId = ktPrdtId;
	}




	/**
	 * @return the prdtLnchDt
	 */
	public String getPrdtLnchDt() {
		return prdtLnchDt;
	}




	/**
	 * @param prdtLnchDt the prdtLnchDt to set
	 */
	public void setPrdtLnchDt(String prdtLnchDt) {
		this.prdtLnchDt = prdtLnchDt;
	}




	/**
	 * @return the prdtId
	 */
	public String getPrdtId() {
		return prdtId;
	}


	/**
	 * @param prdtId the prdtId to set
	 */
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}


	/**
	 * @return the prdtNm
	 */
	public String getPrdtNm() {
		return prdtNm;
	}


	/**
	 * @param prdtNm the prdtNm to set
	 */
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}


	/**
	 * @return the formVrifyNum
	 */
	public String getFormVrifyNum() {
		return formVrifyNum;
	}


	/**
	 * @param formVrifyNum the formVrifyNum to set
	 */
	public void setFormVrifyNum(String formVrifyNum) {
		this.formVrifyNum = formVrifyNum;
	}


	/**
	 * @return the formVrifyDt
	 */
	public String getFormVrifyDt() {
		return formVrifyDt;
	}


	/**
	 * @param formVrifyDt the formVrifyDt to set
	 */
	public void setFormVrifyDt(String formVrifyDt) {
		this.formVrifyDt = formVrifyDt;
	}


	/**
	 * @return the prdtDt
	 */
	public String getPrdtDt() {
		return prdtDt;
	}


	/**
	 * @param prdtDt the prdtDt to set
	 */
	public void setPrdtDt(String prdtDt) {
		this.prdtDt = prdtDt;
	}


	/**
	 * @return the optCpcty
	 */
	public String getOptCpcty() {
		return optCpcty;
	}


	/**
	 * @param optCpcty the optCpcty to set
	 */
	public void setOptCpcty(String optCpcty) {
		this.optCpcty = optCpcty;
	}


	/**
	 * @return the wapSptYn
	 */
	public String getWapSptYn() {
		return wapSptYn;
	}


	/**
	 * @param wapSptYn the wapSptYn to set
	 */
	public void setWapSptYn(String wapSptYn) {
		this.wapSptYn = wapSptYn;
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


	


	public String getRegDttm() {
        return regDttm;
    }


    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
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


    /**
	 * @return the prdtStcnCd
	 */
	public String getPrdtStcnCd() {
		return prdtStcnCd;
	}


	/**
	 * @param prdtStcnCd the prdtStcnCd to set
	 */
	public void setPrdtStcnCd(String prdtStcnCd) {
		this.prdtStcnCd = prdtStcnCd;
	}


	/**
	 * @return the simId
	 */
	public String getSimId() {
		return simId;
	}


	/**
	 * @param simId the simId to set
	 */
	public void setSimId(String simId) {
		this.simId = simId;
	}


	/**
	 * @return the mycpnyPrvdSctnCd
	 */
	public String getMycpnyPrvdSctnCd() {
		return mycpnyPrvdSctnCd;
	}


	/**
	 * @param mycpnyPrvdSctnCd the mycpnyPrvdSctnCd to set
	 */
	public void setMycpnyPrvdSctnCd(String mycpnyPrvdSctnCd) {
		this.mycpnyPrvdSctnCd = mycpnyPrvdSctnCd;
	}


	/**
	 * @return the prdtPrposCd
	 */
	public String getPrdtPrposCd() {
		return prdtPrposCd;
	}


	/**
	 * @param prdtPrposCd the prdtPrposCd to set
	 */
	public void setPrdtPrposCd(String prdtPrposCd) {
		this.prdtPrposCd = prdtPrposCd;
	}


	/**
	 * @return the inDt
	 */
	public String getInDt() {
		return inDt;
	}


	/**
	 * @param inDt the inDt to set
	 */
	public void setInDt(String inDt) {
		this.inDt = inDt;
	}


	/**
	 * @return the ordCntrDt
	 */
	public String getOrdCntrDt() {
		return ordCntrDt;
	}


	/**
	 * @param ordCntrDt the ordCntrDt to set
	 */
	public void setOrdCntrDt(String ordCntrDt) {
		this.ordCntrDt = ordCntrDt;
	}


	/**
	 * @return the formVrifyYn
	 */
	public String getFormVrifyYn() {
		return formVrifyYn;
	}


	/**
	 * @param formVrifyYn the formVrifyYn to set
	 */
	public void setFormVrifyYn(String formVrifyYn) {
		this.formVrifyYn = formVrifyYn;
	}


	/**
	 * @return the prdtColrCd
	 */
	public String getPrdtColrCd() {
		return prdtColrCd;
	}


	/**
	 * @param prdtColrCd the prdtColrCd to set
	 */
	public void setPrdtColrCd(String prdtColrCd) {
		this.prdtColrCd = prdtColrCd;
	}


	/**
	 * @return the oldYn
	 */
	public String getOldYn() {
		return oldYn;
	}


	/**
	 * @param oldYn the oldYn to set
	 */
	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}


	/**
	 * @return the prdtTypeCd
	 */
	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}


	/**
	 * @param prdtTypeCd the prdtTypeCd to set
	 */
	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}


    public String getPrdtStcnCdNm() {
        return prdtStcnCdNm;
    }


    public void setPrdtStcnCdNm(String prdtStcnCdNm) {
        this.prdtStcnCdNm = prdtStcnCdNm;
    }


    public String getMycpnyPrvdSctnCdNm() {
        return mycpnyPrvdSctnCdNm;
    }


    public void setMycpnyPrvdSctnCdNm(String mycpnyPrvdSctnCdNm) {
        this.mycpnyPrvdSctnCdNm = mycpnyPrvdSctnCdNm;
    }


    public String getPrdtPrposCdNm() {
        return prdtPrposCdNm;
    }


    public void setPrdtPrposCdNm(String prdtPrposCdNm) {
        this.prdtPrposCdNm = prdtPrposCdNm;
    }


    public String getPrdtColrCdNm() {
        return prdtColrCdNm;
    }


    public void setPrdtColrCdNm(String prdtColrCdNm) {
        this.prdtColrCdNm = prdtColrCdNm;
    }


    public String getPrdtTypeCdNm() {
        return prdtTypeCdNm;
    }


    public void setPrdtTypeCdNm(String prdtTypeCdNm) {
        this.prdtTypeCdNm = prdtTypeCdNm;
    }


    public String getMnfctNm() {
        return mnfctNm;
    }


    public void setMnfctNm(String mnfctNm) {
        this.mnfctNm = mnfctNm;
    }


    public String getOldYnNm() {
        return oldYnNm;
    }


    public void setOldYnNm(String oldYnNm) {
        this.oldYnNm = oldYnNm;
    }




	public String getInUnitPric() {
		return inUnitPric;
	}




	public void setInUnitPric(String inUnitPric) {
		this.inUnitPric = inUnitPric;
	}




	public String getOutUnitPric() {
		return outUnitPric;
	}




	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}
    
    
	
}
