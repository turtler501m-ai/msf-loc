package com.ktmmobile.msf.formSvcCncl.mapper;

/**
 * 서비스해지 신청서 DB 저장 DTO (Mapper 내부용).
 */
public class SvcCnclInsertDto {

    private Long cancelApyNo;
    private String custNm;
    private String mobileNo;
    private String custType;
    private String useType;
    private Long remainCharge;
    private Long penalty;
    private Long installmentRemain;
    private String cancelReason;
    private String memo;
    private String agncCd;
    private String cretId;

    public Long getCancelApyNo() { return cancelApyNo; }
    public void setCancelApyNo(Long cancelApyNo) { this.cancelApyNo = cancelApyNo; }
    public String getCustNm() { return custNm; }
    public void setCustNm(String custNm) { this.custNm = custNm; }
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public String getCustType() { return custType; }
    public void setCustType(String custType) { this.custType = custType; }
    public String getUseType() { return useType; }
    public void setUseType(String useType) { this.useType = useType; }
    public Long getRemainCharge() { return remainCharge; }
    public void setRemainCharge(Long remainCharge) { this.remainCharge = remainCharge; }
    public Long getPenalty() { return penalty; }
    public void setPenalty(Long penalty) { this.penalty = penalty; }
    public Long getInstallmentRemain() { return installmentRemain; }
    public void setInstallmentRemain(Long installmentRemain) { this.installmentRemain = installmentRemain; }
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
    public String getAgncCd() { return agncCd; }
    public void setAgncCd(String agncCd) { this.agncCd = agncCd; }
    public String getCretId() { return cretId; }
    public void setCretId(String cretId) { this.cretId = cretId; }
}
