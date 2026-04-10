package com.ktmmobile.mcp.etc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.etc.dto.ConsentGiftDto;
import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import com.ktmmobile.mcp.etc.dto.GiftPromotionDto;

@Mapper
public interface GiftMapper {

    List<GiftPromotionBas> getPrmtBasList(GiftPromotionBas giftPromotionBas);

    List<GiftPromotionDto> getPrmtId(GiftPromotionDto giftPromotionDto);

    List<GiftPromotionDto> getGiftArrList(String[] prmtIdArr);

    GiftPromotionDto getPrmtChk(GiftPromotionDto giftPromotionDto);

    List<GiftPromotionDto> getGiftList(GiftPromotionDto giftPromotionDto);

    GiftPromotionDto getMspJuoSubInfoData(GiftPromotionDto giftPromotionDto);

    List<GiftPromotionDto> getChkMspGiftPrmt(GiftPromotionDto giftPromotionDto);

    GiftPromotionDto getMspGiftPrmtCustomer(GiftPromotionDto giftPromotionDto);

    int updateMspGiftPrmtCustomer(GiftPromotionDto giftPromotionDto);

    int insertMspGiftPrmtResult(GiftPromotionDto giftPromotionDto);

    List<GiftPromotionDto> getSaveGiftList(GiftPromotionDto giftPromotionDto);

    int insertMspGiftPrmtResultHist(GiftPromotionDto giftPromotionDto);

    ConsentGiftDto addYnChk(ConsentGiftDto consentGiftDto);

    ConsentGiftDto answerYnChk(ConsentGiftDto consentGiftDto);

    ConsentGiftDto getConMspJuoSubInfoData(ConsentGiftDto consentGiftDto);

    //회신일자 및 (주소가 있는 경우 주소 포함)업데이트
    int updateReplyDate(ConsentGiftDto consentGiftDto);

}
