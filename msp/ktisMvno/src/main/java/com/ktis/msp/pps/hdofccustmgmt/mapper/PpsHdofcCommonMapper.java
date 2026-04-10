package com.ktis.msp.pps.hdofccustmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtInResVo;
@Mapper("ppsHdofcCommonMapper")
public interface PpsHdofcCommonMapper {
	
	/**
	 * 충전요청 후 pps_kt_in_res테이블에 충전결과가 들어오기까지 select
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	int getPpsKtInResCnt(Map<String, Object> pRequestParamMap);
	
	/**
	 * 충전요청 후 pps_kt_in_res테이블에 충전결과가 들어오기오는 프로시져
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void getPpsKtInResCntProc(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * pps_kt_in_res테이블에서 충전 결과를 받아온다
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsKtInResVo getPpsKtInResInfo(Map<String, Object> pRequestParamMap);

	/**
	 * pps_kt_in_res테이블에서 충전 결과를 받아온 후 pps_kt_in_res 테이블의 row를 지운다
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void deletePpsKtInRes(Map<String, Object> pRequestParamMap);
	
	/**
	 * KT통신을 위한 seq생성
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	String getPpsKtSeq();
	
	/**
	 * 대리점명 검색후 select 박스 옵션 가져오기 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getSelectPpsAgentInfoDataList(Map<String, Object> pRequestParamMap);
	
	/**
	 * CMS은행 리스트 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getCmsBankList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 판매점 select리스트 
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> ppsAgentSaleSelect(Map<String, Object> pRequestParamMap);
	
	/**
	 * 공통코드 select리스트 (테이블명, 컬럼명)
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getppsKtCommonCdList (Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 선불  콤보코드 select 리스트 (LovCd )
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getPpsCodeComboList (Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불  설명 리스트 (LovCd )
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getPpsCodeDescInfoList (Map<String, Object> pRequestParamMap);
	
}
