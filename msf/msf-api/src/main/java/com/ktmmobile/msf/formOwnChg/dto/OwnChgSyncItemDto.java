package com.ktmmobile.msf.formOwnChg.dto;

/**
 * [배치] 명의변경 완료 동기화 대상 항목 DTO.
 * OwnChgMapper.selectPendingOwnChgList() 결과 매핑.
 */
public class OwnChgSyncItemDto {

    /** 신청서키 (REQUEST_KEY = custReqSeq) */
    private Long requestKey;
    /** 양도인 휴대폰번호 (MOBILE_NO) */
    private String mobileNo;
    /** 계약번호 (CONTRACT_NUM) */
    private String contractNum;
    /** M전산 예약번호 (MCN_RES_NO) */
    private String mcnResNo;
    /** 명의변경 사유코드 (MCN_STAT_RSN_CD) */
    private String mcnStatRsnCd;
    /** 현재 처리코드 (RC=접수) */
    private String procCd;

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }

    public String getMcnResNo() { return mcnResNo; }
    public void setMcnResNo(String mcnResNo) { this.mcnResNo = mcnResNo; }

    public String getMcnStatRsnCd() { return mcnStatRsnCd; }
    public void setMcnStatRsnCd(String mcnStatRsnCd) { this.mcnStatRsnCd = mcnStatRsnCd; }

    public String getProcCd() { return procCd; }
    public void setProcCd(String procCd) { this.procCd = procCd; }
}
