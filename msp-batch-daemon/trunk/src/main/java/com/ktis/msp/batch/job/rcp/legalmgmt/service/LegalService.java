package com.ktis.msp.batch.job.rcp.legalmgmt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;

import com.ktis.msp.batch.job.rcp.legalmgmt.mapper.LegalMapper;

import com.ktis.msp.batch.manager.common.BatchConstants;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class LegalService extends BaseService {
	
	@Autowired
	private LegalMapper legalMapper;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
		
	/**
	 * 만19세이상이면서 미성년자인 고객 법정대리인 정보 삭제
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setLegalRemove() throws MvnoServiceException{
		
		int procCnt = 0;
		
		try {
			// 만19세이상이면서 미성년자인 고객 목록
			LOGGER.info("만19세이상이면서 미성년자인 고객 처리 시작");
			List<Map<String, Object>> list = legalMapper.getTrgtList();
			
			for(Map<String, Object> rtMap : list) {

				
				String contractNum = (String) rtMap.get("CONTRACT_NUM");
				LOGGER.info("CONTRACT_NUM:" + contractNum);


				// MSP_REQUEST_DTL 계약정보 법정대리인 정보 삭제
				legalMapper.updateMspRequestDtl(contractNum);
				//MSP_REQUEST_AGENT 가입신청_대리인 법정대리인 정보 삭제
				legalMapper.updateMspRequestAgent(contractNum);

				// MCP_REQUEST 계약정보 법정대리인 정보 삭제
				legalMapper.updateMcpRequest(contractNum);
				//MCP_REQUEST_AGENT 가입신청_대리인 법정대리인 정보 삭제
				legalMapper.updateMcpRequestAgent(contractNum);
								
				procCnt++;
			}
			
		} catch(Exception e) {
			throw new MvnoServiceException("ERCP1011", e);
		}
		
		return procCnt;
	}

	/**
	 * 포털사용자 법정대리인 삭제 
	 * 만19세이상이면 사용자 법정대리인 정보 삭제
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteMcpUserAgent() throws MvnoServiceException{
		
		int procCnt = 0;
		
		try {
			// 만19세이상이면서 법정대리인 정보가 있는 사용자 목록
			LOGGER.info("만19세이상이면서 법정대리인 정보가 있는 사용자 목록 처리 시작");
			List<Map<String, Object>> list = legalMapper.getTrgtUserList();
			
			for(Map<String, Object> rtMap : list) {

				
				String userId = (String) rtMap.get("USERID");
				LOGGER.info("userId:" + userId);


				//MCP_USER_AGENT 회원정보 법정대리인 정보 삭제
				legalMapper.deleteMcpUserAgent(userId);

								
				procCnt++;
			}
			
		} catch(Exception e) {
			throw new MvnoServiceException("ERCP1012", e);
		}
		
		return procCnt;
	}
	
	
	
}
