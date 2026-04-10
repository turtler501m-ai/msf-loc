package com.ktmmobile.mcp.mstory.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.type.Alias;

public class MstoryDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<MstoryAttDto> mstoryAttDtoList;
	
	
	private int ntcartSeq;
	private String snsCtgCd;
	private String yy;
	private String mm;
	private String snsCntpntCd;
	private String ntcartTitle;
	private String thumbImgNm;
	private String thumbImgDesc;
	private String pcListImgNm;
	private String pcListImgDesc;
	private String mobileListImgNm;
	private String mobileListImgDesc;
	private String pcLinkUrl;
	private String mobileLinkUrl;
	private String ntcartDate;
	private int hitCnt;
	private String sortOdrg;
	private String useYn;
	private String pstngStartDate;
	private String pstngEndDate;
	private String cretIp;
	private LocalDate cretDt;
	private String cretId;
	private String amdIp;
	private LocalDate amdDt;
	private String amdId;

	@Deprecated
	private List<MstoryAttDto> getMstoryAttDtoList() {
		return mstoryAttDtoList;
	}
	@Deprecated
	private void setMstoryAttDtoList(List<MstoryAttDto> mstoryAttDtoList) {
		this.mstoryAttDtoList = mstoryAttDtoList;
	}
	
	public int getNtcartSeq() {
		return ntcartSeq;
	}
	public void setNtcartSeq(int ntcartSeq) {
		this.ntcartSeq = ntcartSeq;
	}
	public String getSnsCtgCd() {
		return snsCtgCd;
	}
	public void setSnsCtgCd(String snsCtgCd) {
		this.snsCtgCd = snsCtgCd;
	}
	public String getYy() {
		return yy;
	}
	public void setYy(String yy) {
		this.yy = yy;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getSnsCntpntCd() {
		return snsCntpntCd;
	}
	public void setSnsCntpntCd(String snsCntpntCd) {
		this.snsCntpntCd = snsCntpntCd;
	}
	public String getNtcartTitle() {
		return ntcartTitle;
	}
	public void setNtcartTitle(String ntcartTitle) {
		this.ntcartTitle = ntcartTitle;
	}
	public String getThumbImgNm() {
		return thumbImgNm;
	}
	public void setThumbImgNm(String thumbImgNm) {
		this.thumbImgNm = thumbImgNm;
	}
	public String getThumbImgDesc() {
		return thumbImgDesc;
	}
	public void setThumbImgDesc(String thumbImgDesc) {
		this.thumbImgDesc = thumbImgDesc;
	}
	public String getPcListImgNm() {
		return pcListImgNm;
	}
	public void setPcListImgNm(String pcListImgNm) {
		this.pcListImgNm = pcListImgNm;
	}
	public String getPcListImgDesc() {
		return pcListImgDesc;
	}
	public void setPcListImgDesc(String pcListImgDesc) {
		this.pcListImgDesc = pcListImgDesc;
	}
	public String getMobileListImgNm() {
		return mobileListImgNm;
	}
	public void setMobileListImgNm(String mobileListImgNm) {
		this.mobileListImgNm = mobileListImgNm;
	}
	public String getMobileListImgDesc() {
		return mobileListImgDesc;
	}
	public void setMobileListImgDesc(String mobileListImgDesc) {
		this.mobileListImgDesc = mobileListImgDesc;
	}
	public String getPcLinkUrl() {
		return pcLinkUrl;
	}
	public void setPcLinkUrl(String pcLinkUrl) {
		this.pcLinkUrl = pcLinkUrl;
	}
	public String getMobileLinkUrl() {
		return mobileLinkUrl;
	}
	public void setMobileLinkUrl(String mobileLinkUrl) {
		this.mobileLinkUrl = mobileLinkUrl;
	}
	public String getNtcartDate() {
		return ntcartDate;
	}
	public void setNtcartDate(String ntcartDate) {
		this.ntcartDate = ntcartDate;
	}
	public int getHitCnt() {
		return hitCnt;
	}
	public void setHitCnt(int hitCnt) {
		this.hitCnt = hitCnt;
	}
	public String getSortOdrg() {
		return sortOdrg;
	}
	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	public String getCretIp() {
		return cretIp;
	}
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}
	public LocalDate getCretDt() {
		return cretDt;
	}
	public void setCretDt(LocalDate cretDt) {
		this.cretDt = cretDt;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	public LocalDate getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(LocalDate amdDt) {
		this.amdDt = amdDt;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
