package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
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

}
