package com.ktis.msp.pps.sttcmgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcSttcMgmtMapper
 * @Description :   통계관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsHdofcSttcMgmtMapper")
public interface PpsHdofcSttcMgmtMapper {
	
	/**
	 * 선불 개통현황통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcOpenMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 개통현황(통계) 목록조회 엑셀출력 
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcOpenMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 충전현황(통계) 목록조회 
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcRechargeMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 충전현황(통계) 목록조회 엑셀출력
	 * 
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcRechargeMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 예치금입금현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcDepositInMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 예치금입금현황(통계) 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcDepositInMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 예치금출금현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcDepositOutMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 예치금출금현황(통계) 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcDepositOutMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 사용현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcUseMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 사용현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcUseMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 국제현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcIntrnMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 국제현황(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcIntrnMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 카드현황(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcCardMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 카드현황(통계) 엑셀출력 
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcCardMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 ARPU분석통계
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getSttcArpuMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 ARPU분석통계 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getSttcArpuMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 사용현황2(통계) 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcUse2MgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 사용현황2(통계) 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getSttcUse2MgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	

}
