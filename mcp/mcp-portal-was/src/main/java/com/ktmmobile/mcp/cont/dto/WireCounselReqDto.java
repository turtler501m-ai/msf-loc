package com.ktmmobile.mcp.cont.dto;

import java.io.Serializable;
import java.util.Date;

public class WireCounselReqDto  implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 상담번호 */
    private int counselSeq;
    /** 관심상품번호 */
    private int wireProdDtlSeq;
    /** 성명 */
    private String counselNm;
    /** 연락처 */
    private String counselCtn;
    /** 메모 */
    private String counselMemo;
    /** 신청일자 */
    private Date counselRegDt;
    /** 신청상태(I:진행중, C:고객취소, D:관리자삭제, S:신청완료 */
    private String counselStatus;
    /** 진행상태 */
    private String proStatus;

    /** 수정일시 */
    private Date rvisnDt;
    /** 완료일시 */
    private Date doneDt;

    /** 검색 시작일 */
    private String startDate;

    /** 검색 끝 일 */
    private String endDate ;

    /** 구분코드  중분류 */
    private String wireProdCd;
    /** 사업자 */
    private String wireProdCorp;
    /** 상품명  소분류 */
    private String wireProdName;
    /** 검색일 조건 */
    private String selFlagDt;
    /** 검색 조건 */
    private String searchType;
    /** 검색어 */
    private String searchTxt;


    public int getCounselSeq() {
        return counselSeq;
    }
    public void setCounselSeq(int counselSeq) {
        this.counselSeq = counselSeq;
    }
    public int getWireProdDtlSeq() {
        return wireProdDtlSeq;
    }
    public void setWireProdDtlSeq(int wireProdDtlSeq) {
        this.wireProdDtlSeq = wireProdDtlSeq;
    }
    public String getCounselNm() {
        return counselNm;
    }
    public void setCounselNm(String counselNm) {
        this.counselNm = counselNm;
    }
    public String getCounselCtn() {
        return counselCtn;
    }
    public void setCounselCtn(String counselCtn) {
        this.counselCtn = counselCtn;
    }
    public String getCounselMemo() {
        return counselMemo;
    }
    public void setCounselMemo(String counselMemo) {
        this.counselMemo = counselMemo;
    }
    public Date getCounselRegDt() {
        return counselRegDt;
    }
    public void setCounselRegDt(Date counselRegDt) {
        this.counselRegDt = counselRegDt;
    }
    public String getCounselStatus() {
        return counselStatus;
    }
    public void setCounselStatus(String counselStatus) {
        this.counselStatus = counselStatus;
    }
    public Date getRvisnDt() {
        return rvisnDt;
    }
    public void setRvisnDt(Date rvisnDt) {
        this.rvisnDt = rvisnDt;
    }
    public Date getDoneDt() {
        return doneDt;
    }
    public void setDoneDt(Date doneDt) {
        this.doneDt = doneDt;
    }
    public String getStartDate() {
        return startDate;
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
    public String getWireProdCd() {
        return wireProdCd;
    }
    public void setWireProdCd(String wireProdCd) {
        this.wireProdCd = wireProdCd;
    }
    public String getWireProdCorp() {
        return wireProdCorp;
    }
    public void setWireProdCorp(String wireProdCorp) {
        this.wireProdCorp = wireProdCorp;
    }
    public String getWireProdName() {
        return wireProdName;
    }
    public void setWireProdName(String wireProdName) {
        this.wireProdName = wireProdName;
    }
    public String getProStatus() {
        return proStatus;
    }
    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }
    public String getSelFlagDt() {
        return selFlagDt;
    }
    public void setSelFlagDt(String selFlagDt) {
        this.selFlagDt = selFlagDt;
    }
    public String getSearchType() {
        return searchType;
    }
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    public String getSearchTxt() {
        return searchTxt;
    }
    public void setSearchTxt(String searchTxt) {
        this.searchTxt = searchTxt;
    }


}
