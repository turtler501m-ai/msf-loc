package com.ktmmobile.msf.form.servicechange.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RateAdsvcGdncProdXML {

    /** 요금제부가서비스카테고리코드 */
    private String rateAdsvcCtgCd;
    /** 요금제부가서비스안내일련번호 */
    private int rateAdsvcGdncSeq;
    /** 정렬순서 */
    private String sortOdrg;
    /** 사용유효여부 */
    private String useYn;
    /** 요금제부가서비스이미지명 */
    private String rateAdsvcImgNm;
    private String rateAdsvcItemImgNm;
    private String rateAdsvcBnfitItemCd;
    /** 게시시작일 */
    private String pstngStartDate;
    /** 게시종료일 */
    private String pstngEndDate;
    /** 요금제부가서비스카테고리명 */
    private String rateAdsvcCtgNm;
    /** 요금제부가서비스카테고리기본설명 */
    private String rateAdsvcCtgBasDesc;
    /** 요금제부가서비스카테고리상세설명 */
    private String rateAdsvcCtgDtlDesc;
    /** DEPTH키 */
    private int depthKey;
    /** 카테고리출력코드 */
    private String ctgOutputCd;
    /** 상위요금제부가서비스카테고리코드 */
    private String upRateAdsvcCtgCd;
    /** 요금제부가서비스명 */
    private String rateAdsvcNm;
    /** 요금제부가서비스구분코드 */
    private String rateAdsvcDivCd;
    /** 요금제부가서비스카테고리이미지명 */
    private String rateAdsvcCtgImgNm;
    /** 월기본금액설명 */
    private String mmBasAmtDesc;
    /** 월기본금액VAT(포함)설명 */
    private String mmBasAmtVatDesc;
    /** 프로모션요금설명 */
    private String promotionAmtDesc;
    /** 프로모션요금VAT(포함)설명 */
    private String promotionAmtVatDesc;
    /** 상품목록 갯수 */
    private int relCnt;
    /** 부가서비스구분코드 */
    private String addDivCd;
    /** 셀프케어 가입 가능 여부 */
    private String selfYn;
    /** 무료제공 */
    private String freeYn;
    /** 날짜 입력 유형 */
    private String dateType;
    /** 이용 가능 기간 */
    private String usePrd;
    /** 회선 유형 */
    private String lineType;
    /** 서브회선 수 */
    private String lineCnt;
    /** 대표상품 선택 */
    private String mtCd;
    /** 상품기본문구 */
    private String rateAdsvcBasDesc;
    /** 요금제 상세정보 리스트 */
    private List<RateDtlInfo> rateDtlList;

    public String getRateAdsvcCtgCd() { return rateAdsvcCtgCd; }
    public void setRateAdsvcCtgCd(String rateAdsvcCtgCd) { this.rateAdsvcCtgCd = rateAdsvcCtgCd; }
    public int getRateAdsvcGdncSeq() { return rateAdsvcGdncSeq; }
    public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) { this.rateAdsvcGdncSeq = rateAdsvcGdncSeq; }
    public String getSortOdrg() { return sortOdrg; }
    public void setSortOdrg(String sortOdrg) { this.sortOdrg = sortOdrg; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public String getRateAdsvcImgNm() { return rateAdsvcImgNm; }
    public void setRateAdsvcImgNm(String rateAdsvcImgNm) { this.rateAdsvcImgNm = rateAdsvcImgNm; }
    public String getRateAdsvcItemImgNm() { return rateAdsvcItemImgNm; }
    public void setRateAdsvcItemImgNm(String rateAdsvcItemImgNm) { this.rateAdsvcItemImgNm = rateAdsvcItemImgNm; }
    public String getRateAdsvcBnfitItemCd() { return rateAdsvcBnfitItemCd; }
    public void setRateAdsvcBnfitItemCd(String rateAdsvcBnfitItemCd) { this.rateAdsvcBnfitItemCd = rateAdsvcBnfitItemCd; }
    public String getPstngStartDate() { return pstngStartDate; }
    public void setPstngStartDate(String pstngStartDate) { this.pstngStartDate = pstngStartDate; }
    public String getPstngEndDate() { return pstngEndDate; }
    public void setPstngEndDate(String pstngEndDate) { this.pstngEndDate = pstngEndDate; }
    public String getRateAdsvcCtgNm() { return rateAdsvcCtgNm; }
    public void setRateAdsvcCtgNm(String rateAdsvcCtgNm) { this.rateAdsvcCtgNm = rateAdsvcCtgNm; }
    public String getRateAdsvcCtgBasDesc() { return rateAdsvcCtgBasDesc; }
    public void setRateAdsvcCtgBasDesc(String rateAdsvcCtgBasDesc) { this.rateAdsvcCtgBasDesc = rateAdsvcCtgBasDesc; }
    public String getRateAdsvcCtgDtlDesc() { return rateAdsvcCtgDtlDesc; }
    public void setRateAdsvcCtgDtlDesc(String rateAdsvcCtgDtlDesc) { this.rateAdsvcCtgDtlDesc = rateAdsvcCtgDtlDesc; }
    public int getDepthKey() { return depthKey; }
    public void setDepthKey(int depthKey) { this.depthKey = depthKey; }
    public String getCtgOutputCd() { return ctgOutputCd; }
    public void setCtgOutputCd(String ctgOutputCd) { this.ctgOutputCd = ctgOutputCd; }
    public String getUpRateAdsvcCtgCd() { return upRateAdsvcCtgCd; }
    public void setUpRateAdsvcCtgCd(String upRateAdsvcCtgCd) { this.upRateAdsvcCtgCd = upRateAdsvcCtgCd; }
    public String getRateAdsvcNm() { return rateAdsvcNm; }
    public void setRateAdsvcNm(String rateAdsvcNm) { this.rateAdsvcNm = rateAdsvcNm; }
    public String getRateAdsvcDivCd() { return rateAdsvcDivCd; }
    public void setRateAdsvcDivCd(String rateAdsvcDivCd) { this.rateAdsvcDivCd = rateAdsvcDivCd; }
    public String getRateAdsvcCtgImgNm() { return rateAdsvcCtgImgNm; }
    public void setRateAdsvcCtgImgNm(String rateAdsvcCtgImgNm) { this.rateAdsvcCtgImgNm = rateAdsvcCtgImgNm; }
    public String getMmBasAmtDesc() { return mmBasAmtDesc; }
    public void setMmBasAmtDesc(String mmBasAmtDesc) { this.mmBasAmtDesc = mmBasAmtDesc; }
    public String getMmBasAmtVatDesc() { return mmBasAmtVatDesc; }
    public void setMmBasAmtVatDesc(String mmBasAmtVatDesc) { this.mmBasAmtVatDesc = mmBasAmtVatDesc; }
    public String getPromotionAmtDesc() { return promotionAmtDesc; }
    public void setPromotionAmtDesc(String promotionAmtDesc) { this.promotionAmtDesc = promotionAmtDesc; }
    public String getPromotionAmtVatDesc() { return promotionAmtVatDesc; }
    public void setPromotionAmtVatDesc(String promotionAmtVatDesc) { this.promotionAmtVatDesc = promotionAmtVatDesc; }
    public int getRelCnt() { return relCnt; }
    public void setRelCnt(int relCnt) { this.relCnt = relCnt; }
    public String getAddDivCd() { return addDivCd; }
    public void setAddDivCd(String addDivCd) { this.addDivCd = addDivCd; }
    public String getSelfYn() { return selfYn; }
    public void setSelfYn(String selfYn) { this.selfYn = selfYn; }
    public String getFreeYn() { return freeYn; }
    public void setFreeYn(String freeYn) { this.freeYn = freeYn; }
    public String getDateType() { return dateType; }
    public void setDateType(String dateType) { this.dateType = dateType; }
    public String getUsePrd() { return usePrd; }
    public void setUsePrd(String usePrd) { this.usePrd = usePrd; }
    public String getLineType() { return lineType; }
    public void setLineType(String lineType) { this.lineType = lineType; }
    public String getLineCnt() { return lineCnt; }
    public void setLineCnt(String lineCnt) { this.lineCnt = lineCnt; }
    public String getMtCd() { return mtCd; }
    public void setMtCd(String mtCd) { this.mtCd = mtCd; }
    public String getRateAdsvcBasDesc() { return rateAdsvcBasDesc; }
    public void setRateAdsvcBasDesc(String rateAdsvcBasDesc) { this.rateAdsvcBasDesc = rateAdsvcBasDesc; }
    public List<RateDtlInfo> getRateDtlList() { return rateDtlList; }
    public void setRateDtlList(List<RateDtlInfo> rateDtlList) { this.rateDtlList = rateDtlList; }
}
