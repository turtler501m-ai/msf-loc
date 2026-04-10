package com.ktmmobile.mcp.common.dto.db;

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
public class NmcpAppGroupMstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 그룹코드 */
    private String groupCode;
    /** 그룹명 */
    private String groupNm;
    /** 그룹설명 */
    private String groupDesc;
    /** 상태값 A : 활성, C: 취소 */
    private String status;
    /** 생성자아이디 */
    private String cretId;
    /** AMD_ID */
    private String amdId;
    /** 생성일시 */
    private Date cretDt;
    /** 수정일시 */
    private Date amtDt;


    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    public String getGroupNm() {
        return groupNm;
    }
    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }
    public String getGroupDesc() {
        return groupDesc;
    }
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
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


}
