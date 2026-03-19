package com.ktmmobile.msf.formComm.dto;

/**
 * M전산 계약정보 조회 결과 매핑 DTO. ContractInfoMapper.selectContractInfo() 반환.
 * ASIS MypageMapper.xml selectContractNum 동일 필드.
 */
public class ContractInfoDto {

    /** 서비스계약번호 (9자리) */
    private String ncn;
    /** 고객ID */
    private String custId;

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
}
