package com.ktis.msp.pps.ordermgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcOrderMgmtMapper
 * @Description : 물류관리
 * @
 * @ 수정일		수정자		수정내용
 * @ ----------	------	-----------------------------
 * @ 2017.05.01	김웅		최초생성
 * @
 * @author : 김웅
 * @Create Date : 2017. 05. 01.
 */
@Mapper("PpsHdofcOrderMgmtMapper")
public interface PpsHdofcOrderMgmtMapper {
	
	/**
	 * 재고관리
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderGoodsList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재고관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOrderGoodsListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재고관리 타입 select용
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderGoodsType(Map<String, Object> pRequestParamMap);
	
	/**
	 * 재고관리 코드 select용
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderGoodsCd(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 상품코드 리스트
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getPpsOrderCodeList (Map<String, Object> pRequestParamMap);
	
	/**
	 * 재고,입출력관리 등록,수정,삭제
	 * @param pRequestParamMap
	 * @return
	 **/
	void ppsOrderGoodsProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 입출고관리 내역
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderInoutList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 입출고관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOrderInoutListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 주문관리
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderInfoList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 주문관리 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOrderInfoListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 주문관리, 출고관리 등록/수정/삭제
	 * @param pRequestParamMap
	 * @return
	 **/
	void ppsOrderInfoProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 정보조회
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getOrderAgentInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * send_file을 가져온다
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getOrderSendFile(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 리플레쉬 리스트
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getPpsOrderRefreshList (Map<String, Object> pRequestParamMap);
	
	
	
}
