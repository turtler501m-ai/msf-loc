package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.io.Serializable;

public class AcenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;    // 가입신청키
    private String resNo;       // 예약번호
    private String evntGrpCd;   // 해피콜 업무그룹 (공통코드 AcenEvntGrp)
    private String evntType;    // 해피콜 업무상세 (공통코드 AcenEvntType)
    private String regstId;     // 등록자 아이디

    public long getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getEvntGrpCd() {
        return evntGrpCd;
    }

    public void setEvntGrpCd(String evntGrpCd) {
        this.evntGrpCd = evntGrpCd;
    }

    public String getEvntType() {
        return evntType;
    }

    public void setEvntType(String evntType) {
        this.evntType = evntType;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

}
