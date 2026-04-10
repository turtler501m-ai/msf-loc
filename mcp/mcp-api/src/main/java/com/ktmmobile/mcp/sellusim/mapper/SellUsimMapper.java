package com.ktmmobile.mcp.sellusim.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.sellusim.dto.SellUsimDto;

@Mapper
public interface SellUsimMapper {

	/**
	 * 개통유심_판매 가능 여부 확인
	 * @param SellUsimDto
	 * @return int
	 * @throws Exception
	 */
	int selectCheckUsimRegCount(SellUsimDto sellUsimDto);
}
