package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RateAdsvcCtgBasDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 요금제 부가서비스 코드
     */
    private String rateAdsvcCd;

    /**
     * 요금제부가서비스카테고리코드
     */
    private String rateAdsvcCtgCd;

    private String rateAdsvcItemImgNm;

    private String rateAdsvcBnfitItemCd;


    /**
     * 요금제부가서비스카테고리명
     */
    private String rateAdsvcCtgNm;
    /**
     * 요금제부가서비스카테고리기본설명
     */
    private String rateAdsvcCtgBasDesc;
    /**
     * 요금제부가서비스카테고리상세설명
     */
    private String rateAdsvcCtgDtlDesc;
    /**
     * 요금제부가서비스카테고리이미지명
     */
    private String rateAdsvcCtgImgNm;
    /**
     * DEPTH키
     */
    private int depthKey;
    /**
     * 상위요금제부가서비스카테고리코드
     */
    private String upRateAdsvcCtgCd;
    /**
     * 카테고리출력코드
     */
    private String ctgOutputCd;
    /**
     * 정렬순서
     */
    private String sortOdrg;
    /**
     * 안내파일명
     */
    private String rateAdsvcCtgFileNm;
    /**
     * 요금제부가서비스구분코드
     */
    private String rateAdsvcDivCd;
    /**
     * 사용유효여부
     */
    private String useYn;
    /**
     * 요금제부가서비스이미지명
     */
    private String rateAdsvcImgNm;
    /**
     * 게시시작일
     */
    private String pstngStartDate;
    /**
     * 게시종료일
     */
    private String pstngEndDate;
    /**
     * 요금제부가서비스안내일련번호
     */
    private int rateAdsvcGdncSeq;
    /**
     * 요금제명 혹은 부가서비스명
     */
    private String rateAdsvcNm;

    /**
     * 요금제부가서비스혜택항목:데이터
     */
    private String bnfitData;


    /**
     * 요금제부가서비스혜택항목:데이터 숫자
     */
    private int bnfitDataInt;

    /**
     * 요금제부가서비스혜택항목:음성
     */
    private String bnfitVoice;
    /**
     * 요금제부가서비스혜택항목:문자
     */
    private String bnfitSms;
    /**
     * 요금제부가서비스혜택항목:와이파이
     */
    private String bnfitWifi;
    /**
     * 요금제부가서비스혜택항목:데이터
     */
    private String promotionBnfitData;
    /**
     * 요금제부가서비스혜택항목:음성
     */
    private String promotionBnfitVoice;
    /**
     * 요금제부가서비스혜택항목:문자
     */
    private String promotionBnfitSms;
    /**
     * 요금제부가서비스혜택항목:문자
     */
    private String promotionBnfitWifi;

    /**
     * 혜택:음성 이미지명
     */
    private String bnfitVoiceItemImgNm;
    /**
     * 혜택:문자 이미지명
     */
    private String bnfitSmsItemImgNm;
    /**
     * 혜택:와이파이 이미지명
     */
    private String bnfitWifiItemImgNm;
    /**
     * 혜택:데이터 이미지명
     */
    private String bnfitDataItemImgNm;

    /**
     * 상품 갯수
     */
    private int relCnt;
    /**
     * 버튼 표시 유무
     */
    private String btnDisplayYn;

    /** 최대 데이터제공량(데이터(노출문구))
     */
    private String maxDataDelivery;

    //////// 접속 정보 설정 /////////////////////////////
    private String subTabId;
    /**
     * 등록아이피
     */
    private String cretIp;
    /**
     * 생성일시
     */
    private String cretDt;
    /**
     * 생성자아이디
     */
    private String cretId;
    /**
     * 변경아이피
     */
    private String amdIp;
    /**
     * 수정일시
     */
    private String amdDt;
    /**
     * 수정자아이디
     */
    private String amdId;

    //////// 요금제부가서비스 기본정보 설정 ////////////////////
    /**
     * 월기본금액설명
     */
    private String mmBasAmtDesc;
    /**
     * 월기본금액vat(포함)설명
     */
    private String mmBasAmtVatDesc;
    /**
     * 프로모션요금설명
     */
    private String promotionAmtDesc;
    /**
     * 프로모션요금vat(포함)설명
     */
    private String promotionAmtVatDesc;

    /**
     * 약정시 기본료
     */
    private String contractAmtVatDesc;
    /**
     * 요금할인 시 기본료
     */
    private String rateDiscntAmtVatDesc;
    /**
     * 시니어 할인 기본료
     */
    private String seniorDiscntAmtVatDesc;

    //////// param 정보 /////////////////////////////
    private String ctgCdParam1;
    private String ctgCdParam2;
    private String ctgCdParam3;

    //////// 부가서비스/로밍 관련 //////////////////////

    /**
     * 부가서비스구분코드
     */
    private String addDivCd;
    /**
     * 셀프케어 가입 가능 여부
     */
    private String selfYn;
    /**
     * 무료제공
     */
    private String freeYn;
    /**
     * 날짜 입력 유형
     */
    private String dateType;
    /**
     * 이용 가능 기간
     */
    private String usePrd;
    /**
     * 회선 유형
     */
    private String lineType;
    /**
     * 서브회선 수
     */
    private String lineCnt;
    /**
     * 대표상품 선택
     */
    private String mtCd;
    /**
     * selfYn에 따른 가입 신청 버튼 표시 여부
     */
    private String btnYn;


    /**
     * XML - DATA 제공량
     */
    private int xmlDataCnt;


    /**
     * XML - xmlQosCnt 제공량
     */
    private int xmlQosCnt;



    /** XML - CALL 제공량 */
    private int xmlCallCnt;

    /** 스티커 구분코드 */
    private String stickerCtg;

     /** 정렬순서  상위(요금제 변경)
     */
    private String sortOdrg2;

    /** 상품기본문구
    */
    private String rateAdsvcBasDesc;

    /** 요금제 혜택요약 정보 */
    private RateGiftPrmtListDTO rateGiftPrmtListDTO;

    //////// List 설정 //////////////////////////////
    List<RateAdsvcBnfitGdncDtlDTO> bnfitList = new ArrayList<RateAdsvcBnfitGdncDtlDTO>();
    List<RateAdsvcBnfitGdncDtlDTO> bnfitList2 = new ArrayList<RateAdsvcBnfitGdncDtlDTO>();
    List<RateAdsvcGdncLinkDtlDTO> gdncLinkDtlList = new ArrayList<RateAdsvcGdncLinkDtlDTO>();
    List<RateAdsvcGdncDtlDTO> gdncDtlBasList = new ArrayList<RateAdsvcGdncDtlDTO>();
    List<RateAdsvcGdncDtlDTO> gdncDtlList = new ArrayList<RateAdsvcGdncDtlDTO>();
    List<String> ctgCdList = new ArrayList<String>();
    List<String> ctgNmList = new ArrayList<String>();


    public String getRateAdsvcItemImgNm() {
        return rateAdsvcItemImgNm;
    }

    public void setRateAdsvcItemImgNm(String rateAdsvcItemImgNm) {
        this.rateAdsvcItemImgNm = rateAdsvcItemImgNm;
    }

    public String getRateAdsvcBnfitItemCd() {
        return rateAdsvcBnfitItemCd;
    }

    public void setRateAdsvcBnfitItemCd(String rateAdsvcBnfitItemCd) {
        this.rateAdsvcBnfitItemCd = rateAdsvcBnfitItemCd;
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

    public String getRateAdsvcCtgImgNm() {
        return rateAdsvcCtgImgNm;
    }

    public void setRateAdsvcCtgImgNm(String rateAdsvcCtgImgNm) {
        this.rateAdsvcCtgImgNm = rateAdsvcCtgImgNm;
    }

    public int getDepthKey() {
        return depthKey;
    }

    public void setDepthKey(int depthKey) {
        this.depthKey = depthKey;
    }

    public String getCtgOutputCd() {
        return ctgOutputCd;
    }

    public void setCtgOutputCd(String ctgOutputCd) {
        this.ctgOutputCd = ctgOutputCd;
    }

    public String getUpRateAdsvcCtgCd() {
        return upRateAdsvcCtgCd;
    }

    public void setUpRateAdsvcCtgCd(String upRateAdsvcCtgCd) {
        this.upRateAdsvcCtgCd = upRateAdsvcCtgCd;
    }

    public String getSortOdrg() {
        return sortOdrg;
    }

    public void setSortOdrg(String sortOdrg) {
        this.sortOdrg = sortOdrg;
    }

    public String getRateAdsvcCtgFileNm() {
        return rateAdsvcCtgFileNm;
    }

    public void setRateAdsvcCtgFileNm(String rateAdsvcCtgFileNm) {
        this.rateAdsvcCtgFileNm = rateAdsvcCtgFileNm;
    }

    public String getRateAdsvcDivCd() {
        return rateAdsvcDivCd;
    }

    public void setRateAdsvcDivCd(String rateAdsvcDivCd) {
        this.rateAdsvcDivCd = rateAdsvcDivCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRateAdsvcImgNm() {
        return rateAdsvcImgNm;
    }

    public void setRateAdsvcImgNm(String rateAdsvcImgNm) {
        this.rateAdsvcImgNm = rateAdsvcImgNm;
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

    public int getRateAdsvcGdncSeq() {
        return rateAdsvcGdncSeq;
    }

    public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
        this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
    }

    public String getRateAdsvcNm() {
        return rateAdsvcNm;
    }

    public void setRateAdsvcNm(String rateAdsvcNm) {
        this.rateAdsvcNm = rateAdsvcNm;
    }

    public String getBnfitData() {
        return bnfitData;
    }

    public void setBnfitData(String bnfitData) {
        this.bnfitData = bnfitData;
    }

    public String getBnfitVoice() {
        return bnfitVoice;
    }

    public void setBnfitVoice(String bnfitVoice) {
        this.bnfitVoice = bnfitVoice;
    }

    public String getBnfitSms() {
        return bnfitSms;
    }

    public void setBnfitSms(String bnfitSms) {
        this.bnfitSms = bnfitSms;
    }

    public String getBnfitWifi() {
        return bnfitWifi;
    }

    public void setBnfitWifi(String bnfitWifi) {
        this.bnfitWifi = bnfitWifi;
    }

    public String getPromotionBnfitData() {
        return promotionBnfitData;
    }

    public void setPromotionBnfitData(String promotionBnfitData) {
        this.promotionBnfitData = promotionBnfitData;
    }

    public String getPromotionBnfitVoice() {
        return promotionBnfitVoice;
    }

    public void setPromotionBnfitVoice(String promotionBnfitVoice) {
        this.promotionBnfitVoice = promotionBnfitVoice;
    }

    public String getPromotionBnfitSms() {
        return promotionBnfitSms;
    }

    public void setPromotionBnfitSms(String promotionBnfitSms) {
        this.promotionBnfitSms = promotionBnfitSms;
    }

    public String getPromotionBnfitWifi() {
        return promotionBnfitWifi;
    }

    public void setPromotionBnfitWifi(String promotionBnfitWifi) {
        this.promotionBnfitWifi = promotionBnfitWifi;
    }

    public String getBnfitVoiceItemImgNm() {
        return bnfitVoiceItemImgNm;
    }

    public void setBnfitVoiceItemImgNm(String bnfitVoiceItemImgNm) {
        this.bnfitVoiceItemImgNm = bnfitVoiceItemImgNm;
    }

    public String getBnfitSmsItemImgNm() {
        return bnfitSmsItemImgNm;
    }

    public void setBnfitSmsItemImgNm(String bnfitSmsItemImgNm) {
        this.bnfitSmsItemImgNm = bnfitSmsItemImgNm;
    }

    public String getBnfitWifiItemImgNm() {
        return bnfitWifiItemImgNm;
    }

    public void setBnfitWifiItemImgNm(String bnfitWifiItemImgNm) {
        this.bnfitWifiItemImgNm = bnfitWifiItemImgNm;
    }

    public String getBnfitDataItemImgNm() {
        return bnfitDataItemImgNm;
    }

    public void setBnfitDataItemImgNm(String bnfitDataItemImgNm) {
        this.bnfitDataItemImgNm = bnfitDataItemImgNm;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public String getCretDt() {
        return cretDt;
    }

    public void setCretDt(String cretDt) {
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

    public String getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public int getRelCnt() {
        return relCnt;
    }

    public void setRelCnt(int relCnt) {
        this.relCnt = relCnt;
    }

    public String getBtnDisplayYn() {
        return btnDisplayYn;
    }

    public void setBtnDisplayYn(String btnDisplayYn) {
        this.btnDisplayYn = btnDisplayYn;
    }

    public String getSubTabId() {
        return subTabId;
    }

    public void setSubTabId(String subTabId) {
        this.subTabId = subTabId;
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

    public String getContractAmtVatDesc() {
        return contractAmtVatDesc;
    }

    public void setContractAmtVatDesc(String contractAmtVatDesc) {
        this.contractAmtVatDesc = contractAmtVatDesc;
    }

    public String getRateDiscntAmtVatDesc() {
        return rateDiscntAmtVatDesc;
    }

    public void setRateDiscntAmtVatDesc(String rateDiscntAmtVatDesc) {
        this.rateDiscntAmtVatDesc = rateDiscntAmtVatDesc;
    }

    public String getSeniorDiscntAmtVatDesc() {
        return seniorDiscntAmtVatDesc;
    }

    public void setSeniorDiscntAmtVatDesc(String seniorDiscntAmtVatDesc) {
        this.seniorDiscntAmtVatDesc = seniorDiscntAmtVatDesc;
    }

    public String getCtgCdParam1() {
        return ctgCdParam1;
    }

    public void setCtgCdParam1(String ctgCdParam1) {
        this.ctgCdParam1 = ctgCdParam1;
    }

    public String getCtgCdParam2() {
        return ctgCdParam2;
    }

    public void setCtgCdParam2(String ctgCdParam2) {
        this.ctgCdParam2 = ctgCdParam2;
    }

    public String getCtgCdParam3() {
        return ctgCdParam3;
    }

    public void setCtgCdParam3(String ctgCdParam3) {
        this.ctgCdParam3 = ctgCdParam3;
    }

    /*
    public List<RateAdsvcBnfitGdncDtlDTO> getBnfitList() {
        return bnfitList;
    }
    public void setBnfitList(List<RateAdsvcBnfitGdncDtlDTO> bnfitList) {
        this.bnfitList = bnfitList;
    }
    */
    public List<RateAdsvcBnfitGdncDtlDTO> getBnfitList() {
        return bnfitList;
    }

    public void setBnfitList(List<RateAdsvcBnfitGdncDtlDTO> bnfitList) {
        this.bnfitList = bnfitList;
    }

    public List<RateAdsvcBnfitGdncDtlDTO> getBnfitList2() {
        return bnfitList2;
    }

    public void setBnfitList2(List<RateAdsvcBnfitGdncDtlDTO> bnfitList2) {
        this.bnfitList2 = bnfitList2;
    }

    public List<RateAdsvcGdncLinkDtlDTO> getGdncLinkDtlList() {
        return gdncLinkDtlList;
    }

    public void setGdncLinkDtlList(List<RateAdsvcGdncLinkDtlDTO> gdncLinkDtlList) {
        this.gdncLinkDtlList = gdncLinkDtlList;
    }

    public List<RateAdsvcGdncDtlDTO> getGdncDtlBasList() {
        return gdncDtlBasList;
    }

    public void setGdncDtlBasList(List<RateAdsvcGdncDtlDTO> gdncDtlBasList) {
        this.gdncDtlBasList = gdncDtlBasList;
    }

    public List<RateAdsvcGdncDtlDTO> getGdncDtlList() {
        return gdncDtlList;
    }

    public void setGdncDtlList(List<RateAdsvcGdncDtlDTO> gdncDtlList) {
        this.gdncDtlList = gdncDtlList;
    }

    public String getRateAdsvcCd() {
        return rateAdsvcCd;
    }

    public void setRateAdsvcCd(String rateAdsvcCd) {
        this.rateAdsvcCd = rateAdsvcCd;
    }

    public List<String> getCtgCdList() {
        return ctgCdList;
    }

    public void setCtgCdList(List<String> ctgCdList) {
        this.ctgCdList = ctgCdList;
    }

    public List<String> getCtgNmList() {
        return ctgNmList;
    }

    public void setCtgNmList(List<String> ctgNmList) {
        this.ctgNmList = ctgNmList;
    }

    public String getAddDivCd() {
        return addDivCd;
    }

    public void setAddDivCd(String addDivCd) {
        this.addDivCd = addDivCd;
    }

    public String getSelfYn() {
        return selfYn;
    }

    public void setSelfYn(String selfYn) {
        this.selfYn = selfYn;
    }

    public String getFreeYn() {
        return freeYn;
    }

    public void setFreeYn(String freeYn) {
        this.freeYn = freeYn;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getUsePrd() {
        return usePrd;
    }

    public void setUsePrd(String usePrd) {
        this.usePrd = usePrd;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getLineCnt() {
        return lineCnt;
    }

    public void setLineCnt(String lineCnt) {
        this.lineCnt = lineCnt;
    }

    public String getMtCd() {
        return mtCd;
    }

    public void setMtCd(String mtCd) {
        this.mtCd = mtCd;
    }

    public String getBtnYn() {
        return btnYn;
    }

    public void setBtnYn(String btnYn) {
        this.btnYn = btnYn;
    }

    public int getBnfitDataInt() {
        return bnfitDataInt;
    }

    public void setBnfitDataInt(int bnfitDataInt) {
        this.bnfitDataInt = bnfitDataInt;
    }

    public int getXmlDataCnt() {
        return xmlDataCnt;
    }

    public void setXmlDataCnt(int xmlDataCnt) {
        this.xmlDataCnt = xmlDataCnt;
    }

    public int getXmlCallCnt() {
        return xmlCallCnt;
    }

    public void setXmlCallCnt(int xmlCallCnt) {
        this.xmlCallCnt = xmlCallCnt;
    }

    public String getStickerCtg() {
        return stickerCtg;
    }

    public void setStickerCtg(String stickerCtg) {
        this.stickerCtg = stickerCtg;
    }

    public int getXmlQosCnt() {
        return xmlQosCnt;
    }

    public void setXmlQosCnt(int xmlQosCnt) {
        this.xmlQosCnt = xmlQosCnt;
    }

    public String getSortOdrg2() {
        return sortOdrg2;
    }

    public void setSortOdrg2(String sortOdrg2) {
        this.sortOdrg2 = sortOdrg2;
    }

    public String getRateAdsvcBasDesc() {
        return rateAdsvcBasDesc;
    }

    public void setRateAdsvcBasDesc(String rateAdsvcBasDesc) {
        this.rateAdsvcBasDesc = rateAdsvcBasDesc;
    }

    public String getMaxDataDelivery() {
        return maxDataDelivery;
    }

    public void setMaxDataDelivery(String maxDataDelivery) {
        this.maxDataDelivery = maxDataDelivery;
    }

    public RateGiftPrmtListDTO getRateGiftPrmtListDTO() {
        return rateGiftPrmtListDTO;
    }

    public void setRateGiftPrmtListDTO(RateGiftPrmtListDTO rateGiftPrmtListDTO) {
        this.rateGiftPrmtListDTO = rateGiftPrmtListDTO;
    }

}


