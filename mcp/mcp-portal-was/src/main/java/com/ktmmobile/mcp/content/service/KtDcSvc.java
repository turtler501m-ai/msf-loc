package com.ktmmobile.mcp.content.service;

import java.util.List;

import com.ktmmobile.mcp.content.dto.KtDcDto;

public interface KtDcSvc {

    public List<KtDcDto> selectRateNmList(KtDcDto ktDcDto);
    public KtDcDto selectKtDc(KtDcDto ktDcDto);
    int insertKtDc(KtDcDto ktDcDto);

}
