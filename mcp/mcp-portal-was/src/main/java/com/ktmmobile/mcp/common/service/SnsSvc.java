package com.ktmmobile.mcp.common.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.dto.SnsLoginDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;

public interface SnsSvc {

	SnsLoginDto selectSnsIdCheck(String snsId);

	UserSessionDto selectSnsMcpUser(String userId);

	String snsLoginProcess(HttpServletRequest request, UserSessionDto userSessionDto, String snsId);

	int updateSnsLoginInfo(HashMap<String, String> param);

}
