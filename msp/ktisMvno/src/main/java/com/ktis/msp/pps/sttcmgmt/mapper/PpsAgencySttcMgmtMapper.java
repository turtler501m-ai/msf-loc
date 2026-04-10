package com.ktis.msp.pps.sttcmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsAgencySttcMgmtMapper
 * @Description :   통계관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsAgencySttcMgmtMapper")

public interface PpsAgencySttcMgmtMapper {
	
	/**
	 * 선불 대리점 >> 개통현황(통계)
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getAgencySttcOpenMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 대리점 >> 개통현황(통계)엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getAgencySttcOpenMgmtListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 선불대리점 >>가입자이용현황 (통계) 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getAgencySttcSubscribersMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불대리점 >>가입자이용현황 (통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getAgencySttcSubscribersMgmtListExcel(Map<String, Object> pRequestParamMap);
	

}
