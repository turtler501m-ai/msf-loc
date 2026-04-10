package com.ktmmobile.mcp.mypage.dao;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;

public interface MaskingDao {

    int insertMaskingRelease(MaskingDto maskingDto);
    int insertMaskingReleaseHist(MaskingDto maskingDto);

}
