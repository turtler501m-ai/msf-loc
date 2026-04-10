package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class BannerTextDto implements Serializable{
    private static final long serialVersionUID = -1207742026359034810L;

    private String bannTxtSeq;
    private String txtDtlSeq;
    private String bannTxtType;
    private String bgColor;
    private String bannUseYn;
    private String bannPstngStartDate;
    private String bannPstngEndDate;
    private String txtContent;
    private String indcOdrg;
    private String txtUseYn;
    private String txtPstngStartDate;
    private String txtPstngEndDate;

    public String getBannTxtSeq() {
        return bannTxtSeq;
    }

    public void setBannTxtSeq(String bannTxtSeq) {
        this.bannTxtSeq = bannTxtSeq;
    }

    public String getTxtDtlSeq() {
        return txtDtlSeq;
    }

    public void setTxtDtlSeq(String txtDtlSeq) {
        this.txtDtlSeq = txtDtlSeq;
    }

    public String getBannTxtType() {
        return bannTxtType;
    }

    public void setBannTxtType(String bannTxtType) {
        this.bannTxtType = bannTxtType;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getBannUseYn() {
        return bannUseYn;
    }

    public void setBannUseYn(String bannUseYn) {
        this.bannUseYn = bannUseYn;
    }

    public String getBannPstngStartDate() {
        return bannPstngStartDate;
    }

    public void setBannPstngStartDate(String bannPstngStartDate) {
        this.bannPstngStartDate = bannPstngStartDate;
    }

    public String getBannPstngEndDate() {
        return bannPstngEndDate;
    }

    public void setBannPstngEndDate(String bannPstngEndDate) {
        this.bannPstngEndDate = bannPstngEndDate;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public String getIndcOdrg() {
        return indcOdrg;
    }

    public void setIndcOdrg(String indcOdrg) {
        this.indcOdrg = indcOdrg;
    }

    public String getTxtUseYn() {
        return txtUseYn;
    }

    public void setTxtUseYn(String txtUseYn) {
        this.txtUseYn = txtUseYn;
    }

    public String getTxtPstngStartDate() {
        return txtPstngStartDate;
    }

    public void setTxtPstngStartDate(String txtPstngStartDate) {
        this.txtPstngStartDate = txtPstngStartDate;
    }

    public String getTxtPstngEndDate() {
        return txtPstngEndDate;
    }

    public void setTxtPstngEndDate(String txtPstngEndDate) {
        this.txtPstngEndDate = txtPstngEndDate;
    }
}