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

}

