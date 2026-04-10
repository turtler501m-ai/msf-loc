package com.ktmmobile.mcp.common.dto.db;

import com.ktmmobile.mcp.common.util.DateTimeUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NmcpEventBoardBasDto.java
 * 날짜     : 2016. 6. 23. 오후 2:28:01
 * 작성자   : papier
 * 설명     : 이벤트게시판기본 테이블(NMCP_EVENT_BOARD_BAS)
 * </pre>
 */
public class NmcpEventBoardBasDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 게시물일련번호 */
    private long ntcartSeq;
    /** 게시물제목 */
    private String ntcartSubject;
    /** 목록노출여부 */
    private String listViewYn;
    /** 이벤트노출여부 */
    private String eventViewYn;
    /** 목록이미지 */
    private String listImg;
    /** 이미지설명 */
    private String imgDesc;
    /** 이벤트시작일 */
    private Date eventStartDt;
    /** 이벤트종료일 */
    private Date eventEndDt;
    /** 게시물내용 */
    private String ntcartSbst;
    /** 작성자 */
    private String ntcartWriter;
    /** 작성일 */
    private Date ntcartWriteDt;
    /** 조회수 */
    private long ntcartHitCnt;
    /** 생성자아이디 */
    private String cretId;
    /** 수정자아이디 */
    private String amdId;
    /** 생성일시 */
    private Date cretDt;
    /** 수정일시 */
    private Date amdDt;
    /** 이벤트,제휴,기획전 구분코드 */
    private String sbstCtg;
    /** 새창여부 */
    private String linkTarget;
    /** 배너를 클릭했을때 연결할 외부사이트 URL */
    private String linkUrlAdr;
    /** 노출되는 프론트의 URL */
    private String eventUrlAdr;
    /** 기획전 한정 소제목 */
    private String plnSmallTitle;
    /** 기획전 한정 내용 */
    private String plnContent;
    /** 이벤트 시작 시각 */
    private String startHour;
    /** 이벤트 종료 시각 */
    private String endHour;
    /** SNS에 표시될 내용 */
    private String snsSbst;
    /** 모바일 이벤트 내용 */
    private String ntcartSbst2;
    /** 모바일 기획전 내용 */
    private String plnContent2;
    /** 이벤트 당첨자 발표 */
    private String winnerYn;
    /** 이벤트 당첨자 발표 */
    private String eventStatus;

    private String adminEventDate;
    
    public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public long getNtcartSeq() {
        return ntcartSeq;
    }
    public void setNtcartSeq(long ntcartSeq) {
        this.ntcartSeq = ntcartSeq;
    }
    public String getNtcartSubject() {
        return ntcartSubject;
    }
    public void setNtcartSubject(String ntcartSubject) {
        this.ntcartSubject = ntcartSubject;
    }
    public String getListViewYn() {
        return listViewYn;
    }
    public void setListViewYn(String listViewYn) {
        this.listViewYn = listViewYn;
    }
    public String getEventViewYn() {
        return eventViewYn;
    }
    public void setEventViewYn(String eventViewYn) {
        this.eventViewYn = eventViewYn;
    }
    public String getListImg() {
        return listImg;
    }
    public void setListImg(String listImg) {
        this.listImg = listImg;
    }
    public String getImgDesc() {
        return imgDesc;
    }
    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }
    public Date getEventStartDt() {
        return eventStartDt;
    }
    public void setEventStartDt(Date eventStartDt) {
        this.eventStartDt = eventStartDt;
    }
    public Date getEventEndDt() {
        return eventEndDt;
    }
    public void setEventEndDt(Date eventEndDt) {
        this.eventEndDt = eventEndDt;
    }
    public String getNtcartSbst() {
        return ntcartSbst;
    }
    public void setNtcartSbst(String ntcartSbst) {
        this.ntcartSbst = ntcartSbst;
    }
    public String getNtcartWriter() {
        return ntcartWriter;
    }
    public void setNtcartWriter(String ntcartWriter) {
        this.ntcartWriter = ntcartWriter;
    }
    public Date getNtcartWriteDt() {
        return ntcartWriteDt;
    }
    public void setNtcartWriteDt(Date ntcartWriteDt) {
        this.ntcartWriteDt = ntcartWriteDt;
    }
    public long getNtcartHitCnt() {
        return ntcartHitCnt;
    }
    public void setNtcartHitCnt(long ntcartHitCnt) {
        this.ntcartHitCnt = ntcartHitCnt;
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
    public String getSbstCtg() {
        return sbstCtg;
    }
    public void setSbstCtg(String sbstCtg) {
        this.sbstCtg = sbstCtg;
    }
    public String getLinkTarget() {
        return linkTarget;
    }
    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }
    public String getLinkUrlAdr() {
        return linkUrlAdr;
    }
    public void setLinkUrlAdr(String linkUrlAdr) {
        this.linkUrlAdr = linkUrlAdr;
    }
    public String getEventUrlAdr() {
        return eventUrlAdr;
    }
    public void setEventUrlAdr(String eventUrlAdr) {
        this.eventUrlAdr = eventUrlAdr;
    }
    public String getPlnSmallTitle() {
        return plnSmallTitle;
    }
    public void setPlnSmallTitle(String plnSmallTitle) {
        this.plnSmallTitle = plnSmallTitle;
    }
    public String getPlnContent() {
        return plnContent;
    }
    public void setPlnContent(String plnContent) {
        this.plnContent = plnContent;
    }
    public String getStartHour() {
        return startHour;
    }
    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }
    public String getEndHour() {
        return endHour;
    }
    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }
    public String getSnsSbst() {
        return snsSbst;
    }
    public void setSnsSbst(String snsSbst) {
        this.snsSbst = snsSbst;
    }
    public String getNtcartSbst2() {
        return ntcartSbst2;
    }
    public void setNtcartSbst2(String ntcartSbst2) {
        this.ntcartSbst2 = ntcartSbst2;
    }
    public String getPlnContent2() {
        return plnContent2;
    }
    public void setPlnContent2(String plnContent2) {
        this.plnContent2 = plnContent2;
    }
    public String getWinnerYn() {
        return winnerYn;
    }
    public void setWinnerYn(String winnerYn) {
        this.winnerYn = winnerYn;
    }
    public boolean getEventEndDtToAfterDate() {
        return DateTimeUtil.chcekToAfterDate(this.eventEndDt);
    }

    public String getAdminEventDate() {
        return adminEventDate;
    }

    public void setAdminEventDate(String adminEventDate) {
        this.adminEventDate = adminEventDate;
    }
}
