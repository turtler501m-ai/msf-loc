package com.ktis.msp.batch.job.rcp.mrktmgmt.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.mrktmgmt.mapper.MarketingMapper;
import com.ktis.msp.batch.job.rcp.mrktmgmt.vo.MarketingVO;
import com.ktis.msp.batch.manager.common.BatchConstants;

@Service
public class MarketingService extends BaseService {
	
	@Autowired
	private MarketingMapper marketingMapper;
	
	/*
	 * 1. 홈페이지 / 무선상품 가입자 대상을 추출
	 * 2. SMS 발송
	 * 3. SMS발송내역 등록
	 */
	
	@Transactional(rollbackFor=Exception.class)
	public int sendMarketingSms() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("마케팅동의 SMS 발송 BATCH START!!!!!");
		
		// 홈페이지 / 무선상품 가입자 대상
		List<MarketingVO> targetList = marketingMapper.getMarketingSendTarget();
		LOGGER.info("마케팅동의 대상 total[{} 건 / {} sec]", targetList.size(), sw.getTotalTimeSeconds());
		
		int sendCount = 0;
		for(MarketingVO vo : targetList) {
			
			vo.setSendMessage((vo.getSendMessage()).replaceAll(Pattern.quote("#{agrDt}"), vo.getAgrDt()));
			// SMS 발송
			sendCount += marketingMapper.insertMsgQueue(vo);
			
			vo.setReqId(BatchConstants.BATCH_USER_ID);
			
			// SMS발송내역 등록
			marketingMapper.insertSmsSendMst(vo);
			
		}
		
		sw.stop();
		LOGGER.info("마케팅동의 SMS 발송 BATCH END!!!!! total[{} 건 / {} sec]", sendCount, sw.getTotalTimeSeconds());
		
		return sendCount;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int reSendMarketingSms() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("마케팅동의 SMS 재발송 BATCH START!!!!!");
		
		// 홈페이지 / 무선상품 가입자 대상
		List<MarketingVO> targetList = marketingMapper.getMarketingReSendTarget();
		LOGGER.info("마케팅동의 대상 total[{} 건 / {} sec]", targetList.size(), sw.getTotalTimeSeconds());
		
		int sendCount = 0;
		for(MarketingVO vo : targetList) {
			
			// SMS 발송
			sendCount += marketingMapper.insertMsgQueue(vo);
			
			vo.setReqId(BatchConstants.BATCH_USER_ID);
			
			// SMS발송내역 등록
			marketingMapper.updateSmsSendMst(vo);
			
		}
		
		sw.stop();
		LOGGER.info("마케팅동의 SMS 재발송 BATCH END!!!!! total[{} 건 / {} sec]", sendCount, sw.getTotalTimeSeconds());
		
		return sendCount;
	}
}
