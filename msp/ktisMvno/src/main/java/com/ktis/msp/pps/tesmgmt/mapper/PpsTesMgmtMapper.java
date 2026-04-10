package com.ktis.msp.pps.tesmgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsTesMgmtMapper
 * @Description :   선불카드관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsTesMgmtMapper")
public interface PpsTesMgmtMapper {
	
	/**
	 * 청소년요금제 충전/조회내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getTesMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 문자관리 단문문자전송
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsRcgTesProc(Map<String, Object> pRequestParamMap);

}


