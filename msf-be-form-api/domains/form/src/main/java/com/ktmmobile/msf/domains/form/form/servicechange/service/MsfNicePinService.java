package com.ktmmobile.msf.domains.form.form.servicechange.service;

import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;

import java.util.Map;

public interface MsfNicePinService {

    /**
     * <pre>
     * 설명    : NICE PIN 고객 CI 조회
     * @param niceLogDto
     * </pre>
     */
    Map<String, String> getNicePinCi(NiceLogDto niceLogDto);

    /**
     * <pre>
     * 설명    : NICE PIN 고객 DI 조회
     * @param niceLogDto
     * </pre>
     */
    Map<String, String> getNicePinDi(NiceLogDto niceLogDto);

}
