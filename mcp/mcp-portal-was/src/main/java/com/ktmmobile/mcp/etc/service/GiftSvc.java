package com.ktmmobile.mcp.etc.service;

import java.util.List;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.etc.dto.ConsentGiftDto;
import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import com.ktmmobile.mcp.etc.dto.GiftPromotionDto;
import com.ktmmobile.mcp.rate.dto.RateGiftPrmtListDTO;

public interface GiftSvc {

    /**
     * @Description : 프로모션 계층으로 조회 처리
     * @param :
     * @return :
     * @Author : power
     * @Create Date : 2022. 02. 10
     */
    public List<GiftPromotionBas> giftBasList(GiftPromotionBas giftPromotionBas);


    /**
     * @Description : 프로모션 계층으로 조회 처리
     * @param :
     * @return :
     * @Author : power
     * @Create Date : 2022. 02. 10
     */
    public List<GiftPromotionBas> giftBasList(GiftPromotionBas giftPromotionBas ,String defaultOrgnId);

    // 프로모션 아이디 조회
    public String[] getPrmtId(GiftPromotionDto giftPromotionDto);

    // 프로모션 아이디 사은품 조회
    public List<GiftPromotionDto> getGiftArrList(String[] prmtIdArr);

    // 진행중인 프로모션인지 확인
    public GiftPromotionDto getPrmtChk(GiftPromotionDto giftPromotionDto);

    // 사은품 목록 조회
    public List<GiftPromotionDto> getGiftList(GiftPromotionDto giftPromotionDto);

    // 회선정보 조회
    public GiftPromotionDto getMspJuoSubInfoData(GiftPromotionDto giftPromotionDto);

    // 신청여부판단
    public List<GiftPromotionDto> getChkMspGiftPrmt(GiftPromotionDto giftPromotionDto);

    // 대상자 확인
    public GiftPromotionDto getMspGiftPrmtCustomer(GiftPromotionDto giftPromotionDto);

    // 고객정보 업데이트
    public boolean updateMspGiftPrmtCustomer(GiftPromotionDto giftPromotionDto);

    // 사은품정보 저장
    public boolean insertMspGiftPrmtResult(GiftPromotionDto giftPromotionDto);

    // 신청 사은품리스트
    public List<GiftPromotionDto> getSaveGiftList(GiftPromotionDto giftPromotionDto);

    // 제세공과금 주소 노출 여부 및 발송 시간 체크
    public ConsentGiftDto addYnChk(ConsentGiftDto consentGiftDto);

    // 사은품(제세공과금) 신청 이력 체크
    public ConsentGiftDto answerYnChk(ConsentGiftDto consentGiftDto);

    // 회선정보 조회(제세공과금)
    public ConsentGiftDto getConMspJuoSubInfoData(ConsentGiftDto consentGiftDto);

    // 회신일자 및 (주소가 있는 경우 주소 포함)업데이트.
    public boolean updateReplyDate(ConsentGiftDto consentGiftDto);

    /** 사은품 프로모션에 해당하는 혜택요약 조회 */
    RateGiftPrmtListDTO selectRateGiftPrmtByIdList(List<String> prmtIdList);

    /** 사은품 프로모션에 해당하는 혜택요약 조회 */
    RateGiftPrmtListDTO selectRateGiftPrmtByIdList(List<String> prmtIdList,String pageUri);
}
