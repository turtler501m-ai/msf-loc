package com.ktis.msp.org.userinfomgmt.vo;

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
@XmlRootElement(name="userInfoMgmtVo")
public class UserInfoMgmtVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	private String usrId; /** 사용자 ID */
	private String attcSctnCd; /** 소속 구분 코드 */
	private String usrNm; /** 사용자 명 */
	private String pass; /** 비밀번호 */
	private String telNum; /** 전화 번호 */
	private String telNum1; /** 전화 번호 */
	private String telNum2; /** 전화 번호 */
	private String telNum3; /** 전화 번호 */
	private String mblphnNum; /** 핸드폰 번호 */
	private String mblphnNum1; /** 핸드폰 번호 */
	private String mblphnNum2; /** 핸드폰 번호 */
	private String mblphnNum3; /** 핸드폰 번호 */
	private String lastLoginDt; /** 최종 로그인 일시 */
	private int passErrNum; /** 암호 오류 횟수 */
	private String presBiz; /** 사번 */
	private String email; /** 이메일 */
	private String pos; /** 직위 */
	private String odty; /** 직책 */
	private String addr; /** 주소 */
	private String dtlAddr; /** 상세 주소 */
	private String zipcd; /** 우편 번호 */
	private String fax; /** FAX 번호 */
	private String fax1; /** FAX 번호 */
	private String fax2; /** FAX 번호 */
	private String fax3; /** FAX 번호 */
	private int passAtmtNum; /** 비밀번호 시도 횟수 */
	private String delYn; /** 삭제 여부 */
	private String regstId; /** 등록자 ID */
	private String regstNm; /** 등록자 */
	private String regstDttm;
	private String rvisnId; /** 수정자 ID */
	private String rvisnNm; /** 수정자 */
	private String rvisnDttm;
	private String dept; /** 부서코드 */
	private String ipInfo; /** IP정보 */
	private String macAddr; /** MAC주소 */
	private String usgYn; /** 사용여부 */
	private String usgStrtDt; /** 사용시작일자 */
	private String usgEndDt; /** 사용종료일자 */
	private String passLastRvisnDt; /** 최종비밀번호변경일시 */
	private String mngrRule; /** 관리자롤 */
	private String mskAuthYn; /** 마스킹권한여부 */
	private String searchStartDt; /** 검색조건 등록일*/
	private String searchEndDt; /** 검색조건 등록일*/
	private String attcSctnCdNm;
	private String deptNm;
	private String seqNum;  /** 사용자이력일련번호 */
	private String oldPass;  /** 비밀번호 변경 체크*/
	private String orgnId;
	private String orgnNm;
	private String passInit;
	private String delRsn; /** 삭제사유*/
	private String kosId;
	private String stopDt;
	private String stopYn;
	private String procId;
	private String regId;
	private String RegDttm;
	private String orgType;
	private String stopCdName;
	private String procNm;
	private String procDt;
	private String attcSctnNm;
	private String startDate;
	private String endDate;
	private String macChkYn;
	private String macNm;      /** MAC NAME */
	private String macDesc;    /** MAC DESCRIPTION */
	private String oldMacAddr; /** MAC 주소 */
	private String actionFlag; /** 사용자액션(I-등록, U-수정, D-삭제) : 맥주소관리화면 이력 관리 시 사용*/
	private String orgTypeNm;  /** 조직유형타입 이름 */
	
	private String aplDt;		/** 신청일자 */
	private String appYn;		/** 승인여부 A:승인완료, C:반려, P:신청중, D:처리완료 */
	private String appUsrId;	/** 승인자ID */
	private String appUsrNm;	/** 승인자명 */
	private String appDt;		/** 승인일자 */
	private String refRsn;		/** 반려사유 */
	private String appFileId1;	/** 신청파일1 */
	private String appFileId2;	/** 신청파일2 */
	private String appFileNm1;	/** 신청파일명1 */
	private String appFileNm2;	/** 신청파일명2 */
	private String modChk;		/** 승인데이터 확인 */
	
	private String grpId;		/** 권한그룹ID */
	private String usrIdMsk; /** 사용자 ID */

	private String m2mYn; //M2M사용자여부

	private String status; /** 패스워드 초기화 상태 */
	private String statusNm; /** 패스워드 초기화 상태 */
	
	private String otpChkNum; /*OTP 발송 횟수*/
	private String lockYn; /* 계정잠금상태 */
	
	public String getStartDate() {
		return startDate;
	}
	public String getUsrIdMsk() {
		return usrIdMsk;
	}
	public void setUsrIdMsk(String usrIdMsk) {
		this.usrIdMsk = usrIdMsk;
	}
	public String getAppUsrNm() {
		return appUsrNm;
	}
	public void setAppUsrNm(String appUsrNm) {
		this.appUsrNm = appUsrNm;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getModChk() {
		return modChk;
	}
	public void setModChk(String modChk) {
		this.modChk = modChk;
	}
	public String getAppFileNm1() {
		return appFileNm1;
	}
	public void setAppFileNm1(String appFileNm1) {
		this.appFileNm1 = appFileNm1;
	}
	public String getAppFileNm2() {
		return appFileNm2;
	}
	public void setAppFileNm2(String appFileNm2) {
		this.appFileNm2 = appFileNm2;
	}
	public String getAppFileId1() {
		return appFileId1;
	}
	public void setAppFileId1(String appFileId1) {
		this.appFileId1 = appFileId1;
	}
	public String getAppFileId2() {
		return appFileId2;
	}
	public void setAppFileId2(String appFileId2) {
		this.appFileId2 = appFileId2;
	}
	public String getRefRsn() {
		return refRsn;
	}
	public void setRefRsn(String refRsn) {
		this.refRsn = refRsn;
	}
	public String getAplDt() {
		return aplDt;
	}
	public void setAplDt(String aplDt) {
		this.aplDt = aplDt;
	}
	public String getAppYn() {
		return appYn;
	}
	public void setAppYn(String appYn) {
		this.appYn = appYn;
	}
	public String getAppUsrId() {
		return appUsrId;
	}
	public void setAppUsrId(String appUsrId) {
		this.appUsrId = appUsrId;
	}
	public String getAppDt() {
		return appDt;
	}
	public void setAppDt(String appDt) {
		this.appDt = appDt;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAttcSctnNm() {
		return attcSctnNm;
	}
	public void setAttcSctnNm(String attcSctnNm) {
		this.attcSctnNm = attcSctnNm;
	}
	public String getProcDt() {
		return procDt;
	}
	public void setProcDt(String procDt) {
		this.procDt = procDt;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getStopCdName() {
		return stopCdName;
	}
	public void setStopCdName(String stopCdName) {
		this.stopCdName = stopCdName;
	}
	public String getProcNm() {
		return procNm;
	}
	public void setProcNm(String procNm) {
		this.procNm = procNm;
	}
	public String getStopDt() {
		return stopDt;
	}
	public void setStopDt(String stopDt) {
		this.stopDt = stopDt;
	}
	public String getStopYn() {
		return stopYn;
	}
	public void setStopYn(String stopYn) {
		this.stopYn = stopYn;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDttm() {
		return RegDttm;
	}
	public void setRegDttm(String regDttm) {
		RegDttm = regDttm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public String getTelNum1() {
		return telNum1;
	}
	public void setTelNum1(String telNum1) {
		this.telNum1 = telNum1;
	}
	public String getTelNum2() {
		return telNum2;
	}
	public void setTelNum2(String telNum2) {
		this.telNum2 = telNum2;
	}
	public String getTelNum3() {
		return telNum3;
	}
	public void setTelNum3(String telNum3) {
		this.telNum3 = telNum3;
	}
	public String getMblphnNum() {
		return mblphnNum;
	}
	public void setMblphnNum(String mblphnNum) {
		this.mblphnNum = mblphnNum;
	}
	public String getMblphnNum1() {
		return mblphnNum1;
	}
	public void setMblphnNum1(String mblphnNum1) {
		this.mblphnNum1 = mblphnNum1;
	}
	public String getMblphnNum2() {
		return mblphnNum2;
	}
	public void setMblphnNum2(String mblphnNum2) {
		this.mblphnNum2 = mblphnNum2;
	}
	public String getMblphnNum3() {
		return mblphnNum3;
	}
	public void setMblphnNum3(String mblphnNum3) {
		this.mblphnNum3 = mblphnNum3;
	}
	public String getLastLoginDt() {
		return lastLoginDt;
	}
	public void setLastLoginDt(String lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}
	public int getPassErrNum() {
		return passErrNum;
	}
	public void setPassErrNum(int passErrNum) {
		this.passErrNum = passErrNum;
	}
	public String getPresBiz() {
		return presBiz;
	}
	public void setPresBiz(String presBiz) {
		this.presBiz = presBiz;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getOdty() {
		return odty;
	}
	public void setOdty(String odty) {
		this.odty = odty;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getDtlAddr() {
		return dtlAddr;
	}
	public void setDtlAddr(String dtlAddr) {
		this.dtlAddr = dtlAddr;
	}
	public String getZipcd() {
		return zipcd;
	}
	public void setZipcd(String zipcd) {
		this.zipcd = zipcd;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	public int getPassAtmtNum() {
		return passAtmtNum;
	}
	public void setPassAtmtNum(int passAtmtNum) {
		this.passAtmtNum = passAtmtNum;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnNm() {
		return rvisnNm;
	}
	public void setRvisnNm(String rvisnNm) {
		this.rvisnNm = rvisnNm;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
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
	public String getUsgYn() {
		return usgYn;
	}
	public void setUsgYn(String usgYn) {
		this.usgYn = usgYn;
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
	public String getPassLastRvisnDt() {
		return passLastRvisnDt;
	}
	public void setPassLastRvisnDt(String passLastRvisnDt) {
		this.passLastRvisnDt = passLastRvisnDt;
	}
	public String getMngrRule() {
		return mngrRule;
	}
	public void setMngrRule(String mngrRule) {
		this.mngrRule = mngrRule;
	}
	public String getMskAuthYn() {
		return mskAuthYn;
	}
	public void setMskAuthYn(String mskAuthYn) {
		this.mskAuthYn = mskAuthYn;
	}
	public String getSearchStartDt() {
		return searchStartDt;
	}
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	public String getSearchEndDt() {
		return searchEndDt;
	}
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	public String getAttcSctnCdNm() {
		return attcSctnCdNm;
	}
	public void setAttcSctnCdNm(String attcSctnCdNm) {
		this.attcSctnCdNm = attcSctnCdNm;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getOldPass() {
		return oldPass;
	}
	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
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
	public String getPassInit() {
		return passInit;
	}
	public void setPassInit(String passInit) {
		this.passInit = passInit;
	}
	public String getDelRsn() {
		return delRsn;
	}
	public void setDelRsn(String delRsn) {
		this.delRsn = delRsn;
	}
	public String getKosId() {
		return kosId;
	}
	public void setKosId(String kosId) {
		this.kosId = kosId;
	}
	public String getMacChkYn() {
		return macChkYn;
	}
	public void setMacChkYn(String macChkYn) {
		this.macChkYn = macChkYn;
	}
    public String getMacNm() {
        return macNm;
    }
    public void setMacNm(String macNm) {
        this.macNm = macNm;
    }
    public String getMacDesc() {
        return macDesc;
    }
    public void setMacDesc(String macDesc) {
        this.macDesc = macDesc;
    }
    public String getOldMacAddr() {
        return oldMacAddr;
    }
    public void setOldMacAddr(String oldMacAddr) {
        this.oldMacAddr = oldMacAddr;
    }
    public String getActionFlag() {
        return actionFlag;
    }
    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }
    public String getOrgTypeNm() {
        return orgTypeNm;
    }
    public void setOrgTypeNm(String orgTypeNm) {
        this.orgTypeNm = orgTypeNm;
    }
    public String getRegstNm() {
        return regstNm;
    }
    public void setRegstNm(String regstNm) {
        this.regstNm = regstNm;
    }
    
	public String getM2mYn() {
		return m2mYn;
	}
	
	public void setM2mYn(String m2mYn) {
		this.m2mYn = m2mYn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusNm() {
		return statusNm;
	}
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}
	public String getOtpChkNum() {
		return otpChkNum;
	}
	public void setOtpChkNum(String otpChkNum) {
		this.otpChkNum = otpChkNum;
	}
	public String getLockYn() {
		return lockYn;
	}
	public void setLockYn(String lockYn) {
		this.lockYn = lockYn;
	}
}
