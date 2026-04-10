package com.ktmmobile.mcp.common.dto.db;

import java.io.Serializable;
import java.util.Date;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestReqDto.java
 * 날짜     : 2016. 1. 18. 오후 1:57:39
 * 작성자   : papier
 * 설명     : 가입신청_청구정보 테이블(MCP_REQUEST_REQ)
 * </pre>
 */
public class McpRequestChangeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 명의변경_고객명 */
    private String nameChangeNm;
    /** 명의변경_전화번호_앞자리 */
    private String nameChangeTelFn;
    /** 명의변경_전화번호_중간자리 */
    private String nameChangeTelMn;
    /** 명의변경_전화번호_끝자리 */
    private String nameChangeTelRn;
    /** 명의변경_주민등록번호 */
    private String nameChangeRrn;
    /** 완납=P,승계=C */
    private String nameChangePinstallment;
    /** 가입신청_키 */
    private String requestKey;
    /** 등록일시 */
    private Date sysRdate;
    public String getNameChangeNm() {
        return nameChangeNm;
    }
    public void setNameChangeNm(String nameChangeNm) {
        this.nameChangeNm = nameChangeNm;
    }
    public String getNameChangeTelFn() {
        return nameChangeTelFn;
    }
    public void setNameChangeTelFn(String nameChangeTelFn) {
        this.nameChangeTelFn = nameChangeTelFn;
    }
    public String getNameChangeTelMn() {
        return nameChangeTelMn;
    }
    public void setNameChangeTelMn(String nameChangeTelMn) {
        this.nameChangeTelMn = nameChangeTelMn;
    }
    public String getNameChangeTelRn() {
        return nameChangeTelRn;
    }
    public void setNameChangeTelRn(String nameChangeTelRn) {
        this.nameChangeTelRn = nameChangeTelRn;
    }
    public String getNameChangeRrn() {
        return nameChangeRrn;
    }
    public void setNameChangeRrn(String nameChangeRrn) {
        this.nameChangeRrn = nameChangeRrn;
    }
    public String getNameChangePinstallment() {
        return nameChangePinstallment;
    }
    public void setNameChangePinstallment(String nameChangePinstallment) {
        this.nameChangePinstallment = nameChangePinstallment;
    }
    public String getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }





}
