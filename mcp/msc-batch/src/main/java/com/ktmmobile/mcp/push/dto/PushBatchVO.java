package com.ktmmobile.mcp.push.dto;

import java.io.Serializable;
import java.util.List;

public class PushBatchVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tgtServer;
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
	
	/** 사용기간시작일 */
	private String useStDt;
	
	/** 사용기간종료일 */
	private String useEdDt;
	
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
	
	/** 푸쉬 이미지경로 */
	private String pushImgUrl;
	
	/** 푸쉬 이미지명 */
	private String pushImgNm;
	
	/** 링크구분 */
	private String linkDiv;
	
	/** 링크URL */
	private String linkUrl;
	
	/** 발송요일목록 */
	private String sndDowLst;
	
	/** 발송요일 */
	private String sndDow;
	
	/** 발송일 */
	private String sndDays;
	
	/** 발송일자 */
	private String sndDt;
	
	/** 푸쉬발송고유번호 */
	private String pushSndProcNo;
	
	/** 발송대상자추출구분 */
	private String sndTargetExtrDiv;
	
	/** 단말기OS유형 */
	private String appOsTp;
	
	/** 안드로이드 OS버전목록 */
	private String andOsVerLst;
	private String andOsVerLst2;
	
	/** 안드로이드 App 설치버전목록 */
	private String andInstlVerLst;
	private String andInstlVerLst2;
	
	/** iOS OS버전목록 */
	private String iosOsVerLst;
	private String iosOsVerLst2;
	
	/** iOS App 설치버전목록 */
	private String iosInstlVerLst;
	private String iosInstlVerLst2;
	
	/** 고객선택조건유형 */
	private String custTp;
	
	/** 고객구분목록 */
	private String custDivLst;
	
	/** 고객등급목록 */
	private String custGradeLst;
	
	/** 고객상세등급목록 */
	private String custDtlGradeLst;
	
	/** 회원소속유형 */
	private String mbrPositTp;
	
	/** 회원소속상세목록 */
	private String mbrPositDtlLst;
	
	/** 발송대상자일련번호 */
	private String sndTargetSno;
	
	
	/** 회원넘버 */
	private String mbrNo;
	
	/** 회원명 */
	private String mbrNm;
	
	/** 로그인아이디 */
	private String loginId;
	
	/** 휴대폰번호 */
	private String cellNo;
	
	/** 회원소속구분코드 */
	private String mbrPositSctCd;
	
	/** 정회원구분코드*/
	private String mbrGradeTpCd;
	
	/** 회원구분코드 */
	private String mbrSctCd;
	
	/** 회원구분상세코드 */
	private String mbrSctDtlCd;
	
	/** 탈퇴일자 */
	private String scsnDt;
	
	/** app 설치여부 */
	private String appInstlYn;
	
	/** app 설치버전 */
	private String appInstlVer;
	
	/** 단말기os버전 */
	private String appOsVer;
	
	/** 단말기아이디 */
	private String appId;
	
	/** app 푸쉬동의여부 */
	private String appPushAgreeYn;
	
	
	/** 푸쉬발송처리일련번호 */
	private String pushSndProcSno;
	
	/** 처리상태 */
	private String procSt;
	
	/** 발송처리일자 */
	private String sndProcDt;
	
	/** 서버수집여부 */
	private String serverColctYn;
	
	/** 앱수집여부 */
	private String appColctYn;
	
	/** 발송횟수 */
	private long sndCnt;
	
	/** 발송테스트여부 */
	private String sndTestYn;
	
	
	/** 안드로이드 OS버전목록 */
	private String[] andOsVerList;
	
	/** 안드로이드 App 설치버전목록 */
	private String[] andInstlVerList;
	
	/** iOS OS버전목록 */
	private String[] iosOsVerList;
	
	/** iOS App 설치버전목록 */
	private String[] iosInstlVerList;
	
	private List<String> andVerList;
	private List<String> iosVerList;
	
	/** 고객구분목록 */
	private String[] custDivList;
	
	/** 고객등급목록 */
	private String[] custGradeList;
	
	/** 고객상세등급목록 */
	private String[] custDtlGradeList;
	
	/** 회원소속상세목록 */
	private String[] mbrPositDtlList;
	
	
	/** 총건수 */
	private int totCnt;
	
	/** 성공건수 */
	private int successCnt;
	
	/** 실패건수 */
	private int failCnt;
	
	/** 시작 row count */
	private int startIndex;
	
	/** 종료 row count */
	private int endIndex;
	
	private String sysRegIp;
	
	/** 시스템 등록자 아이디 */
	private String sysRegId;
	
	/** 시스템 수정자 아이디 */
	private String sysModId;

	
	
	public String getTgtServer() {
		return tgtServer;
	}

	public void setTgtServer(String tgtServer) {
		this.tgtServer = tgtServer;
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

	public String getUseStDt() {
		return useStDt;
	}

	public void setUseStDt(String useStDt) {
		this.useStDt = useStDt;
	}

	public String getUseEdDt() {
		return useEdDt;
	}

	public void setUseEdDt(String useEdDt) {
		this.useEdDt = useEdDt;
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

	public String getSndDt() {
		return sndDt;
	}

	public void setSndDt(String sndDt) {
		this.sndDt = sndDt;
	}

	public String getPushSndProcNo() {
		return pushSndProcNo;
	}

	public void setPushSndProcNo(String pushSndProcNo) {
		this.pushSndProcNo = pushSndProcNo;
	}

	public String getSndTargetExtrDiv() {
		return sndTargetExtrDiv;
	}

	public void setSndTargetExtrDiv(String sndTargetExtrDiv) {
		this.sndTargetExtrDiv = sndTargetExtrDiv;
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
	
	public String getAndOsVerLst2() {
		return andOsVerLst2;
	}

	public void setAndOsVerLst2(String andOsVerLst2) {
		this.andOsVerLst2 = andOsVerLst2;
	}

	public String getAndInstlVerLst() {
		return andInstlVerLst;
	}

	public void setAndInstlVerLst(String andInstlVerLst) {
		this.andInstlVerLst = andInstlVerLst;
	}

	public void setAndInstlVerLst2(String andInstlVerLst2) {
		this.andInstlVerLst2 = andInstlVerLst2;
	}

	public String getAndInstlVerLst2() {
		return andInstlVerLst2;
	}

	public String getIosOsVerLst() {
		return iosOsVerLst;
	}

	public void setIosOsVerLst(String iosOsVerLst) {
		this.iosOsVerLst = iosOsVerLst;
	}

	public String getIosOsVerLst2() {
		return iosOsVerLst2;
	}

	public void setIosOsVerLst2(String iosOsVerLst2) {
		this.iosOsVerLst2 = iosOsVerLst2;
	}

	public String getIosInstlVerLst() {
		return iosInstlVerLst;
	}

	public void setIosInstlVerLst(String iosInstlVerLst) {
		this.iosInstlVerLst = iosInstlVerLst;
	}

	public String getIosInstlVerLst2() {
		return iosInstlVerLst2;
	}

	public void setIosInstlVerLst2(String iosInstlVerLst2) {
		this.iosInstlVerLst2 = iosInstlVerLst2;
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

	public String getCustGradeLst() {
		return custGradeLst;
	}

	public void setCustGradeLst(String custGradeLst) {
		this.custGradeLst = custGradeLst;
	}

	public String getCustDtlGradeLst() {
		return custDtlGradeLst;
	}

	public void setCustDtlGradeLst(String custDtlGradeLst) {
		this.custDtlGradeLst = custDtlGradeLst;
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

	public String getSndTargetSno() {
		return sndTargetSno;
	}

	public void setSndTargetSno(String sndTargetSno) {
		this.sndTargetSno = sndTargetSno;
	}

	public String getMbrNo() {
		return mbrNo;
	}

	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	public String getMbrNm() {
		return mbrNm;
	}

	public void setMbrNm(String mbrNm) {
		this.mbrNm = mbrNm;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
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

	public String getAppInstlVer() {
		return appInstlVer;
	}

	public void setAppInstlVer(String appInstlVer) {
		this.appInstlVer = appInstlVer;
	}

	public String getAppOsVer() {
		return appOsVer;
	}

	public void setAppOsVer(String appOsVer) {
		this.appOsVer = appOsVer;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppPushAgreeYn() {
		return appPushAgreeYn;
	}

	public void setAppPushAgreeYn(String appPushAgreeYn) {
		this.appPushAgreeYn = appPushAgreeYn;
	}

	public String getPushSndProcSno() {
		return pushSndProcSno;
	}

	public void setPushSndProcSno(String pushSndProcSno) {
		this.pushSndProcSno = pushSndProcSno;
	}

	public String getProcSt() {
		return procSt;
	}

	public void setProcSt(String procSt) {
		this.procSt = procSt;
	}

	public String getSndProcDt() {
		return sndProcDt;
	}

	public void setSndProcDt(String sndProcDt) {
		this.sndProcDt = sndProcDt;
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

	public String[] getAndOsVerList() {
		return andOsVerList;
	}

	public void setAndOsVerList(String[] andOsVerList) {
		this.andOsVerList = andOsVerList;
	}

	public String[] getAndInstlVerList() {
		return andInstlVerList;
	}

	public void setAndInstlVerList(String[] andInstlVerList) {
		this.andInstlVerList = andInstlVerList;
	}

	public String[] getIosOsVerList() {
		return iosOsVerList;
	}

	public void setIosOsVerList(String[] iosOsVerList) {
		this.iosOsVerList = iosOsVerList;
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

	public String[] getCustDivList() {
		return custDivList;
	}

	public void setCustDivList(String[] custDivList) {
		this.custDivList = custDivList;
	}

	public String[] getCustGradeList() {
		return custGradeList;
	}

	public void setCustGradeList(String[] custGradeList) {
		this.custGradeList = custGradeList;
	}

	public String[] getCustDtlGradeList() {
		return custDtlGradeList;
	}

	public void setCustDtlGradeList(String[] custDtlGradeList) {
		this.custDtlGradeList = custDtlGradeList;
	}

	public String[] getMbrPositDtlList() {
		return mbrPositDtlList;
	}

	public void setMbrPositDtlList(String[] mbrPositDtlList) {
		this.mbrPositDtlList = mbrPositDtlList;
	}

	

	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public int getSuccessCnt() {
		return successCnt;
	}

	public void setSuccessCnt(int successCnt) {
		this.successCnt = successCnt;
	}

	public int getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(int failCnt) {
		this.failCnt = failCnt;
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

	public String getSysModId() {
		return sysModId;
	}

	public void setSysModId(String sysModId) {
		this.sysModId = sysModId;
	}

	public String getSysRegIp() {
		return sysRegIp;
	}

	public void setSysRegIp(String sysRegIp) {
		this.sysRegIp = sysRegIp;
	}
	
	
	

}
