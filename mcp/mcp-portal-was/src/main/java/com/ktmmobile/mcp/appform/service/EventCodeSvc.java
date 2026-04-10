package com.ktmmobile.mcp.appform.service;

import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import com.ktmmobile.mcp.rate.dto.EventCodePrmtXML;

import java.util.List;

public interface EventCodeSvc {

    /** 특정 요금제에 해당하는 이벤트 코드 리스트 조회 */
    List<String> getEvntCdList(String rateCd);

    /** 이벤트 코드 추천인ID 표출여부 xml 조회 */
    String getEventCodeRecoUseYnXML(String evntCd);

    GiftPromotionBas getEventVal(String rateCd ,String evntCd);
    GiftPromotionBas getEventchk(String evntCdPrmt);

    List<EventCodePrmtXML> getEventCodePrmtXML(String key);
}
