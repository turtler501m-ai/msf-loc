package com.ktmmobile.msf.common.dto;

import java.io.Serializable;

public class PopupDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String popupSeq;
    private String popupSubject;
    private String widthSize;
    private String heightSize;
    private String xcrd;
    private String ycrd;
    private String popupSbst;
    private String popupUrl;
    private String popupOpenStat;
    private String currentUrl;
    private String menuCode;
    private String outputMenu = "";
    private String pstngStartDate;
    private String pstngEndDate;
    private String platformCd;
    private String popupOutputCd = "";
    private String indcOdrg;
    private String popupShowUrl = "";
    private String filePathNm = "";

    private String zipNo;
    private String roadAddr;
    /*스크롤여부*/
    private String scrollYn = "N" ;

    private String usageType;
    private String oneTimePopupGrp;

    private String contentType;
    private String device;

    public String getZipNo() {
        return zipNo;
    }
    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }
    public String getRoadAddr() {
        return roadAddr;
    }
    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    /**
     * @return the popupSeq
     */
    public String getPopupSeq() {
        return popupSeq;
    }
    /**
     * @param popupSeq the popupSeq to set
     */
    public void setPopupSeq(String popupSeq) {
        this.popupSeq = popupSeq;
    }
    /**
     * @return the popupSubject
     */
    public String getPopupSubject() {
        return popupSubject;
    }
    /**
     * @param popupSubject the popupSubject to set
     */
    public void setPopupSubject(String popupSubject) {
        this.popupSubject = popupSubject;
    }
    /**
     * @return the widthSize
     */
    public String getWidthSize() {
        return widthSize;
    }
    /**
     * @param widthSize the widthSize to set
     */
    public void setWidthSize(String widthSize) {
        this.widthSize = widthSize;
    }
    /**
     * @return the heightSize
     */
    public String getHeightSize() {
        return heightSize;
    }
    /**
     * @param heightSize the heightSize to set
     */
    public void setHeightSize(String heightSize) {
        this.heightSize = heightSize;
    }
    /**
     * @return the xcrd
     */
    public String getXcrd() {
        return xcrd;
    }
    /**
     * @param xcrd the xcrd to set
     */
    public void setXcrd(String xcrd) {
        this.xcrd = xcrd;
    }
    /**
     * @return the ycrd
     */
    public String getYcrd() {
        return ycrd;
    }
    /**
     * @param ycrd the ycrd to set
     */
    public void setYcrd(String ycrd) {
        this.ycrd = ycrd;
    }
    /**
     * @return the popupSbst
     */
    public String getPopupSbst() {
        return popupSbst;
    }
    /**
     * @param popupSbst the popupSbst to set
     */
    public void setPopupSbst(String popupSbst) {
        this.popupSbst = popupSbst;
    }
    /**
     * @return the popupUrl
     */
    public String getPopupUrl() {
        return popupUrl;
    }
    /**
     * @param popupUrl the popupUrl to set
     */
    public void setPopupUrl(String popupUrl) {
        this.popupUrl = popupUrl;
    }
    /**
     * @return the popupOpenStat
     */
    public String getPopupOpenStat() {
        return popupOpenStat;
    }
    /**
     * @param popupOpenStat the popupOpenStat to set
     */
    public void setPopupOpenStat(String popupOpenStat) {
        this.popupOpenStat = popupOpenStat;
    }
    /**
     * @return the currentUrl
     */
    public String getCurrentUrl() {
        return currentUrl;
    }
    /**
     * @param currentUrl the currentUrl to set
     */
    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }
    /**
     * @return the menuCode
     */
    public String getMenuCode() {
        return menuCode;
    }
    /**
     * @param menuCode the menuCode to set
     */
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getScrollYn() {
        return scrollYn;
    }
    public void setScrollYn(String scrollYn) {
        this.scrollYn = scrollYn;
    }
    /**
     * @return the pstngStartDate
     */
    public String getPstngStartDate() {
        return pstngStartDate;
    }
    /**
     * @param pstngStartDate the pstngStartDate to set
     */
    public void setPstngStartDate(String pstngStartDate) {
        this.pstngStartDate = pstngStartDate;
    }
    /**
     * @return the pstngEndDate
     */
    public String getPstngEndDate() {
        return pstngEndDate;
    }
    /**
     * @param pstngEndDate the pstngEndDate to set
     */
    public void setPstngEndDate(String pstngEndDate) {
        this.pstngEndDate = pstngEndDate;
    }
    /**
     * @return the platformCd
     */
    public String getPlatformCd() {
        return platformCd;
    }
    /**
     * @param platformCd the platformCd to set
     */
    public void setPlatformCd(String platformCd) {
        this.platformCd = platformCd;
    }
    /**
     * @return the popupOutputCd
     */
    public String getPopupOutputCd() {
        return popupOutputCd;
    }
    /**
     * @param popupOutputCd the popupOutputCd to set
     */
    public void setPopupOutputCd(String popupOutputCd) {
        this.popupOutputCd = popupOutputCd;
    }
    /**
     * @return the outputMenu
     */
    public String getOutputMenu() {
        return outputMenu;
    }
    /**
     * @param outputMenu the outputMenu to set
     */
    public void setOutputMenu(String outputMenu) {
        this.outputMenu = outputMenu;
    }
    public String getIndcOdrg() {
        return indcOdrg;
    }
    public void setIndcOdrg(String indcOdrg) {
        this.indcOdrg = indcOdrg;
    }
    public String getPopupShowUrl() {
        return popupShowUrl;
    }
    public void setPopupShowUrl(String popupShowUrl) {
        this.popupShowUrl = popupShowUrl;
    }
    public String getFilePathNm() {
        return filePathNm;
    }
    public void setFilePathNm(String filePathNm) {
        this.filePathNm = filePathNm;
    }

    public String getUsageType() {
        return usageType;
    }
    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getOneTimePopupGrp() {
        return oneTimePopupGrp;
    }

    public void setOneTimePopupGrp(String oneTimePopupGrp) {
        this.oneTimePopupGrp = oneTimePopupGrp;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
