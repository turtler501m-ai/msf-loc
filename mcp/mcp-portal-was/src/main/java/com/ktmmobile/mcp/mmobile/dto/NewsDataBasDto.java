package com.ktmmobile.mcp.mmobile.dto;

import java.util.Date;
import java.io.Serializable;

public class NewsDataBasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int newsDatSeq;
    private String newsDatSubject;
    private String statVal;
    private String newsDatImg;
    private String imgDesc;
    private String newsDatSbst;
    private int newsDatHitCnt;
    private String linkUrlAdr;
    private String notiYn;
    private String linkTarget;
    private String cretId;
    private String amdId;
    private Date cretDt;
    private Date amdDt;
    private int nextSeq;
    private String nextSubject;
    private Date nextDt;
    private int preSeq;
    private String preSubject;
    private Date preDt;
    private String newsLineSbst;
    private String notiImgPath;
    private String notiImgDesc;
    private Date upDt;
    private Date toDay;
    private int rn;
    private String searchNm;
    private String searchOpt;
    private String searchValue;

    //페이징
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;  // Pagesize

    private int pageNo;
    private String searchYn;
    private Date stDt;
    private Date edDt;


    public int getRn() {
        return rn;
    }
    public void setRn(int rn) {
        this.rn = rn;
    }
    public Date getToDay() {
        return toDay;
    }
    public void setToDay(Date toDay) {
        this.toDay = toDay;
    }
    public Date getUpDt() {
        return upDt;
    }
    public void setUpDt(Date upDt) {
        this.upDt = upDt;
    }

    public Date getStDt() {
        return stDt;
    }
    public void setStDt(Date stDt) {
        this.stDt = stDt;
    }
    public Date getEdDt() {
        return edDt;
    }
    public void setEdDt(Date edDt) {
        this.edDt = edDt;
    }
    /*검색*/



    public int getNewsDatSeq() {
        return newsDatSeq;
    }
    public void setNewsDatSeq(int newsDatSeq) {
        this.newsDatSeq = newsDatSeq;
    }
    public String getNewsDatSubject() {
        return newsDatSubject;
    }
    public void setNewsDatSubject(String newsDatSubject) {
        this.newsDatSubject = newsDatSubject;
    }
    public String getStatVal() {
        return statVal;
    }
    public void setStatVal(String statVal) {
        this.statVal = statVal;
    }
    public String getNewsDatImg() {
        return newsDatImg;
    }
    public void setNewsDatImg(String newsDatImg) {
        this.newsDatImg = newsDatImg;
    }
    public String getImgDesc() {
        return imgDesc;
    }
    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }
    public String getNewsDatSbst() {
        return newsDatSbst;
    }
    public void setNewsDatSbst(String newsDatSbst) {
        this.newsDatSbst = newsDatSbst;
    }

    public int getNewsDatHitCnt() {
        return newsDatHitCnt;
    }
    public void setNewsDatHitCnt(int newsDatHitCnt) {
        this.newsDatHitCnt = newsDatHitCnt;
    }
    public String getLinkUrlAdr() {
        return linkUrlAdr;
    }
    public void setLinkUrlAdr(String linkUrlAdr) {
        this.linkUrlAdr = linkUrlAdr;
    }

    public String getNotiYn() {
        return notiYn;
    }
    public void setNotiYn(String notiYn) {
        this.notiYn = notiYn;
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
    public String getNewsLineSbst() {
        return newsLineSbst;
    }
    public void setNewsLineSbst(String newsLineSbst) {
        this.newsLineSbst = newsLineSbst;
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
    public String getNotiImgPath() {
        return notiImgPath;
    }
    public void setNotiImgPath(String notiImgPath) {
        this.notiImgPath = notiImgPath;
    }
    public String getNotiImgDesc() {
        return notiImgDesc;
    }
    public void setNotiImgDesc(String notiImgDesc) {
        this.notiImgDesc = notiImgDesc;
    }
    public String getSearchYn() {
        return searchYn;
    }
    public void setSearchYn(String searchYn) {
        this.searchYn = searchYn;
    }



}
