package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;

public class PopupEditorDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String popupSeq;
    private String device;
    private String popupSbst;

    public String getPopupSeq() {
        return popupSeq;
    }

    public void setPopupSeq(String popupSeq) {
        this.popupSeq = popupSeq;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPopupSbst() {
        return popupSbst;
    }

    public void setPopupSbst(String popupSbst) {
        this.popupSbst = popupSbst;
    }
}
