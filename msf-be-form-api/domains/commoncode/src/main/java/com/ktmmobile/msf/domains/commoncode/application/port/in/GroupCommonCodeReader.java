package com.ktmmobile.msf.domains.commoncode.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodeResponse;
import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesResponse;

public interface GroupCommonCodeReader {

    List<CommonCodeResponse> getCommonCodes(String groupId);

    CommonCodesResponse getCommonCodes(List<String> groupIds);
}
