package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.SmsRsvSendMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class SmsRsvSendService extends BaseService {
	
	@Autowired
	private SmsRsvSendMapper smsRsvSendMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
		
	/*
	 * 1. 대량문자예약발송 대상 추출
	 * 2. AM2X_SUBMIT INSERT
	 * 3. 대량문자예약발송 처리완료 저장
	 */
	@Transactional(rollbackFor=Exception.class)
	public int smsRsvSend() throws MvnoServiceException {
		
		int cnt = 0;
		
		try {
			// SMS 재발송 대상 추출
			cnt = smsRsvSendMapper.isSmsRsvList();
			int sendCnt = 0;
			int reqCnt = 0;
			
			KtSmsCommonVO vo = new KtSmsCommonVO();
			
			if (cnt == 0) {
				LOGGER.info("[대량문자예약발송] 처리대상없음");
				return sendCnt;
				
			} else if (cnt == 1) {
				vo = smsRsvSendMapper.selectSmsRsvInfo();
				if("Y".equals(vo.getKakaoYn()) || vo.getKakaoYn() == "Y"){
					vo.setMsgTypeSecond(propertiesService.getString("msg.kakao.ktKNextType"));
					vo.setkSenderkey(propertiesService.getString("msg.kakao.ktSenderKey"));
					sendCnt = smsRsvSendMapper.insertKakaoRsvSendList(vo);
				}else{
					sendCnt = smsRsvSendMapper.insertSmsRsvSendList(vo);
				}
				
				reqCnt = Integer.parseInt(vo.getSendCnt());
				
				if (sendCnt == reqCnt) {
					vo.setResultYn("Y");
					vo.setResultCode("0000");
					LOGGER.info("[대량문자예약발송] MSG_NO:"+vo.getMsgNo()+", "+sendCnt+"건 처리완료");
					
				} else {
					vo.setResultYn("E");
					vo.setResultCode("0001"); // 전송요청건수 불일치
					LOGGER.error("[대량문자예약발송] 건수불일치(MSG_NO:"+vo.getMsgNo()+", 건수:"+sendCnt+"/"+reqCnt+")");
					
				}
				
				smsRsvSendMapper.updateSmsRsvInfo(vo);
				
			} else {
				vo = new KtSmsCommonVO();
				vo.setResultYn("E");
				vo.setResultCode("0002"); // 대상 건 중복 오류
				LOGGER.error("[대량문자예약발송] 중복등록확인(대상건수:"+cnt+")");

				smsRsvSendMapper.updateSmsRsvErrInfo(vo);
				
			}
			
			
		} catch(Exception e) {
			KtSmsCommonVO vo = new KtSmsCommonVO();
			vo.setResultYn("E");
			vo.setResultCode("9999");
			LOGGER.error("[대량문자예약발송] SYSTEM ERROR");
			
			smsRsvSendMapper.updateSmsRsvErrInfo(vo);
			
			throw new MvnoServiceException("ERCP2017", e);
			
		}
		
		return cnt;
	}
	
}
