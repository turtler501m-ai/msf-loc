package com.ktmmobile.mcp.content.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.content.dto.McpReqCombineDto;

@Repository
public class MyCombinationDaoImpl implements MyCombinationDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
	public boolean insertMcpReqCombine(McpReqCombineDto mcpReqCombineDto) {

		return 0 < sqlSessionTemplate.insert("MyCombinationMapper.insertMcpReqCombine", mcpReqCombineDto);
	}

	@Override
	public List<McpReqCombineDto> selectMcpReqCombine(McpReqCombineDto mcpReqCombineDto) {

		return sqlSessionTemplate.selectList("MyCombinationMapper.selectMcpReqCombine", mcpReqCombineDto);
	}

}
