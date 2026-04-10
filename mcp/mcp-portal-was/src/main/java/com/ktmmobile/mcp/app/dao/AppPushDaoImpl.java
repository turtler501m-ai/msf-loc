package com.ktmmobile.mcp.app.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.app.dto.PushBaseVO;


@Repository
public class AppPushDaoImpl implements AppPushDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


	/**
	 * 설명 : 푸시 정보 조회
	 * @Author : 박정웅
	 * @Date : 2021.12.30
	 * @param pushSndSetSno
	 * @return
	 */
    @Override
	public PushBaseVO selectPushSetupBase(String pushSndSetSno) {
		return sqlSessionTemplate.selectOne("AppMapper.selectPushSetupBase", pushSndSetSno);
	}

	/**
	 * 설명 : 푸시 정보 저장
	 * @Author : 박정웅
	 * @Date : 2021.12.30
	 * @param vo
	 * @return
	 */
	@Override
	public int insertPushTargetUserSnd(PushBaseVO vo) {
		return sqlSessionTemplate.insert("AppMapper.insertPushTargetUserSnd", vo);
	}

}
