package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestAgentVo {

    private Long requestKey;

    private LocalDateTime sysRdate = LocalDateTime.now();

    private LocalDateTime rvisnDttm;

    private String rvisnId;

    private String minorAgentName;

    private String minorAgentRrn;

    private String minorAgentRelation;

    private String minorAgentTelFn;

    private String minorAgentTelMn;

    private String minorAgentTelRn;

    private String minorAgentAgrmYn;

    private String minorSelfInqryAgrmYn = null;

    private String minorSelfCertType;

    private String minorSelfIssuExprDt;

    private String minorSelfIssuNum;

    private String jrdclAgentName;

    private String jrdclAgentRrn;

    private String jrdclAgentTelFn;

    private String jrdclAgentTelMn;

    private String jrdclAgentTelRn;

    private String entrustReqRelation = "";

    private String entrustReqNm = "";

    private String entrustResNm = "";

    private String entrustResRrn = "";

    private String entrustResTelFn = "";

    private String entrustResTelMn = "";

    private String entrustResTelRn = "";

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public void setCretDt(LocalDateTime cretDt) { this.sysRdate = cretDt; }
    public void setAmdDt(LocalDateTime amdDt) { this.rvisnDttm = amdDt; }
    public void setAmdId(String amdId) { this.rvisnId = amdId; }
    public void setMinorAgentNm(String minorAgentNm) { this.minorAgentName = minorAgentNm; }
    public void setMinorAgentRrn(String minorAgentRrn) { this.minorAgentRrn = minorAgentRrn; }
    public void setMinorAgentRelTypeCd(String minorAgentRelTypeCd) { this.minorAgentRelation = minorAgentRelTypeCd; }
    public void setMinorAgentTelFnNo(String minorAgentTelFnNo) { this.minorAgentTelFn = minorAgentTelFnNo; }
    public void setMinorAgentTelMnNo(String minorAgentTelMnNo) { this.minorAgentTelMn = minorAgentTelMnNo; }
    public void setMinorAgentTelRnNo(String minorAgentTelRnNo) { this.minorAgentTelRn = minorAgentTelRnNo; }
    public void setMinorAgentAgrmYn(String minorAgentAgrmYn) { this.minorAgentAgrmYn = minorAgentAgrmYn; }
    public void setMinorAgentSelfInqryAgrmYn(String minorAgentSelfInqryAgrmYn) { this.minorSelfInqryAgrmYn = minorAgentSelfInqryAgrmYn; }
    public void setMinorAgentSelfCertTypeCd(String minorAgentSelfCertTypeCd) { this.minorSelfCertType = minorAgentSelfCertTypeCd; }
    public void setIdentityIssuDate(String identityIssuDate) { this.minorSelfIssuExprDt = identityIssuDate; }
    public void setSelfIssuNo(String selfIssuNo) { this.minorSelfIssuNum = selfIssuNo; }
    public void setJrdclAgentNm(String jrdclAgentNm) { this.jrdclAgentName = jrdclAgentNm; }
    public void setJrdclAgentRrn(String jrdclAgentRrn) { this.jrdclAgentRrn = jrdclAgentRrn; }
    public void setJrdclAgentTelFnNo(String jrdclAgentTelFnNo) { this.jrdclAgentTelFn = jrdclAgentTelFnNo; }
    public void setJrdclAgentTelMnNo(String jrdclAgentTelMnNo) { this.jrdclAgentTelMn = jrdclAgentTelMnNo; }
    public void setJrdclAgentTelRnNo(String jrdclAgentTelRnNo) { this.jrdclAgentTelRn = jrdclAgentTelRnNo; }
}
