package com.ktis.msp.pps.filemgmt.mapper;


import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsFileMapper
 * @Description : 기준 공통 mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Mapper("ppsFileMapper")
public interface PpsFileMapper {
	
	
	
	
    
	
	
		
	/**
	 * @Description : 파일 삭제
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	int deleteFile(String fileId);	
		
	
	/**
	 * 고객 이미지 등록/삭제
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> ppsProcImageUploadAndDelete(Map<String, Object> pRequestParamMap) ;


	/**
	 * 서식지 목록조회 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getPpsCustomerImageInfoList(Map<String, Object> pRequestParamMap);
	
	/**
	 *  서식지 목록조회  엑셀출력
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getPpsCustomerImageInfoListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 *  서식지 목록조회 탭조회 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getPpsCustomerImageInfoTabList(Map<String, Object> pRequestParamMap);

	/**
	 * 서식지 등록/수정/삭제 처리 
	 * @param pRequestParamMap
	 */
	void procPpsCustomerImgeInsertUpdate(Map<String, Object> pRequestParamMap);
	
	/**
	 * 회원증빙 
	 * @param pRequestParamMap
	 * @return
	 */
	void procPpsRealPayInfoUpdate(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 회원증빙
	 * @param pRequestParamMap
	 * @return
	 */
	void procAgencyPpsAgentCmsReq(Map<String, Object> pRequestParamMap);
}
