package com.ktmmobile.mcp.common.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.dto.SnsLoginDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;

@Repository
public class SnsDaoImpl implements SnsDao{

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override
    public SnsLoginDto selectSnsIdCheck(String snsId){
        return sqlSessionTemplate.selectOne("SnsMapper.selectSnsIdCheck", snsId);
    }

	@Override
	public UserSessionDto selectSnsMcpUser(String userId) {
		return sqlSessionTemplate.selectOne("SnsMapper.selectSnsMcpUser", userId);
	}

	@Override
	public int updateSnsLoginInfo(HashMap<String, String> param) {
		return sqlSessionTemplate.update("SnsMapper.updateSnsLoginInfo", param);
	}
	
}
