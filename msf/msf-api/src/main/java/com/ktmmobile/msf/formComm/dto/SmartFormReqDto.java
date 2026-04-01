package com.ktmmobile.msf.formComm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 데이터쉐어링 신규 개통 신청 요청 DTO.
 * ASIS AppformReqDto 에서 데이터쉐어링 관련 필드 추출.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartFormReqDto extends SvcChgInfoDto {

    /** 신청키 (DB PK). ASIS requestKey */
    private Long requestKey;
    /** MVNO 오더번호 (14자리). ASIS resNo */
    private String resNo;
    /** 모회선 서비스계약번호 (9자리). ASIS contractNum / prntsContractNo */
    private String contractNum;
    /** 요청 USIM 일련번호. ASIS reqUsimSn */
    private String reqUsimSn;
    /** 청구계정번호. ASIS billAcntNo / prntsBillNo */
    private String billAcntNo;
    /** 대리점 코드. ASIS agentCode */
    private String agentCode;
    /** 접점 숍 ID. ASIS cntpntShopId */
    private String cntpntShopId;
    /** 고객구분: NA=내국인, NM=미성년자, FN=외국인. ASIS cstmrType */
    private String cstmrType;
    /** 진행상태코드. conPreCheck 에서 사용. ASIS prgrStatCd */
    private String prgrStatCd;

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public String getResNo() { return resNo; }
    public void setResNo(String resNo) { this.resNo = resNo; }
    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }
    public String getReqUsimSn() { return reqUsimSn; }
    public void setReqUsimSn(String reqUsimSn) { this.reqUsimSn = reqUsimSn; }
    public String getBillAcntNo() { return billAcntNo; }
    public void setBillAcntNo(String billAcntNo) { this.billAcntNo = billAcntNo; }
    public String getAgentCode() { return agentCode; }
    public void setAgentCode(String agentCode) { this.agentCode = agentCode; }
    public String getCntpntShopId() { return cntpntShopId; }
    public void setCntpntShopId(String cntpntShopId) { this.cntpntShopId = cntpntShopId; }
    public String getCstmrType() { return cstmrType; }
    public void setCstmrType(String cstmrType) { this.cstmrType = cstmrType; }
    public String getPrgrStatCd() { return prgrStatCd; }
    public void setPrgrStatCd(String prgrStatCd) { this.prgrStatCd = prgrStatCd; }
}
