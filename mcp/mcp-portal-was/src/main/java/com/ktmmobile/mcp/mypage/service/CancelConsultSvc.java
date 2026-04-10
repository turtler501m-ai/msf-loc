package com.ktmmobile.mcp.mypage.service;

import java.util.List;

import com.ktmmobile.mcp.mypage.dto.CancelConsultDto;

public interface CancelConsultSvc {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    String cancelConsultRequest(CancelConsultDto cancelConsultDto);

    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);
}
