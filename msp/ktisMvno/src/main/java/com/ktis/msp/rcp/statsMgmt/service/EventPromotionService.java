package com.ktis.msp.rcp.statsMgmt.service;

import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.statsMgmt.mapper.EventPromotionMapper;
import com.ktis.msp.rcp.statsMgmt.vo.EventPromotionVo;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventPromotionService {

    @Autowired
    private EventPromotionMapper eventPromotionMapper;

    @Autowired
    private MaskingService maskingService;

    /** 프로모션명 콤보 목록 조회 */
    public List<?> getPromotionComboList(){
        return eventPromotionMapper.getPromotionComboList();
    }

    /** 당첨결과 콤보 목록 조회 */
    public List<?> getPrizeComboList(EventPromotionVo searchVo){
        return eventPromotionMapper.getPrizeComboList(searchVo);
    }

    /** 게임 참여자 목록 조회 */
    public List<EgovMap> getEventPromotionList(EventPromotionVo searchVO, Map<String, Object> paramMap) {

        List<EgovMap> list = eventPromotionMapper.getEventPromotionList(searchVO);
        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);
        return list;
    }

    /** 게임 참여자 목록 엑셀 다운로드 */
    public List<?> getEventPromotionListExcel(EventPromotionVo searchVO, Map<String, Object> paramMap) {

        List<EgovMap> list = eventPromotionMapper.getEventPromotionListExcel(searchVO);
        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);
        return list;
    }

    private HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();
        maskFields.put("ctn", "MOBILE_PHO");
        maskFields.put("customerName", "CUST_NAME");
        return maskFields;
    }
}
