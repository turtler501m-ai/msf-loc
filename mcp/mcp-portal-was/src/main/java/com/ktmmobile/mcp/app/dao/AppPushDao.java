package com.ktmmobile.mcp.app.dao;

import com.ktmmobile.mcp.app.dto.PushBaseVO;

public interface AppPushDao {

	/**
	 * 설명 : 푸시 정보 조회
	 * @Author : 박정웅
	 * @Date : 2021.12.30
	 * @param pushSndSetSno
	 * @return
	 */
	PushBaseVO selectPushSetupBase(String pushSndSetSno);

	/**
	 * 설명 : 푸시 정보 저장
	 * @Author : 박정웅
	 * @Date : 2021.12.30
	 * @param vo
	 * @return
	 */
	int insertPushTargetUserSnd(PushBaseVO vo);

}
