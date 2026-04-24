package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.OsstStatChkMapper;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

@Service
public class OsstStatChkService extends BaseService {
	@Autowired
	private OsstStatChkMapper osstStatChkMapper;
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public int OsstStatChk() throws MvnoServiceException {
		int cnt = 0;
		
		try {
			LOGGER.info("OSST STATUS CHECK 시작");
			
			List<Map<String, Object>> resultList = osstStatChkMapper.getErrCdList();
						
			if (resultList.size() > 0) {
				StringBuffer sendMsg = new StringBuffer();
								
				for(Map<String, Object> rtMap : resultList) {
					String errCd = (String) rtMap.get("ERR_CD");
					String errMsg = osstStatChkMapper.getErrMsg(errCd);
					if (cnt != 0) {
						sendMsg.append("\n");
					}
					sendMsg.append(rtMap.get("ERR_CNT")+"건 ["+rtMap.get("ERR_CD")+"]오류발생-"+errMsg);
					cnt++;
				}
				
				List<String> sendList = osstStatChkMapper.getSendTargetInfo();
				
				for(String phoneNum : sendList) {
					KtSmsCommonVO smsVO = new KtSmsCommonVO();
					smsVO.setMsgType("2");
					smsVO.setRcptData(phoneNum);
					smsVO.setCallbackNum("18995000");
					smsVO.setSubject("OSST연동 상태 체크 알림");
					smsVO.setMessage(sendMsg.toString());
					smsVO.setReserved01("BATCH");
					smsVO.setReserved02("OsstStatChkSchedule");
					smsVO.setReserved03("SYSTEM");
					smsCommonMapper.insertKtMsgQueue(smsVO);
				}
			}
			
			
		} catch(Exception e) {
			throw new MvnoServiceException("EREQ1001", e);
		} finally {
			LOGGER.info("OSST STATUS CHECK 종료");
		}
		
		return cnt;
	}
		
}
