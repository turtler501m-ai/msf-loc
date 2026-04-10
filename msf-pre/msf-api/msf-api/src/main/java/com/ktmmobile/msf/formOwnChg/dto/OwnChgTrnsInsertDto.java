package com.ktmmobile.msf.formOwnChg.dto;

/**
 * 명의변경 양도인 저장용 DTO (NMCP_NFL_CHG_TRNS).
 * CsTransferMapper grantorRegSave 참조.
 */
public class OwnChgTrnsInsertDto {

    private Long trnsApyNo;
    private String trnsNm;
    private String trnsMobileNo;
    private String trnsPass;
    private String trnsMyslfConfMeth;
    private String trnsResidRegIssGovof;
    private String trnsResidRegIssDate;
    private String trnsDriveLicnsNo;
    private String trnsDriveLicnsIssDate;
    private String trnsTrnsfeNm;
    private String trnsTrnsfeMobileNo;
    private String cretId;
    private String trnsPhoneNo;
    private String trnsStatusVal;

    public Long getTrnsApyNo() { return trnsApyNo; }
    public void setTrnsApyNo(Long trnsApyNo) { this.trnsApyNo = trnsApyNo; }
    public String getTrnsNm() { return trnsNm; }
    public void setTrnsNm(String trnsNm) { this.trnsNm = trnsNm; }
    public String getTrnsMobileNo() { return trnsMobileNo; }
    public void setTrnsMobileNo(String trnsMobileNo) { this.trnsMobileNo = trnsMobileNo; }
    public String getTrnsPass() { return trnsPass; }
    public void setTrnsPass(String trnsPass) { this.trnsPass = trnsPass; }
    public String getTrnsMyslfConfMeth() { return trnsMyslfConfMeth; }
    public void setTrnsMyslfConfMeth(String trnsMyslfConfMeth) { this.trnsMyslfConfMeth = trnsMyslfConfMeth; }
    public String getTrnsResidRegIssGovof() { return trnsResidRegIssGovof; }
    public void setTrnsResidRegIssGovof(String trnsResidRegIssGovof) { this.trnsResidRegIssGovof = trnsResidRegIssGovof; }
    public String getTrnsResidRegIssDate() { return trnsResidRegIssDate; }
    public void setTrnsResidRegIssDate(String trnsResidRegIssDate) { this.trnsResidRegIssDate = trnsResidRegIssDate; }
    public String getTrnsDriveLicnsNo() { return trnsDriveLicnsNo; }
    public void setTrnsDriveLicnsNo(String trnsDriveLicnsNo) { this.trnsDriveLicnsNo = trnsDriveLicnsNo; }
    public String getTrnsDriveLicnsIssDate() { return trnsDriveLicnsIssDate; }
    public void setTrnsDriveLicnsIssDate(String trnsDriveLicnsIssDate) { this.trnsDriveLicnsIssDate = trnsDriveLicnsIssDate; }
    public String getTrnsTrnsfeNm() { return trnsTrnsfeNm; }
    public void setTrnsTrnsfeNm(String trnsTrnsfeNm) { this.trnsTrnsfeNm = trnsTrnsfeNm; }
    public String getTrnsTrnsfeMobileNo() { return trnsTrnsfeMobileNo; }
    public void setTrnsTrnsfeMobileNo(String trnsTrnsfeMobileNo) { this.trnsTrnsfeMobileNo = trnsTrnsfeMobileNo; }
    public String getCretId() { return cretId; }
    public void setCretId(String cretId) { this.cretId = cretId; }
    public String getTrnsPhoneNo() { return trnsPhoneNo; }
    public void setTrnsPhoneNo(String trnsPhoneNo) { this.trnsPhoneNo = trnsPhoneNo; }
    public String getTrnsStatusVal() { return trnsStatusVal; }
    public void setTrnsStatusVal(String trnsStatusVal) { this.trnsStatusVal = trnsStatusVal; }
}
