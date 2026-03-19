package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 단말보험 상품 DTO.
 * ASIS IntmInsrRelDTO 와 동일 역할.
 * 조회 출처: MSP_INTM_INSR_MST@DL_MSP (M플랫폼 DB링크)
 */
public class InsrProductDto {

    /** 단말보험상품코드 */
    private String insrProdCd;

    /** 단말보험상품명 (MSF_CD_DTL.DTL_CD_NM) */
    private String insrProdNm;

    /** 보상한도금액 */
    private Long cmpnLmtAmt;

    /** 보험가입횟수 */
    private Integer insrEnggCnt;

    /** 보험유형코드 */
    private String insrTypeCd;

    /** 대표상품ID */
    private String rprsPrdtId;

    public String getInsrProdCd() { return insrProdCd; }
    public void setInsrProdCd(String insrProdCd) { this.insrProdCd = insrProdCd; }

    public String getInsrProdNm() { return insrProdNm; }
    public void setInsrProdNm(String insrProdNm) { this.insrProdNm = insrProdNm; }

    public Long getCmpnLmtAmt() { return cmpnLmtAmt; }
    public void setCmpnLmtAmt(Long cmpnLmtAmt) { this.cmpnLmtAmt = cmpnLmtAmt; }

    public Integer getInsrEnggCnt() { return insrEnggCnt; }
    public void setInsrEnggCnt(Integer insrEnggCnt) { this.insrEnggCnt = insrEnggCnt; }

    public String getInsrTypeCd() { return insrTypeCd; }
    public void setInsrTypeCd(String insrTypeCd) { this.insrTypeCd = insrTypeCd; }

    public String getRprsPrdtId() { return rprsPrdtId; }
    public void setRprsPrdtId(String rprsPrdtId) { this.rprsPrdtId = rprsPrdtId; }
}
