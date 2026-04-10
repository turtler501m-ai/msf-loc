package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsVacVo
 * @Description : 가상계좌 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsVacVo")
public class PpsVacVo  extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_VAC";

	/**
	 * 가상계좌번호:varchar2(20) <Primary Key>
	 */
	private String vacId = "";
	
	/**
	 * 은행코드 
	 */
	private String vacBankCode = "";
	private String vacBankCd = "";

	/**
	 * 은행명:varchar2(60)
	 */
	private String vacBankName = "";
	private String vacNm = "";

	/**
	 * 이용자구분(A:AGENT, U:USER, N:미사용):varchar2(2)
	 */
	private String userType;
	
	/**
	 * 이용자 구분명 
	 */
	private String userTypeNm;

	/**
	 * 등록관리자:varchar2(20)
	 */
	private String adminId;
	
	/**
	 * 등록관리자명
	 */
	private String adminNm;

	/**
	 * 서비스계정:number(9)
	 */
	private String contractNum;

	/**
	 * 대리점아이디:varchar2(20)
	 */
	private String agentId;
	
	/**
	 * 대리점명
	 */
	private String agentNm;

	/**
	 * 부여상태(N:미사용, Y:사용중), 회수중 없음, 회수일자가 30일 지나면 자동 부여 가능, KGB수정  :char(1)
	 */
	private String status;
	
	/**
	 * 부여상태명
	 */
	private String statusNm;

	/**
	 * 부여일자:date(0)
	 */
	private String openDate;

	/**
	 * 회수일자:date(0)
	 */
	private String closeDate;

	/**
	 * 최종입금일자:date(0)
	 */
	private String lastPaymentDate;

	/**
	 * 수납횟수:number(10)
	 */
	private String payCount;


	/**
	 * 부여횟수:number(10)
	 */
	private String useCount;
	
	/**
	 * 회수버튼
	 */
	private String resetBtn;
	
	
	/**
	 * @return the vacBankCd
	 */
	public String getVacBankCd() {
		return vacBankCd;
	}


	/**
	 * @param vacBankCd the vacBankCd to set
	 */
	public void setVacBankCd(String vacBankCd) {
		this.vacBankCd = vacBankCd;
	}


	/**
	 * @return the vacNm
	 */
	public String getVacNm() {
		return vacNm;
	}


	/**
	 * @param vacNm the vacNm to set
	 */
	public void setVacNm(String vacNm) {
		this.vacNm = vacNm;
	}


	/**
	 * @return the vacId
	 */
	public String getVacId() {
		return vacId;
	}


	/**
	 * @param vacId the vacId to set
	 */
	public void setVacId(String vacId) {
		this.vacId = vacId;
	}


	/**
	 * @return the vacBankCode
	 */
	public String getVacBankCode() {
		return vacBankCode;
	}


	/**
	 * @param vacBankCode the vacBankCode to set
	 */
	public void setVacBankCode(String vacBankCode) {
		this.vacBankCode = vacBankCode;
	}


	/**
	 * @return the vacBankName
	 */
	public String getVacBankName() {
		return vacBankName;
	}


	/**
	 * @param vacBankName the vacBankName to set
	 */
	public void setVacBankName(String vacBankName) {
		this.vacBankName = vacBankName;
	}


	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}


	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}


	/**
	 * @return the userTypeNm
	 */
	public String getUserTypeNm() {
		return userTypeNm;
	}


	/**
	 * @param userTypeNm the userTypeNm to set
	 */
	public void setUserTypeNm(String userTypeNm) {
		this.userTypeNm = userTypeNm;
	}


	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}


	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}


	/**
	 * @return the adminNm
	 */
	public String getAdminNm() {
		return adminNm;
	}


	/**
	 * @param adminNm the adminNm to set
	 */
	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}


	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}


	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}


	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}


	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}


	/**
	 * @return the agentNm
	 */
	public String getAgentNm() {
		return agentNm;
	}


	/**
	 * @param agentNm the agentNm to set
	 */
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the statusNm
	 */
	public String getStatusNm() {
		return statusNm;
	}


	/**
	 * @param statusNm the statusNm to set
	 */
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}


	/**
	 * @return the openDate
	 */
	public String getOpenDate() {
		return openDate;
	}


	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}


	/**
	 * @return the closeDate
	 */
	public String getCloseDate() {
		return closeDate;
	}


	/**
	 * @param closeDate the closeDate to set
	 */
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}


	/**
	 * @return the lastPaymentDate
	 */
	public String getLastPaymentDate() {
		return lastPaymentDate;
	}


	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(String lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}


	/**
	 * @return the payCount
	 */
	public String getPayCount() {
		return payCount;
	}


	/**
	 * @param payCount the payCount to set
	 */
	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}


	/**
	 * @return the useCount
	 */
	public String getUseCount() {
		return useCount;
	}


	/**
	 * @param useCount the useCount to set
	 */
	public void setUseCount(String useCount) {
		this.useCount = useCount;
	}
	
	/**
	 * @return the resetBtn
	 */
	public String getResetBtn() {
		return resetBtn;
	}

	/**
	 * @param useCount the resetBtn to set
	 */
	public void setResetBtn(String resetBtn) {
		this.resetBtn = resetBtn;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
