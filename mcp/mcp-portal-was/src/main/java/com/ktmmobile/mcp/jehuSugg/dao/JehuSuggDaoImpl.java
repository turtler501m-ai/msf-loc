package com.ktmmobile.mcp.jehuSugg.dao;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.jehuSugg.dto.JehuSuggDto;

@Repository
public class JehuSuggDaoImpl implements JehuSuggDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertJehuSuggAjax(JehuSuggDto jehuSuggDto) {
		return sqlSessionTemplate.insert("JehuSuggMapper.insertJehuSuggAjax", jehuSuggDto);
	}
}
