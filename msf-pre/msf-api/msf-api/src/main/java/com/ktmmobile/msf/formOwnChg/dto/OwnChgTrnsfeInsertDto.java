package com.ktmmobile.msf.formOwnChg.dto;

/**
 * 명의변경 양수인 저장용 DTO (NMCP_NFL_CHG_TRNSFE).
 * CsTransferMapper assigneeRegSave 참조.
 */
public class OwnChgTrnsfeInsertDto {

    private Long trnsfeApyNo;
    private String trnsfeNm;
    private String trnsfeMobileNo;
    private String trnsfeResno;
    private String trnsfeMyslfConfMeth;
    private String trnsfeResidRegIssGovof;
    private String trnsfeResidRegIssDate;
    private String trnsfeDriveLicnsNo;
    private String trnsfeDriveLicnsIssDate;
    private String cretId;

    public Long getTrnsfeApyNo() { return trnsfeApyNo; }
    public void setTrnsfeApyNo(Long trnsfeApyNo) { this.trnsfeApyNo = trnsfeApyNo; }
    public String getTrnsfeNm() { return trnsfeNm; }
    public void setTrnsfeNm(String trnsfeNm) { this.trnsfeNm = trnsfeNm; }
    public String getTrnsfeMobileNo() { return trnsfeMobileNo; }
    public void setTrnsfeMobileNo(String trnsfeMobileNo) { this.trnsfeMobileNo = trnsfeMobileNo; }
    public String getTrnsfeResno() { return trnsfeResno; }
    public void setTrnsfeResno(String trnsfeResno) { this.trnsfeResno = trnsfeResno; }
    public String getTrnsfeMyslfConfMeth() { return trnsfeMyslfConfMeth; }
    public void setTrnsfeMyslfConfMeth(String trnsfeMyslfConfMeth) { this.trnsfeMyslfConfMeth = trnsfeMyslfConfMeth; }
    public String getTrnsfeResidRegIssGovof() { return trnsfeResidRegIssGovof; }
    public void setTrnsfeResidRegIssGovof(String trnsfeResidRegIssGovof) { this.trnsfeResidRegIssGovof = trnsfeResidRegIssGovof; }
    public String getTrnsfeResidRegIssDate() { return trnsfeResidRegIssDate; }
    public void setTrnsfeResidRegIssDate(String trnsfeResidRegIssDate) { this.trnsfeResidRegIssDate = trnsfeResidRegIssDate; }
    public String getTrnsfeDriveLicnsNo() { return trnsfeDriveLicnsNo; }
    public void setTrnsfeDriveLicnsNo(String trnsfeDriveLicnsNo) { this.trnsfeDriveLicnsNo = trnsfeDriveLicnsNo; }
    public String getTrnsfeDriveLicnsIssDate() { return trnsfeDriveLicnsIssDate; }
    public void setTrnsfeDriveLicnsIssDate(String trnsfeDriveLicnsIssDate) { this.trnsfeDriveLicnsIssDate = trnsfeDriveLicnsIssDate; }
    public String getCretId() { return cretId; }
    public void setCretId(String cretId) { this.cretId = cretId; }
}
