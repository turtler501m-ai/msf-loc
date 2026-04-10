package com.ktmmobile.mcp.mcash.mapper;

import com.ktmmobile.mcp.mcash.dto.McashUserDto;

import java.util.List;

public interface McashMapper {
    List<McashUserDto> getMcashAvailableCntrList(String userid);

    McashUserDto getMcashJoinInfo(String userid);

    McashUserDto getUserInfoBySvcCntrNo(String svcCntrNo);
}