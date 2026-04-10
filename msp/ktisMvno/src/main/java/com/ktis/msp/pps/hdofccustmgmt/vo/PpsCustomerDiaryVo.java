package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsCustomerDiaryVo
 * @Description : 고객상담 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsCustomerDiaryVo")
public class PpsCustomerDiaryVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_CUSTOMER_DIARY";

	/**
	 * 고객상담고유번호:number(20)
	 */
	private int diaryId;

	/**
	 * 고객번호:number(9)
	 */
	private int contractNum;

	/**
	 * 상담고객명:varchar2(100)
	 */
	private String reqUserName;

	/**
	 * 상담고객구분:varchar2(100)
	 */
	private String reqUserGubun;

	/**
	 * 상담구분:varchar2(100)
	 */
	private String reqType;

	/**
	 * 상담제목:varchar2(200)
	 */
	private String reqTitle;

	/**
	 * 상담내용:varchar2(2000)
	 */
	private String reqBody;

	/**
	 * 답문상태:varchar2(100)
	 */
	private String resStatus;

	/**
	 * 답문내용:varchar2(2000)
	 */
	private String resBody;

	/**
	 * 답문등록아이디:varchar2(100)
	 */
	private String resAdmin;
	
	/** 답문등록자명**/
	
	private String resAdminNm;
	/**
	 * RES_START_DATE:date(0)
	 */
	private String resIngDate;

	/**
	 * RES_END_DATE:date(0)
	 */
	private String resEndDate;

	/**
	 * 등록일자:date(0)
	 */
	private String regDate;

	/**
	 * 등록관리자:varchar2(100)
	 */
	private String regAdmin;
	
	/**
	 * 등록관리자명
	 */
	private String regAdminNm;

	/**
	 * 대리점코드:varchar2(20)
	 */
	private String agentId;
	
	/**
	 * 대리점명
	 */
	private String agentNm;

	/**
	 * 본사코드:varchar2(20)
	 */
	private String bonsaId;

	/**
	 * 비고:varchar2(2000)
	 */
	private String remark;

	/**
	 * 상태:varchar2(20)
	 */
	private String status;
	
	/**
	 * 추가..
	 */
	private String reqUserGubunNm;
	private String reqTypeNm;
	private String resStatusNm;
	private String statusNm;

	/**
	 * @return the reqUserGubunNm
	 */
	public String getReqUserGubunNm() {
		return reqUserGubunNm;
	}




	/**
	 * @param reqUserGubunNm the reqUserGubunNm to set
	 */
	public void setReqUserGubunNm(String reqUserGubunNm) {
		this.reqUserGubunNm = reqUserGubunNm;
	}




	/**
	 * @return the reqTypeNm
	 */
	public String getReqTypeNm() {
		return reqTypeNm;
	}




	/**
	 * @param reqTypeNm the reqTypeNm to set
	 */
	public void setReqTypeNm(String reqTypeNm) {
		this.reqTypeNm = reqTypeNm;
	}




	/**
	 * @return the resStatusNm
	 */
	public String getResStatusNm() {
		return resStatusNm;
	}




	/**
	 * @param resStatusNm the resStatusNm to set
	 */
	public void setResStatusNm(String resStatusNm) {
		this.resStatusNm = resStatusNm;
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
	 * @return the diaryId
	 */
	public int getDiaryId() {
		return diaryId;
	}




	/**
	 * @param diaryId the diaryId to set
	 */
	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}




	/**
	 * @return the contractNum
	 */
	public int getContractNum() {
		return contractNum;
	}




	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(int contractNum) {
		this.contractNum = contractNum;
	}




	/**
	 * @return the reqUserName
	 */
	public String getReqUserName() {
		return reqUserName;
	}




	/**
	 * @param reqUserName the reqUserName to set
	 */
	public void setReqUserName(String reqUserName) {
		this.reqUserName = reqUserName;
	}




	/**
	 * @return the reqUserGubun
	 */
	public String getReqUserGubun() {
		return reqUserGubun;
	}




	/**
	 * @param reqUserGubun the reqUserGubun to set
	 */
	public void setReqUserGubun(String reqUserGubun) {
		this.reqUserGubun = reqUserGubun;
	}




	/**
	 * @return the reqType
	 */
	public String getReqType() {
		return reqType;
	}




	/**
	 * @param reqType the reqType to set
	 */
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}




	/**
	 * @return the reqTitle
	 */
	public String getReqTitle() {
		return reqTitle;
	}




	/**
	 * @param reqTitle the reqTitle to set
	 */
	public void setReqTitle(String reqTitle) {
		this.reqTitle = reqTitle;
	}




	/**
	 * @return the reqBody
	 */
	public String getReqBody() {
		return reqBody;
	}




	/**
	 * @param reqBody the reqBody to set
	 */
	public void setReqBody(String reqBody) {
		this.reqBody = reqBody;
	}




	/**
	 * @return the resStatus
	 */
	public String getResStatus() {
		return resStatus;
	}




	/**
	 * @param resStatus the resStatus to set
	 */
	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}




	/**
	 * @return the resBody
	 */
	public String getResBody() {
		return resBody;
	}




	/**
	 * @param resBody the resBody to set
	 */
	public void setResBody(String resBody) {
		this.resBody = resBody;
	}




	/**
	 * @return the resAdmin
	 */
	public String getResAdmin() {
		return resAdmin;
	}




	/**
	 * @param resAdmin the resAdmin to set
	 */
	public void setResAdmin(String resAdmin) {
		this.resAdmin = resAdmin;
	}
	
	

	/**
	 * @return the resAdminNm
	 */
	public String getResAdminNm() {
		return resAdminNm;
	}



	/**
	 * @param resAdminNm the resAdminNm to set
	 */
	public void setResAdminNm(String resAdminNm) {
		this.resAdminNm = resAdminNm;
	}


	/**
	 * @return the resStartDate
	 */
	public String getResIngDate() {
		return resIngDate;
	}




	/**
	 * @param resStartDate the resStartDate to set
	 */
	public void setResIngDate(String resIngDate) {
		this.resIngDate = resIngDate;
	}




	/**
	 * @return the resEndDate
	 */
	public String getResEndDate() {
		return resEndDate;
	}




	/**
	 * @param resEndDate the resEndDate to set
	 */
	public void setResEndDate(String resEndDate) {
		this.resEndDate = resEndDate;
	}




	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}




	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}




	/**
	 * @return the regAdmin
	 */
	public String getRegAdmin() {
		return regAdmin;
	}




	/**
	 * @param regAdmin the regAdmin to set
	 */
	public void setRegAdmin(String regAdmin) {
		this.regAdmin = regAdmin;
	}




	/**
	 * @return the regAdminNm
	 */
	public String getRegAdminNm() {
		return regAdminNm;
	}




	/**
	 * @param regAdminNm the regAdminNm to set
	 */
	public void setRegAdminNm(String regAdminNm) {
		this.regAdminNm = regAdminNm;
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
	 * @return the bonsaId
	 */
	public String getBonsaId() {
		return bonsaId;
	}




	/**
	 * @param bonsaId the bonsaId to set
	 */
	public void setBonsaId(String bonsaId) {
		this.bonsaId = bonsaId;
	}




	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}




	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}




	

	

}
