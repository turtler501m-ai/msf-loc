package com.ktis.msp.voc.giftvoc.mapper;

import com.ktis.msp.voc.giftvoc.vo.GiftPayStatVo;
import com.ktis.msp.voc.giftvoc.vo.GiftVocVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("GiftVocMapper")
public interface GiftVocMapper {

    List<EgovMap> getGiftVocList(GiftVocVo searchVO);

    GiftVocVo getGiftVocDtl(GiftVocVo searchVO);

    int updateGiftVoc(GiftVocVo searchVO);

    int updateGiftVocStat(GiftVocVo searchVO);

    int updateGiftVocAns(GiftVocVo searchVO);

    int dupChkGiftVoc(GiftVocVo searchVO);

    GiftVocVo getCustInfo(GiftVocVo searchVO);

    int insertGiftVoc(GiftVocVo searchVO);

    List<EgovMap> giftPayStatList(GiftPayStatVo searchVO);

    void insertGiftPayStat(GiftPayStatVo searchVO);
}
