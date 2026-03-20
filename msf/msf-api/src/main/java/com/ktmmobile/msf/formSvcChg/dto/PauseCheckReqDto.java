package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 분실복구/일시정지해제 조회 요청 DTO.
 * checkPause: X26 일시정지이력 + X28 해제가능여부 + X33 분실신고가능여부.
 */
public class PauseCheckReqDto extends SvcChgInfoDto {

    /** 단말구분 (X26 termGubun, 기본값 "M" = 모바일) */
    private String termGubun;

    public String getTermGubun() { return termGubun != null ? termGubun : "M"; }
    public void setTermGubun(String termGubun) { this.termGubun = termGubun; }
}
