package com.ktis.msp.batch.job.dis.dismgmt.vo;

import com.ktis.msp.base.BaseVo;

public class DisVO extends BaseVo {


    private int workCnt;            // 실행건수
    private String strDt;           // 시작일자
    private String endDt;           // 종료일자
    private String procYn;          // 처리여부

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getStrDt() {
        return strDt;
    }

    public void setStrDt(String strDt) {
        this.strDt = strDt;
    }

    public int getWorkCnt() {
        return workCnt;
    }

    public void setWorkCnt(int workCnt) {
        this.workCnt = workCnt;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }
}
