package com.ktmmobile.mcp.push.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.mcp.push.dao.PushBatchDao;
import com.ktmmobile.mcp.push.dto.PushBatchVO;

@Service
public class PushBatchService {
    private static final Logger logger = LoggerFactory.getLogger(PushService.class);
    @Value("${push.interface.server}")
    private String pushprxServer;

	@Autowired
	private PushBatchDao pushBatchDao;
	
	/**
	 * 푸쉬 스케줄 조회
	 */
	
	public List<PushBatchVO> getPushScheduleList(PushBatchVO vo) {
		return pushBatchDao.getPushScheduleList(vo);
	}
	
	/**
	 * 푸쉬 발송 처리 대상자 카운트
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int getPushSndProcCount(PushBatchVO vo) {
		return pushBatchDao.getPushSndProcCount(vo);
	}
	
	/**
	 * 푸쉬 발송 결과 업데이트
	 */
	@Transactional(rollbackFor = Exception.class)
	public int mergePushSndRst(PushBatchVO vo){
		return pushBatchDao.mergePushSndRst(vo);
	}
	
	/**
	 * 푸쉬 발송 처리 등록
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertPushSndResult(PushBatchVO vo){
		return pushBatchDao.insertPushSndResult(vo);
	}
	/**
	 * 푸쉬 발송 처리 갱신
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updatePushSndResult(PushBatchVO vo){
		return pushBatchDao.updatePushSndResult(vo);
	}
	/**
	 * 정적 푸쉬 발송 처리 대상자 추출 등록
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertPushTargetUserFind(PushBatchVO vo) {
		return pushBatchDao.insertPushTargetUserFind(vo);
	}
	
	/**
	 * 정적 푸쉬 발송 처리 기추출 대상자 등록
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertPushTargetUserGet(PushBatchVO vo) {
		return pushBatchDao.insertPushTargetUserGet(vo);
	}
}
