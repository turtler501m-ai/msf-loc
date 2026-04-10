package com.ktmmobile.msf.domains.form.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NmcpLinkInfoDto.java
 * 날짜     : 2017. 5. 24.
 * 작성자   : papier
 * 설명     : 고객포탈 링크 정보
 * </pre>
 */
public class NmcpLinkInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 고객포탈 링크 일련번호(PK) */
    private int linkId;
    /** 링크명 */
    private String linkNm;
    private String linkUrl;
    /** 링크 설명  */
    private String linkDesc;
    /** 링크 타겟 (현재창 :01,새창 :02) */
    private String linkType;
    /** 등록자 아이디 */
    private String cretId;
    /** 수정자 아이디 */
    private String amdId;
    /** 등록일 */
    private Date cretDt;
    /** 수정일 */
    private Date amtDt;
    public int getLinkId() {
        return linkId;
    }
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }
    public String getLinkNm() {
        return linkNm;
    }
    public void setLinkNm(String linkNm) {
        this.linkNm = linkNm;
    }
    public String getLinkUrl() {
        return linkUrl;
    }
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
    public String getLinkDesc() {
        return linkDesc;
    }
    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }
    public String getLinkType() {
        return linkType;
    }
    public void setLinkType(String linkType) {
        this.linkType = linkType;
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
    public Date getAmtDt() {
        return amtDt;
    }
    public void setAmtDt(Date amtDt) {
        this.amtDt = amtDt;
    }





}
