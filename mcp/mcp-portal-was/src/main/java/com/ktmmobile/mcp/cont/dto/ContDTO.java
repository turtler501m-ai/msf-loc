package com.ktmmobile.mcp.cont.dto;

import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

/**
 * @author heejung
 *
 */
public class ContDTO  implements Serializable{

    private static final long serialVersionUID = 1L;

    private String contSeq;
    private String contKind;
    private String cdGroupId1;
    private String cdGroupId2;
    private String useYn;
    private String contTitle;
    private String contText;
    private String contContent;
    private String contMobileContent;
    private String pay;
    private String payvat;
    private String payYn;
    private String cretId;
    private String cretDt;
    private String amdId;
    private String amdDt;
    private String contImg;

    private String searchGubun;
    private String searchText;

    private String socCode;
    private MultipartFile uploadImg;

    private String searchCdGroupId1;
    private String searchCdGroupId2;
    private String searchUseYn;
    private String searchPageNo;

    //페이징 - MOBILE
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;  // Pagesize
    private int prevIndex; //이전글
    private int nextIndex; //다음글

    //페이징
    private int totalCount;
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
    public int getPrevIndex() {
        return prevIndex;
    }
    public void setPrevIndex(int prevIndex) {
        this.prevIndex = prevIndex;
    }
    public int getNextIndex() {
        return nextIndex;
    }
    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }
    public String getContSeq() {
        return contSeq;
    }
    public void setContSeq(String contSeq) {
        this.contSeq = contSeq;
    }
    public String getContKind() {
        return contKind;
    }
    public void setContKind(String contKind) {
        this.contKind = contKind;
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
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getContTitle() {
        return contTitle;
    }
    public void setContTitle(String contTitle) {
        this.contTitle = contTitle;
    }
    public String getContText() {
        return contText;
    }
    public void setContText(String contText) {
        this.contText = contText;
    }
    public String getContContent() {
        return contContent;
    }
    public void setContContent(String contContent) {
        this.contContent = contContent;
    }
    public String getPay() {
        return pay;
    }
    public void setPay(String pay) {
        this.pay = pay;
    }
    public String getPayvat() {
        return payvat;
    }
    public void setPayvat(String payvat) {
        this.payvat = payvat;
    }
    public String getPayYn() {
        return payYn;
    }
    public void setPayYn(String payYn) {
        this.payYn = payYn;
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
    public String getSearchGubun() {
        return searchGubun;
    }
    public void setSearchGubun(String searchGubun) {
        this.searchGubun = searchGubun;
    }
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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
    public String getSocCode() {
        return socCode;
    }
    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }
    public MultipartFile getUploadImg() {
        return uploadImg;
    }
    public void setUploadImg(MultipartFile uploadImg) {
        this.uploadImg = uploadImg;
    }
    public String getContImg() {
        return contImg;
    }
    public void setContImg(String contImg) {
        this.contImg = contImg;
    }
    public String getContMobileContent() {
        return contMobileContent;
    }
    public void setContMobileContent(String contMobileContent) {
        this.contMobileContent = contMobileContent;
    }
    /**
     * @return the searchCdGroupId1
     */
    public String getSearchCdGroupId1() {
        return searchCdGroupId1;
    }
    /**
     * @param searchCdGroupId1 the searchCdGroupId1 to set
     */
    public void setSearchCdGroupId1(String searchCdGroupId1) {
        this.searchCdGroupId1 = searchCdGroupId1;
    }
    /**
     * @return the searchCdGroupId2
     */
    public String getSearchCdGroupId2() {
        return searchCdGroupId2;
    }
    /**
     * @param searchCdGroupId2 the searchCdGroupId2 to set
     */
    public void setSearchCdGroupId2(String searchCdGroupId2) {
        this.searchCdGroupId2 = searchCdGroupId2;
    }
    /**
     * @return the searchUseYn
     */
    public String getSearchUseYn() {
        return searchUseYn;
    }
    /**
     * @param searchUseYn the searchUseYn to set
     */
    public void setSearchUseYn(String searchUseYn) {
        this.searchUseYn = searchUseYn;
    }
    /**
     * @return the searchPageNo
     */
    public String getSearchPageNo() {
        return searchPageNo;
    }
    /**
     * @param searchPageNo the searchPageNo to set
     */
    public void setSearchPageNo(String searchPageNo) {
        this.searchPageNo = searchPageNo;
    }


}
