package com.ktmmobile.msf.formSvcCncl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 해지 사전체크(cancelConsult) 요청 DTO.
 * ASIS: CancelConsultController.cancelConsultAjax() 입력값에 대응.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvcCnclConsultReqDto {

    /** 계약번호 (9자리) */
    private String ncn;
    /** 해지 대상 휴대폰번호 */
    private String ctn;
    /** 생년월일 8자리 (YYYYMMDD) — 미성년자 체크용 */
    private String birthDate;
    /** 본인(휴대폰) 인증 완료 여부 */
    private Boolean phoneAuthCompleted;

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }
    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public Boolean getPhoneAuthCompleted() { return phoneAuthCompleted; }
    public void setPhoneAuthCompleted(Boolean phoneAuthCompleted) { this.phoneAuthCompleted = phoneAuthCompleted; }
}
