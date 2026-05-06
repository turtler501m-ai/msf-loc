package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface McpRequestReadMapper {
    //int getResNoCount(String prgrStatCd);

    List<String> getResNoByMoveMobileNum(Map<String, Object> paramMap);

    int getPreCheckTryCnt(Map<String, Object> paramMap);

}

