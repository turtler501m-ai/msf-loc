package com.ktmmobile.mcp.rate.service;

import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.coupon.dto.McpUserCertDto;
import com.ktmmobile.mcp.rate.dto.*;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;

import java.util.List;

public interface RateAdsvcGdncService {

    /**
     * 설명 : 카테고리 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @return
     */
    public MapWrapper getMapWrapper();

    /**
     * 설명 : 2Depth 의 상품목록 갯수 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public int getProdCount(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 카테고리 xml 의 ctgList 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RateAdsvcCtgBasDTO> getCtgXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);
    public List<RateAdsvcCtgBasDTO> getCtgXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, MapWrapper mapWrapper);


    /**
     * 설명 : 카테고리 xml 의 ctgList 조회
     * @Author : papier
     * @Date : 2023.07.11
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RateAdsvcGdncProdXML> getCtgXmlAllList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);
    public List<RateAdsvcGdncProdXML> getCtgXmlAllList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, MapWrapper mapWrapper);

    /**
     * 설명 : 카테고리 xml 의 proList 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RateAdsvcCtgBasDTO> getProdXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);
    public List<RateAdsvcCtgBasDTO> getProdXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, MapWrapper mapWrapper);




    /**
     * 설명 : prodList 중복 제거
     * @Author : 김동혁
     * @Date : 2023.06.01
     * @param List<rateAdsvcCtgBasDTO>
     * @return List<rateAdsvcCtgBasDTO>
     */
    public List<RateAdsvcCtgBasDTO> deduplicateProdList(List<RateAdsvcCtgBasDTO> dupProdList);

