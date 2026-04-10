package com.ktmmobile.mcp.join.dao;

import com.ktmmobile.mcp.common.dto.db.McpMrktHistDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.dto.UserAgentDto;

@Repository
public class JoinDaoImpl implements JoinDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 아이디 중복 체크
	 */
	@Override
	public int idCheck(String checkId) {
		return (Integer)sqlSessionTemplate.selectOne("JoinMemberMapper.duplicateId", checkId);
	}

	/**
	 * 회원가입 실행
	 */
	@Override
	public int insertMemberInfo(JoinDto joinDto) {
		return (Integer)sqlSessionTemplate.insert("JoinMemberMapper.inserMember", joinDto);
	}

	/**
	 * 중복가입 여부 확인
	 */
	@Override
	public int dupleChk(String pin) {
		int rst = (Integer)sqlSessionTemplate.selectOne("JoinMemberMapper.dupleChk", pin);
		return rst;
	}

	@Override
	public int insertSnsInfo(JoinDto joinDto) {
		return (Integer)sqlSessionTemplate.insert("JoinMemberMapper.insertSnsInfo", joinDto);
	}

	@Override
	public JoinDto getPinToUserInfo(String pin) {
		return sqlSessionTemplate.selectOne("JoinMemberMapper.getPinToUserInfo", pin);
	}
	
	@Override
	public JoinDto getUserToPinInfo(String userId) {
		return sqlSessionTemplate.selectOne("JoinMemberMapper.getUserToPinInfo", userId);
	}

	@Override
	public int dormancyDupleChk(String pin) {
		return (Integer)sqlSessionTemplate.selectOne("JoinMemberMapper.dormancyDupleChk", pin);
	}
	
    @Override
    public boolean insetUserAgent(UserAgentDto userAgent) {
        return     0 < sqlSessionTemplate.insert("JoinMemberMapper.insetUserAgent",userAgent);
    }

	@Override
	public int updateClausePriAdFlag(JoinDto joinDto) {
		return sqlSessionTemplate.update("JoinMemberMapper.updateClausePriAdFlag", joinDto);
	}

	@Override
	public void updateMrktHist(McpMrktHistDto mrktHist) {
		sqlSessionTemplate.update("JoinMemberMapper.updauteMrktHistAree", mrktHist);
	}

	@Override
	public void insertMrktHist(McpMrktHistDto mcpMrktHistDto) {
		sqlSessionTemplate.insert("JoinMemberMapper.insertMrktHist", mcpMrktHistDto);
	}
}
