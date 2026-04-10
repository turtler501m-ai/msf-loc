package com.ktmmobile.mcp.cs.dto;

import java.io.Serializable;

public class BookingDateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String csResDt;
    private String csResTm;
    private int perCnt;
    private String csResTmNm;
    private String remainYn; /*예약가능여부*/

    public String getCsResDt() {
        return csResDt;
    }

    public void setCsResDt(String csResDt) {
        this.csResDt = csResDt;
    }

    public String getCsResTm() {
        return csResTm;
    }

    public void setCsResTm(String csResTm) {
        this.csResTm = csResTm;
    }

    public int getPerCnt() {
        return perCnt;
    }

    public void setPerCnt(int perCnt) {
        this.perCnt = perCnt;
    }

    public String getCsResTmNm() {
        return csResTmNm;
    }

    public void setCsResTmNm(String csResTmNm) {
        this.csResTmNm = csResTmNm;
    }

    public String getRemainYn() {
        return remainYn;
    }

    public void setRemainYn(String remainYn) {
        this.remainYn = remainYn;
    }

    @Override
    public String toString() {
        return "bookingDateDto [csResDt= "+csResDt+", csResTm= "+csResTm+", perCnt= "+perCnt+", csResTmNm= "+csResTmNm+", remainYn= "+remainYn+"]";
    }
}
