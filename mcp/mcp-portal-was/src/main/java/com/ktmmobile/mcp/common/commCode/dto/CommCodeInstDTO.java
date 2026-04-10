package com.ktmmobile.mcp.common.commCode.dto;

import java.io.Serializable;

public class CommCodeInstDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /*테이블 insert 및 update*/
    private String cdGroupId;
    private String cdGroupName;
    private String cdGroupDesc;
    private String status;
    private String cretId;
    private String cretDtFst;
    private String pageNo;
    private String inputSearch;
    private String siteCode;

    private String[] dtlCd;
    private String[] dtlCdNm;
    private String[] useYN;
    private String[] expandStrOne;
    private String[] expandStrTwo;
    private String[] expandStrThree;
    private String[] dtlCdDescArr;
    private int indcOdrg;
    private String amdId;
    private String cretDtSnd;

    /* 뷰 상세페이지에 쓰이는 key */
    private int idx;

    /* 2차코드 테이블 뿌려주기 */
    private int rowNum;
    private String viewDtlCd;
    private String viewDdtlCdNm;
    private String viewUseYN;
    private String[] insertDtSnd;
    private String viewExpandStrOne;
    private String viewExpandStrTwo;
    private String viewExpandStrThree;
    private String dtlCdDesc ;

    public String getInputSearch() {
        return inputSearch;
    }
    public void setInputSearch(String inputSearch) {
        this.inputSearch = inputSearch;
    }
    public String getPageNo() {
        return pageNo;
    }
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
    public String getCretId() {
        return cretId;
    }
    public String setCretId(String cretId) {
        return this.cretId = cretId;
    }
    public String getViewExpandStrOne() {
        return viewExpandStrOne;
    }
    public void setViewExpandStrOne(String viewExpandStrOne) {
        this.viewExpandStrOne = viewExpandStrOne;
    }
    public String getViewExpandStrTwo() {
        return viewExpandStrTwo;
    }
    public void setViewExpandStrTwo(String viewExpandStrTwo) {
        this.viewExpandStrTwo = viewExpandStrTwo;
    }
    public String getViewExpandStrThree() {
        return viewExpandStrThree;
    }
    public void setViewExpandStrThree(String viewExpandStrThree) {
        this.viewExpandStrThree = viewExpandStrThree;
    }
    public String[] getExpandStrOne() {
        return expandStrOne;
    }
    public void setExpandStrOne(String[] expandStrOne) {
        this.expandStrOne = expandStrOne;
    }
    public String[] getExpandStrTwo() {
        return expandStrTwo;
    }
    public void setExpandStrTwo(String[] expandStrTwo) {
        this.expandStrTwo = expandStrTwo;
    }
    public String[] getExpandStrThree() {
        return expandStrThree;
    }
    public void setExpandStrThree(String[] expandStrThree) {
        this.expandStrThree = expandStrThree;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public int getIdx() {
        return idx;
    }
    public String[] getInsertDtSnd() {
        return insertDtSnd;
    }
    public void setInsertDtSnd(String[] insertDtSnd) {
        this.insertDtSnd = insertDtSnd;
    }
    public String getCretDtSnd() {
        return cretDtSnd;
    }
    public void setCretDtSnd(String cretDtSnd) {
        this.cretDtSnd = cretDtSnd;
    }
    public int getIndcOdrg() {
        return indcOdrg;
    }
    public void setIndcOdrg(int indcOdrg) {
        this.indcOdrg = indcOdrg;
    }
    public int getRowNum() {
        return rowNum;
    }
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
    public String getViewDtlCd() {
        return viewDtlCd;
    }
    public void setViewDtlCd(String viewDtlCd) {
        this.viewDtlCd = viewDtlCd;
    }
    public String getViewDdtlCdNm() {
        return viewDdtlCdNm;
    }
    public void setViewDdtlCdNm(String viewDdtlCdNm) {
        this.viewDdtlCdNm = viewDdtlCdNm;
    }
    public String getViewUseYN() {
        return viewUseYN;
    }
    public void setViewUseYN(String viewUseYN) {
        this.viewUseYN = viewUseYN;
    }
    public String getCretDtFst() {
        return cretDtFst;
    }
    public void setCretDtFst(String cretDtFst) {
        this.cretDtFst = cretDtFst;
    }
    public String getCdGroupId() {
        return cdGroupId;
    }
    public void setCdGroupId(String cdGroupId) {
        this.cdGroupId = cdGroupId;
    }
    public String getCdGroupName() {
        return cdGroupName;
    }
    public void setCdGroupName(String cdGroupName) {
        this.cdGroupName = cdGroupName;
    }
    public String getCdGroupDesc() {
        return cdGroupDesc;
    }
    public void setCdGroupDesc(String cdGroupDesc) {
        this.cdGroupDesc = cdGroupDesc;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String[] getDtlCd() {
        return dtlCd;
    }
    public void setDtlCd(String[] dtlCd) {
        this.dtlCd = dtlCd;
    }
    public String[] getDtlCdNm() {
        return dtlCdNm;
    }
    public void setDtlCdNm(String[] dtlCdNm) {
        this.dtlCdNm = dtlCdNm;
    }
    public String[] getUseYN() {
        return useYN;
    }
    public void setUseYN(String[] useYN) {
        this.useYN = useYN;
    }
    public String getSiteCode() {
        return siteCode;
    }
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
    public String getDtlCdDesc() {
        return dtlCdDesc;
    }
    public void setDtlCdDesc(String dtlCdDesc) {
        this.dtlCdDesc = dtlCdDesc;
    }
    public String[] getDtlCdDescArr() {
        return dtlCdDescArr;
    }
    public void setDtlCdDescArr(String[] dtlCdDescArr) {
        this.dtlCdDescArr = dtlCdDescArr;
    }


}
