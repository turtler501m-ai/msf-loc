package com.ktis.msp.rcp.rcpMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.io.Serializable;

public class HubOrderVO extends BaseVo implements Serializable {

    private String orderSeq;            //주문번호

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

}
