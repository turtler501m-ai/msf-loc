package com.ktis.msp.rcp.creditMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.creditMgmt.vo.CreditVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CreditMgmtMapper")
public interface CreditMgmtMapper {
		
	/**
	 * @Description : 신용정보 동의서 LIST 항목을 가져온다.
	 * @Param  : creditVO
	 * @Return : List<?>
	 * @Author : 한상욱
	 * @Create Date : 2016. 04. 06.
	 */
	List<?> selectCreditList(CreditVO creditVO);
	
	String getCreditId();
	
	void creditInsert(Map<String, Object> pReqParamMap);
		
	List<?> getFile1(CreditVO creditVO);
}