package com.ktis.msp.gift.custgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.gift.custgmt.vo.CustMngVO;
import com.ktis.msp.gift.custgmt.vo.PrmtResultVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("custMngMapper")
public interface CustMngMapper {

    
    List<?> custMngList(CustMngVO searchVO);
    
    int giftPrmtCustomerUpdate(CustMngVO searchVO);

    List<?> getCustGiftList(CustMngVO searchVO);

    List<?> getCustGiftPrmtPrdtList(CustMngVO searchVO);
    
    int giftPrmtResultDelete(CustMngVO searchVO);

    void giftPrmtResultInsert(PrmtResultVO searchVO);

    
    List<?> custMngListEx(CustMngVO searchVO);
    
    List<?> custMngListEx2(CustMngVO searchVO);

	void giftPrmtResultHistInsert(Map<String, Object> item);

	List<?> getPrmtPrdtResultList(String requestKey);

	List<?> getMPortalPrmtPrdtResultList(String requestKey);

}
