package com.ktis.msp.pps.warmgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsWarMgmtMapper
 * @Description :   주의고객관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.16  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 3. 16.
 */
@Mapper("PpsWarMgmtMapper")
public interface PpsWarMgmtMapper {
	
	/**
	 * 다량문자모니터링 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getWarInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 다량문자모니터링 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getWarInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 주의고객 등록
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> ppsWarReg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 주의고객관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getWarRegInfoMgmtList(Map<String, Object> pRequestParamMap);
	
}
