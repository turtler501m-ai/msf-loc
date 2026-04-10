package com.ktis.msp.pps.cardmgmt.mapper;
import java.util.List;
import java.util.Map;




import com.ktis.msp.pps.hdofccustmgmt.vo.PpsAgentVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsPinInfoVo;
import com.ktis.msp.pps.cardmgmt.vo.*;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcCardMgmtMapper
 * @Description :   선불카드관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsHdofcCardMgmtMapper")
public interface PpsHdofcCardMgmtMapper {
	/**
	 * 핀정보 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinInfoMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 핀정보 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinInfoMgmtListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 핀생성 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinCreateMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 핀생성 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinCreateMgmtListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 핀 개통 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinOpenMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 핀 개통 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinOpenMgmtListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 핀 출고 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinReleaseMgmtList(Map<String, Object> pRequestParamMap);

	/**
	 * 핀 출고 목록조회 엑셀호출
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinReleaseMgmtListExcel(Map<String, Object> pRequestParamMap);


	/**
	 * 핀정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsPinInfoVo getPpsPinInfoData(Map<String, Object> pRequestParamMap);


	/**
	 * 핀생성 처리
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void getPpsPinInfoCreate(Map<String, Object> pRequestParamMap);


	/**
	 * 핀 출고 처리
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsPinOutProc(Map<String, Object> pRequestParamMap);


	/**
	 * 핀 개통 처리
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsPinOpenProc(Map<String, Object> pRequestParamMap);


	/**
	 * 생성, 출고, 개통시 핀정보 출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinInfListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * PIN관리코드 핻더 리스트 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getMngCodeHeaderList();

	/**
	 * 핀 개통정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsPinSummaryOpenVo getPpsPinOpenSummaryInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 정보 조회 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsAgentVo getAgentInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 핀생성통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinCreateStatsMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 핀생성통계 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinCreateStatsMgmtListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 핀개통통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinOpenStatsMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 핀개통통계 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinOpenStatsMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 핀현황통계 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinInfoStatsMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 핀현황통계 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPinInfoStatsMgmtListExcel(Map<String, Object> pRequestParamMap);

	
}
