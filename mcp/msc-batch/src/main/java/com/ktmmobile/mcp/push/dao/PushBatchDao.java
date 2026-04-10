package com.ktmmobile.mcp.push.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.push.dto.PushBatchVO;

@Repository
public class PushBatchDao {

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSessionTemplate bootoraSqlSession;

	/**
	 * 푸쉬 스케쥴 조회
	 * @param vo
	 * @return
	 */
	public List<PushBatchVO> getPushScheduleList(PushBatchVO vo) {
		return bootoraSqlSession.selectList("PushBatchMapper.getPushScheduleList", vo);
	}
	
	/**
	 * 푸쉬 발송 처리 대상자 카운트
	 */
	public int getPushSndProcCount(PushBatchVO vo) {
		return bootoraSqlSession.selectOne("PushBatchMapper.getPushSndProcCount", vo);
	}
	
	/**
	 * 푸쉬 발송 처리 대상자 등록
	 * @param vo
	 * @return
	 */
	public int mergePushSndRst(PushBatchVO vo){
		return bootoraSqlSession.update("PushBatchMapper.mergePushSndRst", vo);
	}

	/**
	 * 푸쉬 발송처리 등록
	 * @param vo
	 * @return
	 */
	public int insertPushSndResult(PushBatchVO vo){
		return bootoraSqlSession.insert("PushBatchMapper.insertPushSndResult", vo);
	}
	
	/**
	 * 푸쉬 발송 처리 갱신
	 */
	public int updatePushSndResult(PushBatchVO vo){
		return bootoraSqlSession.update("PushBatchMapper.updatePushSndResult", vo);
	}
	
	/**
	 * 정적 푸쉬 발송 처리 대상자 추출 등록
	 */
	public int insertPushTargetUserFind(PushBatchVO vo) {
		return bootoraSqlSession.insert("PushBatchMapper.insertPushTargetUserFind", vo);
	}
	
	/**
	 * 정적 푸쉬 발송 처리 기추출 대상자 등록
	 */
	public int insertPushTargetUserGet(PushBatchVO vo) {
		return bootoraSqlSession.insert("PushBatchMapper.insertPushTargetUserGet", vo);
	}
	
}
