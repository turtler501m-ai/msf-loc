package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import com.ktmmobile.msf.domains.form.common.dto.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AbuseImeiHistDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FormCommWriteMapper {

    //부정사용 주장 저장
    void insertAbuseImeiHist(AbuseImeiHistDto abuseImeiHistDto);

    //MCP_REQUEST INSERT OR UPDATE :: 신규가입 희망번호 조회
    boolean mergeMcpRequest(McpRequestDto request);

    //MCP_REQUEST_OSST 저장 :: 신규가입 희망번호 예약
    boolean insertMcpRequestOsst(McpRequestOsstDto request);

}
