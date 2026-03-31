package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 데이터쉐어링 신규 개통 요청 DTO.
 * ASIS AppformReqDto 중 데이터쉐어링에 필요한 필드만 포함.
 * 세션 없음(stateless) — requestKey/resNo 를 클라이언트가 매 요청에 전달.
 */
public class SvcChgShareDataApplyReqDto {

    // ─── 공통 식별 ───────────────────────────────────────────────
    /** 모회선 계약번호 (NCN, 9자리). ASIS contractNum 동일. */
    private String ncn;
    /** 모회선 휴대폰번호 (CTN, 11자리). */
    private String ctn;
    /** 고객 ID */
    private String custId;
    /** USIM 일련번호 */
    private String reqUsimSn;
    /** 대리점코드 */
    private String agentCode;
    /** 접점판매점코드 (cntpntShopId) */
    private String cntpntShopId;

    // ─── 단계 연계용 (step1~4에서 사용) ─────────────────────────
    /** 신청서 키 (apply 응답 후 클라이언트가 보관해서 step1~4로 전달) */
    private Long requestKey;
    /** 예약번호 — MVNO_ORD_NO 기반. apply 응답 후 전달 */
    private String resNo;
    /** 청구계정번호 (step4 OP0 에 필요) */
    private String billAcntNo;

    public String getNcn()            { return ncn; }
    public void setNcn(String ncn)    { this.ncn = ncn; }

    public String getCtn()            { return ctn; }
    public void setCtn(String ctn)    { this.ctn = ctn; }

    public String getCustId()              { return custId; }
    public void setCustId(String custId)   { this.custId = custId; }

    public String getReqUsimSn()               { return reqUsimSn; }
    public void setReqUsimSn(String reqUsimSn) { this.reqUsimSn = reqUsimSn; }

    public String getAgentCode()               { return agentCode; }
    public void setAgentCode(String agentCode) { this.agentCode = agentCode; }

    public String getCntpntShopId()                    { return cntpntShopId; }
    public void setCntpntShopId(String cntpntShopId)   { this.cntpntShopId = cntpntShopId; }

    public Long getRequestKey()                    { return requestKey; }
    public void setRequestKey(Long requestKey)     { this.requestKey = requestKey; }

    public String getResNo()               { return resNo; }
    public void setResNo(String resNo)     { this.resNo = resNo; }

    public String getBillAcntNo()                  { return billAcntNo; }
    public void setBillAcntNo(String billAcntNo)   { this.billAcntNo = billAcntNo; }
}
