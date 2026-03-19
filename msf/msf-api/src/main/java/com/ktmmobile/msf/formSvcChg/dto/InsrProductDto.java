package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 단말보험 상품 정보 DTO.
 * ASIS IntmInsrRelDTO 와 동일 구조 (단말보험 목록 조회 결과).
 */
public class InsrProductDto {

    /** 보험 상품 코드 */
    private String insrProdCd;

    /** 보험 상품명 */
    private String insrProdNm;

    /** 보험 SOC 코드 (부가서비스 코드) */
    private String soc;

    /** 월 보험료 */
    private String monthlyFee;

    /** 가입 유형 (01: 단독, 02: 결합) */
    private String joinType;

    /** 구매 유형 (자급제/통신사) */
    private String reqBuyType;

    /** 약관 내용 URL */
    private String clauseUrl;

    /** 사용 여부 */
    private String useYn;

    /** 정렬 순서 */
    private Integer sortSeq;

    public String getInsrProdCd() { return insrProdCd; }
    public void setInsrProdCd(String insrProdCd) { this.insrProdCd = insrProdCd; }

    public String getInsrProdNm() { return insrProdNm; }
    public void setInsrProdNm(String insrProdNm) { this.insrProdNm = insrProdNm; }

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(String monthlyFee) { this.monthlyFee = monthlyFee; }

    public String getJoinType() { return joinType; }
    public void setJoinType(String joinType) { this.joinType = joinType; }

    public String getReqBuyType() { return reqBuyType; }
    public void setReqBuyType(String reqBuyType) { this.reqBuyType = reqBuyType; }

    public String getClauseUrl() { return clauseUrl; }
    public void setClauseUrl(String clauseUrl) { this.clauseUrl = clauseUrl; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }

    public Integer getSortSeq() { return sortSeq; }
    public void setSortSeq(Integer sortSeq) { this.sortSeq = sortSeq; }
}
