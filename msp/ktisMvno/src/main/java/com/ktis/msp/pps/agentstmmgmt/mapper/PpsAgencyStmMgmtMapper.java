package com.ktis.msp.pps.agentstmmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsAgencyStmMgmtMapper
 * @Description : 정산수수료 관리 
 * @
 * @ 수정일		수정자		수정내용
 * @ ----------	------	-----------------------------
 * @ 2017.05.01	김웅		최초생성
 * @
 * @author : 김웅
 * @Create Date : 2017. 05. 01.
 */
@Mapper("PpsAgencyStmMgmtMapper")
public interface PpsAgencyStmMgmtMapper {
	
	/**
	 * 대리점별 정산내역 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgentStmHistoryMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점별 정산내역 조회 Excel
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgentStmHistoryMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점정산관리 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgentStmSelfMgmtList(Map<String, Object> pRequestParamMap);

}
