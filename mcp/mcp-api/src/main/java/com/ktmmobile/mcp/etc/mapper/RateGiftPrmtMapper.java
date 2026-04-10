package com.ktmmobile.mcp.etc.mapper;

import com.ktmmobile.mcp.etc.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface RateGiftPrmtMapper {

    int selectRateGiftPrmtCount(RateGiftPrmtDto searchDto);

    List<RateGiftPrmtDto> selectRateGiftPrmtList(RateGiftPrmtDto searchDto, RowBounds rowBounds);

    int selectGiftPrmtCount(RateGiftPrmtDtlDto searchDto);

    List<RateGiftPrmtDtlDto> selectGiftPrmtList(RateGiftPrmtDtlDto searchDto, RowBounds rowBounds);

    RateGiftPrmtDtlDto selectGiftPrmtMstInfo(String prmtId);

    List<RateGiftPrmtSubDto> selectGiftPrmtOrgnList(String prmtId);

    List<RateGiftPrmtSubDto> selectGiftPrmtRateList(String prmtId);

    List<RateGiftPrmtSubDto> selectGiftPrmtOnoffList(String prmtId);

    List<RateGiftPrmtSubDto> selectGiftPrmtModelList(String prmtId);

    List<RateGiftPrmtSubDto> selectGiftPrmtPrdtList(String prmtId);

    List<RateGiftPrmtDto> selectRateGiftPrmtXmlList();

    List<RateAdsvcGdncProdRelDto> selectRateAdsvcGdncProdRelXmlList();

    int selectRateGiftPrmtTotalPrice(String rateAdsvcCd);
}
