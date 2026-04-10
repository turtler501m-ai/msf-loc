package com.ktmmobile.mcp.mypage.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegSvcDaoImpl implements RegSvcDao{
	
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
	
    @Override
    public List<String> getRoamCdList() {
        return sqlSessionTemplate.selectList("RegSvcMapper.getRoamCdList");
    }
    
}