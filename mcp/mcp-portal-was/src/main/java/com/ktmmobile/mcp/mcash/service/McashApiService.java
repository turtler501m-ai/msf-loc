package com.ktmmobile.mcp.mcash.service;

import com.ktmmobile.mcp.mcash.dto.McashApiReqDto;
import com.ktmmobile.mcp.mcash.dto.McashApiResDto;

public interface McashApiService {

    McashApiResDto syncUserInfo(McashApiReqDto requestDto);

    McashApiResDto getRemainCash(McashApiReqDto requestDto);

}