package com.ktmmobile.msf.domains.shared.form.common.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.AuthSmsRequest;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.form.common.application.port.in.CommonSmsWriter;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonSmsService implements CommonSmsWriter {

    /**
     * FIXME: MCP API SMS 발송 요청 및 로그 데이터 저장 로직 개발 필요
     * @param request
     * @return
     */
    @Override public Boolean sendAuthSms(AuthSmsRequest request) {
        return true;
    }

    /**
     * FIXME: 인증번호 검증 및 로그 데이터 저장 로직 개발 필요
     * @param request
     * @return
     */
    @Override public Boolean verifyAuthSms(AuthSmsRequest request) {
        return true;
    }

    /**
     * FIXME: MCP API SMS 발송 요청 및 로그 데이터 저장 로직 개발 필요
     * @param request
     * @return
     */
    @Override public Boolean sendFormSms(CommonSmsRequest request) {
        return true;
    }

    /**
     * FIXME: 인증번호 검증 및 로그 데이터 저장 로직 개발 필요
     * @param request
     * @return
     */
    @Override public Boolean verifyFormSms(CommonSmsRequest request) {
        return true;
    }
}
