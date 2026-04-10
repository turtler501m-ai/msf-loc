package com.ktmmobile.mcp.mypage.service;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.vo.MpTelTotalUseTimeDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;

public interface CallRegService {

	/**
	 * 12. 총 통화 시간 조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param useMonth
	 * @return
	 */
	
	public MpTelTotalUseTimeDto telTotalUseTimeDto(String ncn, String ctn, String custId, String useMonth);

	/**
	 * 기본 제공량 모두 소진하여 초과 요금 발생 고객 구분
	 * @author papier
	 * @Date : 2025.12.10
	 * @return Map<String, Object>
	 * @throws ParseException
	 */
	public Map<String, Object> isOverUsageChargeAjax(String ncn, String contractNum, String ctn, String custId);
	
	/**
	 * 당겨쓰기 조회 
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param mcpUserCntrMngDto
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @return
	 * @throws SocketTimeoutException
	 * @throws SelfServiceException
	 */
	
	public HashMap<String, Object> totalUseTimeList(McpUserCntrMngDto mcpUserCntrMngDto, String ncn, String ctn,
			String custId) throws SocketTimeoutException, SelfServiceException;

	/**
	 * 당겨쓰기 내역 조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param mcpUserCntrMngDto
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @return
	 * @throws SocketTimeoutException
	 * @throws SelfServiceException
	 */
	
	public HashMap<String, Object> totalUseTimeHist(McpUserCntrMngDto mcpUserCntrMngDto, String ncn, String ctn,
			String custId) throws SocketTimeoutException, SelfServiceException;
}
