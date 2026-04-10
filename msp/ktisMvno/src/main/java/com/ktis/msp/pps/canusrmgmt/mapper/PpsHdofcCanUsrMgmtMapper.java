package com.ktis.msp.pps.canusrmgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcCanUsrMgmtMapper
 * @Description :   해지요청 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsHdofcCanUsrMgmtMapper")
public interface PpsHdofcCanUsrMgmtMapper {
	/**
	 * 해지대상자조회 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCanUsrQueueMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지대상자조회 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCanUsrMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 직권해지처리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCanUsrMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 직권해지처리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCanUsrQueueMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지결과 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCanUsrStatsMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지결과 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCanUsrStatsMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지요청
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsCanUsrQueue(Map<String, Object> pRequestParamMap);

}
