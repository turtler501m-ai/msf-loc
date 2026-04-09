package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.JoinInfoReqDto;
import com.ktmmobile.msf.formComm.dto.JoinInfoResVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 가입자정보조회 서비스 인터페이스.
 */
public interface JoinInfoSvc {

    /**
     * 고객명 + 전화번호로 M전산 계약정보 조회 → Y04 인증 → X01 가입정보 조회.
     */
    JoinInfoResVO joinInfo(JoinInfoReqDto req, HttpServletRequest httpRequest);
}
