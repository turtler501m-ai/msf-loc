package com.ktis.msp.pps.imgchkmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcImgChkMgmtMapper
 * @Description :   선불 고객관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsImgChkMgmtMapper")
public interface PpsHdofcImgChkMgmtMapper {
	
	 //List<?> getList(Map<String, Object> pRequestParamMap) throws Exception;
	/**
	 * 검수관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustImgChkMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 검수관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustImgChkMngExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 검수관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustImgChkGetRow(Map<String, Object> pRequestParamMap);
	
	/**
	 * 검수관리 검수등록
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsCustImgChkRegProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 검수관리 검수수정
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsCustImgChkChgProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 현재페이지에 해당되는 rownum을 구한다
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	int getCustImgChkGetRownum(Map<String, Object> pRequestParamMap);
	
	/**
	 * 검수이력관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustImgChkGrpMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 검수이력관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustImgChkGrpMngExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동이체등록 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsAgentCmsReqMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동이체등록 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsAgentCmsReqMngListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동이체등록 세부 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsAgentCmsReqRow(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동이체등록 예금주명, 주민번호 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsAgentCmsReqCustInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동이체등록 수정
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsAgentCmsReqProc(Map<String, Object> pRequestParamMap);
	
}
