package com.ktmmobile.msf.domains.cache.commoncode.application.port.in;

import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;

public interface CommonCodeReader {

    CommonCodeGroups getCommonCodes(CommonCodesRequest request);
}
