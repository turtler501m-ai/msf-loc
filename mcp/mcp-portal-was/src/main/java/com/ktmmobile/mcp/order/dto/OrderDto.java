package com.ktmmobile.mcp.order.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;

public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(OrderDto.class);

    MspSaleSubsdMstDto mspSaleSubsdMstDto;

    String searchStart;
    String searchEnd;
    String requestKey;
    String resNo;
    String sysRdate;
    String operType;
    String instCmsn;
    String orgnId;

    String prodId;
    String reqModelColor;

    String prodNm;
    String listShowText;
    String rateNm;
    String modelPrice;
    String modelPriceVat;

    String modelInstallment;
    String modelDiscount1;
    String modelDiscount2;
    String modelDiscount3;
    String maxDiscount3;
    String modelMonthly;
    String enggMnthCnt;

    String joinPrice;
    String usimPrice;
    String sprtTp;
    String settlAmt;

    String requestStateCode;
    String pstate;
    String tbCd;
    String dlvryNo;
    String reqPayType;
    String reqBuyType;

    String reqBank;
    String reqAccountNumber;
    String reqCardCompany;
    String reqCardNo;

    String dlvryPost;
    String dlvryAddr;
    String dlvryAddrDtl;

    String cstmrName;
    String cstmrMail;
    String cstmrMobileFn;
    String cstmrMobileMn;
    String cstmrMobileRn;

    String baseAmt;
    String dcAmt;
    String addDcAmt;
    String prdtSctnCd;
    String imgPath;

    String proId;
    String cretId;
    String sntyColorCd;

    String prodType;

    String selfProdNm;

    private int apiParam1 = 0;
    private int apiParam2 = 0;

    private String atribValNmOne;

    private String atribValNmTwo;

    private String tmpStep;

    private String selfYn;

    private String onOffType;

    private String state;
    private int stateCount = 0;

    private String phoneCtgLabel;

    private String stateLabel;

    /** 폰 판매가능 여부*/
    private String phoneSaleYn;

    private String percelUrl;

    /** 요금제코드 */
    private String socCode;

    /** 정책코드 */
    private String modelSalePolicyCode ;
    /** 채널점아이디_판매점코드 */
    private String cntpntShopId;

    private String tempType;

    /**
     * 자급제 가격
     */
    private int hndsetSalePrice;

    /**
     * 자급제 포인트
     */
    private int usePoint;

    /**
     * 자급제 카드할인가
     */
    private int cardDcAmt;

    /** 유심종류(RCP2035) 06 */
    private String usimKindsCd;

    public String getTempType() {
        return tempType;
    }
    public void setTempType(String tempType) {
        this.tempType = tempType;
    }
    public String getPercelUrl() {
        return percelUrl;
    }
    public void setPercelUrl(String percelUrl) {
        this.percelUrl = percelUrl;
    }

    public String getPhoneSaleYn() {
        return phoneSaleYn;
    }
    public void setPhoneSaleYn(String phoneSaleYn) {
        this.phoneSaleYn = phoneSaleYn;
    }

    public String getStateLabel() {
        return stateLabel;
    }
    public void setStateLabel(String stateLabel) {
        this.stateLabel = stateLabel;
    }
    public String getPhoneCtgLabel() {
        return phoneCtgLabel;
    }
    public void setPhoneCtgLabel(String phoneCtgLabel) {
        this.phoneCtgLabel = phoneCtgLabel;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getStateCount() {
        return stateCount;
    }
    public void setStateCount(int stateCount) {
        this.stateCount = stateCount;
    }
    public String getOnOffType() {
        return onOffType;
    }
    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
    }
    public String getSelfYn() {
        return selfYn;
    }
    public void setSelfYn(String selfYn) {
        this.selfYn = selfYn;
    }

    public String getAtribValNmOne() {
        return atribValNmOne;
    }
    public void setAtribValNmOne(String atribValNmOne) {
        this.atribValNmOne = atribValNmOne;
    }
    public String getAtribValNmTwo() {
        return atribValNmTwo;
    }
    public void setAtribValNmTwo(String atribValNmTwo) {
        this.atribValNmTwo = atribValNmTwo;
    }

    public String getSearchStart() {
        return searchStart;
    }
    public void setSearchStart(String searchStart) {
        this.searchStart = searchStart;
    }
    public String getSearchEnd() {
        return searchEnd;
    }
    public void setSearchEnd(String searchEnd) {
        this.searchEnd = searchEnd;
    }
    public String getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }
    public String getResNo() {
        return resNo;
    }
    public void setResNo(String resNo) {
        this.resNo = resNo;
    }
    public String getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(String sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getOperType() {
        return operType;
    }
    public void setOperType(String operType) {
        this.operType = operType;
    }
    public String getInstCmsn() {
        return instCmsn;
    }
    public void setInstCmsn(String instCmsn) {
        this.instCmsn = instCmsn;
    }
    public String getOrgnId() {
        return orgnId;
    }
    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }
    public String getProdNm() {
        return prodNm;
    }
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    public String getListShowText() {
        return listShowText;
    }
    public void setListShowText(String listShowText) {
        this.listShowText = listShowText;
    }
    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }
    public String getModelPrice() {
        return modelPrice;
    }

    public int getModelPriceInt() {
        int result = 0;
        try{
            result = Integer.parseInt(modelPrice);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    public void setModelPrice(String modelPrice) {
        this.modelPrice = modelPrice;
    }
    public String getModelInstallment() {
        return modelInstallment;
    }
    public void setModelInstallment(String modelInstallment) {
        this.modelInstallment = modelInstallment;
    }
    public String getModelDiscount1() {
        return modelDiscount1;
    }
    public void setModelDiscount1(String modelDiscount1) {
        this.modelDiscount1 = modelDiscount1;
    }
    public String getModelDiscount2() {
        return modelDiscount2;
    }
    public int getModelDiscount2Int() {
        int result = 0;
        try{
            result = Integer.parseInt(modelDiscount2);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    public void setModelDiscount2(String modelDiscount2) {
        this.modelDiscount2 = modelDiscount2;
    }

    public String getModelDiscount3() {
        return modelDiscount3;
    }
    public int getModelDiscount3Int() {
        int result = 0;
        try{
            result = Integer.parseInt(modelDiscount3);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }

    public void setModelDiscount3(String modelDiscount3) {
        this.modelDiscount3 = modelDiscount3;
    }
    public String getModelMonthly() {
        return modelMonthly;
    }
    public int getModelMonthlyInt() {
        int result = 0;
        try{
            result = Integer.parseInt(modelMonthly);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    public void setModelMonthly(String modelMonthly) {
        this.modelMonthly = modelMonthly;
    }
    public String getJoinPrice() {
        return joinPrice;
    }
    public void setJoinPrice(String joinPrice) {
        this.joinPrice = joinPrice;
    }
    public String getUsimPrice() {
        return usimPrice;
    }
    public void setUsimPrice(String usimPrice) {
        this.usimPrice = usimPrice;
    }
    public String getSprtTp() {
        return sprtTp;
    }
    public void setSprtTp(String sprtTp) {
        this.sprtTp = sprtTp;
    }
    public String getRequestStateCode() {
        /*
         * 서식지 진행상태가 배송중 일때.. (10,03,04)
         * 그리고 택배사 정보 , 택배 송장 번호가 없을때..
         * 배송 대기로 설정
         */
        /*
        if ( ("10".equals(requestStateCode) || "03".equals(requestStateCode) || "04".equals(requestStateCode))
               && ( StringUtils.isBlank(tbCd) || StringUtils.isBlank(dlvryNo) ) ) {
            return "09";//배송 대기로 설정...
        } else {
            return requestStateCode;
        }
        */
        return requestStateCode;
    }
    public void setRequestStateCode(String requestStateCode) {
        this.requestStateCode = requestStateCode;
    }
    public String getPstate() {
        return pstate;
    }
    public void setPstate(String pstate) {
        this.pstate = pstate;
    }
    public String getTbCd() {
        if (StringUtils.isBlank(dlvryNo) || StringUtils.isBlank(tbCd) ) {
            return "";
        } else {
            return tbCd;
        }
    }
    public void setTbCd(String tbCd) {
        this.tbCd = tbCd;
    }
    public String getDlvryNo() {
        if (StringUtils.isBlank(dlvryNo) || StringUtils.isBlank(tbCd) ) {
            return "";
        } else {
            return dlvryNo;
        }
    }
    public void setDlvryNo(String dlvryNo) {
        this.dlvryNo = dlvryNo;
    }
    public String getReqPayType() {
        return reqPayType;
    }
    public void setReqPayType(String reqPayType) {
        this.reqPayType = reqPayType;
    }
    public String getReqBuyType() {
        return reqBuyType;
    }
    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }
    public String getReqBank() {
        return reqBank;
    }
    public void setReqBank(String reqBank) {
        this.reqBank = reqBank;
    }
    public String getReqAccountNumber() {
        return reqAccountNumber;
    }
    public void setReqAccountNumber(String reqAccountNumber) {
        this.reqAccountNumber = reqAccountNumber;
    }
    public String getReqCardCompany() {
        return reqCardCompany;
    }
    public void setReqCardCompany(String reqCardCompany) {
        this.reqCardCompany = reqCardCompany;
    }
    public String getReqCardNo() {
        return reqCardNo;
    }
    public void setReqCardNo(String reqCardNo) {
        this.reqCardNo = reqCardNo;
    }
    public String getDlvryPost() {
        return dlvryPost;
    }
    public void setDlvryPost(String dlvryPost) {
        this.dlvryPost = dlvryPost;
    }
    public String getDlvryAddr() {
        return dlvryAddr;
    }
    public void setDlvryAddr(String dlvryAddr) {
        this.dlvryAddr = dlvryAddr;
    }
    public String getDlvryAddrDtl() {
        return dlvryAddrDtl;
    }
    public void setDlvryAddrDtl(String dlvryAddrDtl) {
        this.dlvryAddrDtl = dlvryAddrDtl;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    public String getCstmrMail() {
        return cstmrMail;
    }
    public void setCstmrMail(String cstmrMail) {
        this.cstmrMail = cstmrMail;
    }
    public String getCstmrMobileFn() {
        return cstmrMobileFn;
    }
    public void setCstmrMobileFn(String cstmrMobileFn) {
        this.cstmrMobileFn = cstmrMobileFn;
    }
    public String getCstmrMobileMn() {
        return cstmrMobileMn;
    }
    public void setCstmrMobileMn(String cstmrMobileMn) {
        this.cstmrMobileMn = cstmrMobileMn;
    }
    public String getCstmrMobileRn() {
        return cstmrMobileRn;
    }
    public void setCstmrMobileRn(String cstmrMobileRn) {
        this.cstmrMobileRn = cstmrMobileRn;
    }
    public String getBaseAmt() {
        return baseAmt;
    }
    public int getBaseAmtInt() {
        int result = 0;
        try{
            result = Integer.parseInt(baseAmt);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    public void setBaseAmt(String baseAmt) {
        this.baseAmt = baseAmt;
    }
    public String getDcAmt() {
        return dcAmt;
    }
    public int getDcAmtInt() {
        int result = 0;
        try{
            result = Integer.parseInt(dcAmt);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    public void setDcAmt(String dcAmt) {
        this.dcAmt = dcAmt;
    }
    public String getAddDcAmt() {
        return addDcAmt;
    }
    public int getAddDcAmtInt() {
        int result = 0;
        try{
            result = Integer.parseInt(addDcAmt);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    public void setAddDcAmt(String addDcAmt) {
        this.addDcAmt = addDcAmt;
    }
    public String getPrdtSctnCd() {
        return prdtSctnCd;
    }
    public void setPrdtSctnCd(String prdtSctnCd) {
        this.prdtSctnCd = prdtSctnCd;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public String getProdId() {
        return prodId;
    }
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public String getReqModelColor() {
        return reqModelColor;
    }
    public void setReqModelColor(String reqModelColor) {
        this.reqModelColor = reqModelColor;
    }
    public String getProId() {
        return proId;
    }
    public void setProId(String proId) {
        this.proId = proId;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getSettlAmt() {
        return settlAmt;
    }
    public void setSettlAmt(String settlAmt) {
        this.settlAmt = settlAmt;
    }
    public String getSntyColorCd() {
        return sntyColorCd;
    }
    public void setSntyColorCd(String sntyColorCd) {
        this.sntyColorCd = sntyColorCd;
    }
    /**
     * @return the maxDiscount3
     */
    public String getMaxDiscount3() {
        return maxDiscount3;
    }
    public int getMaxDiscount3Int() {
        int result = 0;
        try{
            result = Integer.parseInt(maxDiscount3);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    /**
     * @param maxDiscount3 the maxDiscount3 to set
     */
    public void setMaxDiscount3(String maxDiscount3) {
        this.maxDiscount3 = maxDiscount3;
    }
    /**
     * @return the modelPriceVat
     */
    public String getModelPriceVat() {
        return modelPriceVat;
    }

    public int getModelPriceVatInt() {
        int result = 0;
        try{
            result = Integer.parseInt(modelPriceVat);
        }catch (Exception e) {
            logger.debug("must be subclass of java.lang.Number ");
        }
        return result;
    }
    /**
     * @param modelPriceVat the modelPriceVat to set
     */
    public void setModelPriceVat(String modelPriceVat) {
        this.modelPriceVat = modelPriceVat;
    }
    /**
     * @return the enggMnthCnt
     */
    public String getEnggMnthCnt() {
        return enggMnthCnt;
    }
    /**
     * @param enggMnthCnt the enggMnthCnt to set
     */
    public void setEnggMnthCnt(String enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
    }
    public String getProdType() {
        return prodType;
    }
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    public String getModelSalePolicyCode() {
        return modelSalePolicyCode;
    }
    public void setModelSalePolicyCode(String modelSalePolicyCode) {
        this.modelSalePolicyCode = modelSalePolicyCode;
    }
    public String getCntpntShopId() {
        return cntpntShopId;
    }
    public void setCntpntShopId(String cntpntShopId) {
        this.cntpntShopId = cntpntShopId;
    }
    public String getSocCode() {
        return socCode;
    }
    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }

    public int getApiParam1() {
        return apiParam1;
    }

    public void setApiParam1(int apiParam1) {
        this.apiParam1 = apiParam1;
    }

    public int getApiParam2() {
        return apiParam2;
    }

    public void setApiParam2(int apiParam2) {
        this.apiParam2 = apiParam2;
    }

    public String getTmpStep() {
        return tmpStep;
    }
    public void setTmpStep(String tmpStep) {
        this.tmpStep = tmpStep;
    }

    public String getSelfProdNm() {
        return selfProdNm;
    }
    public void setSelfProdNm(String selfProdNm) {
        this.selfProdNm = selfProdNm;
    }
    public MspSaleSubsdMstDto getMspSaleSubsdMstDto() {
        return mspSaleSubsdMstDto;
    }
    public void setMspSaleSubsdMstDto(MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        this.mspSaleSubsdMstDto = mspSaleSubsdMstDto;
    }

    public int getHndsetSalePrice() {
        return hndsetSalePrice;
    }
    public void setHndsetSalePrice(int hndsetSalePrice) {
        this.hndsetSalePrice = hndsetSalePrice;
    }
    public int getUsePoint() {
        return usePoint;
    }
    public void setUsePoint(int usePoint) {
        this.usePoint = usePoint;
    }
    public int getCardDcAmt() {
        return cardDcAmt;
    }
    public void setCardDcAmt(int cardDcAmt) {
        this.cardDcAmt = cardDcAmt;
    }
    public String getUsimKindsCd() {
        return usimKindsCd;
    }
    public void setUsimKindsCd(String usimKindsCd) {
        this.usimKindsCd = usimKindsCd;
    }


}
