package com.ktmmobile.mcp.common.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.dto.SmartroDto;

@Repository
public class SmartroDaoImpl implements SmartroDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertSmartroOrder(SmartroDto smartroDto) {
		return sqlSessionTemplate.insert("SmartroMapper.insertSmartroOrder", smartroDto);
	}

	@Override
	public List<SmartroDto> getSmartroDataList(String orderno) {
		return sqlSessionTemplate.selectList("SmartroMapper.getSmartroDataList",orderno);
	}

}
