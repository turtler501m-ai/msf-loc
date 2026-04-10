package com.ktmmobile.msf.domains.form.common.dto.db;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;



/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NmcpCdDtlDto.java
 * 날짜     : 2016. 2. 5. 오전 10:03:37
 * 작성자   : papier
 * 설명     : 공통코드
 * </pre>
 */
public class NmcpCdDtlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 코드그룹아이디 */
    private String cdGroupId;
    private String cdGroupNm;
    private String cdGroupDesc;
    /** 상세코드 */
    private String dtlCd;
    /** 상세코드명 */
    private String dtlCdNm;
    /** 상세코드설명 */
    private String dtlCdDesc;
    /** 표시순서 */
    private long indcOdrg;
    /** 사용여부 */
    private String useYn;
    /** 확장문자열값1 */
    private String expnsnStrVal1;
    /** 확장문자열값2 */
    private String expnsnStrVal2;
    /** 확장문자열값3 */
    private String expnsnStrVal3;
    private String cdGroupCd;
    /** 이미지명 */
    private String imgNm;
    /** 생성자아이디 */
    private String cretId;
    /** 수정자아이디 */
    private String amdId;
    /** 생성일시 */
    private Date cretDt;
    /** 수정일시 */
    private Date amdDt;

    private int eventCdCtn;

    private String pstngStartDate;
    private String pstngEndDate;



    /** group 확장문자열 값*/
    private String gexpnsnStrVal1;
    private String gexpnsnStrVal2;
    private String gexpnsnStrVal3;

    private String docContent;

    /** 상위그룹코드 */
    private String upGrpCd;

    public int getEventCdCtn() {
        return eventCdCtn;
    }
    public void setEventCdCtn(int eventCdCtn) {
        this.eventCdCtn = eventCdCtn;
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
    public String getDtlCdDesc() {
        if (StringUtils.isBlank(dtlCdDesc) ) {
            return "";
        }
        return dtlCdDesc;
    }
    public void setDtlCdDesc(String dtlCdDesc) {
        this.dtlCdDesc = dtlCdDesc;
    }
    public long getIndcOdrg() {
        return indcOdrg;
    }
    public void setIndcOdrg(long indcOdrg) {
        this.indcOdrg = indcOdrg;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getExpnsnStrVal1() {
        if (StringUtils.isBlank(expnsnStrVal1) ) {
            return "";
        }
        return expnsnStrVal1;
    }
    public void setExpnsnStrVal1(String expnsnStrVal1) {
        this.expnsnStrVal1 = expnsnStrVal1;
    }
    public String getExpnsnStrVal2() {
        if (StringUtils.isBlank(expnsnStrVal2) ) {
            return "";
        }
        return expnsnStrVal2;
    }
    public void setExpnsnStrVal2(String expnsnStrVal2) {
        this.expnsnStrVal2 = expnsnStrVal2;
    }
    public String getExpnsnStrVal3() {
        if (StringUtils.isBlank(expnsnStrVal3) ) {
            return "";
        }
        return expnsnStrVal3;
    }
    public void setExpnsnStrVal3(String expnsnStrVal3) {
        this.expnsnStrVal3 = expnsnStrVal3;
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
    public String getCdGroupNm() {
        return cdGroupNm;
    }
    public void setCdGroupNm(String cdGroupNm) {
        this.cdGroupNm = cdGroupNm;
    }
    public String getCdGroupCd() {
        return cdGroupCd;
    }
    public void setCdGroupCd(String cdGroupCd) {
        this.cdGroupCd = cdGroupCd;
    }
    public String getImgNm() {
        return imgNm;
    }
    public void setImgNm(String imgNm) {
        this.imgNm = imgNm;
    }
    public String getGexpnsnStrVal1() {
        return gexpnsnStrVal1;
    }
    public void setGexpnsnStrVal1(String gexpnsnStrVal1) {
        this.gexpnsnStrVal1 = gexpnsnStrVal1;
    }
    public String getGexpnsnStrVal2() {
        return gexpnsnStrVal2;
    }
    public void setGexpnsnStrVal2(String gexpnsnStrVal2) {
        this.gexpnsnStrVal2 = gexpnsnStrVal2;
    }
    public String getGexpnsnStrVal3() {
        return gexpnsnStrVal3;
    }
    public void setGexpnsnStrVal3(String gexpnsnStrVal3) {
        this.gexpnsnStrVal3 = gexpnsnStrVal3;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public String getDocContent() {
        return docContent;
    }
    public String getUpGrpCd() {
        return upGrpCd;
    }
    public void setUpGrpCd(String upGrpCd) {
        this.upGrpCd = upGrpCd;
    }
    public String getCdGroupDesc() {
        return cdGroupDesc;
    }
    public void setCdGroupDesc(String cdGroupDesc) {
        this.cdGroupDesc = cdGroupDesc;
    }
}
