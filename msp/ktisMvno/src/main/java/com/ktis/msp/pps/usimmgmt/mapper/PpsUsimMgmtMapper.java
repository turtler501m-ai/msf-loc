package com.ktis.msp.pps.usimmgmt.mapper;
import java.util.List;
import java.util.Map;

import com.ktis.msp.pps.usimmgmt.vo.PpsUsimVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsUsimMgmtMapper
 * @Description :   USIM관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.16  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 3. 16.
 */
@Mapper("PpsUsimMgmtMapper")
public interface PpsUsimMgmtMapper {
	/**
	 * 유심정보 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimInfoMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 유심정보 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 유심정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsUsimVo getPpsUsimInfoData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심입고 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimCreateMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 유심입고 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimCreateMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심출고 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimOutMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 유심출고 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimOutMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심반품 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimBackMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 유심반품 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimBackMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심입고상세 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsUsimVo getPpsUsimCreateData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심 번호 select 박스 옵션 가져오기 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getSelectPpsUsimModeDataList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 유심 입고
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void ppsUsimCreateProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심 출고
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void ppsUsimOutProc(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 유심 반품
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void ppsUsimBackProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심모델 관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimModelInfoMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 유심모델 관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getUsimModelInfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 유심모델 관리 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsUsimVo getPpsUsimModelInfoData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심 반품
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void ppsUsimModelInputProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 유심관리 proc 호출용
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsUsimInfoForProcData(Map<String, Object> pRequestParamMap);
	
}
