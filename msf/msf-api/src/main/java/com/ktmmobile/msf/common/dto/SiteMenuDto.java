package com.ktmmobile.msf.common.dto;

import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

public class SiteMenuDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int menuSeq;
    private String menuCode;
    private String menuNm;
    private String groupKey;
    private String prntsKey;
    private int depthKey;
    private String urlAdr;
    private int repUrlSeq;
    private String statVal;
    private String acesAlwdYn;
    private String menuOutputCd;
    private String cntpntCd;
    private String platformCd;
    private String menuLinkCd;
    private String menuStressYn;
    private String mobileMenuUseYn;
    private String appMenuUseYn;
    private String pstngStartDate;
    private String pstngEndDate;
    private String chatbotTipSbst;
    private String cretId;
    private String amdId;
    private String cretDt;
    private String amdDt;
    private int sortKey;
    private String menuHierTypeCd;
    private MultipartFile listImg;
    private String imgDesc;
    private String linkUrlAdr;
    private String menuDesc;
    private String attYn;
    private String filePathNm;
    private String fileType;
    private int fileCapa;
    private MultipartFile listImg2;
    private String imgDesc2;
    private String linkUrlAdr2;
    private String attYn2;

    private int nextDepthCnt;
    private String autGradeCd = "";

    public String getAutGradeCd() {
		return autGradeCd;
	}
	public void setAutGradeCd(String autGradeCd) {
		this.autGradeCd = autGradeCd;
	}
	public int getMenuSeq() {
		return menuSeq;
	}
	public void setMenuSeq(int menuSeq) {
		this.menuSeq = menuSeq;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getGroupKey() {
		return groupKey;
	}
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
	public String getPrntsKey() {
		return prntsKey;
	}
	public void setPrntsKey(String prntsKey) {
		this.prntsKey = prntsKey;
	}
	public int getDepthKey() {
		return depthKey;
	}
	public void setDepthKey(int depthKey) {
		this.depthKey = depthKey;
	}
	public String getUrlAdr() {
		return urlAdr;
	}
	public void setUrlAdr(String urlAdr) {
		this.urlAdr = urlAdr;
	}
	public int getRepUrlSeq() {
		return repUrlSeq;
	}
	public void setRepUrlSeq(int repUrlSeq) {
		this.repUrlSeq = repUrlSeq;
	}
	public String getStatVal() {
		return statVal;
	}
	public void setStatVal(String statVal) {
		this.statVal = statVal;
	}
	public String getAcesAlwdYn() {
		return acesAlwdYn;
	}
	public void setAcesAlwdYn(String acesAlwdYn) {
		this.acesAlwdYn = acesAlwdYn;
	}
	public String getMenuOutputCd() {
		return menuOutputCd;
	}
	public void setMenuOutputCd(String menuOutputCd) {
		this.menuOutputCd = menuOutputCd;
	}
	public String getCntpntCd() {
		return cntpntCd;
	}
	public void setCntpntCd(String cntpntCd) {
		this.cntpntCd = cntpntCd;
	}
	public String getPlatformCd() {
		return platformCd;
	}
	public void setPlatformCd(String platformCd) {
		this.platformCd = platformCd;
	}
	public String getMenuLinkCd() {
		return menuLinkCd;
	}
	public void setMenuLinkCd(String menuLinkCd) {
		this.menuLinkCd = menuLinkCd;
	}
	public String getMenuStressYn() {
		return menuStressYn;
	}
	public void setMenuStressYn(String menuStressYn) {
		this.menuStressYn = menuStressYn;
	}
	public String getMobileMenuUseYn() {
		return mobileMenuUseYn;
	}
	public void setMobileMenuUseYn(String mobileMenuUseYn) {
		this.mobileMenuUseYn = mobileMenuUseYn;
	}
	public String getAppMenuUseYn() {
		return appMenuUseYn;
	}
	public void setAppMenuUseYn(String appMenuUseYn) {
		this.appMenuUseYn = appMenuUseYn;
	}
	public String getPstngStartDate() {
		return pstngStartDate;
	}
	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}
	public String getPstngEndDate() {
		return pstngEndDate;
	}
	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}
	public String getChatbotTipSbst() {
		return chatbotTipSbst;
	}
	public void setChatbotTipSbst(String chatbotTipSbst) {
		this.chatbotTipSbst = chatbotTipSbst;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	public int getSortKey() {
		return sortKey;
	}
	public void setSortKey(int sortKey) {
		this.sortKey = sortKey;
	}
	public String getMenuHierTypeCd() {
		return menuHierTypeCd;
	}
	public void setMenuHierTypeCd(String menuHierTypeCd) {
		this.menuHierTypeCd = menuHierTypeCd;
	}
	public MultipartFile getListImg() {
		return listImg;
	}
	public void setListImg(MultipartFile listImg) {
		this.listImg = listImg;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}
	public String getLinkUrlAdr() {
		return linkUrlAdr;
	}
	public void setLinkUrlAdr(String linkUrlAdr) {
		this.linkUrlAdr = linkUrlAdr;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public String getAttYn() {
		return attYn;
	}
	public void setAttYn(String attYn) {
		this.attYn = attYn;
	}
	public String getFilePathNm() {
		return filePathNm;
	}
	public void setFilePathNm(String filePathNm) {
		this.filePathNm = filePathNm;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getFileCapa() {
		return fileCapa;
	}
	public void setFileCapa(int fileCapa) {
		this.fileCapa = fileCapa;
	}
	public MultipartFile getListImg2() {
		return listImg2;
	}
	public void setListImg2(MultipartFile listImg2) {
		this.listImg2 = listImg2;
	}
	public String getImgDesc2() {
		return imgDesc2;
	}
	public void setImgDesc2(String imgDesc2) {
		this.imgDesc2 = imgDesc2;
	}
	public String getLinkUrlAdr2() {
		return linkUrlAdr2;
	}
	public void setLinkUrlAdr2(String linkUrlAdr2) {
		this.linkUrlAdr2 = linkUrlAdr2;
	}
	public String getAttYn2() {
		return attYn2;
	}
	public void setAttYn2(String attYn2) {
		this.attYn2 = attYn2;
	}
	public int getNextDepthCnt() {
		return nextDepthCnt;
	}
	public void setNextDepthCnt(int nextDepthCnt) {
		this.nextDepthCnt = nextDepthCnt;
	}

}
