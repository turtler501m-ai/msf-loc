package com.ktis.msp.rcp.statsMgmt.mapper;

import com.ktis.msp.rcp.statsMgmt.vo.EventPromotionVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("EventPromotionMapper")
public interface EventPromotionMapper {

    public List<?> getPromotionComboList();

    public List<?> getPrizeComboList(EventPromotionVo searchVO);

    List<EgovMap> getEventPromotionList(EventPromotionVo searchVO);

    List<EgovMap> getEventPromotionListExcel(EventPromotionVo searchVO);

}
