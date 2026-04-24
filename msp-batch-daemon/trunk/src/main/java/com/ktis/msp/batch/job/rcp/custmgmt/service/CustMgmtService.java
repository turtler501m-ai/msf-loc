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
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

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
				KtSmsCommonVO smsVO = new KtSmsCommonVO();
				
				smsVO.setScheduleTime((String) rtMap.get("REQUEST_TIME"));
				smsVO.setRcptData((String) rtMap.get("SVC_TEL_NO"));
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02("7");
				smsVO.setReserved03("YthBlckSmsSchedule");
				smsVO.setTemplateId("7");
				LOGGER.info("가입자본인 TEL : {}", rtMap.get("SVC_TEL_NO"));
				LOGGER.info("RequestTime : {}", smsVO.getScheduleTime());
				// SMS 발송 등록
				smsCommonMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				sendVO.setTemplateId(smsVO.getTemplateId());
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS 발송내역 등록
				smsCommonMapper.insertKtSmsSendMst(sendVO);
				
				// 법정대리인
				if(rtMap.get("AGENT_TEL_NO") != null && !"".equals(rtMap.get("AGENT_TEL_NO"))) {
					smsVO.setRcptData((String) rtMap.get("AGENT_TEL_NO"));
					
					LOGGER.info("법정대리인 TEL : {}", rtMap.get("AGENT_TEL_NO"));
					
					// SMS 발송 등록
					smsCommonMapper.insertKtMsgTmpQueue(smsVO);
					sendVO.setMsgId(smsVO.getMsgId());
					// SMS 발송내역 등록
					smsCommonMapper.insertKtSmsSendMst(sendVO);
					
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
				
				KtSmsCommonVO smsVO = new KtSmsCommonVO();
				smsVO.setRcptData((String) resultMap.get("SUBSCRIBER_NO"));
				smsVO.setScheduleTime((String) resultMap.get("REQUEST_TIME"));
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02("45");
				smsVO.setReserved03("EnggCloseNotiSchedule");
				smsVO.setTemplateId("45");
				
				smsCommonMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				//MSP_SMS_SEND_MST 테이블 UPDATE
				sendVO.setTemplateId("45");
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS 발송내역 등록
				smsCommonMapper.insertKtSmsSendMst(sendVO);
				procCnt++;
			}
		}catch(Exception e) {
			throw new MvnoServiceException("약정만료전 고객고지 스케줄러 에러", e);
		}
		
		return procCnt;
	}	
	
	/**
	 * 약정만료 1일전 고객고지
	 * @param param
	 * @return
	 * @throws MvnoServiceException
	 */
	public int setEnggDayCloseNoti() throws MvnoServiceException{
		int procCnt = 0;
		
		try{
						
			List<Map<String, Object>> list = custMapper.getEnggDayCloseNotiList();
			
			for(Map<String, Object> resultMap : list){
				LOGGER.debug("ctn=" + resultMap.get("SUBSCRIBER_NO"));
				
				KtSmsCommonVO smsVO = new KtSmsCommonVO();
				smsVO.setRcptData((String) resultMap.get("SUBSCRIBER_NO"));
				smsVO.setScheduleTime((String) resultMap.get("REQUEST_TIME"));
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02("100");
				smsVO.setReserved03("EnggDayCloseNotiSchedule");
				smsVO.setTemplateId("100");
				
				smsCommonMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				//MSP_SMS_SEND_MST 테이블 UPDATE
				sendVO.setTemplateId("100");
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS 발송내역 등록
				smsCommonMapper.insertKtSmsSendMst(sendVO);
				procCnt++;
			}
		}catch(Exception e) {
			throw new MvnoServiceException("약정만료 1일전 고객고지 스케줄러 에러", e);
		}
		
		return procCnt;
	}
	
	/**
	 * 약정만료 후 30일 , 60일 , 90일 , 120일 , 180일 고객 재약정 고객 고지
	 * 20210316 추가
	 * @param param
	 * @return
	 * @throws MvnoServiceException
	 */
	public int setEnggExtendNoti() throws MvnoServiceException{
		int procCnt = 0;
		
		try{

			List<Map<String, Object>> list = custMapper.getEnggExtendNotiList();
			
			for(Map<String, Object> resultMap : list){
				//LOGGER.debug("ctn=" + resultMap.get("SUBSCRIBER_NO"));
				
				KtSmsCommonVO smsVO = new KtSmsCommonVO();
				//LOGGER.debug("net=" + resultMap.get("SOC_INFO"));
				if( resultMap.get("SOC_INFO") == "3G" || "3G".equals(resultMap.get("SOC_INFO") )){
					smsVO.setRcptData((String) resultMap.get("SUBSCRIBER_NO"));
					smsVO.setScheduleTime((String) resultMap.get("REQUEST_TIME"));
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02("174");
					smsVO.setReserved03("EnggExtendNotiSchedule");
					smsVO.setTemplateId("174");
					
					smsCommonMapper.insertKtMsgTmpQueue(smsVO);
					
					SmsSendVO sendVO = new SmsSendVO();
					//MSP_SMS_SEND_MST 테이블 UPDATE
					sendVO.setTemplateId("174");
					sendVO.setMsgId(smsVO.getMsgId());
					sendVO.setSendReqDttm(smsVO.getScheduleTime());
					sendVO.setReqId(BatchConstants.BATCH_USER_ID);
					
					// SMS 발송내역 등록
					smsCommonMapper.insertKtSmsSendMst(sendVO);
					procCnt++;
				}
			}
			LOGGER.debug("문자전송건수" + procCnt);
		}catch(Exception e) {
			throw new MvnoServiceException("약정만료 후 30일 , 60일 , 90일 , 120일 , 180일 고객 재약정 고객 고지 에러", e);
		}
		
		return procCnt;
	}	
	
	/**
	 * 약정만료 후 리텐션 고지
	 * @param param
	 * @return
	 * @throws MvnoServiceException
	 */
	public int setRetentionNoti() throws MvnoServiceException{
		int procCnt = 0;
		
		try{
			String templateId = "133";
			
			List<Map<String, Object>> list = custMapper.getRetentionNotiList();
			
			for(Map<String, Object> resultMap : list){
				LOGGER.debug("ctn=" + resultMap.get("SUBSCRIBER_NO"));
				
				KtSmsCommonVO smsVO = new KtSmsCommonVO();
				smsVO.setRcptData((String) resultMap.get("SUBSCRIBER_NO"));
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02(templateId);
				smsVO.setReserved03("RetentionNotiSchedule");
				smsVO.setTemplateId(templateId);
				
				smsCommonMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				//MSP_SMS_SEND_MST 테이블 UPDATE
				sendVO.setTemplateId(templateId);
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS 발송내역 등록
				smsCommonMapper.insertKtSmsSendMst(sendVO);
				procCnt++;
			}
		}catch(Exception e) {
			throw new MvnoServiceException("약정만료 후 리텐션 고지 스케줄러 에러", e);
		}
		
		return procCnt;
	}
}
