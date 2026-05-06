package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import com.ktmmobile.msf.domains.form.form.common.dto.McpRequestOsstRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FormCommReadMapper {

    String generateResNo();

    long generateRequestKey();

    long getCustRequestSeq();

    //@@삭제필요@@
    AgentInfoResponse selectAgentInfo2(AgentInfoRequest request);

    List<AgentInfoResponse> selectAgentInfo(AgentInfoRequest request);

    int selectOsstCount(McpRequestOsstRequest request);

    String selectOsstOrdNo(McpRequestOsstRequest request);


}
