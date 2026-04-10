package com.ktmmobile.mcp.cs.dto;

import java.util.Date;
import java.io.Serializable;

public class CsMcpUserCntrDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usrid;
    private String cntrMobileNo;
    private Date sysRdate;
    private String svcCntrNo;
    private String repNo;

    public String getUsrid() {
        return usrid;
    }
    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }
    public String getCntrMobileNo() {
        return cntrMobileNo;
    }
    public void setCntrMobileNo(String cntrMobileNo) {
        this.cntrMobileNo = cntrMobileNo;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getSvcCntrNo() {
        return svcCntrNo;
    }
    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }
    public String getRepNo() {
        return repNo;
    }
    public void setRepNo(String repNo) {
        this.repNo = repNo;
    }
}
