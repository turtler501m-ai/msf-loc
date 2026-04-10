package com.ktmmobile.mcp.mypage.service;

import com.ktmmobile.mcp.mypage.dto.MaskingDto;


public interface MaskingSvc {

    int insertMaskingRelease(MaskingDto maskingDto);
    int insertMaskingReleaseHist(MaskingDto maskingDto);

}
