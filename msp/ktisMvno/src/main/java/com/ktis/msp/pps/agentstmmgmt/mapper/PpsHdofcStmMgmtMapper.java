package com.ktis.msp.pps.agentstmmgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcStmMgmtMapper
 * @Description : 정산수수료 관리 
 * @
 * @ 수정일		수정자		수정내용
 * @ ----------	------	-----------------------------
 * @ 2017.05.01	김웅		최초생성
 * @
 * @author : 김웅
 * @Create Date : 2017. 05. 01.
 */
@Mapper("PpsHdofcStmMgmtMapper")
public interface PpsHdofcStmMgmtMapper {
	
	/**
	 * 기본수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 기본수수료 목록조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * Grade수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmGradeMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * Grade수수료 목록조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmGradeMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 명변 수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmMbMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 명변 수수료 목록조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmMbMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 우량 고객 수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmUlMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 우량 고객 수수료 목록조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmUlMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 환수 등록 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmRefundExcelMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 환수 등록 목록조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmRefundExcelMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동 이체 수수료 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmCmsMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동 이체 수수료 목록조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmCmsMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지 환수 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmRefundMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지 환수 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmRefundMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지 환수 등록
	 **/
	void procPpsStmRefundExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 미사용 환수 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmNoUseMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 미사용 환수 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmNoUseMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 미사용 환수 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmReOpenMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 미사용 환수 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmReOpenMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재충전 수수료 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmRcgMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재충전 수수료 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmRcgMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 조정 수수료 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmModMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 조정 수수료 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmModMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점별 정산내역 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmHistoryMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점별 정산내역 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmHistoryMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 통합수수료정산 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 통합수수료정산  Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 각 수수료 상태 변경
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmStatusChg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점별 정산내역 수정
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsAgentStmAgentMod(Map<String, Object> pRequestParamMap);
	
	/**
	 * 조정수수료 등록
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsAgentStmMod(Map<String, Object> pRequestParamMap);
	
	/**
	 * 정책그룹리스트
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getPpsAgentStmGroupData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 정책관리 리스트
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmSetupMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 정책 값 변경
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmSetupChg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 정책 세부내역 리스트
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmSetupDtlMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 정책 코드 내역
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmCodeMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 중복 개통 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmOverlapList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 중복 개통 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmOverlapListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 현재 정산진행상태 체크
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmCntMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 기본수수료 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmBasic(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 grade수수료 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmGrade(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 명변수수료 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmMb(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 재충전수수료 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmRcg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 비정상해지 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmRefund(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 기준사용미달 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmNouse(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 우량수수료 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmReopen(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 우량수수료 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmUl(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 자동이체수수료 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmCms(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 대리점별 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStmAgent(Map<String, Object> pRequestParamMap);
	
	/**
	 * 수동 통합 정산
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsStm(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점정산관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmSelfMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점정산관리 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getStmSelfMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점정산관리 등록
	 * @param pRequestParamMap
	 * @return
	 **/
	void procPpsAgentStmSelf(Map<String, Object> pRequestParamMap);
}
