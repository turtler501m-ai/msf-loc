package com.ktmmobile.msf.domains.shared.form.common.faceauth.adapter.repository.mybatis.msp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpRequestOsst;

@Mapper
public interface FaceAuthMcpMapper {

    Integer insertMcpRequestOsst(McpRequestOsst  mcpRequestOsst);

    McpRequestOsst selectMcpRequestOsst(@Param("appEventCd") String appEventCd, @Param("resNo") String resNo);

    McpFathResultPush selectMcpFathResultPush(String transactionId);
}
