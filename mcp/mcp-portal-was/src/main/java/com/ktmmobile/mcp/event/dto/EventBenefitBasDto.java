package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EventBenefitBasDto implements Serializable {

    private static final long serialVersionUID= -1243707472229450009L;

    /** 이벤트 혜택 관리 기본 일련번호  */
    private long eventBenefitSeq;

    /** 이벤트 혜택 관리 기본 일련번호 _복사할  */
    private long eventBenefitCopySeq;

    /** 이벤트 혜택 관리 기본 제목 */
    private String eventBenefitNm;
    /** 게시기간 시작 A */
    private String pstngStartDate;
    /** 게시기간 끝 A */
    private String pstngEndDate;
    /** 시기간 시작 B */
    private String pstngStartSecDate;
    /** 게시기간 끝 B */
    private String pstngEndSecDate;
    /** 정렬번호 */
    private long sortKey;
    /** 사용여부 */
    private String useYn;
    /** 등록아이피 */
    private String cretIp;
    /** 등록일자 */
    private Date cretDt;
    /** 등록 사용자 아이디 */
    private String cretId;
    /** 수정자 아이피 */
    private String amdIp;
    /** 수정일자 */
    private Date amdDt;
    /** 수정자 아이디 */
    private String amdId;

    /** A, B 구분 A :1 B:2  */
    private String benefitModType;

    /** 이벤트 혜택 관리 상세  LIST */
    private List<EventBenefitDtlDto> benefitDtlList;


    /** 이벤트 혜택 관계 정보  LIST */
    private List<EventBenefitRelationDto> benefitRelationList;

    private long rowNum;  //ROW_NUM

    public long getEventBenefitSeq() {
        return eventBenefitSeq;
    }

    public void setEventBenefitSeq(long eventBenefitSeq) {
        this.eventBenefitSeq = eventBenefitSeq;
    }

    public long getEventBenefitCopySeq() {
        return eventBenefitCopySeq;
    }

    public void setEventBenefitCopySeq(long eventBenefitCopySeq) {
        this.eventBenefitCopySeq = eventBenefitCopySeq;
    }

    public String getEventBenefitNm() {
        return eventBenefitNm;
    }

    public void setEventBenefitNm(String eventBenefitNm) {
        this.eventBenefitNm = eventBenefitNm;
    }

    public String getPstngStartDate() {
        return pstngStartDate;
    }

    public void setPstngStartDate(String pstngStartDate) {
        this.pstngStartDate = pstngStartDate;
    }

    public String getPstngEndDate() {
        return pstngEndDate;
    }

    public void setPstngEndDate(String pstngEndDate) {
        this.pstngEndDate = pstngEndDate;
    }

    public String getPstngStartSecDate() {
        return pstngStartSecDate;
    }

    public void setPstngStartSecDate(String pstngStartSecDate) {
        this.pstngStartSecDate = pstngStartSecDate;
    }

    public String getPstngEndSecDate() {
        return pstngEndSecDate;
    }

    public void setPstngEndSecDate(String pstngEndSecDate) {
        this.pstngEndSecDate = pstngEndSecDate;
    }

    public long getSortKey() {
        return sortKey;
    }

    public void setSortKey(long sortKey) {
        this.sortKey = sortKey;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdIp() {
        return amdIp;
    }

    public void setAmdIp(String amdIp) {
        this.amdIp = amdIp;
    }

    public Date getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public List<EventBenefitDtlDto> getBenefitDtlList() {
        return benefitDtlList;
    }

    public void setBenefitDtlList(List<EventBenefitDtlDto> benefitDtlList) {
        this.benefitDtlList = benefitDtlList;
    }

    public long getRowNum() {
        return rowNum;
    }

    public void setRowNum(long rowNum) {
        this.rowNum = rowNum;
    }

    public List<EventBenefitRelationDto> getBenefitRelationList() {
        return benefitRelationList;
    }

    public void setBenefitRelationList(List<EventBenefitRelationDto> benefitRelationList) {
        this.benefitRelationList = benefitRelationList;
    }

    public String getBenefitModType() {
        return benefitModType;
    }

    public void setBenefitModType(String benefitModType) {
        this.benefitModType = benefitModType;
    }
}
