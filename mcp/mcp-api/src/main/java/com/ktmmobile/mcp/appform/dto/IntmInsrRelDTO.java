package com.ktmmobile.mcp.appform.dto;

import static com.ktmmobile.mcp.cmmn.constants.Constants.REQ_BUY_TYPE_PHONE;

import java.io.Serializable;

public class IntmInsrRelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 보험상품코드 */
    String insrProdCd;
    
    /** 단말ID */
    String rprsPrdtId;
    
    /** 보험상품명 */
    String insrProdNm;

    /** 구매유형 단말
     * 구매:MM
     * USIM(유심)단독 구매:UU
     * */
    private String reqBuyType = REQ_BUY_TYPE_PHONE;
    
    /** 보상한도금액 */
    int cmpnLmtAmt;
    
    /** 보험약정기간 */
    int insrEnggCnt;


    public String getInsrProdCd() {
        return insrProdCd;
    }
    public void setInsrProdCd(String insrProdCd) {
        this.insrProdCd = insrProdCd;
    }
    public String getRprsPrdtId() {
        return rprsPrdtId;
    }
    public void setRprsPrdtId(String rprsPrdtId) {
        this.rprsPrdtId = rprsPrdtId;
    }
    public int getCmpnLmtAmt() {
        return cmpnLmtAmt;
    }
    public void setCmpnLmtAmt(int cmpnLmtAmt) {
        this.cmpnLmtAmt = cmpnLmtAmt;
    }
    public int getInsrEnggCnt() {
        return insrEnggCnt;
    }
    public void setInsrEnggCnt(int insrEnggCnt) {
        this.insrEnggCnt = insrEnggCnt;
    }
    public String getInsrProdNm() {
        return insrProdNm;
    }
    public void setInsrProdNm(String insrProdNm) {
        this.insrProdNm = insrProdNm;
    }
    public String getReqBuyType() {
        return reqBuyType;
    }
    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }
}
