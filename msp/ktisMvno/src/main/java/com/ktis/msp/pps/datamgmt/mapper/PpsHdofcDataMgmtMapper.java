package com.ktis.msp.pps.datamgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : ppsHdofcDataMgmtMapper
 * @Description :   데이타관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsHdofcDataMgmtMapper")
public interface PpsHdofcDataMgmtMapper {
	
	/**
	 * 선불정산대상 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getDataInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불정산대상, 유심임대선불관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getDataInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심임대선불관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getDataInfoUsimMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심임대선불관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getDataInfoUsimMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * CMS충전대상 목록조회
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getCmsDataInfoMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * CMS충전대상  엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getCmsDataInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 *우량정산대상  목록조회
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getDataInfoExcellentMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 우량정산대상 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getDataInfoExcellentMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	/**
	 *환수정산대상  목록조회
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getDataInfoClawbackMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 환수정산대상 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getDataInfoClawbackMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 *재충전대상  목록조회
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getReChargeDataInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재충전대상 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getReChargeDataInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간선불정산대상 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getRealDataInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간선불정산대상 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getRealDataInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	
	
}
