package com.ktis.msp.pps.imgchkmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsAgencyImgChkMgmtMapper
 * @Description :   선불대리점 개통관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsAgencyImgChkMgmtMapper")
public interface PpsAgencyImgChkMgmtMapper {
	/**
	 * 선불 대리점 검수관리 조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyCustImgChkMngList(Map<String, Object> pRequestParamMap);

	/**
	 * 선불 대리점 검수관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyCustImgChkMngExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 검수관리 검수수정
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsAgencyCustImgChkChgProc(Map<String, Object> pRequestParamMap);
	
}
