package com.ktmmobile.mcp.event.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

public class EventBenefitDtlDto  implements Serializable {

    private static final long serialVersionUID = 1132491896347576062L;

    private MultipartFile file;

    /** 이벤트 혜택 관리 기본 일련번호  */
    private long eventBenefitSeq;

    /** 이벤트 혜택 관리 기본 일련번호 _복사할  */
    private long eventBenefitCopySeq;


    /** 혜택리스트 일련번호 */
    private long dtlSeq;
    /** A, B 구분 A :1 B:2  */
    private String benefitModType;
    /** 혜택 이미지  */
    private String benefitDtlImgNm;
    /** 혜택 제목  */
    private String benefitDtlNm;
    /** 혜택 설명  */
    private String benefitDtlDesc;
    /** 정렬번호 */
    private long sortKey;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

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

    public long getDtlSeq() {
        return dtlSeq;
    }

    public void setDtlSeq(long dtlSeq) {
        this.dtlSeq = dtlSeq;
    }

    public String getBenefitModType() {
        return benefitModType;
    }

    public void setBenefitModType(String benefitModType) {
        this.benefitModType = benefitModType;
    }

    public String getBenefitDtlImgNm() {
        return benefitDtlImgNm;
    }

    public void setBenefitDtlImgNm(String benefitDtlImgNm) {
        this.benefitDtlImgNm = benefitDtlImgNm;
    }

    public String getBenefitDtlNm() {
        return benefitDtlNm;
    }

    public void setBenefitDtlNm(String benefitDtlNm) {
        this.benefitDtlNm = benefitDtlNm;
    }

    public String getBenefitDtlDesc() {
        return benefitDtlDesc;
    }

    public void setBenefitDtlDesc(String benefitDtlDesc) {
        this.benefitDtlDesc = benefitDtlDesc;
    }

    public long getSortKey() {
        return sortKey;
    }

    public void setSortKey(long sortKey) {
        this.sortKey = sortKey;
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


}