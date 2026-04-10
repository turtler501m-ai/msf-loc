package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpskKtJuoCusVo
 * @Description : KT현행화고객정보 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppskKtJuoCusVo")
public class PpsKtJuoCusVo  extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	public static final String TABLE = "PPS_KT_JUO_CUS";

	/**
	 * 고객 계정 번호:number(9) <Primary Key>
	 */
	private int customerId;

	/**
	 * 고객유형 : B 법인사업자, G 공공기관, I 개인고객, O 개인사업자:char(1)
	 */
	private String customerType;

	/**
	 * 고객유형명
	 */
	private String customerTypeNm;
	
	/**
	 * 주민번호-암호화:varchar2(50)
	 */
	private String customerSsn;

	/**
	 * 법인번호-암호화:varchar2(50)
	 */
	private String drivrLicnsNo;

	/**
	 * 개인사업자번호-암호화:varchar2(50)
	 */
	private String taxId;

	/**
	 * 우편번호:varchar2(6)
	 */
	private String customerAdrZip;

	/**
	 * 주소(동까지):varchar2(100)
	 */
	private String customerAdrPrimaryLn;

	/**
	 * 주소(번지이후):varchar2(100)
	 */
	private String customerAdrSecondaryLn;

	/**
	 * 이름:varchar2(60)
	 */
	private String customerLinkName;

	/**
	 * 직업 코드: 01 자영업, 02 회사원, 03 공무원, 04 투자기관직원, 05 주부, 06 학생, 07 농,수산업등, 08 외교관(공관원), 09 미군, 10 교사, 99 기타:varchar2(2)
	 */
	private String occupationCode;

	/**
	 * 고객실별번호구분코드:varchar2(3)
	 */
	private String custIdntNoIndCd;
	
	/**
	 * 고객실별번호구분코드명:
	 */
	private String custIdntNoIndCdNm;

	/**
	 * EVNT_CD:varchar2(3)
	 */
	private String evntCd;

	/**
	 * EVNT_TRTM_CD:number(9)
	 */
	private int evntTrtmCd;

	/**
	 * 마스킹 정보
	 */
	private String customerSsnMsk;
	private String driverLicnsNoMsk;
	private String customerLinkNameMsk;
	
	public String getCustomerSsnMsk() {
		return customerSsnMsk;
	}

	public void setCustomerSsnMsk(String customerSsnMsk) {
		this.customerSsnMsk = customerSsnMsk;
	}

	public String getDriverLicnsNoMsk() {
		return driverLicnsNoMsk;
	}

	public void setDriverLicnsNoMsk(String driverLicnsNoMsk) {
		this.driverLicnsNoMsk = driverLicnsNoMsk;
	}

	public String getCustomerLinkNameMsk() {
		return customerLinkNameMsk;
	}

	public void setCustomerLinkNameMsk(String customerLinkNameMsk) {
		this.customerLinkNameMsk = customerLinkNameMsk;
	}


	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}


	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}


	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	/**
	 * @return the customerTypeNm
	 */
	public String getCustomerTypeNm() {
		return customerTypeNm;
	}


	/**
	 * @param customerTypeNm the customerTypeNm to set
	 */
	public void setCustomerTypeNm(String customerTypeNm) {
		this.customerTypeNm = customerTypeNm;
	}


	/**
	 * @return the customerSsn
	 */
	public String getCustomerSsn() {
		return customerSsn;
	}


	/**
	 * @param customerSsn the customerSsn to set
	 */
	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}


	/**
	 * @return the drivrLicnsNo
	 */
	public String getDrivrLicnsNo() {
		return drivrLicnsNo;
	}


	/**
	 * @param drivrLicnsNo the drivrLicnsNo to set
	 */
	public void setDrivrLicnsNo(String drivrLicnsNo) {
		this.drivrLicnsNo = drivrLicnsNo;
	}


	/**
	 * @return the taxId
	 */
	public String getTaxId() {
		return taxId;
	}


	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}


	/**
	 * @return the customerAdrZip
	 */
	public String getCustomerAdrZip() {
		return customerAdrZip;
	}


	/**
	 * @param customerAdrZip the customerAdrZip to set
	 */
	public void setCustomerAdrZip(String customerAdrZip) {
		this.customerAdrZip = customerAdrZip;
	}


	/**
	 * @return the customerAdrPrimaryLn
	 */
	public String getCustomerAdrPrimaryLn() {
		return customerAdrPrimaryLn;
	}


	/**
	 * @param customerAdrPrimaryLn the customerAdrPrimaryLn to set
	 */
	public void setCustomerAdrPrimaryLn(String customerAdrPrimaryLn) {
		this.customerAdrPrimaryLn = customerAdrPrimaryLn;
	}


	/**
	 * @return the customerAdrSecondaryLn
	 */
	public String getCustomerAdrSecondaryLn() {
		return customerAdrSecondaryLn;
	}


	/**
	 * @param customerAdrSecondaryLn the customerAdrSecondaryLn to set
	 */
	public void setCustomerAdrSecondaryLn(String customerAdrSecondaryLn) {
		this.customerAdrSecondaryLn = customerAdrSecondaryLn;
	}


	/**
	 * @return the customerLinkName
	 */
	public String getCustomerLinkName() {
		return customerLinkName;
	}


	/**
	 * @param customerLinkName the customerLinkName to set
	 */
	public void setCustomerLinkName(String customerLinkName) {
		this.customerLinkName = customerLinkName;
	}


	/**
	 * @return the occupationCode
	 */
	public String getOccupationCode() {
		return occupationCode;
	}


	/**
	 * @param occupationCode the occupationCode to set
	 */
	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}


	/**
	 * @return the custIdntNoIndCd
	 */
	public String getCustIdntNoIndCd() {
		return custIdntNoIndCd;
	}
	
	
	/**
	 * @param custIdntNoIndCd the custIdntNoIndCd to set
	 */
	public void setCustIdntNoIndCd(String custIdntNoIndCd) {
		this.custIdntNoIndCd = custIdntNoIndCd;
	}
	
	
	/**
	 * @return the custIdntNoIndCdNm
	 */
	public String getCustIdntNoIndCdNm() {
		return custIdntNoIndCdNm;
	}
	
	
	/**
	 * @param custIdntNoIndCdNm the custIdntNoIndCdNm to set
	 */
	public void setCustIdntNoIndCdNm(String custIdntNoIndCdNm) {
		this.custIdntNoIndCdNm = custIdntNoIndCdNm;
	}


	/**
	 * @return the evntCd
	 */
	public String getEvntCd() {
		return evntCd;
	}


	/**
	 * @param evntCd the evntCd to set
	 */
	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}


	/**
	 * @return the evntTrtmCd
	 */
	public int getEvntTrtmCd() {
		return evntTrtmCd;
	}


	/**
	 * @param evntTrtmCd the evntTrtmCd to set
	 */
	public void setEvntTrtmCd(int evntTrtmCd) {
		this.evntTrtmCd = evntTrtmCd;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
