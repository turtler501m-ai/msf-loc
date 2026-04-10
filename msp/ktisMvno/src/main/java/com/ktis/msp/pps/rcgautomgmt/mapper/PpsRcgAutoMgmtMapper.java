package com.ktis.msp.pps.rcgautomgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsRcgAutoMgmtMapper
 * @Description :   대리점 자동충전관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.16  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 3. 16.
 */
@Mapper("PpsRcgAutoMgmtMapper")
public interface PpsRcgAutoMgmtMapper {
	
	/**
	 * 자동충전관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgAutoInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동충전관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgAutoInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동충전관변경이력 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgAutoHistInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동충전변경이력 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgAutoHistInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동충전관리 등록
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	Map<String, Object> ppsRcgAutoReg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점명 검색후 select 박스 옵션 가져오기 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getSelectPpsRcgAutoAgentList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 자동충전관리 요금제 코드리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsRcgAutoSocListData(Map<String, Object> pRequestParamMap);
	
	/**
	 * RS요금제여부 확인 목록조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getRcgAutoPps35Chk(Map<String, Object> pRequestParamMap);
	
}
