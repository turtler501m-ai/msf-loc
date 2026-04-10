/**
 * 
 */
package com.ktis.msp.cmn.login.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

public class UserApprVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

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
	
	private String regstNm; /** 등록자 */
	private String regstDttm;
	
	private String rvisnNm; /** 수정자 */
	
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
	private String appFileId1; /** 파일ID1 */
	private String appFileId2; /** 파일ID2 */
	private String appYn; /** 승인여부 */
	private String appUsrId; /** 승인자id */
	private String appDt; /** 승인날짜 */
	private String aplDt; /** t신청날자 */
	
	private String regstId; /** 등록자ID */
    private String regDttm; /** 등록일시 */
    private String rvisnId; /** 수정자ID */
    private String rvisnDttm; /** 수정일시 */
    private String attDttm; /** 첨부일시 */
    private String fileNm; /** 파일명 */
    private String attDsc; /** 첨부설명 */
    private String attRout; /** 첨부경로 */
    private String attSctn; /** 업무구분 */
    private String fileSeq; /** SEQ */
    private String fileId; /** 파일ID */
    
    private String orgnId;
    private String orgnNm;
    
    private String file_upload1_count;
    private String file_upload1_s_0;
    private String file_upload1_s_1;
    private String file_upload1_s_2;
    private String file_upload1_s_3;
    private String file_upload1_s_4;
    

    private String file_upload2_count;
    private String file_upload2_s_0;
    private String file_upload2_s_1;
    private String file_upload2_s_2;
    

    private String file_upload3_count;
    private String file_upload3_s_0;
    

    private String file_upload4_count;
    private String file_upload4_s_0;
    
    private String contractNum;
    
    private String strRequestKey;
    
    @Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    
    



	/**
	 * @return the file_upload1_s_3
	 */
	public String getFile_upload1_s_3() {
		return file_upload1_s_3;
	}






	/**
	 * @param file_upload1_s_3 the file_upload1_s_3 to set
	 */
	public void setFile_upload1_s_3(String file_upload1_s_3) {
		this.file_upload1_s_3 = file_upload1_s_3;
	}






	/**
	 * @return the file_upload1_s_4
	 */
	public String getFile_upload1_s_4() {
		return file_upload1_s_4;
	}






	/**
	 * @param file_upload1_s_4 the file_upload1_s_4 to set
	 */
	public void setFile_upload1_s_4(String file_upload1_s_4) {
		this.file_upload1_s_4 = file_upload1_s_4;
	}






	/**
	 * @return the strRequestKey
	 */
	public String getStrRequestKey() {
		return strRequestKey;
	}






	/**
	 * @param strRequestKey the strRequestKey to set
	 */
	public void setStrRequestKey(String strRequestKey) {
		this.strRequestKey = strRequestKey;
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
	 * @return the file_upload4_count
	 */
	public String getFile_upload4_count() {
		return file_upload4_count;
	}



	/**
	 * @param file_upload4_count the file_upload4_count to set
	 */
	public void setFile_upload4_count(String file_upload4_count) {
		this.file_upload4_count = file_upload4_count;
	}



	/**
	 * @return the file_upload4_s_0
	 */
	public String getFile_upload4_s_0() {
		return file_upload4_s_0;
	}



	/**
	 * @param file_upload4_s_0 the file_upload4_s_0 to set
	 */
	public void setFile_upload4_s_0(String file_upload4_s_0) {
		this.file_upload4_s_0 = file_upload4_s_0;
	}



	/**
	 * @return the file_upload2_count
	 */
	public String getFile_upload2_count() {
		return file_upload2_count;
	}



	/**
	 * @param file_upload2_count the file_upload2_count to set
	 */
	public void setFile_upload2_count(String file_upload2_count) {
		this.file_upload2_count = file_upload2_count;
	}



	/**
	 * @return the file_upload2_s_0
	 */
	public String getFile_upload2_s_0() {
		return file_upload2_s_0;
	}



	/**
	 * @param file_upload2_s_0 the file_upload2_s_0 to set
	 */
	public void setFile_upload2_s_0(String file_upload2_s_0) {
		this.file_upload2_s_0 = file_upload2_s_0;
	}



	/**
	 * @return the file_upload2_s_1
	 */
	public String getFile_upload2_s_1() {
		return file_upload2_s_1;
	}



	/**
	 * @param file_upload2_s_1 the file_upload2_s_1 to set
	 */
	public void setFile_upload2_s_1(String file_upload2_s_1) {
		this.file_upload2_s_1 = file_upload2_s_1;
	}



	/**
	 * @return the file_upload2_s_2
	 */
	public String getFile_upload2_s_2() {
		return file_upload2_s_2;
	}



	/**
	 * @param file_upload2_s_2 the file_upload2_s_2 to set
	 */
	public void setFile_upload2_s_2(String file_upload2_s_2) {
		this.file_upload2_s_2 = file_upload2_s_2;
	}



	/**
	 * @return the file_upload3_count
	 */
	public String getFile_upload3_count() {
		return file_upload3_count;
	}



	/**
	 * @param file_upload3_count the file_upload3_count to set
	 */
	public void setFile_upload3_count(String file_upload3_count) {
		this.file_upload3_count = file_upload3_count;
	}



	/**
	 * @return the file_upload3_s_0
	 */
	public String getFile_upload3_s_0() {
		return file_upload3_s_0;
	}



	/**
	 * @param file_upload3_s_0 the file_upload3_s_0 to set
	 */
	public void setFile_upload3_s_0(String file_upload3_s_0) {
		this.file_upload3_s_0 = file_upload3_s_0;
	}



	/**
	 * @return the file_upload1_count
	 */
	public String getFile_upload1_count() {
		return file_upload1_count;
	}


	/**
	 * @param file_upload1_count the file_upload1_count to set
	 */
	public void setFile_upload1_count(String file_upload1_count) {
		this.file_upload1_count = file_upload1_count;
	}


	/**
	 * @return the file_upload1_s_0
	 */
	public String getFile_upload1_s_0() {
		return file_upload1_s_0;
	}


	/**
	 * @param file_upload1_s_0 the file_upload1_s_0 to set
	 */
	public void setFile_upload1_s_0(String file_upload1_s_0) {
		this.file_upload1_s_0 = file_upload1_s_0;
	}


	/**
	 * @return the file_upload1_s_1
	 */
	public String getFile_upload1_s_1() {
		return file_upload1_s_1;
	}


	/**
	 * @param file_upload1_s_1 the file_upload1_s_1 to set
	 */
	public void setFile_upload1_s_1(String file_upload1_s_1) {
		this.file_upload1_s_1 = file_upload1_s_1;
	}


	/**
	 * @return the file_upload1_s_2
	 */
	public String getFile_upload1_s_2() {
		return file_upload1_s_2;
	}


	/**
	 * @param file_upload1_s_2 the file_upload1_s_2 to set
	 */
	public void setFile_upload1_s_2(String file_upload1_s_2) {
		this.file_upload1_s_2 = file_upload1_s_2;
	}

	/**
	 * @return the attDttm
	 */
	public String getAttDttm() {
		return attDttm;
	}
	/**
	 * @param attDttm the attDttm to set
	 */
	public void setAttDttm(String attDttm) {
		this.attDttm = attDttm;
	}
	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return fileNm;
	}
	/**
	 * @param fileNm the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	/**
	 * @return the attDsc
	 */
	public String getAttDsc() {
		return attDsc;
	}
	/**
	 * @param attDsc the attDsc to set
	 */
	public void setAttDsc(String attDsc) {
		this.attDsc = attDsc;
	}
	/**
	 * @return the attRout
	 */
	public String getAttRout() {
		return attRout;
	}
	/**
	 * @param attRout the attRout to set
	 */
	public void setAttRout(String attRout) {
		this.attRout = attRout;
	}
	/**
	 * @return the attSctn
	 */
	public String getAttSctn() {
		return attSctn;
	}
	/**
	 * @param attSctn the attSctn to set
	 */
	public void setAttSctn(String attSctn) {
		this.attSctn = attSctn;
	}
	/**
	 * @return the fileSeq
	 */
	public String getFileSeq() {
		return fileSeq;
	}
	/**
	 * @param fileSeq the fileSeq to set
	 */
	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}
	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	
	public String getStartDate() {
		return startDate;
	}
	public String getAplDt() {
		return aplDt;
	}
	public void setAplDt(String aplDt) {
		this.aplDt = aplDt;
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
	
	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}


}
