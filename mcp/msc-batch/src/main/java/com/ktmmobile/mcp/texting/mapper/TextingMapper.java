package com.ktmmobile.mcp.texting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.texting.dto.TextingDto;

@Mapper
public interface TextingMapper {

    List<TextingDto> selectTextingNoList(String startDt);


}
