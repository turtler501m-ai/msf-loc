package com.ktis.msp.org.prmtmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class EventCodePrmtVO extends BaseVo {

    private static final long serialVersionUID = 1L;

    private String srchStrtDt;
    private String srchEndDt;
    private String srchEventCd;

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

    public String getSrchEventCd() {
        return srchEventCd;
    }

    public void setSrchEventCd(String srchEventCd) {
        this.srchEventCd = srchEventCd;
    }
}
