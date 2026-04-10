package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsKtJuoBanVo
 * @Description : KT현행화 청구계정정보 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsKtJuoBanVo")
public class PpsKtJuoBanVo  extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	

	public static final String TABLE = "PPS_KT_JUO_BAN";

	/**
	 * 청구 계정 번호:number(9) <Primary Key>
	 */
	private String ban;
	
	private String customerBan;

	/**
	 * 고객 번호:number(9)
	 */
	private int customerId;
	


	/**
	 * 미납상태:char(1)
	 */
	private String colDelinqStatus;
	
	private String colDelinqStatusNm;

	/**
	 * 판매회사코드:varchar2(3)
	 */
	private String marketGubun;

	private String marketGubunNm;
	
	/**
	 * 납부방법 C 신용카드, D 은행계좌 자동이체, K QOOK집전화합산, M QOOK인터넷합산, R 지로, S 삼성프리텔카드, W 와이브로합산, Z 기타:char(1)
	 */
	private String blBillingMethod;
	
	/**
	 * 납부방법명
	 */
	private String blBillingMethodNm;

	
	/**
	 * BAN 구분코드 R 일반, I 지능망 선불, G 캐시선불, E 임대사업자, N 에버그린모바일, S 삼성네트웍스, T 현대자동차, B 에넥스, P KT 파워텔:char(1)
	 */
	private String blSpclBanCode;

	/**
	 * BAN구분코드명
	 */
	private String blSpclBanCodeNm;
	
	/**
	 * 납부 계좌 번호 예금주 주민번호:varchar2(50)
	 */
	private String bankAcctHolderId;

	/**
	 * 우편번호:char(6)
	 */
	private String banAdrZip;

	/**
	 * 주소(동까지):varchar2(100)
	 */
	private String banAdrPrimaryLn;

	/**
	 * 주소(번지이후):varchar2(100)
	 */
	private String banAdrSecondaryLn;

	/**
	 * 이름:varchar2(60)
	 */
	private String banLinkName;

	/**
	 * EVNT_CD:varchar2(3)
	 */
	private String evntCd;
	
	private String eventNm;

	/**
	 * EVNT_TRTM_CD:number(9)
	 */
	private int evntTrtmCd;


	/**
	 * EVNT_TRTM_DT:date(0)
	 */
	private String evntTrtmDt;
	

	/**
	 * 마스킹 정보
	 */
	private String banLinkNameMsk;
	private String banAdrSecondaryLnMsk;
	private String bankAcctHolderIdMsk;
		
	public String getBanLinkNameMsk() {
		return banLinkNameMsk;
	}

	public void setBanLinkNameMsk(String banLinkNameMsk) {
		this.banLinkNameMsk = banLinkNameMsk;
	}

	public String getBanAdrSecondaryLnMsk() {
		return banAdrSecondaryLnMsk;
	}

	public void setBanAdrSecondaryLnMsk(String banAdrSecondaryLnMsk) {
		this.banAdrSecondaryLnMsk = banAdrSecondaryLnMsk;
	}

	public String getBankAcctHolderIdMsk() {
		return bankAcctHolderIdMsk;
	}

	public void setBankAcctHolderIdMsk(String bankAcctHolderIdMsk) {
		this.bankAcctHolderIdMsk = bankAcctHolderIdMsk;
	}



	/**
	 * @return the customerBan
	 */
	public String getCustomerBan() {
		return customerBan;
	}




	/**
	 * @param customerBan the customerBan to set
	 */
	public void setCustomerBan(String customerBan) {
		this.customerBan = customerBan;
	}




	/**
	 * @return the marketGubunNm
	 */
	public String getMarketGubunNm() {
		return marketGubunNm;
	}




	/**
	 * @param marketGubunNm the marketGubunNm to set
	 */
	public void setMarketGubunNm(String marketGubunNm) {
		this.marketGubunNm = marketGubunNm;
	}




	/**
	 * @return the eventNm
	 */
	public String getEventNm() {
		return eventNm;
	}




	/**
	 * @param eventNm the eventNm to set
	 */
	public void setEventNm(String eventNm) {
		this.eventNm = eventNm;
	}





	
	
	

	/**
	 * @return the ban
	 */
	public String getBan() {
		return ban;
	}




	/**
	 * @param ban the ban to set
	 */
	public void setBan(String ban) {
		this.ban = ban;
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
	 * @return the colDelinqStatus
	 */
	public String getColDelinqStatus() {
		return colDelinqStatus;
	}




	/**
	 * @param colDelinqStatus the colDelinqStatus to set
	 */
	public void setColDelinqStatus(String colDelinqStatus) {
		this.colDelinqStatus = colDelinqStatus;
	}




	/**
	 * @return the colDelinqStatusNm
	 */
	public String getColDelinqStatusNm() {
		return colDelinqStatusNm;
	}




	/**
	 * @param colDelinqStatusNm the colDelinqStatusNm to set
	 */
	public void setColDelinqStatusNm(String colDelinqStatusNm) {
		this.colDelinqStatusNm = colDelinqStatusNm;
	}




	/**
	 * @return the marketGubun
	 */
	public String getMarketGubun() {
		return marketGubun;
	}




	/**
	 * @param marketGubun the marketGubun to set
	 */
	public void setMarketGubun(String marketGubun) {
		this.marketGubun = marketGubun;
	}




	/**
	 * @return the blBillingMethod
	 */
	public String getBlBillingMethod() {
		return blBillingMethod;
	}




	/**
	 * @param blBillingMethod the blBillingMethod to set
	 */
	public void setBlBillingMethod(String blBillingMethod) {
		this.blBillingMethod = blBillingMethod;
	}




	/**
	 * @return the blBillingMethodNm
	 */
	public String getBlBillingMethodNm() {
		return blBillingMethodNm;
	}




	/**
	 * @param blBillingMethodNm the blBillingMethodNm to set
	 */
	public void setBlBillingMethodNm(String blBillingMethodNm) {
		this.blBillingMethodNm = blBillingMethodNm;
	}




	/**
	 * @return the blSpclBanCode
	 */
	public String getBlSpclBanCode() {
		return blSpclBanCode;
	}




	/**
	 * @param blSpclBanCode the blSpclBanCode to set
	 */
	public void setBlSpclBanCode(String blSpclBanCode) {
		this.blSpclBanCode = blSpclBanCode;
	}




	/**
	 * @return the blSpclBanCodeNm
	 */
	public String getBlSpclBanCodeNm() {
		return blSpclBanCodeNm;
	}




	/**
	 * @param blSpclBanCodeNm the blSpclBanCodeNm to set
	 */
	public void setBlSpclBanCodeNm(String blSpclBanCodeNm) {
		this.blSpclBanCodeNm = blSpclBanCodeNm;
	}




	/**
	 * @return the bankAcctHolderId
	 */
	public String getBankAcctHolderId() {
		return bankAcctHolderId;
	}




	/**
	 * @param bankAcctHolderId the bankAcctHolderId to set
	 */
	public void setBankAcctHolderId(String bankAcctHolderId) {
		this.bankAcctHolderId = bankAcctHolderId;
	}




	/**
	 * @return the banAdrZip
	 */
	public String getBanAdrZip() {
		return banAdrZip;
	}




	/**
	 * @param banAdrZip the banAdrZip to set
	 */
	public void setBanAdrZip(String banAdrZip) {
		this.banAdrZip = banAdrZip;
	}




	/**
	 * @return the banAdrPrimaryLn
	 */
	public String getBanAdrPrimaryLn() {
		return banAdrPrimaryLn;
	}




	/**
	 * @param banAdrPrimaryLn the banAdrPrimaryLn to set
	 */
	public void setBanAdrPrimaryLn(String banAdrPrimaryLn) {
		this.banAdrPrimaryLn = banAdrPrimaryLn;
	}




	/**
	 * @return the banAdrSecondaryLn
	 */
	public String getBanAdrSecondaryLn() {
		return banAdrSecondaryLn;
	}




	/**
	 * @param banAdrSecondaryLn the banAdrSecondaryLn to set
	 */
	public void setBanAdrSecondaryLn(String banAdrSecondaryLn) {
		this.banAdrSecondaryLn = banAdrSecondaryLn;
	}




	/**
	 * @return the banLinkName
	 */
	public String getBanLinkName() {
		return banLinkName;
	}




	/**
	 * @param banLinkName the banLinkName to set
	 */
	public void setBanLinkName(String banLinkName) {
		this.banLinkName = banLinkName;
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




	/**
	 * @return the evntTrtmDt
	 */
	public String getEvntTrtmDt() {
		return evntTrtmDt;
	}




	/**
	 * @param evntTrtmDt the evntTrtmDt to set
	 */
	public void setEvntTrtmDt(String evntTrtmDt) {
		this.evntTrtmDt = evntTrtmDt;
	}




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
