package com.ktis.msp.batch.job.rcp.custmgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.custmgmt.mapper.CustMgmtMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

@Service
public class CustMgmtService extends BaseService {
	
	@Autowired
	private CustMgmtMapper custMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	/**
	 * 청소년유해물차단SMS
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setYthBlockSms() throws MvnoServiceException{
		
		int procCnt = 0;
		
		try {
			// 청소년유해차단 발송대상 추출
			List<Map<String, Object>> list = custMapper.getMinorSmsTrgtList();
			
			for(Map<String, Object> rtMap : list) {
				
				// 가입자본인
				SmsCommonVO smsVO = new SmsCommonVO();
				
				smsVO.setRequestTime((String) rtMap.get("REQUEST_TIME"));
				smsVO.setMobileNo((String) rtMap.get("SVC_TEL_NO"));
				smsVO.setTemplateId("7");
				
				LOGGER.info("가입자본인 TEL : {}", rtMap.get("SVC_TEL_NO"));
				LOGGER.info("RequestTime : {}", smsVO.getRequestTime());
				
				// SMS 발송 등록
				smsCommonMapper.insertTemplateMsgQueue(smsVO);
				
				smsVO.setSendDivision("MSP");
				smsVO.setContractNum((String) rtMap.get("CONTRACT_NUM"));
				smsVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS 발송내역 등록
				smsCommonMapper.insertSmsSendMst(smsVO);
				
				// 법정대리인
				if(rtMap.get("AGENT_TEL_NO") != null && !"".equals(rtMap.get("AGENT_TEL_NO"))) {
					smsVO.setMobileNo((String) rtMap.get("AGENT_TEL_NO"));
					
					LOGGER.info("법정대리인 TEL : {}", rtMap.get("AGENT_TEL_NO"));
					
					// SMS 발송 등록
					smsCommonMapper.insertTemplateMsgQueue(smsVO);
					
					smsVO.setSendDivision("AGT");
					// SMS 발송내역 등록
					smsCommonMapper.insertSmsSendMst(smsVO);
					
				}
				
				procCnt++;
			}
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1044", e);
		}
		
		return procCnt;
	}
	
	/**
	 * 약정만료 전 고객고지
	 * @param param
	 * @return
	 * @throws MvnoServiceException
	 */
	public int setEnggCloseNoti() throws MvnoServiceException{
		int procCnt = 0;
		
		try{
			String lastDayYn = custMapper.getLastDayCheck();
			LOGGER.debug("lastDayYn=" + lastDayYn);
			
			List<Map<String, Object>> list = custMapper.getEnggCloseNotiList(lastDayYn);
			
			for(Map<String, Object> resultMap : list){
				LOGGER.debug("ctn=" + resultMap.get("SUBSCRIBER_NO"));
				
				SmsCommonVO smsVO = new SmsCommonVO();
				smsVO.setRequestTime((String) resultMap.get("REQUEST_TIME"));
				smsVO.setMobileNo((String) resultMap.get("SUBSCRIBER_NO"));
				smsVO.setTemplateId("45");
				
				smsCommonMapper.insertTemplateMsgQueue(smsVO);
				procCnt++;
			}
		}catch(Exception e) {
			throw new MvnoServiceException("약정만료전 고객고지 스케줄러 에러", e);
		}
		
		return procCnt;
	}
}
