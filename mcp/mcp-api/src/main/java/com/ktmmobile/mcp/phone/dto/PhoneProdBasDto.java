package com.ktmmobile.mcp.phone.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.mcp.cmmn.xss.RequestWrapper;


/**
 * @Class Name : PhoneProdBasDto
 * @Description : MCP 상품 기본 Dto
 *  1.정렬 처리기능
 *  2.각종 코드값에 대한 Label 반환기능
 *
 * @author : ant
 * @Create Date : 2016. 1. 5.
 */
public class PhoneProdBasDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 상품 최저가를 위한 리스트정보 */
    List<MspSaleSubsdMstDto> mspSaleSubsdMstListForLowPrice;

    /** 단품정보 */
    List<PhoneSntyBasDto> phoneSntyBasDtosList;

    /** 단품정보 판매여부에 상관없이 전체 출력  */
    List<PhoneSntyBasDto> phoneSntyBasDtoListY;

    /** 단품정보리스트 (품절,판매중단 표기 안함) */
    List<PhoneSntyBasDto> phoneSntyBasDtoListOnline;

    /** 상품이미지  */
    private List<PhoneProdImgDto> phoneProdImgDtoList;

    /** 대표상품의 상품이미지 정보  */
    private PhoneProdImgDto rprtPhoneProdImgDto;

    /**레이어보이기 파일 아이템  */
    private MultipartFile layerFileItem;

    /** 상품id */
    private String prodId;

    /** 상품분류아이디  LTE:04 ,3G:03*/
    private String prodCtgId;

    /** 상품명 */
    private String prodNm;

    /**제조사코드 */
    private String makrCd;

    /** 노출여부 */
    private String showYn;

    /** 판매여부*/
    private String saleYn;

    /** 출고일자 */
    private String ofwDate;

    /** 중고여부 */
    private String shandYn;

    /** 목록노출 텍스트 */
    private String listShowText;

    /** 목록노출옵션 */
    private String listShowOptn;

    /** 추가설명 1*/
    private String apdDesc1;

    /** 추가설명 2*/
    private String apdDesc2;

    /** 추가설명 3*/
    private String apdDesc3;

    /** 스티커노출(상) 01:BEST ,02:NEW, 03:신상 , 04:특가, 05:인기상품*/
    private String stckTypeTop;

    /** 스티커노출(하) 01:최대공시금지원, 02:공시지원금확대, 03:이벤트, 04:국내유일 */
    private String stckTypeTail;

    /** 노출순서 */
    int showOdrg;

    /** 단말기 상품설명 */
    private String sntyProdDesc;

    /** 단말기네트워크 */
    private String sntyNet;

    /** 단말기 디스플레이 */
    private String sntyDisp;

    /** 단말기 크기*/
    private String sntySize;

    /** 단말기 무게*/
    private String sntyWeight;

    /** 단말기 메모리 */
    private String sntyMemr;

    /** 단말기 베터리*/
    private String sntyBtry;

    /** 단말기 os*/
    private String sntyOs;

    /** 단말기 대기시간 */
    private String sntyWaitTime;

    /** 단말기 카메라 */
    private String sntyCam;

    /** 단말기 영상통화 */
    private String sntyVideTlk;

    /** 생성자 id*/
    private String cretId;

    /** 수정자 id */
    private String amdId;

    /** 최저가 가격 */
    private int payMnthAmt;
    /** 최저가 할부원금 */
    private int  instAmt;


    /** 생성일시 */
    Date cretDt;

    /** 수정일시 */
    Date amdDt;

    /** 대표모델id */
    private String rprsPrdtId;

    /** 레이어팝업 노출여부 */
    private String layerYn;

    /** 레이어이미지팝업경로 */
    private String layerImageUrl;

    /**단말기명 */
    private String sntyProdNm;

    /**출시월 */
    private String sntyRelMonth;

    /**단말기 색상 */
    private String sntyColor;

    /** 단말기 제조사/브랜드명 */
    private String sntyMaker;

    /** 단말기 모델번호  */
    private String sntyModelId;

    /** 핸드폰 상세 설명  */
    private String sntyDetailSpec;

    /**제조사명  */
    private String mnfctNm;

    /** 판매여부 text */
    private String saleYnLabel;

    /** 노출 여부 text */
    private String showYnLabel;

    /** 상품카테고리(3G,LTE) TEXT*/
    private String prodCtgIdLabel;

    /** 제조사명 */
    private String makrNm;

    /** 재고수량 */
    int inventoryAmt;

    /** 중고폰 판매가격 */
    int salePrice;

    /** 상품등급 */
    private String prodGrade;

    /** 중고폰분류값 (중고폰:01,외산폰:02) */
    private String shandType;

    /** 중고폰분류값 label */
    private String shandTypeLabel;

    /** 정렬순서구분값  */
    private OrderEnum orderEnum;

    /** 추천요금제 정보  */
    private String recommendRate;

    /** 중고폰 워런티 */
    private String usedWarranty;

    /** 상품순서 정렬용 생성일자 -> 출시일로변경 */
    private String orderCretDt;

    /** 선택한 단품의 대표 이미지를 불러오기 위해서 세팅하는 모델Id*/
    private String hndsetModelId;

    /** 중고폰 링크 url*/
    private String rinkUrl;


    /** 상품 분류  (일반 :01, 0원 상품 :02)*/
    private String prodType;

    private String prodTypeLabel;

    /** 렌탈 기본료 금액 */
    int rentalBaseAmt;

    /** 렌탈 기본료 할인 금액 */
    int rentalBaseDcAmt;

    /** 단말기 배상 금액 */
    int rentalModelCpAmt;

    /** Phone_Query.listPhoneProdBasForFrontOneQuery 상품리스트 조회에 정책 코드  */
    private String salePlcyCd ;

    /** 자급제폰 여부(Y : 자급제폰, N: 자급제폰아님) */
    private String sesplsYn;

    /** 출고단가 할인가 */
    private int outUnitPric;

    /** 입고단가 최초가*/
    private int inUnitPric ;


    private String agrmTrmBase;


    private String recommendRateNoargm;

    private String repRate;

    private String repRateDataType; //추천요금제의 dataType(LTE, 5G)

    /** SETTL_YN,  -- 결제여부(Y,N) */
    private String settlYn;

    public void setRepRateDataType(String repRateDataType) {
        this.repRateDataType = repRateDataType;
    }

    public String getRepRateDataType() {
        return this.repRateDataType;
    }

    public void setRepRate(String repRate) {
        this.repRate = repRate;
    }

    public String getRepRate() {
        return this.repRate;
    }

    public int getOutUnitPric() {
        return outUnitPric;
    }


    public void setOutUnitPric(int outUnitPric) {
        this.outUnitPric = outUnitPric;
    }

    public int getInUnitPric() {
        return inUnitPric;
    }

    public void setInUnitPric(int inUnitPric) {
        this.inUnitPric = inUnitPric;
    }


    /** 멀티파트 일경우 입력값들의 Xss 방어를 위한 처리 작업을한다.*/
    public void cleanXssAtMultipart() {

        prodNm = RequestWrapper.cleanXSS(prodNm);
        apdDesc1 = RequestWrapper.cleanXSS(apdDesc1);
        apdDesc2 = RequestWrapper.cleanXSS(apdDesc2);
        apdDesc3 = RequestWrapper.cleanXSS(apdDesc3);
        sntyProdNm = RequestWrapper.cleanXSS(sntyProdNm);
        sntySize = RequestWrapper.cleanXSS(sntySize);
        sntyProdDesc = RequestWrapper.cleanXSS(sntyProdDesc);
        sntyWeight = RequestWrapper.cleanXSS(sntyWeight);
        sntyModelId = RequestWrapper.cleanXSS(sntyModelId);
        sntyMemr = RequestWrapper.cleanXSS(sntyMemr);
        sntyNet = RequestWrapper.cleanXSS(sntyNet);
        sntyBtry = RequestWrapper.cleanXSS(sntyBtry);
        sntyMaker = RequestWrapper.cleanXSS(sntyMaker);
        sntyOs = RequestWrapper.cleanXSS(sntyOs);
        sntyRelMonth = RequestWrapper.cleanXSS(sntyRelMonth);
        sntyWaitTime = RequestWrapper.cleanXSS(sntyWaitTime);
        sntyColor = RequestWrapper.cleanXSS(sntyColor);
        sntyCam = RequestWrapper.cleanXSS(sntyCam);
        sntyDisp = RequestWrapper.cleanXSS(sntyDisp);
        sntyVideTlk = RequestWrapper.cleanXSS(sntyVideTlk);
    }


    /**
    * @Description :대표상품의 이미지 정보를 가져온다.
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 27.
    */
    public PhoneProdImgDto getRprtPhoneProdImgDto() {
        List<PhoneSntyBasDto> tempSnty = getPhoneSntyBasDtosList();
        String atribValCd1 = "";
        if (tempSnty != null && tempSnty.size() > 0) {
            for (PhoneSntyBasDto basket : tempSnty) { //해당상품의 단품속성을 순회 하면서 대표상품을 찾는다.
                if(StringUtils.isNoneEmpty(hndsetModelId)) {				//입력받은 핸드셋 모델이 있을경우
                    if (hndsetModelId.equals(basket.getHndsetModelId())) {
                        atribValCd1 = basket.getAtribValCd1();
                        break;
                    }
                } else {													//입력받은 핸드셋 모델이 없을경우 대표여부인 모델을 set 한다.
                    if ("Y".equals(basket.getRprsPrdtYn())) {
                        atribValCd1 = basket.getAtribValCd1();
                        break;
                    }
                }
            }
        }

        List<PhoneProdImgDto> tempImage = getPhoneProdImgDtoList();
        if (tempImage != null && tempImage.size() > 0) {
            for (PhoneProdImgDto basket : tempImage) {		 //상품이미지정보를 검사해서 해당 색상코드에 해당하는 상품정보를 가져온다.
                if (atribValCd1.equals(basket.getSntyColorCd())) {	//대표단품일경우 해당 단품의 색상코드값을 가져온다.
                    rprtPhoneProdImgDto = basket;
                    break;
                }
            }
        }
        return rprtPhoneProdImgDto;
    }
    /**
    * @Description : 정렬 처리 최저가를 구하기 위해서 단말기 할부원금을 기준으로 내림차순정렬한다.
    * @Author : ant
    * @Create Date : 2016. 1. 25.
    */
    public void doSort() {
        Collections.sort(mspSaleSubsdMstListForLowPrice, new Comparator<MspSaleSubsdMstDto>() {
            @Override
            public int compare(MspSaleSubsdMstDto o1, MspSaleSubsdMstDto o2) {
                OrderEnum oe = getOrderEnum();		//현재 선택되있는 정렬순서를 가져옴
                return oe.orderStragety(o1,o2);
            }
        });
    }

    /**
    * @Description : 판매중이아니면
    * return true;
    * @return
    * @Author : ant
    * @Create Date : 2016. 3. 3.
    */
    public boolean doNotSale() {
        if ("N".equals(saleYn)) {
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


    public List<MspSaleSubsdMstDto> getMspSaleSubsdMstListForLowPrice() {
        return mspSaleSubsdMstListForLowPrice;
    }

    public void setMspSaleSubsdMstListForLowPrice(
            List<MspSaleSubsdMstDto> mspSaleSubsdMstListForLowPrice) {
        this.mspSaleSubsdMstListForLowPrice = mspSaleSubsdMstListForLowPrice;
    }


    public String getShandTypeLabel() {
        if ("01".equals(shandType)) {
            this.shandTypeLabel = "중고폰";
            return shandTypeLabel;
        } else if ("02".equals(shandType)) {
            this.shandTypeLabel = "액세서리";
            return shandTypeLabel;
        }  else if ("03".equals(shandType)) {
            this.shandTypeLabel = "직구";
            return shandTypeLabel;
        }
        return shandTypeLabel;
    }

    public String getSaleYnLabel() {
        if (saleYn == null || saleYn.equals("N")) {
            this.saleYnLabel = "미판매";
        } else {
            this.saleYnLabel = "판매";
        }
        return saleYnLabel;
    }

    public String getShowYnLabel() {
        if (showYn == null || showYn.equals("N")) {
            this.showYnLabel = "미노출";
        } else {
            this.showYnLabel = "노출";
        }
        return showYnLabel;
    }

    public String getProdCtgIdLabel() {
        if (prodCtgId == null) {
            this.prodCtgIdLabel = "알수없음";
        } else if (prodCtgId.equals("03")) {
            this.prodCtgIdLabel = "3G";
        } else if (prodCtgId.equals("04")) {
            this.prodCtgIdLabel = "LTE";
        }
        return prodCtgIdLabel;
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

    public String getShandYn() {
        return shandYn;
    }

    public void setShandYn(String shandYn) {
        this.shandYn = shandYn;
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

    public int getShowOdrg() {
        return showOdrg;
    }

    public void setShowOdrg(int showOdrg) {
        this.showOdrg = showOdrg;
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

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public Date getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

    public String getRprsPrdtId() {
        return rprsPrdtId;
    }

    public void setRprsPrdtId(String rprsPrdtId) {
        this.rprsPrdtId = rprsPrdtId;
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

    public String getLayerYn() {
        return layerYn;
    }

    public void setLayerYn(String layerYn) {
        this.layerYn = layerYn;
    }

    public String getLayerImageUrl() {
        return layerImageUrl;
    }

    public void setLayerImageUrl(String layerImageUrl) {
        this.layerImageUrl = layerImageUrl;
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

    public String getSntyDetailSpec() {
        return sntyDetailSpec;
    }

    public void setSntyDetailSpec(String sntyDetailSpec) {
        this.sntyDetailSpec = sntyDetailSpec;
    }

    public MultipartFile getLayerFileItem() {
        return layerFileItem;
    }

    public void setLayerFileItem(MultipartFile layerFileItem) {
        this.layerFileItem = layerFileItem;
    }

    public List<PhoneProdImgDto> getPhoneProdImgDtoList() {
        return phoneProdImgDtoList;
    }

    public void setPhoneProdImgDtoList(List<PhoneProdImgDto> phoneProdImgDtoList) {
        this.phoneProdImgDtoList = phoneProdImgDtoList;
    }

    public List<PhoneSntyBasDto> getPhoneSntyBasDtosList() {
        return phoneSntyBasDtosList;
    }

    public void setPhoneSntyBasDtosList(List<PhoneSntyBasDto> phoneSntyBasDtosList) {
        this.phoneSntyBasDtosList = phoneSntyBasDtosList;
    }

    public String getMnfctNm() {
        return mnfctNm;
    }

    public void setMnfctNm(String mnfctNm) {
        this.mnfctNm = mnfctNm;
    }

    public String getMakrNm() {
        return makrNm;
    }

    public void setMakrNm(String makrNm) {
        this.makrNm = makrNm;
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

    public String getProdGradeTxt() {
        if (prodGrade != null && prodGrade.equals("N")) {
            return  "신품";
        } else if (prodGrade != null) {
            return prodGrade + "등급";
        } else {
            return "";
        }
    }

    public void setProdGrade(String prodGrade) {
        this.prodGrade = prodGrade;
    }

    public void setSaleYnLabel(String saleYnLabel) {
        this.saleYnLabel = saleYnLabel;
    }

    public void setShowYnLabel(String showYnLabel) {
        this.showYnLabel = showYnLabel;
    }

    public void setProdCtgIdLabel(String prodCtgIdLabel) {
        this.prodCtgIdLabel = prodCtgIdLabel;
    }

    public String getShandType() {
        return shandType;
    }

    public void setShandType(String shandType) {
        this.shandType = shandType;
    }
    public OrderEnum getOrderEnum() {
        return orderEnum;
    }
    public void setOrderEnum(OrderEnum orderEnum) {
        this.orderEnum = orderEnum;
    }
    public String getRecommendRate() {
        return recommendRate;
    }
    public void setRecommendRate(String recommendRate) {
        this.recommendRate = recommendRate;
    }
    public String getUsedWarranty() {
        if (usedWarranty == null || usedWarranty.trim().equals("")) {
            return "없음";
        }
        return usedWarranty;
    }
    public void setUsedWarranty(String usedWarranty) {
        this.usedWarranty = usedWarranty;
    }
    public String getOrderCretDt() {
        return orderCretDt;
    }
    public void setOrderCretDt(String orderCretDt) {
        this.orderCretDt = orderCretDt;
    }

    public List<PhoneSntyBasDto> getPhoneSntyBasDtoListY() {
        List<PhoneSntyBasDto> saleYList = null;
        if(phoneSntyBasDtosList != null) {
            saleYList = new ArrayList<PhoneSntyBasDto>();
            for (PhoneSntyBasDto a : phoneSntyBasDtosList) {
                if ("Y".equals(a.getSaleYn())) {
                    saleYList.add(a);
                }
            }
        }
        return saleYList;
    }

    public List<PhoneSntyBasDto> getPhoneSntyBasDtoListOnline() {
        List<PhoneSntyBasDto> onlineList = null;
        if(phoneSntyBasDtosList != null) {
            onlineList = new ArrayList<PhoneSntyBasDto>();
            for (PhoneSntyBasDto a : phoneSntyBasDtosList) {
                if ("Y".equals(a.getSaleYn()) && !"Y".equals(a.getSdoutYn())) {
                    onlineList.add(a);
                }
            }
        }
        return onlineList;
    }
    public String getHndsetModelId() {
        return hndsetModelId;
    }
    public void setHndsetModelId(String hndsetModelId) {
        this.hndsetModelId = hndsetModelId;
    }


    public String getRinkUrl() {
        return rinkUrl;
    }
    public void setRinkUrl(String rinkUrl) {
        this.rinkUrl = rinkUrl;
    }
    public String getProdType() {
        return prodType;
    }
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdTypeLabel(){
        if("01".equals(prodType)){
            prodTypeLabel="일반";
        }else if("04".equals(prodType)){
            prodTypeLabel="중고폰";
        }else if("02".equals(prodType)){
            prodTypeLabel="0원상품";
        }else if("05".equals(prodType)){
            prodTypeLabel="자급제폰";
        }else{
            prodTypeLabel="";
        }
        return prodTypeLabel;
    }

    public int getRentalBaseAmt() {
        return rentalBaseAmt;
    }
    public void setRentalBaseAmt(int rentalBaseAmt) {
        this.rentalBaseAmt = rentalBaseAmt;
    }
    public int getRentalBaseDcAmt() {
        return rentalBaseDcAmt;
    }
    public void setRentalBaseDcAmt(int rentalBaseDcAmt) {
        this.rentalBaseDcAmt = rentalBaseDcAmt;
    }
    public int getRentalModelCpAmt() {
        return rentalModelCpAmt;
    }
    public void setRentalModelCpAmt(int rentalModelCpAmt) {
        this.rentalModelCpAmt = rentalModelCpAmt;
    }
    public String getSalePlcyCd() {
        return salePlcyCd;
    }

    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }


    public int getPayMnthAmt() {
        return payMnthAmt;
    }


    public void setPayMnthAmt(int payMnthAmt) {
        this.payMnthAmt = payMnthAmt;
    }


    public int getInstAmt() {
        return instAmt;
    }


    public void setInstAmt(int instAmt) {
        this.instAmt = instAmt;
    }

    public String getSesplsYn() {
        return sesplsYn;
    }

    public void setSesplsYn(String sesplsYn) {
        this.sesplsYn = sesplsYn;
    }

    public String getAgrmTrmBase() {
        return agrmTrmBase;
    }


    public void setAgrmTrmBase(String agrmTrmBase) {
        this.agrmTrmBase = agrmTrmBase;
    }


    public String getRecommendRateNoargm() {
        return recommendRateNoargm;
    }


    public void setRecommendRateNoargm(String recommendRateNoargm) {
        this.recommendRateNoargm = recommendRateNoargm;
    }

    public String getSettlYn() {
        return settlYn;
    }

    public void setSettlYn(String settlYn) {
        this.settlYn = settlYn;
    }



}
