package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;


public class EventBoardDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private int rnum;
    private int ntcartSeq;
    private String sbstCtg;
    private String sbstCtgNm;
    private String ntcartSubject;
    private String listViewYn;
    private String eventViewYn;
    private String listImg;
    private String imgUrl;
    private String imgDesc;
    private String eventStartDt;
    private String eventEndDt;
    private String ntcartSbst;
    private String ntcartWriter;
    private String ntcartWriteDt;

    private int ntcartHitCnt;
    private String eventUrlAdr;
    private String linkUrlAdr;
    private String linkTarget;
    private String linkUrlAdrMo;
    private String linkTargetMo;
    private String cretId;
    private String amdId;
    private Date cretDt;
    private Date amdDt;
    private String searchNm;
    private String searchOpt;
    private String exposureSrc;
    private String plnSmallTitle;
    private String plnContent;
    private String formJson;
    private String searchValue;
    private String startHour;
    private String endHour;
    private String searchYn;
    private String[] plnTitle;

    /*프론트 각종 저장값*/
    private int dDay;
    private String chkChoice;
    private String allCh;
    private String egCh;
    private String jeCh;
    private String tabNo;

    //추가 20150218노태기
    private String comEventStartDt;
    private String comEventEndDt;
    private String comCretDt;

    //당첨자발표가 있는지 여부
    private String winnerYn;
    private String wnrSeq;

    //페이징
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;  // Pagesize
    private int pageNo;
    private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
    private int pagingStartNo;		//페이지네이션 시작 변수
    private int pagingEndNo;		//페이지네이션 끝 변수
    private int pagingStart;		//페이지 처음 <<
    private int pagingFront;		//페이지 앞을호 <
    private int pagingNext;			//페이지 다음 >
    private int pagingEnd;			//페이지 마지막 >>
    private int pagingSize;			//페이지 사이즈>>
    private int boardNum;
    private String ntcartSbst2;		//모바일 이벤트 내용
    private String plnContent2;		//모바일 기획전 내용
    private String snsSbst;			//sns에 들어갈 내용
    private int nextSeq;
    private String nextSubject;
    private Date nextDt;
    private int preSeq;
    private String preSubject;
    private Date preDt;
    private int eventOrder;//정렬순서
    private String proModule ; //PC , 모바일 구분
    private String sort;
    private String tgtEvent;

    private String thumbImgNm;//
    private String thumbImgDesc;//
    private String mobileListImgNm;//
    private String eventAdd1Yn;//
    private String eventAdd1Sbst;//
    private String eventAdd2Yn;//
    private String eventAdd3Yn;//
    private String verificationUrl;//

    //이번달 이벤트 상태값
    private String eventStatus;
    //이번달/제휴 이벤트 분기값
    private String eventBranch;

    //친구초대 ID 값
    private String recommend;

    private String eventCategory;

  //댓글이벤트 코드
    private String eventJoinCd;

    private String adminEventDate;

    private String eventHeaderTitle;

    /* meta 추가 */
    private String eventDescription; //디스크립션(meta)
    private String eventKeyword; //키워드(meta)
    private String eventTitle; //타이틀(meta)

    public String getLinkUrlAdrMo() {
        return linkUrlAdrMo;
    }
    public void setLinkUrlAdrMo(String linkUrlAdrMo) {
        this.linkUrlAdrMo = linkUrlAdrMo;
    }
    public String getLinkTargetMo() {
        return linkTargetMo;
    }
    public void setLinkTargetMo(String linkTargetMo) {
        this.linkTargetMo = linkTargetMo;
    }
    public String getEventBranch() {
        return eventBranch;
    }
    public void setEventBranch(String eventBranch) {
        this.eventBranch = eventBranch;
    }
    public String getTgtEvent() {
        return tgtEvent;
    }
    public void setTgtEvent(String tgtEvent) {
        this.tgtEvent = tgtEvent;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String[] getPlnTitle() {
        return plnTitle;
    }
    public void setPlnTitle(String[] plnTitle) {
        this.plnTitle = plnTitle;
    }
    public int getEventOrder() {
        return eventOrder;
    }
    public void setEventOrder(int eventOrder) {
        this.eventOrder = eventOrder;
    }
    public int getRnum() {
        return rnum;
    }
    public void setRnum(int rnum) {
        this.rnum = rnum;
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
    public String getNtcartSubject() {
        return ntcartSubject;
    }
    public String getNtcartSubjectRepace() {
        ntcartSubject=ntcartSubject.replace("\'", "");
        ntcartSubject=ntcartSubject.replace("\"", "");
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
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getNtcartWriteDt() {
        return ntcartWriteDt;
    }
    public void setNtcartWriteDt(String ntcartWriteDt) {
        this.ntcartWriteDt = ntcartWriteDt;
    }
    public String getSearchNm() {
        return searchNm;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public int getNtcartHitCnt() {
        return ntcartHitCnt;
    }
    public void setNtcartHitCnt(int ntcartHitCnt) {
        this.ntcartHitCnt = ntcartHitCnt;
    }
    public String getEventUrlAdr() {
        return eventUrlAdr;
    }
    public void setEventUrlAdr(String eventUrlAdr) {
        this.eventUrlAdr = eventUrlAdr;
    }
    public String getLinkUrlAdr() {
        return linkUrlAdr;
    }
    public void setLinkUrlAdr(String linkUrlAdr) {
        this.linkUrlAdr = linkUrlAdr;
    }
    public String getLinkTarget() {
        return linkTarget;
    }
    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
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
    public String getFormJson() {
        return formJson;
    }
    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }
    public String getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
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
    public String getSearchYn() {
        return searchYn;
    }
    public void setSearchYn(String searchYn) {
        this.searchYn = searchYn;
    }
    public int getdDay() {
        return dDay;
    }
    public void setdDay(int dDay) {
        this.dDay = dDay;
    }
    public String getChkChoice() {
        return chkChoice;
    }
    public void setChkChoice(String chkChoice) {
        this.chkChoice = chkChoice;
    }
    public String getAllCh() {
        return allCh;
    }
    public void setAllCh(String allCh) {
        this.allCh = allCh;
    }
    public String getEgCh() {
        return egCh;
    }
    public void setEgCh(String egCh) {
        this.egCh = egCh;
    }
    public String getJeCh() {
        return jeCh;
    }
    public void setJeCh(String jeCh) {
        this.jeCh = jeCh;
    }
    public String getTabNo() {
        return tabNo;
    }
    public void setTabNo(String tabNo) {
        this.tabNo = tabNo;
    }
    public String getWinnerYn() {
        return winnerYn;
    }
    public void setWinnerYn(String winnerYn) {
        this.winnerYn = winnerYn;
    }
    public String getWnrSeq() {
        return wnrSeq;
    }
    public void setWnrSeq(String wnrSeq) {
        this.wnrSeq = wnrSeq;
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
    public String getExposureSrc() {
        return exposureSrc;
    }
    public void setExposureSrc(String exposureSrc) {
        this.exposureSrc = exposureSrc;
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
    public int getBoardNum() {
        return boardNum;
    }
    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }
    public String getComEventStartDt() {
        return comEventStartDt;
    }
    public void setComEventStartDt(String comEventStartDt) {
        this.comEventStartDt = comEventStartDt;
    }
    public String getComEventEndDt() {
        return comEventEndDt;
    }
    public void setComEventEndDt(String comEventEndDt) {
        this.comEventEndDt = comEventEndDt;
    }
    public String getComCretDt() {
        return comCretDt;
    }
    public void setComCretDt(String comCretDt) {
        this.comCretDt = comCretDt;
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
    public String getSnsSbst() {
        return snsSbst;
    }
    public void setSnsSbst(String snsSbst) {
        this.snsSbst = snsSbst;
    }
    public int getNextSeq() {
        return nextSeq;
    }
    public void setNextSeq(int nextSeq) {
        this.nextSeq = nextSeq;
    }
    public String getNextSubject() {
        return nextSubject;
    }
    public void setNextSubject(String nextSubject) {
        this.nextSubject = nextSubject;
    }
    public Date getNextDt() {
        return nextDt;
    }
    public void setNextDt(Date nextDt) {
        this.nextDt = nextDt;
    }
    public int getPreSeq() {
        return preSeq;
    }
    public void setPreSeq(int preSeq) {
        this.preSeq = preSeq;
    }
    public String getPreSubject() {
        return preSubject;
    }
    public void setPreSubject(String preSubject) {
        this.preSubject = preSubject;
    }
    public Date getPreDt() {
        return preDt;
    }
    public void setPreDt(Date preDt) {
        this.preDt = preDt;
    }
    public String getProModule() {
        return proModule;
    }
    public void setProModule(String proModule) {
        this.proModule = proModule;
    }
    public String getThumbImgNm() {
        return thumbImgNm;
    }
    public void setThumbImgNm(String thumbImgNm) {
        this.thumbImgNm = thumbImgNm;
    }
    public String getThumbImgDesc() {
        return thumbImgDesc;
    }
    public void setThumbImgDesc(String thumbImgDesc) {
        this.thumbImgDesc = thumbImgDesc;
    }
    public String getMobileListImgNm() {
        return mobileListImgNm;
    }
    public void setMobileListImgNm(String mobileListImgNm) {
        this.mobileListImgNm = mobileListImgNm;
    }
    public String getEventAdd1Yn() {
        return eventAdd1Yn;
    }
    public void setEventAdd1Yn(String eventAdd1Yn) {
        this.eventAdd1Yn = eventAdd1Yn;
    }
    public String getEventAdd1Sbst() {
        return eventAdd1Sbst;
    }
    public void setEventAdd1Sbst(String eventAdd1Sbst) {
        this.eventAdd1Sbst = eventAdd1Sbst;
    }
    public String getEventAdd2Yn() {
        return eventAdd2Yn;
    }
    public void setEventAdd2Yn(String eventAdd2Yn) {
        this.eventAdd2Yn = eventAdd2Yn;
    }
    public String getEventAdd3Yn() {
        return eventAdd3Yn;
    }
    public void setEventAdd3Yn(String eventAdd3Yn) {
        this.eventAdd3Yn = eventAdd3Yn;
    }
    public String getVerificationUrl() {
        return verificationUrl;
    }
    public void setVerificationUrl(String verificationUrl) {
        this.verificationUrl = verificationUrl;
    }
    public String getEventStatus() {
        return eventStatus;
    }
    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
    public String getRecommend() {
        return recommend;
    }
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
    public String getEventCategory() {
        return eventCategory;
    }
    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }
    public String getEventJoinCd() {
        return eventJoinCd;
    }
    public void setEventJoinCd(String eventJoinCd) {
        this.eventJoinCd = eventJoinCd;
    }

    public String getAdminEventDate() {
        return adminEventDate;
    }

    public void setAdminEventDate(String adminEventDate) {
        this.adminEventDate = adminEventDate;
    }
    public String getEventHeaderTitle() {
        return eventHeaderTitle;
    }
    public void setEventHeaderTitle(String eventHeaderTitle) {
        this.eventHeaderTitle = eventHeaderTitle;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public String getEventKeyword() {
        return eventKeyword;
    }
    public void setEventKeyword(String eventKeyword) {
        this.eventKeyword = eventKeyword;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

}
