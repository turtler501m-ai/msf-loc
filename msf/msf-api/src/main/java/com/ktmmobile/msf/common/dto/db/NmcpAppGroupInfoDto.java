package com.ktmmobile.msf.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NmcpAppGroupMtsDto.java
 * 날짜     : 2017. 4. 12. 오후 2:28:01
 * 작성자   : papier
 * 설명     : 스캔정보 그룹 마스터 정보 (NMCP_APP_GROUP_MTS)
 * </pre>
 */
public class NmcpAppGroupInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 그룹명 */
    private String groupCode;
    /** 페이지코드 */
    private String pageCode;
    /** 페이지순서 */
    private String pageSeq;
    /** 상태값 A : 활성, C: 취소 */
    private String status;
    /** 생성자ID */
    private String cretId;
    /** 수정자ID */
    private String amdId;
    /** 생성일 */
    private Date cretDt;
    /** 수정일 */
    private Date amtDt;

    /** 페이지명 */
    private String pageNm;
    /** 파일명 */
    private String fileNm;
    private String fileExt;



    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    public String getPageCode() {
        return pageCode;
    }
    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }
    public String getPageSeq() {
        return pageSeq;
    }
    public void setPageSeq(String pageSeq) {
        this.pageSeq = pageSeq;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public String getPageNm() {
        return pageNm;
    }
    public void setPageNm(String pageNm) {
        this.pageNm = pageNm;
    }
    public String getFileNm() {
        return fileNm;
    }
    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }
    public String getFileExt() {
        return fileExt;
    }
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }


}
