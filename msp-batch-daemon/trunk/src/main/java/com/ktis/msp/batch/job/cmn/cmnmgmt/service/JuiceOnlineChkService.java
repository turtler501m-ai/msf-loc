package com.ktis.msp.batch.job.cmn.cmnmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.cmnmgmt.mapper.JuiceOnlineChkMapper;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

@Service
public class JuiceOnlineChkService extends BaseService {
	@Autowired
	private JuiceOnlineChkMapper juiceOnlineChkMapper;
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public int JuiceOnlineChk() throws MvnoServiceException {
		int cnt = 0;
		
		try {
			LOGGER.info("JUICE ONLINE CHECK 시작");
			
			cnt = juiceOnlineChkMapper.getJuiceOnlineCnt();
						
			if (cnt == 0) {
				
				List<String> sendList = juiceOnlineChkMapper.getSendTargetInfo();
				
				for(String phoneNum : sendList) {
					KtSmsCommonVO smsVO = new KtSmsCommonVO();
					smsVO.setMsgType("2");
					smsVO.setRcptData(phoneNum);
					smsVO.setCallbackNum("18995000");
					smsVO.setSubject("JUICE연동 상태 체크 알림");
					smsVO.setMessage("JUICE 전문 처리 건수가 0건 입니다. 확인 바랍니다.");
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02("JuiceOnlineChkSchedule");
					smsVO.setReserved03("SYSTEM");
					smsCommonMapper.insertKtMsgQueue(smsVO);
				}
			}
			
			
		} catch(Exception e) {
			throw new MvnoServiceException("EREQ1001", e);
		} finally {
			LOGGER.info("JUICE ONLINE CHECK 종료");
		}
		
		return cnt;
	}
		
}
