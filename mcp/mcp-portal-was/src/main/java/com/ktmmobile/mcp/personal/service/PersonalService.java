package com.ktmmobile.mcp.personal.service;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.personal.dto.PersonalDto;

public interface PersonalService {

	PersonalDto getPersonalUrlInfo(PersonalDto personalDto);

	String sendPersonalSms(AuthSmsDto authSmsDto);

	McpUserCntrMngDto getPersonalUser(String pageType, String ncn);

}
