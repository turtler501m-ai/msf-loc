package com.ktmmobile.mcp.app.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PushBaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MultipartFile pushImgNmFile;

    private String name;
    private String seq;
    private String version;
    private String os;
    private String title;
    private String memo;
    private String dnUrl;

    private String userId;

    private String sysRegIp;

    private String sysChgIp;

    //as-is private Timestamp useStDt;
    private String aplyStrtDtime; //사용기간 시작일

    //as-is private Timestamp useEdDt;
    private String aplyEndDtime; //사용기간종료일

	/** checkedList */
	private String checkedList;

	/** selectedList */
	private String selectedList;

	/** 순서 */
	private String rowcnt;

	/** 푸쉬발송설정일련번호 */
	private String pushSndSetSno;

	/** 발송처리구분 */
	private String sndProcDiv;

	/** 발송횟수구분 */
	private String sndCntDiv;

	/** 발송일구분 */
	private String sndDtDiv;

	/** 발송시간 */
	private String sndTime;

	/** 승인여부 */
	private String approveYn;

	//타켓 서비스명
	private String tgtServer;


	/** 푸쉬발송고유번호 */
	private String pushSndProcNo;

	/** 메시지유형 */
	private String msgTp;

	/** 푸쉬유형 */
	private String pushTp;

	/** 푸쉬아이콘 */
	private String pushIconTp;

	/** 푸쉬대상업무유형 */
	private String pushWorkTp;

	/** 발송제목 */
	private String sndTitle;

	/** 발송메시지 */
	private String sndMsg;

	/** 푸쉬 이미지 경로 */
	private String pushImgUrl;

	/** 푸쉬 이미지 명 */
	private String pushImgNm;

	/** 링크구분 */
	private String linkDiv;

	/** 링크URL */
	private String linkUrl;

	/** 발송대상자추출구분 */
	private String sndTargetExtrDiv;

	/** 발송대상자일련번호 */
	private String sndTargetSno;

	/** 단말기OS유형 */
	private String appOsTp;

	/** 안드로이드 OS버전목록 */
	private String andOsVerLst;
	private String[] andOsVerList;

	/** 안드로이드 App 설치버전목록 */
	private String andInstlVerLst;
	private String[] andInstlVerList;

	/** iOS OS버전목록 */
	private String iosOsVerLst;
	private String[] iosOsVerList;

	/** iOS App 설치버전목록 */
	private String iosInstlVerLst;
	private String[] iosInstlVerList;

	private List<String> andVerList;
	private List<String> iosVerList;

	/** 고객선택조건유형 */
	private String custTp;

	/** 고객구분목록 */
	private String custDivLst;
	private String[] custDivList;

	/** 고객등급목록 */
	private String custGradeLst;
	private String[] custGradeList;

	/** 고객상세등급목록 */
	private String custDtlGradeLst;
	private String[] custDtlGradeList;

	/** 회원소속유형 */
	private String mbrPositTp;

	/** 회원소속상세목록 */
	private String mbrPositDtlLst;
	private String[] mbrPositDtlList;

	/** 발송요일목록 */
	private String sndDowLst;
	private String sndDow;

	/** 발송일 */
	private String sndDays;

	/** 발송일자 */
	private String sndDtStr;
	private String sndDt;

	/** 회원번호 */
	private String mbrNo;

	/** 핸드폰번호 */
	private String cellNo;

	/** 단말기OS버전 */
	private String appOsVer;

	/** App 설치버전 */
	private String appInstlVer;

	/** 고객상세등급 */
	private String custDtlGrade;

	/** 단말기아이디 */
	private String appId;

	/** 발송횟수 */
	private long sndCnt;

	/** 발송테스트여부 */
	private String sndTestYn;

	/** 로그인아이디 */
	private String loginId;

	private List<String> loginIdList;

	/** 회원 명 */
	private String mbrNm;

	private List<String> mbrNmList;

	/** 엑셀 파일 경로 */
	private String exlUpFilePath;

	/** 엑셀 파일 명 */
	private MultipartFile exlPushFileNm;

	private String exlUpFileNm;
	/** 결과 메시지 */
	private String msg;

	/** 푸쉬 테스트 번호 리스트 */
	private String cellNoList;

	/** 푸쉬 테스트 번호 */
	private String testCellNo;

	/** 푸쉬발송처리일련번호 */
	private String pushSndProcSno;

	/** 총 건수 */
	private String totCnt;

	/** 성공 건수 */
	private String successCnt;

	/** 실패 건수 */
	private String failCnt;

	/** 발송처리일자 */
	private Timestamp sndProcDt;

	/** 발송요청일자 */
	private Timestamp sndRegDt;

	/** 처리상태 */
	private String procSt;

	/** 서버수집여부 */
	private String serverColctYn;

	/** 앱수집여부 */
	private String appColctYn;

	/** 재발송여부  */
	private String resendYn;

	/** 회원소속구분코드 */
	private String mbrPositSctCd;

	/** 정회원구분코드 */
	private String mbrGradeTpCd;

	/** 회원구분코드 */
	private String mbrSctCd;

	/** 회원구분상세코드 */
	private String mbrSctDtlCd;

	/** 탈퇴일자 */
	private String scsnDt;

	/** app설치여부 */
	private String appInstlYn;

	/** app푸쉬동의여부 */
	private String appPushAgreeYn;

	/** 결과 */
	private String returnResult;

	/** 시작 row count */
	private int startIndex;

	/** 종료 row count */
	private int endIndex;

	private String sysRegId;

	private String sysChgId;

	public String getAplyStrtDtime() {
		return aplyStrtDtime;
	}

	public void setAplyStrtDtime(String aplyStrtDtime) {
		this.aplyStrtDtime = aplyStrtDtime;
	}

	public String getAplyEndDtime() {
		return aplyEndDtime;
	}

	public void setAplyEndDtime(String aplyEndDtime) {
		this.aplyEndDtime = aplyEndDtime;
	}

	public String getCheckedList() {
		return checkedList;
	}

	public void setCheckedList(String checkedList) {
		this.checkedList = checkedList;
	}

	public String getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(String selectedList) {
		this.selectedList = selectedList;
	}

	public String getRowcnt() {
		return rowcnt;
	}

	public void setRowcnt(String rowcnt) {
		this.rowcnt = rowcnt;
	}

	public String getPushSndSetSno() {
		return pushSndSetSno;
	}

	public void setPushSndSetSno(String pushSndSetSno) {
		this.pushSndSetSno = pushSndSetSno;
	}

	public String getSndProcDiv() {
		return sndProcDiv;
	}

	public void setSndProcDiv(String sndProcDiv) {
		this.sndProcDiv = sndProcDiv;
	}

	public String getSndCntDiv() {
		return sndCntDiv;
	}

	public void setSndCntDiv(String sndCntDiv) {
		this.sndCntDiv = sndCntDiv;
	}

	public String getSndDtDiv() {
		return sndDtDiv;
	}

	public void setSndDtDiv(String sndDtDiv) {
		this.sndDtDiv = sndDtDiv;
	}

	public String getSndTime() {
		return sndTime;
	}

	public void setSndTime(String sndTime) {
		this.sndTime = sndTime;
	}

	public String getApproveYn() {
		return approveYn;
	}

	public void setApproveYn(String approveYn) {
		this.approveYn = approveYn;
	}

	public String getPushSndProcNo() {
		return pushSndProcNo;
	}

	public void setPushSndProcNo(String pushSndProcNo) {
		this.pushSndProcNo = pushSndProcNo;
	}

	public String getMsgTp() {
		return msgTp;
	}

	public void setMsgTp(String msgTp) {
		this.msgTp = msgTp;
	}

	public String getPushTp() {
		return pushTp;
	}

	public void setPushTp(String pushTp) {
		this.pushTp = pushTp;
	}

	public String getPushIconTp() {
		return pushIconTp;
	}

	public void setPushIconTp(String pushIconTp) {
		this.pushIconTp = pushIconTp;
	}

	public String getPushWorkTp() {
		return pushWorkTp;
	}

	public void setPushWorkTp(String pushWorkTp) {
		this.pushWorkTp = pushWorkTp;
	}

	public String getSndTitle() {
		return sndTitle;
	}

	public void setSndTitle(String sndTitle) {
		this.sndTitle = sndTitle;
	}

	public String getSndMsg() {
		return sndMsg;
	}

	public void setSndMsg(String sndMsg) {
		this.sndMsg = sndMsg;
	}

	public String getPushImgUrl() {
		return pushImgUrl;
	}

	public void setPushImgUrl(String pushImgUrl) {
		this.pushImgUrl = pushImgUrl;
	}

	public String getPushImgNm() {
		return pushImgNm;
	}

	public void setPushImgNm(String pushImgNm) {
		this.pushImgNm = pushImgNm;
	}

	public String getLinkDiv() {
		return linkDiv;
	}

	public void setLinkDiv(String linkDiv) {
		this.linkDiv = linkDiv;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getSndTargetExtrDiv() {
		return sndTargetExtrDiv;
	}

	public void setSndTargetExtrDiv(String sndTargetExtrDiv) {
		this.sndTargetExtrDiv = sndTargetExtrDiv;
	}

	public String getSndTargetSno() {
		return sndTargetSno;
	}

	public void setSndTargetSno(String sndTargetSno) {
		this.sndTargetSno = sndTargetSno;
	}

	public String getAppOsTp() {
		return appOsTp;
	}

	public void setAppOsTp(String appOsTp) {
		this.appOsTp = appOsTp;
	}

	public String getAndOsVerLst() {
		return andOsVerLst;
	}

	public void setAndOsVerLst(String andOsVerLst) {
		this.andOsVerLst = andOsVerLst;
	}

	public String[] getAndOsVerList() {
		return andOsVerList;
	}

	public void setAndOsVerList(String[] andOsVerList) {
		this.andOsVerList = andOsVerList;
	}

	public String getAndInstlVerLst() {
		return andInstlVerLst;
	}

	public void setAndInstlVerLst(String andInstlVerLst) {
		this.andInstlVerLst = andInstlVerLst;
	}

	public String[] getAndInstlVerList() {
		return andInstlVerList;
	}

	public void setAndInstlVerList(String[] andInstlVerList) {
		this.andInstlVerList = andInstlVerList;
	}

	public String getIosOsVerLst() {
		return iosOsVerLst;
	}

	public void setIosOsVerLst(String iosOsVerLst) {
		this.iosOsVerLst = iosOsVerLst;
	}

	public String[] getIosOsVerList() {
		return iosOsVerList;
	}

	public void setIosOsVerList(String[] iosOsVerList) {
		this.iosOsVerList = iosOsVerList;
	}

	public String getIosInstlVerLst() {
		return iosInstlVerLst;
	}

	public void setIosInstlVerLst(String iosInstlVerLst) {
		this.iosInstlVerLst = iosInstlVerLst;
	}

	public String[] getIosInstlVerList() {
		return iosInstlVerList;
	}

	public void setIosInstlVerList(String[] iosInstlVerList) {
		this.iosInstlVerList = iosInstlVerList;
	}

	public List<String> getAndVerList() {
		return andVerList;
	}

	public void setAndVerList(List<String> andVerList) {
		this.andVerList = andVerList;
	}

	public List<String> getIosVerList() {
		return iosVerList;
	}

	public void setIosVerList(List<String> iosVerList) {
		this.iosVerList = iosVerList;
	}

	public String getCustTp() {
		return custTp;
	}

	public void setCustTp(String custTp) {
		this.custTp = custTp;
	}

	public String getCustDivLst() {
		return custDivLst;
	}

	public void setCustDivLst(String custDivLst) {
		this.custDivLst = custDivLst;
	}

	public String[] getCustDivList() {
		return custDivList;
	}

	public void setCustDivList(String[] custDivList) {
		this.custDivList = custDivList;
	}

	public String getCustGradeLst() {
		return custGradeLst;
	}

	public void setCustGradeLst(String custGradeLst) {
		this.custGradeLst = custGradeLst;
	}

	public String[] getCustGradeList() {
		return custGradeList;
	}

	public void setCustGradeList(String[] custGradeList) {
		this.custGradeList = custGradeList;
	}

	public String getCustDtlGradeLst() {
		return custDtlGradeLst;
	}

	public void setCustDtlGradeLst(String custDtlGradeLst) {
		this.custDtlGradeLst = custDtlGradeLst;
	}

	public String[] getCustDtlGradeList() {
		return custDtlGradeList;
	}

	public void setCustDtlGradeList(String[] custDtlGradeList) {
		this.custDtlGradeList = custDtlGradeList;
	}

	public String getMbrPositTp() {
		return mbrPositTp;
	}

	public void setMbrPositTp(String mbrPositTp) {
		this.mbrPositTp = mbrPositTp;
	}

	public String getMbrPositDtlLst() {
		return mbrPositDtlLst;
	}

	public void setMbrPositDtlLst(String mbrPositDtlLst) {
		this.mbrPositDtlLst = mbrPositDtlLst;
	}

	public String[] getMbrPositDtlList() {
		return mbrPositDtlList;
	}

	public void setMbrPositDtlList(String[] mbrPositDtlList) {
		this.mbrPositDtlList = mbrPositDtlList;
	}

	public String getSndDowLst() {
		return sndDowLst;
	}

	public void setSndDowLst(String sndDowLst) {
		this.sndDowLst = sndDowLst;
	}

	public String getSndDow() {
		return sndDow;
	}

	public void setSndDow(String sndDow) {
		this.sndDow = sndDow;
	}

	public String getSndDays() {
		return sndDays;
	}

	public void setSndDays(String sndDays) {
		this.sndDays = sndDays;
	}

	public String getSndDtStr() {
		return sndDtStr;
	}

	public void setSndDtStr(String sndDtStr) {
		this.sndDtStr = sndDtStr;
	}

	public String getSndDt() {
		return sndDt;
	}

	public void setSndDt(String sndDt) {
		this.sndDt = sndDt;
	}

	public String getMbrNo() {
		return mbrNo;
	}

	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public String getAppOsVer() {
		return appOsVer;
	}

	public void setAppOsVer(String appOsVer) {
		this.appOsVer = appOsVer;
	}

	public String getAppInstlVer() {
		return appInstlVer;
	}

	public void setAppInstlVer(String appInstlVer) {
		this.appInstlVer = appInstlVer;
	}

	public String getCustDtlGrade() {
		return custDtlGrade;
	}

	public void setCustDtlGrade(String custDtlGrade) {
		this.custDtlGrade = custDtlGrade;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public long getSndCnt() {
		return sndCnt;
	}

	public void setSndCnt(long sndCnt) {
		this.sndCnt = sndCnt;
	}

	public String getSndTestYn() {
		return sndTestYn;
	}

	public void setSndTestYn(String sndTestYn) {
		this.sndTestYn = sndTestYn;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public List<String> getLoginIdList() {
		return loginIdList;
	}

	public void setLoginIdList(List<String> loginIdList) {
		this.loginIdList = loginIdList;
	}

	public String getMbrNm() {
		return mbrNm;
	}

	public void setMbrNm(String mbrNm) {
		this.mbrNm = mbrNm;
	}

	public List<String> getMbrNmList() {
		return mbrNmList;
	}

	public void setMbrNmList(List<String> mbrNmList) {
		this.mbrNmList = mbrNmList;
	}

	public String getExlUpFilePath() {
		return exlUpFilePath;
	}

	public void setExlUpFilePath(String exlUpFilePath) {
		this.exlUpFilePath = exlUpFilePath;
	}



	public MultipartFile getExlPushFileNm() {
		return exlPushFileNm;
	}

	public void setExlPushFileNm(MultipartFile exlPushFileNm) {
		this.exlPushFileNm = exlPushFileNm;
	}

	public String getExlUpFileNm() {
		return exlUpFileNm;
	}

	public void setExlUpFileNm(String exlUpFileNm) {
		this.exlUpFileNm = exlUpFileNm;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCellNoList() {
		return cellNoList;
	}

	public void setCellNoList(String cellNoList) {
		this.cellNoList = cellNoList;
	}

	public String getTestCellNo() {
		return testCellNo;
	}

	public void setTestCellNo(String testCellNo) {
		this.testCellNo = testCellNo;
	}

	public String getPushSndProcSno() {
		return pushSndProcSno;
	}

	public void setPushSndProcSno(String pushSndProcSno) {
		this.pushSndProcSno = pushSndProcSno;
	}

	public String getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}

	public String getSuccessCnt() {
		return successCnt;
	}

	public void setSuccessCnt(String successCnt) {
		this.successCnt = successCnt;
	}

	public String getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(String failCnt) {
		this.failCnt = failCnt;
	}

	public Timestamp getSndProcDt() {
		return sndProcDt;
	}

	public void setSndProcDt(Timestamp sndProcDt) {
		this.sndProcDt = sndProcDt;
	}

	public Timestamp getSndRegDt() {
		return sndRegDt;
	}

	public void setSndRegDt(Timestamp sndRegDt) {
		this.sndRegDt = sndRegDt;
	}

	public String getProcSt() {
		return procSt;
	}

	public void setProcSt(String procSt) {
		this.procSt = procSt;
	}

	public String getServerColctYn() {
		return serverColctYn;
	}

	public void setServerColctYn(String serverColctYn) {
		this.serverColctYn = serverColctYn;
	}

	public String getAppColctYn() {
		return appColctYn;
	}

	public void setAppColctYn(String appColctYn) {
		this.appColctYn = appColctYn;
	}

	public String getResendYn() {
		return resendYn;
	}

	public void setResendYn(String resendYn) {
		this.resendYn = resendYn;
	}

	public String getMbrPositSctCd() {
		return mbrPositSctCd;
	}

	public void setMbrPositSctCd(String mbrPositSctCd) {
		this.mbrPositSctCd = mbrPositSctCd;
	}

	public String getMbrGradeTpCd() {
		return mbrGradeTpCd;
	}

	public void setMbrGradeTpCd(String mbrGradeTpCd) {
		this.mbrGradeTpCd = mbrGradeTpCd;
	}

	public String getMbrSctCd() {
		return mbrSctCd;
	}

	public void setMbrSctCd(String mbrSctCd) {
		this.mbrSctCd = mbrSctCd;
	}

	public String getMbrSctDtlCd() {
		return mbrSctDtlCd;
	}

	public void setMbrSctDtlCd(String mbrSctDtlCd) {
		this.mbrSctDtlCd = mbrSctDtlCd;
	}

	public String getScsnDt() {
		return scsnDt;
	}

	public void setScsnDt(String scsnDt) {
		this.scsnDt = scsnDt;
	}

	public String getAppInstlYn() {
		return appInstlYn;
	}

	public void setAppInstlYn(String appInstlYn) {
		this.appInstlYn = appInstlYn;
	}

	public String getAppPushAgreeYn() {
		return appPushAgreeYn;
	}

	public void setAppPushAgreeYn(String appPushAgreeYn) {
		this.appPushAgreeYn = appPushAgreeYn;
	}

	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getSysRegId() {
		return sysRegId;
	}

	public void setSysRegId(String sysRegId) {
		this.sysRegId = sysRegId;
	}

	public String getSysChgId() {
		return sysChgId;
	}

	public void setSysChgId(String sysChgId) {
		this.sysChgId = sysChgId;
	}

	public String getSysRegIp() {
		return sysRegIp;
	}

	public void setSysRegIp(String sysRegIp) {
		this.sysRegIp = sysRegIp;
	}

	public String getSysChgIp() {
		return sysChgIp;
	}

	public void setSysChgIp(String sysChgIp) {
		this.sysChgIp = sysChgIp;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDnUrl() {
		return dnUrl;
	}

	public void setDnUrl(String dnUrl) {
		this.dnUrl = dnUrl;
	}

	public MultipartFile getPushImgNmFile() {
		return pushImgNmFile;
	}

	public void setPushImgNmFile(MultipartFile pushImgNmFile) {
		this.pushImgNmFile = pushImgNmFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTgtServer() {
		return tgtServer;
	}

	public void setTgtServer(String tgtServer) {
		this.tgtServer = tgtServer;
	}






}
