package com.ktmmobile.mcp.document.receive.service;

import com.ktmmobile.mcp.document.receive.dto.DocRcvDetailDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvItemDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvRequestDto;

import java.util.List;
import java.util.Map;

public interface DocumentReceiveService {
    DocRcvDetailDto getDocRcvDetail(String docRcvId);

    Map<String, Object> resendNewOtp(DocRcvRequestDto docRcvRequest);

    Map<String, Object> authenticateOtp(DocRcvRequestDto docRcvRequest);

    List<DocRcvItemDto> getDocRcvItemPendingList(String docRcvId);

    void insertLogRequest(Map<String, Object> paramMap);
}
