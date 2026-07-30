package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.port.out;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpRequestOsst;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathSelfUrl;

public interface FaceAuthRepository {

    Integer registMcpRequestOsst(McpRequestOsst mcpRequestOsst);

    Integer copyMcpRequestOsstToMsf(String appEventCd, String resNo);

    Integer registFathSelfUrl(MsfFathSelfUrl msfFathSelfUrl);

    McpFathResultPush getMspFathResultPush(String transactionId);

    MsfFathResultPush getMsfFathResultPush(String resNo);

    Integer registMsfFathResultPushByMsp(McpFathResultPush mcpPush);

    Integer registMsfFathResultPush(MsfFathResultPush mcpPush);
}
