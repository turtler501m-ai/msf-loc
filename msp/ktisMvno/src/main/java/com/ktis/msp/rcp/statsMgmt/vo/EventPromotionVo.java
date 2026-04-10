package com.ktis.msp.rcp.statsMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class EventPromotionVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    private String srchStrtDt;
    private String srchEndDt;
    private String promotionList;
    private String promotionId;
    private String prizeList;
    private String pSearchGbn;
    private String pSearchName;

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

    public String getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(String promotionList) {
        this.promotionList = promotionList;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(String prizeList) {
        this.prizeList = prizeList;
    }

    public String getpSearchGbn() {
        return pSearchGbn;
    }

    public void setpSearchGbn(String pSearchGbn) {
        this.pSearchGbn = pSearchGbn;
    }

    public String getpSearchName() {
        return pSearchName;
    }

    public void setpSearchName(String pSearchName) {
        this.pSearchName = pSearchName;
    }
}
