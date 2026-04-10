package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;


public class SearchDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String searchType;

    /** 
     * 메뉴명, 1DEPTH > 2DEPTH > 3DEPTH, 링크
    */
    private String menuSeq;
    private String menuCode;
    private String menuNm;
    private String groupKey;
    private String prntsKey;
    private String depthKey;
    private String urlAdr;
    private String repUrlSeq;
    private String chatbotTipSbst;
    private String menuDesc;
    private String attYn;
    private String sortKey;
    
    /**
     *  휴대폰
     *  - 이미지, 휴대폰명, 링크
    */
    private String prodId;
    private String prodCtgId;
    private String prodCtgName;
    private String prodNm;
    private String makrCd;
    private String rprsPrdtId;
    private String showYn;
    private String saleYn;
    private String ofwDate;
    private String listShowText;
    private String listShowOptn;
    private String apdDesc1;
    private String apdDesc2;
    private String apdDesc3;
    private String stckTypeTop;
    private String stckTypeTail;
    private String sntyProdDesc;
    private String sntyNet;
    private String sntyDisp;
    private String sntySize;
    private String sntyWeight;
    private String sntyMemr;
    private String sntyBtry;
    private String sntyOs;
    private String sntyWaitTime;
    private String sntyCam;
    private String sntyVideTlk;
    private String sntyProdNm;
    private String sntyRelMonth;
    private String sntyColor;
    private String sntyMaker;
    private String sntyModelId;
    private String mnfctNm;
    private String zipcd;
    private String addr;
    private String dtlAddr;
    private String telnum;
    private String email;
    private String makrNm;
    private String shandYn;
    private String inventoryAmt;
    private String salePrice;
    private String prodGrade;
    private String shandType;
    private String shandTypeName;
    private String recommendRate;
    private String usedWarranty;
    private String usedWarrantyName;
    private String sntyColorCd;
    private String imgTypeCd;
    private String imgPath;
    private String hndsetModelId;
    private String hndsetModelNm;
    private String showOdrg;
    
    
    /**
     * 요금제
     * - 요금제명, 기본료, 프로모션요금
     * - NMCP_RATE_ADSVC_BNFIT_GDNC_DTL 여러건을 하나로 통합하여 색인
     * - NMCP_RATE_ADSVC_GDNC_DTL 여러건을 하나로 통합하여 색인
     * 보완
     * - 총개수
     * - 페이징
     */ 
    private String rateAdsvcGdncSeq;
    private String rateDivCd;
    private String rateAdsvcNm;
    private String rateAdsvcApdNm;
    private String rateAdsvcBasDesc;
    private String mmBasAmtDesc;
    private String mmBasAmtVatDesc;
    private String promotionAmtDesc;
    private String promotionAmtVatDesc;
    private String rateAdsvcCtgCd;
    private String rateAdsvcCtgNm;
    private String rateAdsvcCtgBasDesc;
    private String rateAdsvcCtgDtlDesc;
    private String rateAdsvcProdRelSeq;
    private String rateAdsvcCd;
    private String rateAdsvcItemCd;
    private String rateAdsvcItemNm1;
    private String rateAdsvcItemSbst;
    private String rateAdsvcBnfitItemCd;
    private String rateAdsvcItemNm2;
    private String rateAdsvcItemDesc;
    private String rateAdsvcItemApdDesc;
    private String sortOdrg1;
    private String sortOdrg2;
    
    /**
     * 부가서비스
     * - 부가서비스카테고리, 부가서비스명
     * - NMCP_RATE_ADSVC_GDNC_DTL 여러건을 하나로 통합하여 색인
     * 보완
     * - 총개수
     * - 페이징
     */ 
    //private String rateAdsvcGdncSeq;
    //private String rateAdsvcNm;
    //private String rateAdsvcApdNm;
    //private String rateAdsvcBasDesc;
    //private String mmBasAmtDesc;
    //private String mmBasAmtVatDesc;
    //private String promotionAmtDesc;
    //private String promotionAmtVatDesc;
    //private String rateAdsvcCtgCd;
    //private String rateAdsvcCtgNm;
    //private String rateAdsvcCtgBasDesc;
    //private String rateAdsvcCtgDtlDesc;
    //private String rateAdsvcProdRelSeq;
    //private String rateAdsvcCd;
    //private String rateAdsvcItemCd;
    private String rateAdsvcItemNm;
    //private String rateAdsvcItemSbst;
    //private String sortOdrg1;
    //private String sortOdrg2;
    
    /**
     * 진행중인이벤트
     * - 이벤트이미지, 이벤트기간, 이벤트명
     * 보완
     * - 총개수
     * - 페이징
     */ 
    private String ntcartSeq;
    private String sbstCtg;
    private String sbstCtgNm;
    private String ntcartSubject;
    private String listImg;
    private String thumbImgNm;
    private String thumbImgDesc;
    private String mobileListImgNm;
    private String imgDesc;
    private String ntcartSbst;
    private String dtlCdNm;
    private String eventStartDt;
    private String eventEndDt;
    private String eventStartDate;
    private String eventEndDate;
    private String startHour;
    private String endHour;
    private String linkTarget;
    private String linkUrlAdr;
    private String eventUrlAdr;
    private String ntcartHitCnt;
    private String eventAdd1Yn;
    private String eventAdd1Sbst;
    private String eventAdd2Yn;
    private String eventAdd3Yn;
    private String verificationUrl;
    private String plnSmallTitle;
    private String plnContent;
    private String snsSbst;
    private String ntcartSbst2;
    private String plnContent2;
    
    
    /**
     * 자주묻는 질문
     * - 질문, 답변
     * 보완
     * - 총개수
     * - 페이징
     */ 
    //private String sbstCtg;
    private String sbstSubCtgCd;
    //private String dtlCdNm;
    private String boardSeq;
    private String boardSubject;
    private String boardContents;
    private String boardHitCnt;
    
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getMenuSeq() {
		return menuSeq;
	}
	public void setMenuSeq(String menuSeq) {
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
	public String getDepthKey() {
		return depthKey;
	}
	public void setDepthKey(String depthKey) {
		this.depthKey = depthKey;
	}
	public String getUrlAdr() {
		return urlAdr;
	}
	public void setUrlAdr(String urlAdr) {
		this.urlAdr = urlAdr;
	}
	public String getRepUrlSeq() {
		return repUrlSeq;
	}
	public void setRepUrlSeq(String repUrlSeq) {
		this.repUrlSeq = repUrlSeq;
	}
	public String getChatbotTipSbst() {
		return chatbotTipSbst;
	}
	public void setChatbotTipSbst(String chatbotTipSbst) {
		this.chatbotTipSbst = chatbotTipSbst;
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
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProdCtgId() {
		return prodCtgId;
	}
	public void setProdCtgId(String prodCtgId) {
		this.prodCtgId = prodCtgId;
	}
	public String getProdCtgName() {
		return prodCtgName;
	}
	public void setProdCtgName(String prodCtgName) {
		this.prodCtgName = prodCtgName;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getMakrCd() {
		return makrCd;
	}
	public void setMakrCd(String makrCd) {
		this.makrCd = makrCd;
	}
	public String getRprsPrdtId() {
		return rprsPrdtId;
	}
	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}
	public String getShowYn() {
		return showYn;
	}
	public void setShowYn(String showYn) {
		this.showYn = showYn;
	}
	public String getSaleYn() {
		return saleYn;
	}
	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}
	public String getOfwDate() {
		return ofwDate;
	}
	public void setOfwDate(String ofwDate) {
		this.ofwDate = ofwDate;
	}
	public String getListShowText() {
		return listShowText;
	}
	public void setListShowText(String listShowText) {
		this.listShowText = listShowText;
	}
	public String getListShowOptn() {
		return listShowOptn;
	}
	public void setListShowOptn(String listShowOptn) {
		this.listShowOptn = listShowOptn;
	}
	public String getApdDesc1() {
		return apdDesc1;
	}
	public void setApdDesc1(String apdDesc1) {
		this.apdDesc1 = apdDesc1;
	}
	public String getApdDesc2() {
		return apdDesc2;
	}
	public void setApdDesc2(String apdDesc2) {
		this.apdDesc2 = apdDesc2;
	}
	public String getApdDesc3() {
		return apdDesc3;
	}
	public void setApdDesc3(String apdDesc3) {
		this.apdDesc3 = apdDesc3;
	}
	public String getStckTypeTop() {
		return stckTypeTop;
	}
	public void setStckTypeTop(String stckTypeTop) {
		this.stckTypeTop = stckTypeTop;
	}
	public String getStckTypeTail() {
		return stckTypeTail;
	}
	public void setStckTypeTail(String stckTypeTail) {
		this.stckTypeTail = stckTypeTail;
	}
	public String getSntyProdDesc() {
		return sntyProdDesc;
	}
	public void setSntyProdDesc(String sntyProdDesc) {
		this.sntyProdDesc = sntyProdDesc;
	}
	public String getSntyNet() {
		return sntyNet;
	}
	public void setSntyNet(String sntyNet) {
		this.sntyNet = sntyNet;
	}
	public String getSntyDisp() {
		return sntyDisp;
	}
	public void setSntyDisp(String sntyDisp) {
		this.sntyDisp = sntyDisp;
	}
	public String getSntySize() {
		return sntySize;
	}
	public void setSntySize(String sntySize) {
		this.sntySize = sntySize;
	}
	public String getSntyWeight() {
		return sntyWeight;
	}
	public void setSntyWeight(String sntyWeight) {
		this.sntyWeight = sntyWeight;
	}
	public String getSntyMemr() {
		return sntyMemr;
	}
	public void setSntyMemr(String sntyMemr) {
		this.sntyMemr = sntyMemr;
	}
	public String getSntyBtry() {
		return sntyBtry;
	}
	public void setSntyBtry(String sntyBtry) {
		this.sntyBtry = sntyBtry;
	}
	public String getSntyOs() {
		return sntyOs;
	}
	public void setSntyOs(String sntyOs) {
		this.sntyOs = sntyOs;
	}
	public String getSntyWaitTime() {
		return sntyWaitTime;
	}
	public void setSntyWaitTime(String sntyWaitTime) {
		this.sntyWaitTime = sntyWaitTime;
	}
	public String getSntyCam() {
		return sntyCam;
	}
	public void setSntyCam(String sntyCam) {
		this.sntyCam = sntyCam;
	}
	public String getSntyVideTlk() {
		return sntyVideTlk;
	}
	public void setSntyVideTlk(String sntyVideTlk) {
		this.sntyVideTlk = sntyVideTlk;
	}
	public String getSntyProdNm() {
		return sntyProdNm;
	}
	public void setSntyProdNm(String sntyProdNm) {
		this.sntyProdNm = sntyProdNm;
	}
	public String getSntyRelMonth() {
		return sntyRelMonth;
	}
	public void setSntyRelMonth(String sntyRelMonth) {
		this.sntyRelMonth = sntyRelMonth;
	}
	public String getSntyColor() {
		return sntyColor;
	}
	public void setSntyColor(String sntyColor) {
		this.sntyColor = sntyColor;
	}
	public String getSntyMaker() {
		return sntyMaker;
	}
	public void setSntyMaker(String sntyMaker) {
		this.sntyMaker = sntyMaker;
	}
	public String getSntyModelId() {
		return sntyModelId;
	}
	public void setSntyModelId(String sntyModelId) {
		this.sntyModelId = sntyModelId;
	}
	public String getMnfctNm() {
		return mnfctNm;
	}
	public void setMnfctNm(String mnfctNm) {
		this.mnfctNm = mnfctNm;
	}
	public String getZipcd() {
		return zipcd;
	}
	public void setZipcd(String zipcd) {
		this.zipcd = zipcd;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getDtlAddr() {
		return dtlAddr;
	}
	public void setDtlAddr(String dtlAddr) {
		this.dtlAddr = dtlAddr;
	}
	public String getTelnum() {
		return telnum;
	}
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMakrNm() {
		return makrNm;
	}
	public void setMakrNm(String makrNm) {
		this.makrNm = makrNm;
	}
	public String getShandYn() {
		return shandYn;
	}
	public void setShandYn(String shandYn) {
		this.shandYn = shandYn;
	}
	public String getInventoryAmt() {
		return inventoryAmt;
	}
	public void setInventoryAmt(String inventoryAmt) {
		this.inventoryAmt = inventoryAmt;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getProdGrade() {
		return prodGrade;
	}
	public void setProdGrade(String prodGrade) {
		this.prodGrade = prodGrade;
	}
	public String getShandType() {
		return shandType;
	}
	public void setShandType(String shandType) {
		this.shandType = shandType;
	}
	public String getShandTypeName() {
		return shandTypeName;
	}
	public void setShandTypeName(String shandTypeName) {
		this.shandTypeName = shandTypeName;
	}
	public String getRecommendRate() {
		return recommendRate;
	}
	public void setRecommendRate(String recommendRate) {
		this.recommendRate = recommendRate;
	}
	public String getUsedWarranty() {
		return usedWarranty;
	}
	public void setUsedWarranty(String usedWarranty) {
		this.usedWarranty = usedWarranty;
	}
	public String getUsedWarrantyName() {
		return usedWarrantyName;
	}
	public void setUsedWarrantyName(String usedWarrantyName) {
		this.usedWarrantyName = usedWarrantyName;
	}
	public String getSntyColorCd() {
		return sntyColorCd;
	}
	public void setSntyColorCd(String sntyColorCd) {
		this.sntyColorCd = sntyColorCd;
	}
	public String getImgTypeCd() {
		return imgTypeCd;
	}
	public void setImgTypeCd(String imgTypeCd) {
		this.imgTypeCd = imgTypeCd;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getHndsetModelId() {
		return hndsetModelId;
	}
	public void setHndsetModelId(String hndsetModelId) {
		this.hndsetModelId = hndsetModelId;
	}
	public String getHndsetModelNm() {
		return hndsetModelNm;
	}
	public void setHndsetModelNm(String hndsetModelNm) {
		this.hndsetModelNm = hndsetModelNm;
	}
	public String getShowOdrg() {
		return showOdrg;
	}
	public void setShowOdrg(String showOdrg) {
		this.showOdrg = showOdrg;
	}
	public String getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	public void setRateAdsvcGdncSeq(String rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	public String getRateDivCd() {
		return rateDivCd;
	}
	public void setRateDivCd(String rateDivCd) {
		this.rateDivCd = rateDivCd;
	}
	public String getRateAdsvcNm() {
		return rateAdsvcNm;
	}
	public void setRateAdsvcNm(String rateAdsvcNm) {
		this.rateAdsvcNm = rateAdsvcNm;
	}
	public String getRateAdsvcApdNm() {
		return rateAdsvcApdNm;
	}
	public void setRateAdsvcApdNm(String rateAdsvcApdNm) {
		this.rateAdsvcApdNm = rateAdsvcApdNm;
	}
	public String getRateAdsvcBasDesc() {
		return rateAdsvcBasDesc;
	}
	public void setRateAdsvcBasDesc(String rateAdsvcBasDesc) {
		this.rateAdsvcBasDesc = rateAdsvcBasDesc;
	}
	public String getMmBasAmtDesc() {
		return mmBasAmtDesc;
	}
	public void setMmBasAmtDesc(String mmBasAmtDesc) {
		this.mmBasAmtDesc = mmBasAmtDesc;
	}
	public String getMmBasAmtVatDesc() {
		return mmBasAmtVatDesc;
	}
	public void setMmBasAmtVatDesc(String mmBasAmtVatDesc) {
		this.mmBasAmtVatDesc = mmBasAmtVatDesc;
	}
	public String getPromotionAmtDesc() {
		return promotionAmtDesc;
	}
	public void setPromotionAmtDesc(String promotionAmtDesc) {
		this.promotionAmtDesc = promotionAmtDesc;
	}
	public String getPromotionAmtVatDesc() {
		return promotionAmtVatDesc;
	}
	public void setPromotionAmtVatDesc(String promotionAmtVatDesc) {
		this.promotionAmtVatDesc = promotionAmtVatDesc;
	}
	public String getRateAdsvcCtgCd() {
		return rateAdsvcCtgCd;
	}
	public void setRateAdsvcCtgCd(String rateAdsvcCtgCd) {
		this.rateAdsvcCtgCd = rateAdsvcCtgCd;
	}
	public String getRateAdsvcCtgNm() {
		return rateAdsvcCtgNm;
	}
	public void setRateAdsvcCtgNm(String rateAdsvcCtgNm) {
		this.rateAdsvcCtgNm = rateAdsvcCtgNm;
	}
	public String getRateAdsvcCtgBasDesc() {
		return rateAdsvcCtgBasDesc;
	}
	public void setRateAdsvcCtgBasDesc(String rateAdsvcCtgBasDesc) {
		this.rateAdsvcCtgBasDesc = rateAdsvcCtgBasDesc;
	}
	public String getRateAdsvcCtgDtlDesc() {
		return rateAdsvcCtgDtlDesc;
	}
	public void setRateAdsvcCtgDtlDesc(String rateAdsvcCtgDtlDesc) {
		this.rateAdsvcCtgDtlDesc = rateAdsvcCtgDtlDesc;
	}
	public String getRateAdsvcProdRelSeq() {
		return rateAdsvcProdRelSeq;
	}
	public void setRateAdsvcProdRelSeq(String rateAdsvcProdRelSeq) {
		this.rateAdsvcProdRelSeq = rateAdsvcProdRelSeq;
	}
	public String getRateAdsvcCd() {
		return rateAdsvcCd;
	}
	public void setRateAdsvcCd(String rateAdsvcCd) {
		this.rateAdsvcCd = rateAdsvcCd;
	}
	public String getRateAdsvcItemCd() {
		return rateAdsvcItemCd;
	}
	public void setRateAdsvcItemCd(String rateAdsvcItemCd) {
		this.rateAdsvcItemCd = rateAdsvcItemCd;
	}
	public String getRateAdsvcItemNm1() {
		return rateAdsvcItemNm1;
	}
	public void setRateAdsvcItemNm1(String rateAdsvcItemNm1) {
		this.rateAdsvcItemNm1 = rateAdsvcItemNm1;
	}
	public String getRateAdsvcItemSbst() {
		return rateAdsvcItemSbst;
	}
	public void setRateAdsvcItemSbst(String rateAdsvcItemSbst) {
		this.rateAdsvcItemSbst = rateAdsvcItemSbst;
	}
	public String getRateAdsvcBnfitItemCd() {
		return rateAdsvcBnfitItemCd;
	}
	public void setRateAdsvcBnfitItemCd(String rateAdsvcBnfitItemCd) {
		this.rateAdsvcBnfitItemCd = rateAdsvcBnfitItemCd;
	}
	public String getRateAdsvcItemNm2() {
		return rateAdsvcItemNm2;
	}
	public void setRateAdsvcItemNm2(String rateAdsvcItemNm2) {
		this.rateAdsvcItemNm2 = rateAdsvcItemNm2;
	}
	public String getRateAdsvcItemDesc() {
		return rateAdsvcItemDesc;
	}
	public void setRateAdsvcItemDesc(String rateAdsvcItemDesc) {
		this.rateAdsvcItemDesc = rateAdsvcItemDesc;
	}
	public String getRateAdsvcItemApdDesc() {
		return rateAdsvcItemApdDesc;
	}
	public void setRateAdsvcItemApdDesc(String rateAdsvcItemApdDesc) {
		this.rateAdsvcItemApdDesc = rateAdsvcItemApdDesc;
	}
	public String getSortOdrg1() {
		return sortOdrg1;
	}
	public void setSortOdrg1(String sortOdrg1) {
		this.sortOdrg1 = sortOdrg1;
	}
	public String getSortOdrg2() {
		return sortOdrg2;
	}
	public void setSortOdrg2(String sortOdrg2) {
		this.sortOdrg2 = sortOdrg2;
	}
	public String getRateAdsvcItemNm() {
		return rateAdsvcItemNm;
	}
	public void setRateAdsvcItemNm(String rateAdsvcItemNm) {
		this.rateAdsvcItemNm = rateAdsvcItemNm;
	}
	public String getNtcartSeq() {
		return ntcartSeq;
	}
	public void setNtcartSeq(String ntcartSeq) {
		this.ntcartSeq = ntcartSeq;
	}
	public String getSbstCtg() {
		return sbstCtg;
	}
	public void setSbstCtg(String sbstCtg) {
		this.sbstCtg = sbstCtg;
	}
	public String getSbstCtgNm() {
		return sbstCtgNm;
	}
	public void setSbstCtgNm(String sbstCtgNm) {
		this.sbstCtgNm = sbstCtgNm;
	}
	public String getNtcartSubject() {
		return ntcartSubject;
	}
	public void setNtcartSubject(String ntcartSubject) {
		this.ntcartSubject = ntcartSubject;
	}
	public String getListImg() {
		return listImg;
	}
	public void setListImg(String listImg) {
		this.listImg = listImg;
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
	public String getMobileListImgNm() {
		return mobileListImgNm;
	}
	public void setMobileListImgNm(String mobileListImgNm) {
		this.mobileListImgNm = mobileListImgNm;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}
	public String getNtcartSbst() {
		return ntcartSbst;
	}
	public void setNtcartSbst(String ntcartSbst) {
		this.ntcartSbst = ntcartSbst;
	}
	public String getDtlCdNm() {
		return dtlCdNm;
	}
	public void setDtlCdNm(String dtlCdNm) {
		this.dtlCdNm = dtlCdNm;
	}
	public String getEventStartDt() {
		return eventStartDt;
	}
	public void setEventStartDt(String eventStartDt) {
		this.eventStartDt = eventStartDt;
	}
	public String getEventEndDt() {
		return eventEndDt;
	}
	public void setEventEndDt(String eventEndDt) {
		this.eventEndDt = eventEndDt;
	}
	public String getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public String getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getLinkTarget() {
		return linkTarget;
	}
	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}
	public String getLinkUrlAdr() {
		return linkUrlAdr;
	}
	public void setLinkUrlAdr(String linkUrlAdr) {
		this.linkUrlAdr = linkUrlAdr;
	}
	public String getEventUrlAdr() {
		return eventUrlAdr;
	}
	public void setEventUrlAdr(String eventUrlAdr) {
		this.eventUrlAdr = eventUrlAdr;
	}
	public String getNtcartHitCnt() {
		return ntcartHitCnt;
	}
	public void setNtcartHitCnt(String ntcartHitCnt) {
		this.ntcartHitCnt = ntcartHitCnt;
	}
	public String getEventAdd1Yn() {
		return eventAdd1Yn;
	}
	public void setEventAdd1Yn(String eventAdd1Yn) {
		this.eventAdd1Yn = eventAdd1Yn;
	}
	public String getEventAdd1Sbst() {
		return eventAdd1Sbst;
	}
	public void setEventAdd1Sbst(String eventAdd1Sbst) {
		this.eventAdd1Sbst = eventAdd1Sbst;
	}
	public String getEventAdd2Yn() {
		return eventAdd2Yn;
	}
	public void setEventAdd2Yn(String eventAdd2Yn) {
		this.eventAdd2Yn = eventAdd2Yn;
	}
	public String getEventAdd3Yn() {
		return eventAdd3Yn;
	}
	public void setEventAdd3Yn(String eventAdd3Yn) {
		this.eventAdd3Yn = eventAdd3Yn;
	}
	public String getVerificationUrl() {
		return verificationUrl;
	}
	public void setVerificationUrl(String verificationUrl) {
		this.verificationUrl = verificationUrl;
	}
	public String getPlnSmallTitle() {
		return plnSmallTitle;
	}
	public void setPlnSmallTitle(String plnSmallTitle) {
		this.plnSmallTitle = plnSmallTitle;
	}
	public String getPlnContent() {
		return plnContent;
	}
	public void setPlnContent(String plnContent) {
		this.plnContent = plnContent;
	}
	public String getSnsSbst() {
		return snsSbst;
	}
	public void setSnsSbst(String snsSbst) {
		this.snsSbst = snsSbst;
	}
	public String getNtcartSbst2() {
		return ntcartSbst2;
	}
	public void setNtcartSbst2(String ntcartSbst2) {
		this.ntcartSbst2 = ntcartSbst2;
	}
	public String getPlnContent2() {
		return plnContent2;
	}
	public void setPlnContent2(String plnContent2) {
		this.plnContent2 = plnContent2;
	}
	public String getSbstSubCtgCd() {
		return sbstSubCtgCd;
	}
	public void setSbstSubCtgCd(String sbstSubCtgCd) {
		this.sbstSubCtgCd = sbstSubCtgCd;
	}
	public String getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getBoardSubject() {
		return boardSubject;
	}
	public void setBoardSubject(String boardSubject) {
		this.boardSubject = boardSubject;
	}
	public String getBoardContents() {
		return boardContents;
	}
	public void setBoardContents(String boardContents) {
		this.boardContents = boardContents;
	}
	public String getBoardHitCnt() {
		return boardHitCnt;
	}
	public void setBoardHitCnt(String boardHitCnt) {
		this.boardHitCnt = boardHitCnt;
	}
    
}

