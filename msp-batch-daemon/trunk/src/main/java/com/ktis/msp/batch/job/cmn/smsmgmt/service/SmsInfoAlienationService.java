package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.SmsInfoAlienationMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class SmsInfoAlienationService extends BaseService {
	
	@Autowired
	private SmsInfoAlienationMapper smsInfoAlienationMapper;

	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public SmsInfoAlienationService() {
		setLogPrefix("[SmsInfoAlienationService]");
	}
	
	/*
	 * 1. 정보소외계층 고객을 대상으로 개통 익월에 개통내역 안내 SMS를 자동 발송처리
	 */
	@Transactional(rollbackFor=Exception.class)
	public int smsInfoAlienation() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("정보소외계층 고객을 대상 SMS 발송 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	
    	int sendCount = 0;
    	
    	try {
			List<Map<String,Object>> list = smsInfoAlienationMapper.selectInfoAnSendList();
			if(list.size() > 0){
				for(Map<String,Object> map : list){

					/* 내용 변환값 */
					String smsInCtn = String.valueOf(map.get("SMS_IN_CTN"));
					String modelName = String.valueOf(map.get("MODEL_NAME"));
					String fstRateNm = String.valueOf(map.get("FST_RATE_NM"));
					String instInfoA = String.valueOf(map.get("INST_INFO_A"));
					String instInfoB = String.valueOf(map.get("INST_INFO_B"));
					String instInfoC = String.valueOf(map.get("INST_INFO_C"));
					String openAgntNm = String.valueOf(map.get("OPEN_AGNT_NM"));
					String scheduleTime = String.valueOf(map.get("SCHEDULE_TIME"));
					
					String templateId = "481";
					KtSmsCommonVO smsVO = smsCommonMapper.getKtTemplateText(templateId);
					smsVO.setMessage((smsVO.getTemplateText())
											.replaceAll(Pattern.quote("#{data1}"), modelName)
											.replaceAll(Pattern.quote("#{data2}"), fstRateNm)
											.replaceAll(Pattern.quote("#{data3}"), instInfoA)
											.replaceAll(Pattern.quote("#{data4}"), instInfoB)
											.replaceAll(Pattern.quote("#{data5}"), instInfoC)
											.replaceAll(Pattern.quote("#{data6}"), openAgntNm)
										);
					
					LOGGER.debug("정보소외계층 고객을 대상 SMS 전송 내용 : {}", smsVO.getMessage());
					
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02(templateId);
					smsVO.setReserved03("SmsInfoAlienationSchedule");
					smsVO.setTemplateId(templateId);
					smsVO.setRcptData(smsInCtn);
					smsVO.setScheduleTime(scheduleTime);
					// SMS 발송 등록
					sendCount += smsCommonMapper.insertKtMsgTmpQueue(smsVO);
					
					SmsSendVO sendVO = new SmsSendVO();
					sendVO.setTemplateId(templateId);
					sendVO.setMsgId(smsVO.getMsgId());
					sendVO.setSendReqDttm(smsVO.getScheduleTime());
					sendVO.setReqId(BatchConstants.BATCH_USER_ID);
					// SMS 발송내역 등록
					smsCommonMapper.insertKtSmsSendMst(sendVO);
				}
			} else {
				LOGGER.info("SMS 대상 없음");				
			}
			
			LOGGER.info(generateLogMsg("정보소외계층 고객을 대상 SMS발송 종료"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2017", e);
		}
    	return sendCount;
	}
	
	public String StringNumbericComma(String strWon) throws Exception {
		String strCommaNumberic = "";

		try {
			DecimalFormat df = new DecimalFormat("##,###");
			strCommaNumberic = df.format(Integer.parseInt(calcMath(Double.parseDouble(strWon))));
		} catch(Exception e) {
			throw e;
		}
		return strCommaNumberic;
	}
	
	public static String calcMath(double nCalcVal) {
	    return String.valueOf((int)Math.floor(nCalcVal / 10) * 10);
	}
}
