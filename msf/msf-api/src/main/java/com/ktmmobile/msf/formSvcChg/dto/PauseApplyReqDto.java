package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 분실복구/일시정지해제 처리 요청 DTO.
 * applyType: "PAUSE_CNL" (일시정지해제 X30), "LOST_CNL" (분실복구 X35).
 */
public class PauseApplyReqDto extends SvcChgInfoDto {

    /** 처리 유형: PAUSE_CNL(일시정지해제), LOST_CNL(분실복구) */
    private String applyType;

    /** 비밀번호 (X30/X35 strPwdNumInsert, 4~8자리) */
    private String password;

    /** 비밀번호 유형: PP(일시정지암호, 기본), CP(개인정보암호) */
    private String pwdType;

    public String getApplyType() { return applyType; }
    public void setApplyType(String applyType) { this.applyType = applyType; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPwdType() { return pwdType != null ? pwdType : "PP"; }
    public void setPwdType(String pwdType) { this.pwdType = pwdType; }
}
