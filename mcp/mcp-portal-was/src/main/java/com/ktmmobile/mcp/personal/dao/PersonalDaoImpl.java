package com.ktmmobile.mcp.personal.dao;

import com.ktmmobile.mcp.personal.dto.PersonalDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonalDaoImpl implements PersonalDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public PersonalDto getPersonalUrl(PersonalDto personalDto) {
		return sqlSessionTemplate.selectOne("PersonalMapper.getPersonalUrl", personalDto);
	}

}
