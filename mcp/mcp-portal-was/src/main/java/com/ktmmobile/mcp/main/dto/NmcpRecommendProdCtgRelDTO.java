package com.ktmmobile.mcp.main.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;

public class NmcpRecommendProdCtgRelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 추천상품카테고리코드 */
    private String recommendProdCtgCd;
    /** 추천상품안내일련번호 */
    private int recommendProdGdncSeq;
    /** 상품코드 */
    private String prodId;
    /** 제품코드 */
    private String prdtId;
    /** 상품명 */
    private String prodNm;
    /** 판매정책코드 */
    private String salePlcyCd;
    /** 요금제코드 */
    private String rateCd;
    /** 요금제명 */
    private String rateNm;
    /** 추천상품기본문구 */
    private String recommendProdBasDesc;
    /** 추천상품상세문구 */
    private String recommendProdDtlDesc;
    /** 추천여부 */
    private String recommendYn;
    /** 신규여부 */
    private String newYn;
    /** 추천상품가격 */
    private int salePrice;
    /** 링크유형 */
    private String linkCd;
    /** 링크URL */
    private String linkUrl;
    /** 정렬우선순위 */
    private String sortOdrg;
    /** 사용유효여부 */
    private String useYn;
    /** 게시시작일 */
    private String pstngStartDate;
    /** 게시종료일 */
    private String pstngEndDate;

    /** 라벨 코드 */
    private String labelCode ;

    private NmcpRecommendProdDTO nmcpProdComendDTO;
    private PhoneProdBasDto phoneProdBasDto;
    private MspRateMstDto mspRateMstDTO;
    private MspSaleSubsdMstDto mspSaleSubsdMstDto;

    ///////////// List 정보 //////////////////////////
    //List<RateAdsvcBnfitGdncDtlDTO> bnfitList = new ArrayList<RateAdsvcBnfitGdncDtlDTO>();

    ///////////// 요금제 정보 //////////////////////////
    /** 요금제명 혹은 부가서비스명 */
    private String rateAdsvcNm;
    /** 요금제부가서비스기본설명 */
    private String rateAdsvcBasDesc;
    /** 월기본금액설명 */
    private String mmBasAmtDesc;
    /** 월기본금액vat(포함)설명 */
    private String mmBasAmtVatDesc;
    /** 프로모션요금설명 */
    private String promotionAmtDesc;
    /** 프로모션요금vat(포함)설명 */
    private String promotionAmtVatDesc;

    /** 요금제부가서비스혜택항목:데이터 */
    private String bnfitData;
    /** 요금제부가서비스혜택항목:음성 */
    private String bnfitVoice;
    /** 요금제부가서비스혜택항목:문자 */
    private String bnfitSms;
    /** 요금제부가서비스혜택항목:문자 */
    private String bnfitWifi;

    /** 요금제부가서비스혜택항목:데이터 */
    private String promotionBnfitData;
    /** 요금제부가서비스혜택항목:음성 */
    private String promotionBnfitVoice;
    /** 요금제부가서비스혜택항목:문자 */
    private String promotionBnfitSms;
    /** 요금제부가서비스혜택항목:문자 */
    private String promotionBnfitWifi;

    /**  최대 데이터제공량(데이터(노출문구)) */
    private String maxDataDelivery;

    ///////////// 공통 정보 ///////////////////////////
    /** 등록IP */
    private Date cretIp;
    /** 등록일시 */
    private Date cretDt;
    /** 등록자 아이디 */
    private String cretId;
    /** 수정IP */
    private Date amdIp;
    /** 수정일시 */
    private Date amdDt;
    /** 수정자 아이디 */
    private String amdId;
    /** I:쓰기, U:수정 */
    private String modIu;

    public String getRecommendProdCtgCd() {
        return recommendProdCtgCd;
    }
    public void setRecommendProdCtgCd(String recommendProdCtgCd) {
        this.recommendProdCtgCd = recommendProdCtgCd;
    }

    public int getRecommendProdGdncSeq() {
        return recommendProdGdncSeq;
    }
    public void setRecommendProdGdncSeq(int recommendProdGdncSeq) {
        this.recommendProdGdncSeq = recommendProdGdncSeq;
    }

    public String getProdId() {
        return prodId;
    }
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getPrdtId() {
        return prdtId;
    }
    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }

    public String getProdNm() {
        return prodNm;
    }
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getSalePlcyCd() {
        return salePlcyCd;
    }
    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }

    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getRecommendProdBasDesc() {
        return recommendProdBasDesc;
    }

    public void setRecommendProdBasDesc(String recommendProdBasDesc) {
        this.recommendProdBasDesc = recommendProdBasDesc;
    }

    public String getRecommendProdDtlDesc() {
        return recommendProdDtlDesc;
    }
    public void setRecommendProdDtlDesc(String recommendProdDtlDesc) {
        this.recommendProdDtlDesc = recommendProdDtlDesc;
    }


    public int getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getRecommendYn() {
        return recommendYn;
    }
    public void setRecommendYn(String recommendYn) {
        this.recommendYn = recommendYn;
    }

    public String getNewYn() {
        return newYn;
    }
    public void setNewYn(String newYn) {
        this.newYn = newYn;
    }

    public String getLinkCd() {
        return linkCd;
    }
    public void setLinkCd(String linkCd) {
        this.linkCd = linkCd;
    }

    public String getLinkUrl() {
        return linkUrl;
    }
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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

    public String getRateAdsvcNm() {
        return rateAdsvcNm;
    }
    public void setRateAdsvcNm(String rateAdsvcNm) {
        this.rateAdsvcNm = rateAdsvcNm;
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

    public Date getCretIp() {
        return cretIp;
    }
    public void setCretIp(Date cretIp) {
        this.cretIp = cretIp;
    }

    public Date getCretDt() {
        return cretDt;
    }
    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public Date getAmdIp() {
        return amdIp;
    }
    public void setAmdIp(Date amdIp) {
        this.amdIp = amdIp;
    }

    public Date getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public String getModIu() {
        return modIu;
    }
    public void setModIu(String modIu) {
        this.modIu = modIu;
    }
    public NmcpRecommendProdDTO getNmcpProdComendDTO() {
        return nmcpProdComendDTO;
    }
    public void setNmcpProdComendDTO(NmcpRecommendProdDTO nmcpProdComendDTO) {
        this.nmcpProdComendDTO = nmcpProdComendDTO;
    }

    public PhoneProdBasDto getPhoneProdBasDto() {
        return phoneProdBasDto;
    }
    public void setPhoneProdBasDto(PhoneProdBasDto phoneProdBasDto) {
        this.phoneProdBasDto = phoneProdBasDto;
    }

    public MspRateMstDto getMspRateMstDTO() {
        return mspRateMstDTO;
    }
    public void setMspRateMstDTO(MspRateMstDto mspRateMstDTO) {
        this.mspRateMstDTO = mspRateMstDTO;
    }

    public MspSaleSubsdMstDto getMspSaleSubsdMstDto() {
        return mspSaleSubsdMstDto;
    }
    public void setMspSaleSubsdMstDto(MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        this.mspSaleSubsdMstDto = mspSaleSubsdMstDto;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }
    public String getMaxDataDelivery() {
        return maxDataDelivery;
    }
    public void setMaxDataDelivery(String maxDataDelivery) {
        this.maxDataDelivery = maxDataDelivery;
    }
}
