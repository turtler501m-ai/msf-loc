package com.ktis.msp.pps.ordermgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
/**
 * @Class Name : PpsAgencyOrdermgmtMapper
 * @Description :   선불대리점 온라인주문관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsAgencyOrderMgmtMapper")
public interface PpsAgencyOrderMgmtMapper {
	
	/**
	 * 대리점 주문관리
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderInfoList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재고관리
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderGoodsList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 주문관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOrderInfoListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 정보조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderAgentInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 주문관리, 출고관리 등록/수정/삭제
	 * @param pRequestParamMap
	 * @return
	 **/
	void ppsOrderInfoProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * send_file을 가져온다
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getOrderSendFile(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재고관리 코드 select용
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderGoodsCd(Map<String, Object> pRequestParamMap);


}
