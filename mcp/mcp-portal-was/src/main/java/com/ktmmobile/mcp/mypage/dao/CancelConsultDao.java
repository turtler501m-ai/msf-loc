package com.ktmmobile.mcp.mypage.dao;

import java.util.List;

import com.ktmmobile.mcp.mypage.dto.CancelConsultDto;

public interface CancelConsultDao {

    int countCancelConsult(CancelConsultDto cancelConsultDto);

    int insertNmcpCustReqMst(CancelConsultDto cancelConsultDto);

    int insertCancelConsult(CancelConsultDto cancelConsultDto);

    List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto);

}
