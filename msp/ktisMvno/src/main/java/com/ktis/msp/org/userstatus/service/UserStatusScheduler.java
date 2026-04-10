package com.ktis.msp.org.userstatus.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class UserStatusScheduler {
	
	private final transient Log LOGGER = LogFactory.getLog(getClass());
	
//	@Autowired
//	private SchdMgmtMapper schdMgmtMapper;
	
	@Autowired
	private UserStatusService userStatusService;
	
//	private ClsAcntConfig config;
	
	public void executeJob() {
		
		LOGGER.debug("UserStatusScheduler START!!");
		
		// 배치 시작 로그
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 로그생성
//		param.put("SCHD_ID", "SCHD000021"); /* 사용자 상태(정지) 변경 */
//		param.put("SRL_NUM", schdMgmtMapper.getSchdLogSeq());
//		param.put("PROC_STAT_CD", config.SCHD_PROC_STAT_ING);
		
//		schdMgmtMapper.insertClsSchdLog(param);
		
		// 배치 실행
		try {
			param = userStatusService.callUserStopStatusProc(param);
		} catch(Exception e) {
			LOGGER.debug("param=" + param);
		}
		
		// 배치 종료 로그
//		schdMgmtMapper.updateClsSchdLog(param);
		
	}
	
}
