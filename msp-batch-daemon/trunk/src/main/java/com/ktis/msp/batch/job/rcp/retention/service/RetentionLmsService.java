package com.ktis.msp.batch.job.rcp.retention.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.retention.mapper.RetentionLmsMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class RetentionLmsService extends BaseService {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private RetentionLmsMapper retentionLmsMapper;

	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	public RetentionLmsService() {
		setLogPrefix("[RetentionLmsService]");
	}	

	// 대상 고객 추출
	// 1. 3G/LTE 단말 구매고객 중 사용중인 고객
	// 2. 재약정/우수기변 미가입 고객
	// 3. 20~70대 고객 중 30개월차 도래고객 (재도래 고객은 포함하지않음)
    @Transactional(rollbackFor=Exception.class)
	public void insertRetentionLmsTrgt() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("가입후 30개월차 도래고객 추출 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
		try {
			retentionLmsMapper.insertRetentionLmsTrgt();
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1001", e);
		}

    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("가입후 30개월차 도래고객 추출 END"));
    	LOGGER.info(generateLogMsg("================================================================="));
	}    
    
	// LMS 전송
	// 매월 7~13일 10시(500건), 13시(500건), 15시(500건), 17시(200건) 일 최대 1700건
    @Transactional(rollbackFor=Exception.class)
	public void sendLmsRetentionTrgt(BatchCommonVO batchVo) throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("가입후 30개월차 도래고객 LMS 발송 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.KOREA);
			Date date = new Date();
			String time = sdf.format(date);
			int maxCnt = 0;
			
			// 10,13,15시에는 500건  17시에는 200건 발송
			if("17".equals(time)){
				maxCnt = 200;
			} else {
				maxCnt = 500;				
			}
			
	    	LOGGER.info(generateLogMsg("발송 대상 select"));
	    	List<Map<String, Object>> list = retentionLmsMapper.selectLmsSendList(maxCnt);
	    	LOGGER.info(generateLogMsg("발송 대상 : " + list.size() + " 건"));
	    	
	    	if(list.size() > 0){

				for(int i=0; i<list.size(); i++) {

					String strContractNum = String.valueOf(list.get(i).get("CONTRACT_NUM"));
					String strCtn = String.valueOf(list.get(i).get("CTN"));
					String strTypeCd = "MSP";
					
					String templateId = "110";
					KtSmsCommonVO smsVO = smsCommonMapper.getKtTemplateText(templateId);
					smsVO.setMessage((smsVO.getTemplateText()));
										
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02(templateId);
					smsVO.setReserved03("RetentionLmsSchedule");
					smsVO.setTemplateId(templateId);
					smsVO.setRcptData(strCtn);
					
					// SMS 발송 등록
					int iLmsSendCnt = 0;
					iLmsSendCnt = smsCommonMapper.insertKtMsgTmpQueue(smsVO);

					if(iLmsSendCnt > 0){ // 문자 정상 발송시
						SmsSendVO sendVO = new SmsSendVO();
						//RETENTION_LMS_SEND_MST 테이블 UPDATE 
						sendVO.setContractNum(strContractNum);
						//MSP_SMS_SEND_MST 테이블 UPDATE
						sendVO.setTemplateId(templateId);
						sendVO.setMsgId(smsVO.getMsgId());
						sendVO.setSendReqDttm(smsVO.getScheduleTime());
						sendVO.setReqId(BatchConstants.BATCH_USER_ID);
						
						// SMS 발송내역 등록
						smsCommonMapper.insertKtSmsSendMst(sendVO);
						
						// MST 테이블에 처리결과 update
						retentionLmsMapper.updateLmsSendList(sendVO);
					} else {
				    	LOGGER.info(generateLogMsg("[ LMS 발송 실패 ] " + "계약번호 : " + strContractNum + "전화번호 : " + strCtn));						
					}
				}
		    	LOGGER.info(generateLogMsg("LMS 발송 종료"));
			} else {
				LOGGER.info("LMS 발송 대상 없음");				
			}
	    	
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1001", e);
		}

    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("가입후 30개월차 도래고객 LMS 발송 END"));
    	LOGGER.info(generateLogMsg("================================================================="));
	}    
        
}
