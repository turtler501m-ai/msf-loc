package com.ktmmobile.msf.form.newchange.dto;

import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.Serializable;
import java.util.List;


public class FormDtlDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cdGroupId1;
    private String cdGroupId2;
    private String selectCdGroupId1;
    private String selectCdGroupId2;
    private String docVer;
    private String orgDocVer;
    private String docContent;
    private String useYn;
    private String cretId;
    private String cretDt;
    private String amdId;
    private String amdDt;
    private String cretNm;
    private String modeYn;
    private String formNm ; //약관명
    private String cretDate;

    private int totalCount;

    private String searchCdGroupId1;
    private String searchCdGroupId2;
    private String searchUseYn;
    private int searchPageNo;

    //페이징
    private int rNum;
    private int pageNo;
    private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
    private int pagingStartNo;		//페이지네이션 시작 변수
    private int pagingEndNo;		//페이지네이션 끝 변수
    private int pagingStart;		//페이지 처음 <<
    private int pagingFront;		//페이지 앞을호 <
    private int pagingNext;			//페이지 다음 >
    private int pagingEnd;			//페이지 마지막 >>
    private int pagingSize;			//페이지 사이즈>>
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;  // Pagesize

    private String cdGroupId;
    private String dtlCd;
    private String dtlCdNm;

    private String pHolder;

    //이벤트 타이머기능 이중화
    private String eventStartDt;
    private String eventEndDt;
    private String startHour;
    private String endHour;
    private String eventStartDtSec;
    private String eventEndDtSec;
    private String startHourSec;
    private String endHourSec;
    private String docContentSec;

    private List<McpUserCntrMngDto> cntrList;
    private String docType;
    private String expnsnStrVal;
    private String name;

    private String adminEventDate;

    public FormDtlDTO() {}

    public FormDtlDTO(String cdGroupId1, String cdGroupId2) {
        this.cdGroupId1 = cdGroupId1;
        this.cdGroupId2 = cdGroupId2;
    }

    public String getUnescapedDocContent() {
        return StringEscapeUtils.unescapeHtml(this.docContent);
    }

    public String getCdGroupId() {
        return cdGroupId;
    }
    public void setCdGroupId(String cdGroupId) {
        this.cdGroupId = cdGroupId;
    }
    public String getDtlCd() {
        return dtlCd;
    }
    public void setDtlCd(String dtlCd) {
        this.dtlCd = dtlCd;
    }
    public String getDtlCdNm() {
        return dtlCdNm;
    }
    public void setDtlCdNm(String dtlCdNm) {
        this.dtlCdNm = dtlCdNm;
    }
    public String getCretDate() {
        return cretDate;
    }
    public void setCretDate(String cretDate) {
        this.cretDate = cretDate;
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
    public String getCdGroupId1() {
        return cdGroupId1;
    }
    public void setCdGroupId1(String cdGroupId1) {
        this.cdGroupId1 = cdGroupId1;
    }
    public String getCdGroupId2() {
        return cdGroupId2;
    }
    public void setCdGroupId2(String cdGroupId2) {
        this.cdGroupId2 = cdGroupId2;
    }

    public String getSelectCdGroupId1() {
        return selectCdGroupId1;
    }
    public void setSelectCdGroupId1(String selectCdGroupId1) {
        this.selectCdGroupId1 = selectCdGroupId1;
    }
    public String getSelectCdGroupId2() {
        return selectCdGroupId2;
    }
    public void setSelectCdGroupId2(String selectCdGroupId2) {
        this.selectCdGroupId2 = selectCdGroupId2;
    }
    public String getDocVer() {
        return docVer;
    }
    public void setDocVer(String docVer) {
        this.docVer = docVer;
    }
    public String getOrgDocVer() {
        return orgDocVer;
    }
    public void setOrgDocVer(String orgDocVer) {
        this.orgDocVer = orgDocVer;
    }
    public String getDocContent() {
        return docContent;
    }
    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getCretDt() {
        return cretDt;
    }
    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public String getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }
    public String getCretNm() {
        return cretNm;
    }
    public void setCretNm(String cretNm) {
        this.cretNm = cretNm;
    }
    public String getModeYn() {
        return modeYn;
    }
    public void setModeYn(String modeYn) {
        this.modeYn = modeYn;
    }
    public int getrNum() {
        return rNum;
    }
    public void setrNum(int rNum) {
        this.rNum = rNum;
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
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public String getSearchCdGroupId1() {
        return searchCdGroupId1;
    }
    public void setSearchCdGroupId1(String searchCdGroupId1) {
        this.searchCdGroupId1 = searchCdGroupId1;
    }
    public String getSearchCdGroupId2() {
        return searchCdGroupId2;
    }
    public void setSearchCdGroupId2(String searchCdGroupId2) {
        this.searchCdGroupId2 = searchCdGroupId2;
    }
    public String getSearchUseYn() {
        return searchUseYn;
    }
    public void setSearchUseYn(String searchUseYn) {
        this.searchUseYn = searchUseYn;
    }
    public int getSearchPageNo() {
        return searchPageNo;
    }
    public void setSearchPageNo(int searchPageNo) {
        this.searchPageNo = searchPageNo;
    }
    public String getFormNm() {
        return formNm;
    }
    public void setFormNm(String formNm) {
        this.formNm = formNm;
    }
    public String getpHolder() {
        return pHolder;
    }
    public void setpHolder(String eventHolder) {
        this.pHolder = eventHolder;
    }
    public List<McpUserCntrMngDto> getCntrList() {
        return cntrList;
    }
    public void setCntrList(List<McpUserCntrMngDto> cntrList) {
        this.cntrList = cntrList;
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
    public String getEventStartDtSec() {
        return eventStartDtSec;
    }
    public void setEventStartDtSec(String eventStartDtSec) {
        this.eventStartDtSec = eventStartDtSec;
    }
    public String getEventEndDtSec() {
        return eventEndDtSec;
    }
    public void setEventEndDtSec(String eventEndDtSec) {
        this.eventEndDtSec = eventEndDtSec;
    }
    public String getStartHourSec() {
        return startHourSec;
    }
    public void setStartHourSec(String startHourSec) {
        this.startHourSec = startHourSec;
    }
    public String getEndHourSec() {
        return endHourSec;
    }
    public void setEndHourSec(String endHourSec) {
        this.endHourSec = endHourSec;
    }
    public String getDocContentSec() {
        return docContentSec;
    }
    public void setDocContentSec(String docContentSec) {
        this.docContentSec = docContentSec;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getExpnsnStrVal() {
        return expnsnStrVal;
    }

    public void setExpnsnStrVal(String expnsnStrVal) {
        this.expnsnStrVal = expnsnStrVal;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminEventDate() {
        return adminEventDate;
    }

    public void setAdminEventDate(String adminEventDate) {
        this.adminEventDate = adminEventDate;
    }
}
