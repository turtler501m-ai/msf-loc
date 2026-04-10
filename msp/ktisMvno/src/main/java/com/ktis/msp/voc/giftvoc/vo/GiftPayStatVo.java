package com.ktis.msp.voc.giftvoc.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.util.List;

public class GiftPayStatVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    private String giftPayStatSeq;
    private String payMon;
    private String contractNum;
    private String custNm;
    private String ctn;
    private String taxRplyYn;
    private String promNm;
    private String giftType;
    private String giftAmt;
    private String subStatus;
    private String openAgntNm;
    private String regstId;

    // 검색조건
    private String srchStrtDt;
    private String srchEndDt;
    private String searchGbn;
    private String searchName;

    private String fileName;

    private List<GiftPayStatVo> items;		//엑셀 rows

    public String getGiftPayStatSeq() {
        return giftPayStatSeq;
    }

    public void setGiftPayStatSeq(String giftPayStatSeq) {
        this.giftPayStatSeq = giftPayStatSeq;
    }

    public String getPayMon() {
        return payMon;
    }

    public void setPayMon(String payMon) {
        this.payMon = payMon;
    }

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getTaxRplyYn() {
        return taxRplyYn;
    }

    public void setTaxRplyYn(String taxRplyYn) {
        this.taxRplyYn = taxRplyYn;
    }

    public String getPromNm() {
        return promNm;
    }

    public void setPromNm(String promNm) {
        this.promNm = promNm;
    }

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public String getGiftAmt() {
        return giftAmt;
    }

    public void setGiftAmt(String giftAmt) {
        this.giftAmt = giftAmt;
    }

    public String getSrchStrtDt() {
        return srchStrtDt;
    }

    public void setSrchStrtDt(String srchStrtDt) {
        this.srchStrtDt = srchStrtDt;
    }

    public String getSrchEndDt() {
        return srchEndDt;
    }

    public void setSrchEndDt(String srchEndDt) {
        this.srchEndDt = srchEndDt;
    }

    public String getSearchGbn() {
        return searchGbn;
    }

    public void setSearchGbn(String searchGbn) {
        this.searchGbn = searchGbn;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getOpenAgntNm() {
        return openAgntNm;
    }

    public void setOpenAgntNm(String openAgntNm) {
        this.openAgntNm = openAgntNm;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<GiftPayStatVo> getItems() {
        return items;
    }

    public void setItems(List<GiftPayStatVo> items) {
        this.items = items;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }
}
