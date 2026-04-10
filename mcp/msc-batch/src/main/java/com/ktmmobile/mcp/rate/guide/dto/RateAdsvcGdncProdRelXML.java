package com.ktmmobile.mcp.rate.guide.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcProdRelSeq", "rateAdsvcGdncSeq", "rateAdsvcCd", "rateAdsvcNm", "useYn", "pstngStartDate", "pstngEndDate",
        "giftPrmtSeqs", "evntCd"})
public class RateAdsvcGdncProdRelXML {
    /** 요금제부가서비스상품관계일련번호 */
    private int rateAdsvcProdRelSeq;
    /** 안내일련번호 */
    private int rateAdsvcGdncSeq;
    /** 요금제부가서비스코드 */
    private String rateAdsvcCd;
    /** 요금제부가서비스명 */
    private String rateAdsvcNm;
    /** 사용유효여부 */
    private String useYn;
    /** 게시시작일 */
    private String pstngStartDate;
    /** 게시종료일 */
    private String pstngEndDate;
    /** 요금제 혜택요약 일련번호 (구분값 |) */
    private String giftPrmtSeqs;
    /** 이벤트코드 프로모션 이벤트코드 */
    private String evntCd;

    public int getRateAdsvcProdRelSeq() {
        return rateAdsvcProdRelSeq;
    }

    @XmlElement
    public void setRateAdsvcProdRelSeq(int rateAdsvcProdRelSeq) {
        this.rateAdsvcProdRelSeq = rateAdsvcProdRelSeq;
    }
    public int getRateAdsvcGdncSeq() {
        return rateAdsvcGdncSeq;
    }

    @XmlElement
    public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
        this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
    }
    public String getRateAdsvcCd() {
        return rateAdsvcCd;
    }

    @XmlElement
    public void setRateAdsvcCd(String rateAdsvcCd) {
        this.rateAdsvcCd = rateAdsvcCd;
    }
    public String getRateAdsvcNm() {
        return rateAdsvcNm;
    }

    @XmlElement
    public void setRateAdsvcNm(String rateAdsvcNm) {
        this.rateAdsvcNm = rateAdsvcNm;
    }
    public String getUseYn() {
        return useYn;
    }

    @XmlElement
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getPstngStartDate() {
        return pstngStartDate;
    }

    @XmlElement
    public void setPstngStartDate(String pstngStartDate) {
        this.pstngStartDate = pstngStartDate;
    }
    public String getPstngEndDate() {
        return pstngEndDate;
    }

    @XmlElement
    public void setPstngEndDate(String pstngEndDate) {
        this.pstngEndDate = pstngEndDate;
    }

    public String getGiftPrmtSeqs() {
        return giftPrmtSeqs;
    }

    @XmlElement
    public void setGiftPrmtSeqs(String giftPrmtSeqs) {
        this.giftPrmtSeqs = giftPrmtSeqs;
    }

    public String getEvntCd() {
        return evntCd;
    }

    @XmlElement
    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }
}
