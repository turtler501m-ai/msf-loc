package com.ktis.msp.batch.job.rcp.rcpmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class UsimDlvryCanVO extends BaseVo {

    private Long selfDlvryIdx;
    private String histYn;
    private String cstmrNativeRrn;

    public Long getSelfDlvryIdx() {
        return selfDlvryIdx;
    }

    public void setSelfDlvryIdx(Long selfDlvryIdx) {
        this.selfDlvryIdx = selfDlvryIdx;
    }

    public String getHistYn() {
        return histYn;
    }

    public void setHistYn(String histYn) {
        this.histYn = histYn;
    }

    public String getCstmrNativeRrn() {
        return cstmrNativeRrn;
    }

    public void setCstmrNativeRrn(String cstmrNativeRrn) {
        this.cstmrNativeRrn = cstmrNativeRrn;
    }
}
