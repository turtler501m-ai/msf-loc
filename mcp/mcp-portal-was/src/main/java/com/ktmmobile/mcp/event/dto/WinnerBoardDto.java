package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;

public class WinnerBoardDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private int pwnrSeq;
	private int ntcartSeq;
	private String sbstCtg;
	private String sbstCtgNm;
	private String pwnrSubject;
	private String statVal;
	private String eventStartDt;
	private String eventEndDt;
	private String pwnrSbst;
	private String pwnrHitCnt;
	private String cretId;
	private String amdId;
	private Date cretDt;
	private Date amdDt;
	private String searchNm;
	private String searchOpt;
	private String searchValue;
	private int dDay;	/*이벤트 종료일 Dday*/


	private int boardNum;

	private String tempEventSubj;	//당첨자관리에서 부모이벤트의 제목을 보여줌

	//추가 20160218노태기
	private String conEventStartDt;
	private String conEventEndDt;
	private String conCretDt;

	//페이징
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;  // Pagesize

	private int pageNo;
	private int pageNo2;
	private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
	private int pagingStartNo;		//페이지네이션 시작 변수
	private int pagingEndNo;		//페이지네이션 끝 변수
	private int pagingStart;		//페이지 처음 <<
	private int pagingFront;		//페이지 앞을호 <
	private int pagingNext;			//페이지 다음 >
	private int pagingEnd;			//페이지 마지막 >>
	private int pagingSize;			//페이지 사이즈>>

	private String tabNo;
	private String chkChoice;
	private String ntcartSubject;
	private String searchYn;
	
	//이벤트/제휴 분기값
	private String eventBranch;

	public String getNtcartSubject() {
		return ntcartSubject;
	}
	public void setNtcartSubject(String ntcartSubject) {
		this.ntcartSubject = ntcartSubject;
	}
	public int getPwnrSeq() {
		return pwnrSeq;
	}
	public void setPwnrSeq(int pwnrSeq) {
		this.pwnrSeq = pwnrSeq;
	}
	public int getNtcartSeq() {
		return ntcartSeq;
	}
	public void setNtcartSeq(int ntcartSeq) {
		this.ntcartSeq = ntcartSeq;
	}
	public String getSbstCtg() {
		return sbstCtg;
	}
	public void setSbstCtg(String sbstCtg) {
		this.sbstCtg = sbstCtg;
	}
	public String getSbstCtgNm() {
		return sbstCtgNm;
	}
	public void setSbstCtgNm(String sbstCtgNm) {
		this.sbstCtgNm = sbstCtgNm;
	}
	public String getPwnrSubject() {
		return pwnrSubject;
	}
	public void setPwnrSubject(String pwnrSubject) {
		this.pwnrSubject = pwnrSubject;
	}
	public String getStatVal() {
		return statVal;
	}
	public void setStatVal(String statVal) {
		this.statVal = statVal;
	}


	public String getEventStartDt() {
		return eventStartDt;
	}
	public void setEventStartDt(String eventStartDt) {
		this.eventStartDt = eventStartDt;
	}
	public String getEventEndDt() {
		return eventEndDt;
	}
	public void setEventEndDt(String eventEndDt) {
		this.eventEndDt = eventEndDt;
	}
	public String getPwnrSbst() {
		return pwnrSbst;
	}
	public void setPwnrSbst(String pwnrSbst) {
		this.pwnrSbst = pwnrSbst;
	}
	public String getPwnrHitCnt() {
		return pwnrHitCnt;
	}
	public void setPwnrHitCnt(String pwnrHitCnt) {
		this.pwnrHitCnt = pwnrHitCnt;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}

	public Date getCretDt() {
		return cretDt;
	}
	public void setCretDt(Date cretDt) {
		this.cretDt = cretDt;
	}
	public Date getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(Date amdDt) {
		this.amdDt = amdDt;
	}
	public String getSearchNm() {
		return searchNm;
	}
	public void setSearchNm(String searchNm) {
		this.searchNm = searchNm;
	}
	public String getSearchOpt() {
		return searchOpt;
	}
	public void setSearchOpt(String searchOpt) {
		this.searchOpt = searchOpt;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public int getdDay() {
		return dDay;
	}
	public void setdDay(int dDay) {
		this.dDay = dDay;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getTempEventSubj() {
		return tempEventSubj;
	}
	public void setTempEventSubj(String tempEventSubj) {
		this.tempEventSubj = tempEventSubj;
	}
	public int getSkipResult() {
		return skipResult;
	}
	public void setSkipResult(int skipResult) {
		this.skipResult = skipResult;
	}
	public int getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageNo2() {
		return pageNo2;
	}
	public void setPageNo2(int pageNo2) {
		this.pageNo2 = pageNo2;
	}
	public int getPagingPosition() {
		return pagingPosition;
	}
	public void setPagingPosition(int pagingPosition) {
		this.pagingPosition = pagingPosition;
	}
	public int getPagingStartNo() {
		return pagingStartNo;
	}
	public void setPagingStartNo(int pagingStartNo) {
		this.pagingStartNo = pagingStartNo;
	}
	public int getPagingEndNo() {
		return pagingEndNo;
	}
	public void setPagingEndNo(int pagingEndNo) {
		this.pagingEndNo = pagingEndNo;
	}
	public int getPagingStart() {
		return pagingStart;
	}
	public void setPagingStart(int pagingStart) {
		this.pagingStart = pagingStart;
	}
	public int getPagingFront() {
		return pagingFront;
	}
	public void setPagingFront(int pagingFront) {
		this.pagingFront = pagingFront;
	}
	public int getPagingNext() {
		return pagingNext;
	}
	public void setPagingNext(int pagingNext) {
		this.pagingNext = pagingNext;
	}
	public int getPagingEnd() {
		return pagingEnd;
	}
	public void setPagingEnd(int pagingEnd) {
		this.pagingEnd = pagingEnd;
	}
	public int getPagingSize() {
		return pagingSize;
	}
	public void setPagingSize(int pagingSize) {
		this.pagingSize = pagingSize;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTabNo() {
		return tabNo;
	}
	public void setTabNo(String tabNo) {
		this.tabNo = tabNo;
	}
	public String getChkChoice() {
		return chkChoice;
	}
	public void setChkChoice(String chkChoice) {
		this.chkChoice = chkChoice;
	}
	public String getConEventStartDt() {
		return conEventStartDt;
	}
	public void setConEventStartDt(String conEventStartDt) {
		this.conEventStartDt = conEventStartDt;
	}
	public String getConEventEndDt() {
		return conEventEndDt;
	}
	public void setConEventEndDt(String conEventEndDt) {
		this.conEventEndDt = conEventEndDt;
	}
	public String getConCretDt() {
		return conCretDt;
	}
	public void setConCretDt(String conCretDt) {
		this.conCretDt = conCretDt;
	}
	public String getSearchYn() {
		return searchYn;
	}
	public void setSearchYn(String searchYn) {
		this.searchYn = searchYn;
	}
	public String getEventBranch() {
		return eventBranch;
	}
	public void setEventBranch(String eventBranch) {
		this.eventBranch = eventBranch;
	}

}
