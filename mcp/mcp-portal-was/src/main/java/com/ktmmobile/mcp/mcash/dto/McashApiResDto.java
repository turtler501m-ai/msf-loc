package com.ktmmobile.mcp.mcash.dto;

public class McashApiResDto extends McashApiDto {

    /* response field - common */
    private String mdspCustId;      // 캐시파이 고객 아이디
    private String rspCode;         // 응답 코드
    private String rspMsg;          // 응답 메시지

    /* response field - sync user */
    private String rspApiNo;        // 응답 전문 번호

    /* response field - check cash */
    private int acuCash;            // 적립 캐시
    private int acuPamCash;         // 적립 예정 캐시
    private int totRmndCash;        // 총 잔여 캐시
    private int rvSchDeNc;          // 적립 예정 거래 건수

    /* response field - check user */
    private String jnYn;            // 가입 여부
    private String jnDt;            // 가입 일자
    private String bfPotalId;       // 이전 포탈ID
    private String bfSvcContId;     // 이전 서비스계약ID
    private String chgDt;           // 변경 일자
    private String wdYn;            // 탈회 여부
    private String wdDt;            // 탈회 일자

    public McashApiResDto() {
        super();
    }

    public String getMdspCustId() {
        return mdspCustId;
    }

    public void setMdspCustId(String mdspCustId) {
        this.mdspCustId = mdspCustId;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public String getRspApiNo() {
        return rspApiNo;
    }

    public void setRspApiNo(String rspApiNo) {
        this.rspApiNo = rspApiNo;
    }

    public int getAcuCash() {
        return acuCash;
    }

    public void setAcuCash(int acuCash) {
        this.acuCash = acuCash;
    }

    public int getAcuPamCash() {
        return acuPamCash;
    }

    public void setAcuPamCash(int acuPamCash) {
        this.acuPamCash = acuPamCash;
    }

    public int getTotRmndCash() {
        return totRmndCash;
    }

    public void setTotRmndCash(int totRmndCash) {
        this.totRmndCash = totRmndCash;
    }

    public int getRvSchDeNc() {
        return rvSchDeNc;
    }

    public void setRvSchDeNc(int rvSchDeNc) {
        this.rvSchDeNc = rvSchDeNc;
    }

    public String getJnYn() {
        return jnYn;
    }

    public void setJnYn(String jnYn) {
        this.jnYn = jnYn;
    }

    public String getJnDt() {
        return jnDt;
    }

    public void setJnDt(String jnDt) {
        this.jnDt = jnDt;
    }

    public String getBfPotalId() {
        return bfPotalId;
    }

    public void setBfPotalId(String bfPotalId) {
        this.bfPotalId = bfPotalId;
    }

    public String getBfSvcContId() {
        return bfSvcContId;
    }

    public void setBfSvcContId(String bfSvcContId) {
        this.bfSvcContId = bfSvcContId;
    }

    public String getChgDt() {
        return chgDt;
    }

    public void setChgDt(String chgDt) {
        this.chgDt = chgDt;
    }

    public String getWdYn() {
        return wdYn;
    }

    public void setWdYn(String wdYn) {
        this.wdYn = wdYn;
    }

    public String getWdDt() {
        return wdDt;
    }

    public void setWdDt(String wdDt) {
        this.wdDt = wdDt;
    }
}




