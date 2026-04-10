package com.ktmmobile.mcp.common.dao;

import java.util.HashMap;

import com.ktmmobile.mcp.common.dto.SnsLoginDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;

public interface SnsDao {

	SnsLoginDto selectSnsIdCheck(String snsId);

	UserSessionDto selectSnsMcpUser(String userId);

	int updateSnsLoginInfo(HashMap<String, String> param);

}
