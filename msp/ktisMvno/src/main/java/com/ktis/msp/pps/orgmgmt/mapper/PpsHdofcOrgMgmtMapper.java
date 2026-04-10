package com.ktis.msp.pps.orgmgmt.mapper;
import java.util.List;
import java.util.Map;

import com.ktis.msp.pps.orgmgmt.vo.PpsOnlineOrderVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcOrgMgmtMapper
 * @Description :   조직관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("ppsHdofcOrgMgmtMapper")
public interface PpsHdofcOrgMgmtMapper {
	
	/**
	 * 예치금출금내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getAgentDepositMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 예치금출금내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getAgentDepositMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 온라인주문내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOnlineOrderMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 온라인주문내역 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOnlineOrderMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 온라인주문내역 처리 
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsOnlineOrderAdminProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 온라인주문 대리점 
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void getPpsOnlineOrderAgentProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 온라인주문 상세내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsOnlineOrderVo getOnlineOrderInfo(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 개통대리점 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOpenAgentInfoList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 개통대리점 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOpenAgentInfoListExcel(Map<String, Object> pRequestParamMap);
	
	

}
