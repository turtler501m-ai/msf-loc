package com.ktmmobile.mcp.log.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.log.dto.BathHistDto;

@Repository
public class BathHistDao {

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSession bootoraSqlSession;

	@Autowired
	@Qualifier(value="booteventSqlSession")
	private SqlSession booteventSqlSession;

	public void insertHist(BathHistDto bathHistDto) {
		bootoraSqlSession.insert("BatchHistMapper.insertHist", bathHistDto);

	}

}
