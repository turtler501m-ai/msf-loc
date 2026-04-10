package com.ktmmobile.mcp.apppush.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.apppush.dto.AppPushDto;

@Mapper
public interface AppPushMapper {

    int insertSendPushAll(AppPushDto appPushDto);

}
