package com.ktis.msp.pps.agencycustmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsAgencyCustMgmtMapper
 * @Description :   선불대리점 개통관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsAgencyCustMgmtMapper")
public interface PpsAgencyCustMgmtMapper {
	/**
	 * 선불 대리점 개통목록 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyOpenInfoMngList(Map<String, Object> pRequestParamMap);

	/**
	 * 선불 대리점 개통목록 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyOpenInfoMngListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 대리점 개통목록 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyOpenInfoMngDtlList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 대리점 서식지 노출여부 검색
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyOpenInfoAgentList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 대리점 고객 cms정보조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getPpsAgencyUserCmsInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 CMS설정요청
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> getAgencyPpsAgentCmsReq(Map<String, Object> pRequestParamMap);
	
	

}
