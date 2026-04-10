package com.ktmmobile.mcp.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NmcpAppFormMstDto.java
 * 날짜     : 2017. 4. 14. 오후 2:28:01
 * 작성자   : papier
 * 설명     : 스캔페이지마스터 정보 (NMCP_APP_FORM_MST)
 * </pre>
 */
public class NmcpAppFormMstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 페이지명 */
    private String pageNm;
    /** 페이지코드 */
    private String pageCode;
    /** 파일명 */
    private String fileNm;
    private String fileExt;

    /** 사용유무 프로그램에서 따로 영향이 없음 (사용하지 않음)*/
    private String useYn;
    /** 생성자ID */
    private String cretId;
    /** 수정자ID */
    private String amdId;
    /** 생성년월 */
    private Date cretDt;
    /** 수정년월 */
    private Date amtDt;
    public String getPageNm() {
        return pageNm;
    }
    public void setPageNm(String pageNm) {
        this.pageNm = pageNm;
    }
    public String getPageCode() {
        return pageCode;
    }
    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
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
