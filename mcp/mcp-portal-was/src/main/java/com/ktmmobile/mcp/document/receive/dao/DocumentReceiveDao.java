package com.ktmmobile.mcp.document.receive.dao;

import com.ktmmobile.mcp.document.receive.dto.DocRcvDetailDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvItemDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvUrlOtpDto;

import java.util.List;
import java.util.Map;

public interface DocumentReceiveDao {

    DocRcvDetailDto getDocRcvDetail(String docRcvId);

    int increaseUrlOtpFailCount(DocRcvUrlOtpDto docRcvUrlOtpDto);

    int updateUrlOtpAuth(DocRcvUrlOtpDto docRcvUrlOtpDto);

    DocRcvUrlOtpDto getDocRcvUrlOtp(DocRcvUrlOtpDto docRcvUrlOtpDto);

    int insertDocRcvUrlOtp(DocRcvUrlOtpDto docRcvUrlOtpDto);

    List<DocRcvItemDto> getDocRcvItemList(String docRcvId);

    DocRcvUrlOtpDto getLastDocRcvUrlOtp(String rcvUrlId);

    int insertLogRequest(Map<String, Object> paramMap);
}
