package com.ktmmobile.msf.domains.cache.commoncode.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

public interface CommonCodeRepository {

    List<CommonCode> findMspCommonCodes();

    List<CommonCode> findMcpCommonCodes();

    List<CommonCode> findSmartFormCommonCodes();
}
