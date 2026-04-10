package com.ktis.msp.org.userinfomgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : CanUserResVO
 * @Description : CAN 사용자 관리 RES VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017.06.09 TREXSHIN 최초생성
 * @
 * @author : TREXSHIN
 * @Create Date : 2017.06.09.
 */
public class CanUserResVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795671671543658506L;
	
	private String usrId = "";
	private String usrNm = "";
	private String usgStrtDt = "";
	private String usgEndDt = "";
	private String reason = "";
	private String regDt = "";
	
	private String saveType = "";
	private String seqNum = "";
	
	private String grpId = "";
	private String operType = "";

	private String usrIdMsk = "";
	
	public String getUsrIdMsk() {
		return usrIdMsk;
	}
	public void setUsrIdMsk(String usrIdMsk) {
		this.usrIdMsk = usrIdMsk;
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
	public String getUsgStrtDt() {
		return usgStrtDt;
	}
	public void setUsgStrtDt(String usgStrtDt) {
		this.usgStrtDt = usgStrtDt;
	}
	public String getUsgEndDt() {
		return usgEndDt;
	}
	public void setUsgEndDt(String usgEndDt) {
		this.usgEndDt = usgEndDt;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
}
