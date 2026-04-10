package com.ktmmobile.mcp.mcash.dao;

import com.ktmmobile.mcp.mcash.dto.McashUserDto;

import java.util.List;

public interface McashDao {

    McashUserDto getMcashUserByUserid(String userid);

    McashUserDto getMcashUserByCustomerId(String customerId);

    McashUserDto getMcashUserBySvcCntrNo(McashUserDto mcashDto);

    McashUserDto getUserInfoBySvcCntrNo(String svcCntrNo);

    List<McashUserDto> getMcashAvailableCntrList(String userid);

    McashUserDto getMcashJoinInfo(String userid);

    boolean insertMcashUserInfo(McashUserDto mcashUserDto);

    boolean updateMcashUserInfo(McashUserDto mcashUserDto);

    boolean insertMcashUserHist(McashUserDto mcashUserDto);

    int getMcashJoinCnt(String userid);

    int getMcashMenuAccessCnt();
}