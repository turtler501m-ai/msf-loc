package com.ktmmobile.msf.domains.shared.common.sms.application.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.in.CommonSmsWriter;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.out.SmsRepository;
import com.ktmmobile.msf.domains.shared.common.sms.domain.code.CommonSmsType;
import com.ktmmobile.msf.domains.shared.common.sms.domain.code.StepEndStatus;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.IdVerifValidationDetail;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.MspSmsData;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.SmsSendedOtpData;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonSmsService implements CommonSmsWriter {

    private final SmsRepository smsRepository;
    private final CacheService<SmsSendedOtpData> cacheService;

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
    @Override
    @Transactional
    public Boolean sendSms(CommonSmsRequest request) {
        if (
            !StringUtils.hasText(request.path()) ||
            !StringUtils.hasText(request.phone())
        ) {
            throw new InvalidValueException("잘못된 접근입니다.");
        }

        String userId = "nonMember";
        String url = "";
        if (CommonSmsType.F_1_FTH.equals(request.type())) {
            url = "https://";
        }
        saveMspSmsData(request.phone(), request.type().getMessage(request.value(), request.name(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시mm분")), url), request.type().getCode(), userId);

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
    @Override
    @Transactional
    public String sendOtpSms(CommonSmsRequest request) {
        if (
            !StringUtils.hasText(request.path())
        ) {
            throw new InvalidValueException("잘못된 접근입니다.: " + request.path());
        }

        // 스마트서식지 신규/변경, 서비스변경, 명의변경, 서비스해지에서 사용하는 발송 요청인 경우에
        // 휴대폰번호가 반드시 필요
        if (request.type().getCode().matches("^F-[1234]-.*") && (!StringUtils.hasText(request.phone()) || !StringUtils.hasText(request.name()))) {
            throw new InvalidValueException("잘못된 접근입니다.: " + request.type().getCode() + ", " + request.phone() + ", " + request.name());
        }
        if (CommonSmsType.F_0_OTP.equals(request.type()) && !StringUtils.hasText(request.token())) {
            throw new InvalidValueException("잘못된 접근입니다.: " + request.type().getCode() + ", " + request.token());
        }

        String phoneNumber = request.phone();
        String userId = "nonMember";
        String userName = request.name();
        if (!StringUtils.hasText(phoneNumber)) {
            // 로그인 사용자 토큰을 통한 사용자 정보 중 휴대폰번호 추출
            MsfUser user = AuthenticationUtils.getUser();
            phoneNumber = smsRepository.getUserPhone(user.getId());
            userId = user.getId();
            userName = user.getName();
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

        IdVerifValidationDetail idVerifValidationDetail = IdVerifValidationDetail.builder()
            .moduTypeCd(request.type().getCode())
            .compTypeCd("C")
            .stepEndYn(StepEndStatus.N)
            .veriRsltSbst("I")
            .referer(request.type().getCode())
            .authNm(userName)
            .mobileNo(phoneNumber)
            .requestKey(0L)
            .reqSeq(0L)
            .resSeq(0L)
            .rip(RequestUtils.getClientIp())
            .urlTypeCd(request.type().getCode() + "-SND")
            .build();

        smsRepository.registerMsfCrtVldDtl(idVerifValidationDetail);

        saveMspSmsData(phoneNumber, request.type().getMessage(authNumber), request.type().getCode(), userId);

        String savedKey = request.type().getCode() + ":" + UUID.randomUUID();

        SmsSendedOtpData sendedData = SmsSendedOtpData.builder()
            .key(idVerifValidationDetail.getCrtVldDtlSeq())
            .phone(phoneNumber)
            .type(request.type().getCode())
            .path(request.path())
            .name(userName)
            .token(request.token())
            .value(authNumber)
            .build();
        cacheService.setValue(savedKey, sendedData, Duration.ofMinutes(3));

        return savedKey;
    }

    /**
     * 입력된 인증번호 검증
     * FIXME: 인증번호 검증 및 로그 데이터 저장 로직 개발 필요
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Boolean verifyOtpSms(CommonSmsRequest request) {
        if (
            !StringUtils.hasText(request.token()) ||
            !StringUtils.hasText(request.value())
        ) {
            throw new InvalidValueException("잘못된 접근입니다.");
        }

        // 1. token을 통한 Redis 데이터 조회
        SmsSendedOtpData sendedData = cacheService.getValue(request.token());

        // 2. 인증 번호 추출 및 value 값 비교
        if (!request.value().equals(sendedData.getValue())) {
            throw new InvalidValueException("입력한 인증번호가 일치하지 않습니다.");
        }

        // 3. MSF_CRT_VLD_DTL 테이블 등록
        IdVerifValidationDetail idVerifValidationDetail = IdVerifValidationDetail.builder()
            .crtVldDtlSeq(sendedData.getKey())
            .moduTypeCd(request.type().getCode())
            .compTypeCd("D")
            .stepEndYn(StepEndStatus.Y)
            .veriRsltSbst("Y")
            .referer(request.type().getCode())
            .authNm(sendedData.getName())
            .mobileNo(sendedData.getPhone())
            .requestKey(0L)
            .reqSeq(0L)
            .resSeq(0L)
            .rip(RequestUtils.getClientIp())
            .urlTypeCd(request.type().getCode() + "-CHK")
            .build();

        smsRepository.registerMsfCrtVldDtl(idVerifValidationDetail);

        return true;
    }

    /**
     * SMS 발송 데이터 DB 저장
     *
     * @param rcptData 휴대폰번호
     * @param message 발송메세지
     * @param reserved02 발송형식
     * @param reserved03 사용자ID
     * @return
     */
    private void saveMspSmsData(String rcptData, String message, String reserved02, String reserved03) {
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
        MspSmsData data = MspSmsData.builder()
            .msgType(1)
            .message(message)
            .callbackNum("18995000")
            .rcptData(rcptData)
            .kAdflag("N")
            .reserved01("MSF")
            .reserved02(reserved02)
            .reserved03(reserved03).build();
        smsRepository.registerSmsInfo(data);
    }

}
