package com.ktmmobile.mcp.mcash.service;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.mcash.dto.McashShopDto;
import com.ktmmobile.mcp.mcash.dto.McashUserDto;

import java.util.List;
import java.util.Map;

public interface McashService {

    Map<String, String> chkMcashUse();

    Map<String, String> getMcashJoinCondition(UserSessionDto userSession);

    List<McashUserDto> getMcashAvailableCntrList(String userid);

    String getMcashMainUrl(String userid);

    McashUserDto getMcashUserByUserid(String userid);

    McashUserDto getMcashJoinInfo(String userid);

    Map<String, String> joinMcashUser(String svcCntrNo);

    Map<String, String> changeMcashUser(String svcCntrNo);

    Map<String, String> cancelMcashUser(String userid, String evntTypeDtl);

    Map<String, Object> getRemainCash(String userid);

    boolean isMcashAuth();

    McashUserDto getMcashUserBySvcCntrNo(McashUserDto mcashDto);

    int getMcashJoinCnt(String userid);

    int getMcashMenuAccessCnt();

    List<McashShopDto> getShopDiscountRateList();
}