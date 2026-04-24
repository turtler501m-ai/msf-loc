package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.SmsRetryMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

@Service
public class SmsRetryService extends BaseService {
	
	@Autowired
	private SmsRetryMapper smsRetryMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	/*
	 * 1. SMS 재발송 대상을 추출
	 * 2. SMS 발송
	 * 3. SMS발송내역 수정
	 */
	@Transactional(rollbackFor=Exception.class)
	public int smsRetry() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("SMS 재발송 BATCH START!!!!!");
		
		int sendCount = 0;
		
		try {
			// SMS 재발송 대상 추출
			List<KtSmsCommonVO> targetList = smsRetryMapper.getSmsRetryList();
			LOGGER.info("SMS 재발송 대상 total[{} 건 / {} sec]", targetList.size(), sw.getTotalTimeSeconds());
			
			for(KtSmsCommonVO vo : targetList) {
				
				// SMS 발송
				sendCount += smsCommonMapper.insertKtMsgQueue(vo);
				
				vo.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS발송내역 수정
				smsCommonMapper.updateSmsSendMst(vo);
				
			}
			
		} catch(Exception e) {
			throw new MvnoServiceException("ECMN2001", e);
		}
		sw.stop();
		LOGGER.info("SMS 재발송 BATCH END!!!!! total[{} 건 / {} sec]", sendCount, sw.getTotalTimeSeconds());
		
		return sendCount;
	}
	
}
