package com.ktmmobile.msf.domains.shared.form.common.application.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.InvalidValueException;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.form.common.application.port.in.CommonSmsWriter;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonSmsService implements CommonSmsWriter {

    /**
     * 일반 SMS 발송
     * FIXME: MCP API SMS 발송 요청 개발 필요
     *
     * <pre>
     * - type:
     *   - form 신규/변경 안면인증 URL 전송: form-faceurl
     *   - form 신규/변경 접수완료 APP URL 전송: form-app
     *   - admin 간편신청서 URL 발송: admin-simpleform (대리점관리자)
     * </pre>
     *
     * @param request
     * @return
     */
    @Override public Boolean sendSms(CommonSmsRequest request) {
        if (
            !StringUtils.hasText(request.type()) ||
            !StringUtils.hasText(request.path())
        ) {
            throw new InvalidValueException("잘못된 접근입니다.");
        }
        return true;
    }
    /**
     * 인증번호 SMS 발송
     * FIXME: MCP API SMS 발송 요청 및 로그 데이터 저장 로직 개발 필요
     *
     * <pre>
     * - type:
     *   - form 로그인 인증: form-otp
     *   - form 신규/변경 휴대폰인증: form-newchange-legalagent
     *   - form 서비스변경 휴대폰인증: form-servicechange-legalagent
     *   - form 명의변경 휴대폰인증: form-ownerchange-legalagent
     *   - form 서비스해지 휴대폰인증: form-termination-legalagent
     *   - admin 로그인 인증: admin-otp (대리점관리자, 시스템관리자에서 사용)
     * </pre>
     *
     * @param request
     * @return
     */
    @Override public String sendOtpSms(CommonSmsRequest request) {
        if (
            !StringUtils.hasText(request.type()) ||
            !StringUtils.hasText(request.path())
        ) {
            throw new InvalidValueException("잘못된 접근입니다.");
        }

        return request.type() + ":" + UUID.randomUUID();
    }

    /**
     * 입력된 인증번호 검증
     * FIXME: 인증번호 검증 및 로그 데이터 저장 로직 개발 필요
     *
     * @param request
     * @return
     */
    @Override public Boolean verifyOtpSms(CommonSmsRequest request) {
        if (
            !StringUtils.hasText(request.type()) ||
            !StringUtils.hasText(request.path()) ||
            !StringUtils.hasText(request.token()) ||
            !StringUtils.hasText(request.value())
        ) {
            throw new InvalidValueException("잘못된 접근입니다.");
        }
        return true;
    }
}
