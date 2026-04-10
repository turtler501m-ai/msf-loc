package com.ktmmobile.mcp.mstore.dto;

import java.io.Serializable;

public class MstoreApiDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String empen;       //사번 (CI값)
    private String mbrNm;       //회원명
    private String procDivStat; //처리구분 (I : 등록/수정, Z: 고객파기 (탈퇴) )

    public String getEmpen() {
        return empen;
    }

    public void setEmpen(String empen) {
        this.empen = empen;
    }

    public String getMbrNm() {
        return mbrNm;
    }

    public void setMbrNm(String mbrNm) {
        this.mbrNm = mbrNm;
    }

    public String getProcDivStat() {
        return procDivStat;
    }

    public void setProcDivStat(String procDivStat) {
        this.procDivStat = procDivStat;
    }
}
