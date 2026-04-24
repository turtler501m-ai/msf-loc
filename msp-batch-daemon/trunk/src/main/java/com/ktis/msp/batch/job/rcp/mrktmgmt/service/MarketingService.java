package com.ktis.msp.batch.job.rcp.mrktmgmt.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.mrktmgmt.mapper.MarketingMapper;
import com.ktis.msp.batch.job.rcp.mrktmgmt.vo.MarketingVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class MarketingService extends BaseService {
	
	@Autowired
	private MarketingMapper marketingMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
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
		
		int sendCount = 0;
		
		try {
			// 홈페이지 / 무선상품 가입자 대상
			List<MarketingVO> targetList = marketingMapper.getMarketingSendTarget();
			LOGGER.info("마케팅동의 대상 total[{} 건 / {} sec]", targetList.size(), sw.getTotalTimeSeconds());
			String senderKey = propertiesService.getString("msg.kakao.ktSenderKey");
			String kNextType = propertiesService.getString("msg.kakao.ktKNextType");
			
			
			for(MarketingVO vo : targetList) {
				vo.setSendMessage((vo.getSendMessage()).replaceAll(Pattern.quote("#{agrDt}"), vo.getAgrDt()));
				
				//20190730 kakao알림톡 추가
				vo.setSenderKey(senderKey);
				vo.setkNextType(kNextType);
				vo.setReqId(BatchConstants.BATCH_USER_ID);
				
				KtSmsCommonVO smsVO = new KtSmsCommonVO();
				smsVO = smsCommonMapper.getKtTemplateText(vo.getTemplateId());
				
				smsVO.setMsgType("6");
				smsVO.setRcptData(vo.getMobileNo());
				smsVO.setCallbackNum(vo.getSendNumber());
				smsVO.setSubject(vo.getSubject());
				smsVO.setMessage(vo.getSendMessage());
				smsVO.setkMessage(vo.getSendMessage());
				smsVO.setScheduleTime(vo.getRequestTime());
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02(vo.getTemplateId());
				smsVO.setReserved03("MarketingSchedule");
				smsVO.setMsgTypeSecond(kNextType);
				smsVO.setkSenderkey(senderKey);
				smsVO.setFailSend("Y");
				// SMS 발송
				sendCount += smsCommonMapper.insertKtMsgQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				sendVO.setSendDivision(vo.getSendDivision());
				sendVO.setContractNum(vo.getContractNum());
				sendVO.setTemplateId(vo.getTemplateId());
				sendVO.setMseq(smsVO.getMsgId());
				sendVO.setRequestTime(vo.getRequestTime());
				sendVO.setReqId(BatchConstants.BATCH_USER_ID);
				sendVO.setPortalUserId(vo.getUserid());
				sendVO.setKtSmsYn("Y");
				// SMS발송내역 등록
				smsCommonMapper.insertSmsSendMst(sendVO);
				
			}
		} catch(Exception e) {
			throw new MvnoServiceException("ERCP1001", e);
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
		
		int sendCount = 0;
		
		try {
			// 홈페이지 / 무선상품 가입자 대상
			List<MarketingVO> targetList = marketingMapper.getMarketingReSendTarget();
			LOGGER.info("마케팅동의 대상 total[{} 건 / {} sec]", targetList.size(), sw.getTotalTimeSeconds());
			
			String senderKey = propertiesService.getString("msg.kakao.ktSenderKey");
			String kNextType = propertiesService.getString("msg.kakao.ktKNextType");
			
			for(MarketingVO vo : targetList) {
				
				//20190730 kakao알림톡 추가
				vo.setSenderKey(senderKey);
				vo.setkNextType(kNextType);
				vo.setReqId(BatchConstants.BATCH_USER_ID);
				
				KtSmsCommonVO smsVO = new KtSmsCommonVO();
				
				//TMP ID
				smsVO = smsCommonMapper.getKtTemplateText(vo.getReserved02());
				
				smsVO.setMsgType("6");
				smsVO.setRcptData(vo.getRcptData());
				smsVO.setCallbackNum(vo.getCallbackNum());
				smsVO.setSubject(vo.getSubject());
				smsVO.setMessage(vo.getMessage());
				smsVO.setkMessage(vo.getMessage());
				smsVO.setScheduleTime(vo.getScheduleTime());
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02(vo.getReserved02());
				smsVO.setReserved03("MarketingReSchedule");
				smsVO.setMsgTypeSecond(kNextType);
				smsVO.setkSenderkey(senderKey);
				smsVO.setFailSend("Y");
				// SMS 발송
				sendCount += smsCommonMapper.insertKtMsgQueue(smsVO);
				
				vo.setMsgId(smsVO.getMsgId());
				// SMS발송내역 등록
				marketingMapper.updateSmsSendMst(vo);
				
			}
		} catch(Exception e) {
			throw new MvnoServiceException("ERCP1002", e);
		}
		sw.stop();
		LOGGER.info("마케팅동의 SMS 재발송 BATCH END!!!!! total[{} 건 / {} sec]", sendCount, sw.getTotalTimeSeconds());
		
		return sendCount;
	}
}
