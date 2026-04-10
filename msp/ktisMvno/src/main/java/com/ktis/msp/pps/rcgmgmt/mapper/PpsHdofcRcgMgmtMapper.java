package com.ktis.msp.pps.rcgmgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : ppsHdofcRcgMgmtMapper
 * @Description :   선불 충전관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsHdofcRcgMgmtMapper")
public interface PpsHdofcRcgMgmtMapper {
	
	/**
	 * 충전내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간자동출금내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgRealCmsMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간 자동출금내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgRealCmsMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 가상계좌입금내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgVacRechargeMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 가상계좌입금내역 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgVacRechargeMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 가상계좌 관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgVacInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 가상계좌 관리 목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgVacInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 *POS충전내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgPosRechargeMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * POS충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgPosRechargeMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 일괄충전 고객조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgBatchSearchMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 일괄충전
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> ppsRcgBatch(Map<String, Object> pRequestParamMap);
	
	/**
	 * 일괄충전 고객조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgBatchSearchMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 가상계좌관리 - 가상계좌회수
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> ppsRcgVacReset(Map<String, Object> pRequestParamMap);
	
	/**
	 * 충전내역 - 충전취소
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> ppsRcgCancel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점정보 얻어옴
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgentInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점예치금조정
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> goPpsDepositModify(Map<String, Object> pRequestParamMap);
	
	/**
	 * 일괄충전내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgBatchInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 일괄충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgBatchInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * ATM충전내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgFtpAtmRcgInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * ATM충전내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgFtpAtmRcgInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	
	
	
	

}