    /**
     * 설명 : 상품 정보 가져오기
     * @Author : 김동혁
     * @Date : 2023.06.12
     * @param RateAdsvcCtgBasDTO
     * @return RateAdsvcCtgBasDTO
     */
    public RateAdsvcCtgBasDTO getProdBySeq(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 상품 관계 정보 가져오기
     * @Author : 김동혁
     * @Date : 2023.06.13
     * @param RateAdsvcCtgBasDTO
     * @return RateAdsvcGdncProdRelXML
     */
    public RateAdsvcGdncProdRelXML getRateAdsvcGdncProdRel(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 회선으로 계약번호 조회
     * @Author : 김동혁
     * @Date : 2023.06.13
     * @param McpUserCertDto
     * @return McpUserCertDto
     */
    public List<McpUserCertDto> getMcpUserCntrInfoA(McpUserCertDto mcpUserCertDto);

    /**
     * 설명 : 요금제부가서비스안내기본 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public RateAdsvcGdncBasXML getRateAdsvcGdncBasXml(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 요금제부가서비스안내상세 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RateAdsvcGdncDtlXML> getRateAdsvcGdncDtlXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 요금제부가서비스혜택안내상세 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RateAdsvcBnfitGdncDtlXML> getRateAdsvcBnfitGdncDtlXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 요금제부가서비스혜택안내상세 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RateAdsvcGdncLinkDtlXML> getRateAdsvcGdncLinkDtlXmlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 요금제부가서비스안내상품관계 xml 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @return
     */
    public List<RateAdsvcGdncProdRelXML> getRateAdsvcGdncProdRelXml();

    /**
     * 설명 : 사용후기 목록 총갯수 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public int getRequestreviewTotalCnt(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 부가서비스 코드로 부가서비스 상세 xml 조회
     * @Author : papier
     * @Date : 2023.04.18
     * @param rateAdsvcCd
     * @return
     */
    public List<RateAdsvcGdncDtlXML> getRateAdsvcGdncDtlXml(String rateAdsvcCd);


    /**
     * 설명 : 요금제 코드로  상세  조회
     * @Author : papier
     * @Date : 2025.01.10
     * @param rateAdsvcCd
     * @return
     */
    public RateDtlInfo getRateDtlInfo(String rateAdsvcCd);

    /** 설명 : 요금제 코드로 상세 조회 (특정 날짜 기준) */
    RateDtlInfo getRateDtlInfoWithDate(String rateAdsvcCd, String baseDate);

    /**
     * 설명 : 사용후기 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RequestReviewDto> getRequestreviewList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 약정할인 프로그램 및 할인반환금 안내 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @return
     */
    public RateAgreeDTO getRateAgreeView();

    /**
     * 설명 : 요금제부가서비스혜택안내 목록 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */
    public List<RateAdsvcBnfitGdncDtlDTO> getRateAdsvcBnfitGdncDtlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);

    /**
     * 설명 : 코드 상세 조회
     * @Author : 강채신
     * @Date : 2021.12.30
     * @param grpCd
     * @param dtlCd
     * @return
     */
    public NmcpCdDtlDto getDtlCode(String grpCd, String dtlCd);

    /**
     * 설명 : 상품코드로 시퀀스값 조회
     * @Author : 김동혁
     * @Date : 2023.06.22
     * @param rateAdsvcCd
     * @return
     */
    public RateAdsvcGdncProdRelXML getRateAdsvcProdRelBySoc(String soc);

    /**
     * 설명 : 로밍 가입신청 validation check
     * @Author : 김동혁
     * @Date : 2023.06.23
     * @param rateAdsvcCtgBasDTO
     * @param soc
     * @param strtDt
     * @param strtTm
     * @param endDt
     * @param addPhone
     * @return JsonReturnDto
     */
    public JsonReturnDto regRoamValidationChk(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, String soc, String strtDt, String strtTm, String endDt, String[] addPhone);

    /**
     * 설명 : 로밍상품(서브) 대표상품 prodHstSeq 가져오기
     * @Author : 김동혁
     * @Date : 2023.07.11
     * @param soc
     * @param addPhone
     * @return String
     */
    public String getMtProdHstSeq(String mtCd, String strtDt, String endDt, String addPhone, String ncn);

    /**
     * 설명 : 로밍 가입에 필요한 param 생성
     * @Author : 김동혁
     * @Date : 2023.06.23
     * @param rateAdsvcCtgBasDTO
     * @param strtDt
     * @param strtTm
     * @param endDt
     * @param addPhone
     * @return
     */
    public String getRoamParam(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, String soc, String strtDt, String strtTm, String endDt, String[] addPhone, String mtProdHstSeq);

    /**
     * 설명 : 전체 요금제 추출
     * @Author : papier
     * @Date : 2025.01.23
     * @return
     * @param mapWrapper
     */
    public List<RateAdsvcGdncProdXML> getCtgRateAllList(MapWrapper mapWrapper);

    /**
     * 설명 : 상세요금제정보 패치
     * @Author : papier
     * @Date : 2025.01.23
     * @return
     */
    public List<RateDtlInfo> getRateDtlInfoList(String rateAdsvcCtgCd, MapWrapper mapWrapper, List<RateGiftPrmtXML> rateGiftPrmtXmlList, List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelXml);


    /**
     * 설명 : 상세요금제정보 패치
     * @Author : papier
     * @Date : 2025.11.15
     * @return
     */
    public List<RateDtlInfo> getRateDtlInfoList(String rateAdsvcCtgCd, MapWrapper mapWrapper, List<RateGiftPrmtXML> giftPrmtXmlList, List<RateAdsvcGdncProdRelXML> prodRelXmlList , Boolean isDetailHtml) ;

    /**
     * 설명 : 요금제 혜택요약 xml 조회
     * @Author : hsy
     */
    List<RateGiftPrmtXML> getRateGiftPrmtXmlList();

    /**
     * 설명 : 요금제 혜택요약 정보 세팅
     * @Author : hsy
     */
    RateGiftPrmtListDTO initRateGiftPrmtInfo(List<RateGiftPrmtXML> xmlList, String giftPrmtSeqs, String baseDate);

    RateGiftPrmtListDTO initRateGiftPrmtInfo(List<RateGiftPrmtXML> xmlList, String giftPrmtSeqs, String baseDate, String pageUri);

    public void initEffPriceInfo(RateDtlInfo rateDtlInfo, List<NmcpCdDtlDto> jehuPriceReflectList, List<NmcpCdDtlDto> promoPriceReflectList, int totalPrice);

    List<RateAdsvcCtgBasDTO> getRateAmountList();
}
