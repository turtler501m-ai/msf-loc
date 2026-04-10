package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RateDtlInfo implements Serializable {

    private static final long serialVersionUID = -6412942200249049815L;

    //RateAdsvcGdncBasXML

    /**
     * 요금제 부가서비스 코드
     */
    private String rateAdsvcCd;

    /**
     * 요금제명 혹은 부가서비스명
     */
    private String rateAdsvcNm;


    private RateAdsvcGdncBasXML rateAdsvcGdncBas;  // 요금제 부가 서비스 기본 안내

    private Map<String, RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncMap ; //요금제부가서비스혜택안내상세리스트

    private List<RateGdncBannerDtlDTO> rateGdncBannerList ;//요금제부가서비스 배너 상세리스트

    private List<RateAdsvcGdncDtlXML> rateAdsvcGdncDtlList ; //요금제부가서비스안내상세


    private Map<String, RateGdncPropertyDtlDTO> rateGdncPropertyMap ;  //요금제부가서비스 속성

    private RateGiftPrmtListDTO rateGiftPrmtListDTO;  // 요금제 혜택요약 정보

    private RateGdncEffPriceDtlDTO rateGdncEffPriceDtl; // 요금제 체감가 정보

    public RateAdsvcGdncBasXML getRateAdsvcGdncBas() {
        return rateAdsvcGdncBas;
    }

    public void setRateAdsvcGdncBas(RateAdsvcGdncBasXML rateAdsvcGdncBas) {
        this.rateAdsvcGdncBas = rateAdsvcGdncBas;
    }

    public Map<String, RateAdsvcBnfitGdncDtlXML> getRateAdsvcBnfitGdncMap() {
        return rateAdsvcBnfitGdncMap;
    }

    public void setRateAdsvcBnfitGdncMap(Map<String, RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncMap) {
        this.rateAdsvcBnfitGdncMap = rateAdsvcBnfitGdncMap;
    }

    public List<RateGdncBannerDtlDTO> getRateGdncBannerList() {
        return rateGdncBannerList;
    }

    public void setRateGdncBannerList(List<RateGdncBannerDtlDTO> rateGdncBannerList) {
        this.rateGdncBannerList = rateGdncBannerList;
    }

    public Map<String, RateGdncPropertyDtlDTO> getRateGdncPropertyMap() {
        return rateGdncPropertyMap;
    }

    public void setRateGdncPropertyMap(Map<String, RateGdncPropertyDtlDTO> rateGdncPropertyMap) {
        this.rateGdncPropertyMap = rateGdncPropertyMap;
    }

    public String getRateAdsvcCd() {
        return rateAdsvcCd;
    }

    public void setRateAdsvcCd(String rateAdsvcCd) {
        this.rateAdsvcCd = rateAdsvcCd;
    }

    public String getRateAdsvcNm() {
        return rateAdsvcNm;
    }

    public void setRateAdsvcNm(String rateAdsvcNm) {
        this.rateAdsvcNm = rateAdsvcNm;
    }

    public RateGiftPrmtListDTO getRateGiftPrmtListDTO() {
        return rateGiftPrmtListDTO;
    }

    public void setRateGiftPrmtListDTO(RateGiftPrmtListDTO rateGiftPrmtListDTO) {
        this.rateGiftPrmtListDTO = rateGiftPrmtListDTO;
    }

    public RateGdncEffPriceDtlDTO getRateGdncEffPriceDtl() {
        return rateGdncEffPriceDtl;
    }

    public void setRateGdncEffPriceDtl(RateGdncEffPriceDtlDTO rateGdncEffPriceDtl) {
        this.rateGdncEffPriceDtl = rateGdncEffPriceDtl;
    }

    public List<RateAdsvcGdncDtlXML> getRateAdsvcGdncDtlList() {
        return rateAdsvcGdncDtlList;
    }

    public void setRateAdsvcGdncDtlList(List<RateAdsvcGdncDtlXML> rateAdsvcGdncDtlList) {
        this.rateAdsvcGdncDtlList = rateAdsvcGdncDtlList;
    }
}
