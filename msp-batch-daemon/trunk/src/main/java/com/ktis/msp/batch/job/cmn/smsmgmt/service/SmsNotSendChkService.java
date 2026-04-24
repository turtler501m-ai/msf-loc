package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.SmsNotSendChkMapper;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

@Service
public class SmsNotSendChkService extends BaseService {
	
	@Autowired
	private SmsNotSendChkMapper smsNotSendChkMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	public SmsNotSendChkService() {
		setLogPrefix("[SmsNotSendChkService]");
	}
	
	/*
	 * 1. AM2X_SUBMIT STATUS 가 0이면서 SCHEDUL_TIME 20분이상 지났을 경우 체크
	 * 2. 데이터 확인 후 AM2X_SUBMIT_BACK 이동 후 삭제
	 * 3. 담당자에게 문자발송 실패 확인 문자 발송
	 */
	@Transactional(rollbackFor=Exception.class)
	public int smsNotSendChk() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("SMS NOT SEND CHK START"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	int chkCnt = 0;
		try {
			/* 미발송건 CHK 시작*/
			chkCnt = smsNotSendChkMapper.selectSmsNotSend();
			if(chkCnt > 0){
				/* 미발송건 BACKUP */
				smsNotSendChkMapper.insertSmsNotSendBackup();
				/* 미발송건 데이터 삭제 */
				smsNotSendChkMapper.deleteSmsNotSend();
				/* 미발송건 진행시 1건 삭제 */
				chkCnt = 1;
				
				List<String> sendList = smsNotSendChkMapper.getSendTargetInfo();
				
				for(String phoneNum : sendList) {
					KtSmsCommonVO smsVO = new KtSmsCommonVO();
					smsVO.setMsgType("1");
					smsVO.setRcptData(phoneNum);
					smsVO.setCallbackNum("18995000");
					smsVO.setSubject("SMS 미발송건 상태 체크 알림");
					smsVO.setMessage("SMS 미발송건 확인 [ 건수 : "+chkCnt+"]");
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02("SmsNotSendChkSchedule");
					smsVO.setReserved03("SYSTEM");
					smsCommonMapper.insertKtMsgQueue(smsVO);
				}
			}
		} catch(Exception e) {
			throw new MvnoServiceException("SMS NOT SEND CHK END", e);
		}
		return chkCnt;
	}
}
