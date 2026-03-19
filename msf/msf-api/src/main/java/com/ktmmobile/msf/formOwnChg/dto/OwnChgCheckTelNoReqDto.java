package com.ktmmobile.msf.formOwnChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 명의변경 연락처 상이 검증 요청 (AS-IS nameChgChkTelNo 대응).
 * 명의변경 회선 번호와 양도인 미납 연락처·법정대리인 연락처가 달라야 함.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnChgCheckTelNoReqDto {

    /** 명의변경 대상 회선 휴대폰번호 (양도인 회선) */
    private String grantorPhone;
    /** 양도인 미납 연락처 (etcMobile) — NOR 검사 시 사용 */
    private String etcMobile;
    /** 검사 구분: NOR=양도인, NEE=양수인 */
    private String chkTelType;
    /** 양도인 법정대리인 연락처 (미성년자 시) */
    private String grMinorAgentTel;
    /** 양수인 법정대리인 연락처 (미성년자 시, NEE 시) */
    private String minorAgentTel;
    /** 양수인 연락가능 연락처 (NEE 시: 명변회선·법정대리인과 상이 검사) */
    private String contactPhone;

    public String getGrantorPhone() { return grantorPhone; }
    public void setGrantorPhone(String grantorPhone) { this.grantorPhone = grantorPhone; }
    public String getEtcMobile() { return etcMobile; }
    public void setEtcMobile(String etcMobile) { this.etcMobile = etcMobile; }
    public String getChkTelType() { return chkTelType; }
    public void setChkTelType(String chkTelType) { this.chkTelType = chkTelType; }
    public String getGrMinorAgentTel() { return grMinorAgentTel; }
    public void setGrMinorAgentTel(String grMinorAgentTel) { this.grMinorAgentTel = grMinorAgentTel; }
    public String getMinorAgentTel() { return minorAgentTel; }
    public void setMinorAgentTel(String minorAgentTel) { this.minorAgentTel = minorAgentTel; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
}
