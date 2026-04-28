package com.ktmmobile.msf.domains.shared.common.sms.application.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.exception.InvalidValueException;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.MspSmsData;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.in.CommonSmsWriter;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.out.SmsRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonSmsService implements CommonSmsWriter {

    private final SmsRepository smsRepository;

    /**
     * 일반 SMS 발송
     * FIXME: MCP API SMS 발송 요청 개발 필요
     *
     * <pre>
     * - type:
     *   - 설명: 첫번째 - [F: 스마트서식지, A: 관리자]
     *          두번째 - [0: 기타, 1: 신규/변경, 2: 서비스변경, 3: 명의변경, 4: 서비스해지]
     *          세번째 - [OTP: SMS 번호인증, FTH: 안면인증, VDP: 법정대리인 인증, CMP: 신청완료, DLN: 다운로드, ESY: 간편신청서]
     *   - 사용 type:
     *     . F-1-FTH: 스마트서식지 신규/변경 안면인증 URL 전송
     *     . F-1-CMP: 스마트서식지 신규/변경 접수완료 신청서 URL 전송
     *     . F-2-CMP: 스마트서식지 서비스변경 접수완료 신청서 URL 전송
     *     . F-3-CMP: 스마트서식지 명의변경 접수완료 신청서 URL 전송
     *     . F-4-CMP: 스마트서식지 서비스해지 접수완료 신청서 URL 전송
     *     . F-1-DLN: 스마트서식지 신규/변경 APP 다운로드 URL 전송
     *     . F-2-DLN: 스마트서식지 서비스변경 APP 다운로드 URL 전송
     *     . F-3-DLN: 스마트서식지 명의변경 APP 다운로드 URL 전송
     *     . F-4-DLN: 스마트서식지 서비스해지 APP 다운로드 URL 전송
     *     . A-0-ESY: 관리자 간편신청서 URL 발송 (대리점관리자)
     * </pre>
     *
     * @param request
     * @return
     */
    @Override public Boolean sendSms(CommonSmsRequest request) {
        if (
            !StringUtils.hasText(request.type()) ||
            !StringUtils.hasText(request.path()) ||
            !StringUtils.hasText(request.phone())
        ) {
            throw new InvalidValueException("잘못된 접근입니다.");
        }

        /*
         * 테이블:
         *   - AM2X_SUBMIT
         * SMS 발송 데이터
         * MSG_ID:
         *   - AM2X_SUBMIT_SEQ.NEXTVAL
         * MSG_TYPE:
         *   - 1 (SMS)
         *   - 2 (LMS)
         * SUBJECT:
         *   - MSG_TYPE = 1: NULL
         *   - MSG_TYPE = 2: [제목]
         * SCHEDULE_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         * SUBMIT_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         * MESSGAE: [메세지]
         * CALLBACK_NUM: 18995000 (콜센터 대표번호)
         * RCPT_DATA: [휴대폰번호]
         * K_ADFLAG: 'N'
         * RESERVED01: 'MSF'
         * RESERVED02: [사용구분값]
         * RESERVED03: [사용자ID]
         */

        return true;
    }

    /**
     * 인증번호 SMS 발송
     * FIXME: MCP API SMS 발송 요청 및 로그 데이터 저장 로직 개발 필요
     *
     * <pre>
     * - type:
     *   - 설명: 첫번째 - [F: 스마트서식지, A: 관리자]
     *          두번째 - [0: 기타, 1: 신규/변경, 2: 서비스변경, 3: 명의변경, 4: 서비스해지]
     *          세번째 - [OTP: SMS 번호인증, FTH: 안면인증, VDP: 법정대리인 인증, CMP: 신청완료, DLN: 다운로드, ESY: 간편신청서]
     *   - 사용 type:
     *     . F-0-OTP: 스마트서식지 SMS 번호인증
     *     . F-1-VDP: 스마트서식지 신규/변경 법정대리인 인증
     *     . F-2-VDP: 스마트서식지 서비스변경 법정대리인 인증
     *     . F-3-VDP: 스마트서식지 명의변경 법정대리인 인증
     *     . F-4-VDP: 스마트서식지 서비스해지 법정대리인 인증
     *     . A-0-OTP: 관리자 SMS 번호인증 (대시점관리자, 시스템관리자)
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

        // 스마트서식지 신규/변경, 서비스변경, 명의변경, 서비스해지에서 사용하는 발송 요청인 경우에
        // 휴대폰번호가 반드시 필요
        if (request.type().matches("^F-[1234]-.*") && !StringUtils.hasText(request.phone())) {
            throw new InvalidValueException("잘못된 접근입니다.");
        }

        String phoneNumber = request.phone();
        String reserved03 = "nonMember";
        if (!StringUtils.hasText(phoneNumber)) {
            // 로그인 사용자 토큰을 통한 사용자 정보 중 휴대폰번호 추출
            MsfUser user = AuthenticationUtils.getUser();
            phoneNumber = smsRepository.getUserPhone(user.getId());
            reserved03 = user.getId();
        }
        phoneNumber = phoneNumber.replace("[^0-9]", "");

        String authNumber = "";
        try {
            StringBuilder randomAuthNumber = new StringBuilder();
            Random objRandom = SecureRandom.getInstance("SHA1PRNG");

            for (int i = 0; i < 6; i++) {
                randomAuthNumber.append(objRandom.nextInt(10));
            }

            authNumber = randomAuthNumber.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new SimpleDomainException("서비스 처리중 오류가 발생 하였습니다.");
        }
        String sendingMessage = "인증번호는 " + authNumber + " 입니다.";

        /*
         * 테이블:
         *   - AM2X_SUBMIT
         * SMS 발송 데이터
         * MSG_ID:
         *   - SELECT AM2X_SUBMIT_SEQ.NEXTVAL FROM DUAL
         * MSG_TYPE:
         *   - 1 (SMS)
         *   - 2 (LMS)
         * SUBJECT:
         *   - MSG_TYPE = 1: NULL
         *   - MSG_TYPE = 2: [제목]
         * SCHEDULE_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         * SUBMIT_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         * MESSGAE: [메세지]
         * CALLBACK_NUM: 18995000 (콜센터 대표번호)
         * RCPT_DATA: [휴대폰번호]
         * K_ADFLAG: 'N'
         * RESERVED01: 'MSF'
         * RESERVED02: [사용구분값]
         * RESERVED03: [사용자ID]
         */
        MspSmsData data = MspSmsData.builder()
            .msgType(1)
            .message(sendingMessage)
            .callbackNum("18995000")
            .rcptData(phoneNumber)
            .kAdflag("N")
            .reserved01("MSF")
            .reserved02(request.type())
            .reserved03(reserved03).build();
        log.debug("smsData: {}", data);
        smsRepository.registerSmsInfo(data);

        String savedKey = request.type() + ":" + UUID.randomUUID();
        // FIXME: 생서한 인증번호 Redis 서버 저장 로직 추가

        return savedKey;
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
