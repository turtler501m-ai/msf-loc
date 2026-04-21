package com.ktmmobile.msf.domains.commoncode.application.port.in;

import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodeGroups;

public interface CommonCodeReader {

    CommonCodeGroups getCommonCodes(CommonCodesRequest request);
}
