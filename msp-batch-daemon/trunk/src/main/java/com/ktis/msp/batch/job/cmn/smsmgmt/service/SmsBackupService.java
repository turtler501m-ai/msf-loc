package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.SmsBackupMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

@Service
public class SmsBackupService extends BaseService {
	
	@Autowired
	private SmsBackupMapper smsBackupMapper;
	
	public SmsBackupService() {
		setLogPrefix("[SmsBackupService]");
	}
	
	/*
	 * 1. SMS 6개월 이전 데이터 삭제 후 BACKUP
	 * 2. MSP_SMS_RESEND_MST 테이블 6개월 이전 데이터 삭제
	 */
	@Transactional(rollbackFor=Exception.class)
	public int smsBackup() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("SMS BACKUP START"));
    	LOGGER.info(generateLogMsg("================================================================="));
    	
    	int sendCount = 0;
    	
		try {
			/* BACKUP 시작*/
			sendCount = smsBackupMapper.insertSmsBackup();
			/* BACKUP 데이터 삭제 */
			smsBackupMapper.deleteSmsBackupData();
			/* RESEND 데이터 삭제 */
			smsBackupMapper.deleteSmsResend();
		} catch(Exception e) {
			throw new MvnoServiceException("SMS BACKUP ERROR", e);
		}
		return sendCount;
	}
}
