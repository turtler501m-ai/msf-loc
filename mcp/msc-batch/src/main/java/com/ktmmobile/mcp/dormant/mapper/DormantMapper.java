package com.ktmmobile.mcp.dormant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.dormant.dto.DormantDto;

@Mapper
public interface DormantMapper {

	List<DormantDto> selectDormantList();

	int deleteAssociateMember();

	List<DormantDto> selectDormantRegularList();

	int insertDormantRegularMember(String userid);

	int deleteDormantRegularMember(String userid);

}
