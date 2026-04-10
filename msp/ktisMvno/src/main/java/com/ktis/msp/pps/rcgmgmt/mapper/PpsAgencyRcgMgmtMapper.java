package com.ktis.msp.pps.rcgmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsAgencyRcgMgmtMapper
 * @Description :   선불 대리점 충전관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsAgencyRcgMgmtMapper")
public interface PpsAgencyRcgMgmtMapper {
	
	/**
	 * 대리점 충전목록조회
	 * @param pRequestParamMap
	 * @return
	 * */
	List<?> getAgencyRcgInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 대리점 충전목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyRcgInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 가상계좌.번호
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyRcgAgentInfoMgmt(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 대리점 예치금내역조회
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	List<?> getAgencyRcgDepositHstList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 고객번호조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	int agencySearchCtn(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 고객조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> getAgenyCusRemains(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 고객조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> agencyRecharge(Map<String, Object> pRequestParamMap);
	
	
	

}
