package com.ktmmobile.msf.domains.commoncode.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

public interface CommonCodeRepository {

    List<CommonCode> findAllCommonCodes();
}
