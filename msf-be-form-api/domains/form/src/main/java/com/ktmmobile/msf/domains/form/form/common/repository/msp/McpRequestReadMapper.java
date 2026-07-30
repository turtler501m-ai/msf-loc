package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface McpRequestReadMapper {
    //int getResNoCount(String prgrStatCd);

    List<String> getResNoByMoveMobileNum(Map<String, Object> paramMap);

    int getPreCheckTryCnt(Map<String, Object> paramMap);

    // 명의변경 예약번호 조회
    String generateResNo();

    int selectMcpRequest(Long requestKey);

    int selectMcpRequestCstmr(Long requestKey);

    int selectMcpRequestAgent(Long requestKey);

    int selectMcpRequestReq(Long requestKey);

    int selectMcpRequestSaleinfo(Long requestKey);

    int selectMcpRequestMove(Long requestKey);

    int selectMcpRequestDvcChg(Long requestKey);

    int selectMcpRequestAddition(Long requestKey);
}

