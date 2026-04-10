package com.ktmmobile.mcp.main.dto;

import java.io.Serializable;

public class NmcpRecommendProdDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private String prodId;               // 상품아이디
	private String prodCtgId;            // 상품분류아이디 (lte,3g)
	private String prodCtgName;          // 상품(색인)
    private String prodNm;                // 상품명(색인)
    private String makrCd;                // 제조사코드
    private String rprsPrdtId;           // 대표모델id
    private String showYn;                // 노출여부
    private String saleYn;                // 판매여부
    private String ofwDate;               // 출고일자
    private String listShowText;         // 목록노출텍스트(색인)
    private String listShowOptn;         // 목록노출옵션(색인)
    private String apdDesc1;             // 추가설명1(색인)
    private String apdDesc2;             // 추가설명2(색인)
    private String apdDesc3;             // 추가설명3(색인)
    private String stckTypeTop;          // 스티커노출(상)
    private String stckTypeTail;         // 스티커노출(하)
    private String sntyProdDesc;         // 단말기상품설명(색인)
    private String sntyNet;               // 단말기네트워크(색인)
    private String sntyDisp;              // 단말기디스플레이(색인)
    private String sntySize;              // 단말기크기(색인)
    private String sntyWeight;            // 단말기무게(색인)
    private String sntyMemr;              // 단말기메모리(색인)
    private String sntyBtry;              // 단말기배터리(색인)
    private String sntySs;                // 단말기os(색인)
    private String sntyWaitTime;         // 단말기대기시간(색인)
    private String sntyCam;               // 단말기카메라(색인)
    private String sntyVideTlk;          // 단말기영상통화(색인)
    private String sntyProdNm;           // 단말기명(색인)
    private String sntyRelMonth;         // 출시월(색인)
    private String sntyColor;             // 단말기색상(색인)
    private String sntyMaker;             // 단말기제조사/브랜드명(색인)
    private String sntyModelId;          // 단말기모델번호(색인)
    private String makrNm;                // 제조사명 중고폰에서만사용
    private String shandYn;               //  중고폰여부
    private int inventoryAmt;             // 중고폰 재고수량
    private int salePrice;                // 중고폰 판매가격
    private String prodGrade;             // 종고폰 등급(색인)
    private String shandType;             // 중고폰분류값(중고폰,외산폰)
    private String shandTypeName;        // 중고폰분류(색인)
    private String recommendRate;         // 추천요금제
    private String usedWarranty;          // 중고폰 워런티(색인)
    private String usedWarrantyName;     // 중고폰 워런티(색인)
    private String sntyColorCd;          // 색상코드
    private String imgTypeCd;            // 이미지유형구분(앞,뒤..)
    private String imgPath;               // 이미지경로
	private String hndsetModelNm;        // 단말기모델명(색인)
     
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
	public String getSntySs() {
		return sntySs;
	}
	public void setSntySs(String sntySs) {
		this.sntySs = sntySs;
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
	public int getInventoryAmt() {
		return inventoryAmt;
	}
	public void setInventoryAmt(int inventoryAmt) {
		this.inventoryAmt = inventoryAmt;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
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
	public String getHndsetModelNm() {
		return hndsetModelNm;
	}
	public void setHndsetModelNm(String hndsetModelNm) {
		this.hndsetModelNm = hndsetModelNm;
	}
}
