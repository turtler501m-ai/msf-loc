package com.ktmmobile.msf.domains.shared.common.sms.application.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.common.messagesender.support.property.MessageSenderProperties;
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorStatus;
import com.ktmmobile.msf.commons.logincore.support.context.LoginSessionContext;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsResponse;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.in.CommonSmsWriter;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.out.SmsRepository;
import com.ktmmobile.msf.domains.shared.common.sms.domain.code.CommonSmsType;
import com.ktmmobile.msf.domains.shared.common.sms.domain.code.StepEndStatus;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.IdVerifValidationDetail;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.MspSmsData;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.SmsSendedOtpData;
import com.ktmmobile.msf.domains.shared.common.sms.support.property.CommonSmsProperties;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonSmsService implements CommonSmsWriter {

    private static final int AUTH_NUMBER_LENGTH = 6;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final String RESERVED01 = "MSF";
    private static final String CALLCENTER = "18995000";

    private final MessageSenderProperties messageSenderProperties;
    private final CommonSmsProperties commonSmsProperties;
    private final SmsRepository smsRepository;
    private final CacheService<SmsSendedOtpData> cacheService;
    private final LoginSessionFlowProcessor loginSessionFlowProcessor;

    /**
     * 일반 SMS 발송
     *
     * <pre>
     * - type:
     *   - 설명: 첫번째 - [F: 스마트서식지, A: 관리자]
     *          두번째 - [0: 기타, 1: 신규/변경, 2: 서비스변경, 3: 명의변경, 4: 서비스해지]
     *          세번째 - [OTP: SMS 번호인증, FTH: 안면인증, VDP: 법정대리인 인증, CMP: 신청완료, DLN: 다운로드, ESY: 간편신청서]
     *   - 사용 type:
     *     . F-1-FTH: 스마트서식지 신규/변경 안면인증 URL 전송
     *     . F-2-FTH: 스마트서식지 서비스변경 안면인증 URL 전송
     *     . F-3-FTH: 스마트서식지 명의변경 안면인증 URL 전송
     *     . F-4-FTH: 스마트서식지 서비스해지 안면인증 URL 전송
     *     . F-1-CMP: 스마트서식지 신규/변경 접수완료 신청서 URL 전송
     *     . F-2-CMP: 스마트서식지 서비스변경 접수완료 신청서 URL 전송
     *     . F-3-CMP: 스마트서식지 명의변경 접수완료 신청서 URL 전송
     *     . F-4-CMP: 스마트서식지 서비스해지 접수완료 신청서 URL 전송
     *     . F-1-ADN: 스마트서식지 신규/변경 GooglePlay APP 다운로드 URL 전송
     *     . F-2-ADN: 스마트서식지 서비스변경 GooglePlay APP 다운로드 URL 전송
     *     . F-3-ADN: 스마트서식지 명의변경 GooglePlay APP 다운로드 URL 전송
     *     . F-4-ADN: 스마트서식지 서비스해지 GooglePlay APP 다운로드 URL 전송
     *     . F-1-IDN: 스마트서식지 신규/변경 AppStore APP 다운로드 URL 전송
     *     . F-2-IDN: 스마트서식지 서비스변경 AppStore APP 다운로드 URL 전송
     *     . F-3-IDN: 스마트서식지 명의변경 AppStore APP 다운로드 URL 전송
     *     . F-4-IDN: 스마트서식지 서비스해지 AppStore APP 다운로드 URL 전송
     *     . A-0-ESY: 관리자 간편신청서 URL 발송 (대리점관리자)
     * </pre>
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Boolean sendSms(CommonSmsRequest request) {
        validateSendSmsRequired(request);

        SmsData result = createSmsData(request);
        saveMspSmsData(request.phone(),
            request.type().getType(),
            result.title(),
            result.message(),
            request.type().getCode(),
            result.userId());

        return true;
    }

    private void validateSendSmsRequired(CommonSmsRequest request) {
        if (!StringUtils.hasText(request.path()) || !StringUtils.hasText(request.phone())) {
            throw new SimpleDomainException(invalidAccessMessage(request.path()));
        }
    }

    @NonNull
    private static SmsData createSmsData(CommonSmsRequest request) {
        String userId = AuthenticationUtils.getUser().getUserId();
        String url = StringUtils.hasText(request.url()) ? request.url() : "";
        String title = StringUtils.hasText(request.title()) ? request.title() : request.type().getTitle();
        String message = StringUtils.hasText(request.message())
            ? request.message()
            : request.type().getMessage(request.value(), request.name(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시mm분")), url);
        return new SmsData(userId, title, message);
    }

    private record SmsData(String userId, String title, String message) { }


    /**
     * 일반 카카오 알림톡 발송
     *
     * <pre>
     * - type:
     *   - 설명: 첫번째 - [F: 스마트서식지, A: 관리자]
     *          두번째 - [0: 기타, 1: 신규/변경, 2: 서비스변경, 3: 명의변경, 4: 서비스해지]
     *          세번째 - [OTP: SMS 번호인증, FTH: 안면인증, VDP: 법정대리인 인증, CMP: 신청완료, DLN: 다운로드, ESY: 간편신청서]
     *   - 사용 type:
     *     . F-1-FTH: 스마트서식지 신규/변경 안면인증 URL 전송
     *     . F-2-FTH: 스마트서식지 서비스변경 안면인증 URL 전송
     *     . F-3-FTH: 스마트서식지 명의변경 안면인증 URL 전송
     *     . F-4-FTH: 스마트서식지 서비스해지 안면인증 URL 전송
     *     . F-1-CMP: 스마트서식지 신규/변경 접수완료 신청서 URL 전송
     *     . F-2-CMP: 스마트서식지 서비스변경 접수완료 신청서 URL 전송
     *     . F-3-CMP: 스마트서식지 명의변경 접수완료 신청서 URL 전송
     *     . F-4-CMP: 스마트서식지 서비스해지 접수완료 신청서 URL 전송
     * </pre>
     *
     * @param request
     * @return
     */
    @Override
    @LoginSessionContext
    @Transactional
    public Boolean sendKakao(CommonSmsRequest request) {
        validateSendSmsRequired(request);

        SmsData smsData = createSmsData(request);
        saveMspKakaoData(
            request.phone(),
            smsData.title(),
            smsData.message(),
            request.type().getCode(),
            smsData.userId());

        return true;
    }

    /**
     * 인증번호 SMS 발송
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
    @LoginSessionContext
    @Transactional
    public CommonSmsResponse sendOtpSms(CommonSmsRequest request) {
        validateSendOtpSms(request);

        String phoneNumber = request.phone();
        String userId;
        String userName = request.name();
        if (!StringUtils.hasText(phoneNumber)) {
            // 로그인 사용자 토큰을 통한 사용자 정보 중 휴대폰번호 추출
            LoginSessionUser user = loginSessionFlowProcessor.getSessionUser(request.token());
            userId = user.userId();
            userName = user.userName();
            phoneNumber = user.phoneNumber();
        } else {
            userId = AuthenticationUtils.getUser().getUserId();
        }
        phoneNumber = phoneNumber.replaceAll("\\D", "");

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

        String authNumber = generateAuthNumber();
        saveMspSmsData(phoneNumber,
            request.type().getType(),
            request.type().getTitle(),
            request.type().getMessage(authNumber),
            request.type().getCode(),
            userId);

        String savedKey = request.type().getCode() + ":" + UUID.randomUUID();

        SmsSendedOtpData sentData = SmsSendedOtpData.builder()
            .key(idVerifValidationDetail.getCrtVldDtlSeq())
            .phone(phoneNumber)
            .type(request.type().getCode())
            .path(request.path())
            .name(userName)
            .token(request.token())
            .value(authNumber)
            .build();
        cacheService.setValue(savedKey, sentData, Duration.ofMinutes(3));

        if (commonSmsProperties.otp().exposeAuthNumber()) {
            return CommonSmsResponse.of(savedKey, authNumber);
        }
        return CommonSmsResponse.of(savedKey);
    }

    private void validateSendOtpSms(CommonSmsRequest request) {
        if (!StringUtils.hasText(request.path())) {
            throw new SimpleDomainException(invalidAccessMessage(request.path()));
        }

        // 스마트서식지 신규/변경, 서비스변경, 명의변경, 서비스해지에서 사용하는 발송 요청인 경우에
        // 휴대폰번호가 반드시 필요
        if (request.type().getCode().matches("^F-[1234]-.*") && (!StringUtils.hasText(request.phone()) || !StringUtils.hasText(request.name()))) {
            throw new SimpleDomainException(invalidAccessMessage(request.type().getCode()));
        }
        boolean isUserOtpType = CommonSmsType.F_0_OTP.equals(request.type()) || CommonSmsType.A_0_OTP.equals(request.type());
        if (isUserOtpType && !StringUtils.hasText(request.token())) {
            throw new SimpleDomainException(invalidAccessMessage(request.type().getCode()));
        }
        if (isUserOtpType) {
            LoginTwoFactorStatus status = loginSessionFlowProcessor.getTwoFactorStatus(request.token());
            if (!status.sessionExists()) {
                throw new SimpleDomainException(messageWithDetail("인증 진행을 처음부터 다시 시작하세요.", request.type().getCode()));
            }
        }
    }

    private String invalidAccessMessage(String detail) {
        return messageWithDetail("잘못된 접근입니다.", detail);
    }

    private String messageWithDetail(String message, String detail) {
        return message + (commonSmsProperties.error().includeDetail() ? ": " + detail : "");
    }

    @NonNull
    private static String generateAuthNumber() {
        StringBuilder randomAuthNumber = new StringBuilder(AUTH_NUMBER_LENGTH);

        for (int i = 0; i < AUTH_NUMBER_LENGTH; i++) {
            randomAuthNumber.append(SECURE_RANDOM.nextInt(10));
        }

        return randomAuthNumber.toString();
    }

    /**
     * 입력된 인증번호 검증
     *
     * @param request
     * @return
     */
    @Override
    @LoginSessionContext
    @Transactional
    public Boolean verifyOtpSms(CommonSmsRequest request) {
        validateVerifyOtpSms(request);

        // 1. token을 통한 Redis 데이터 조회
        SmsSendedOtpData sentData = cacheService.getValue(request.token());
        if (sentData == null) {
            throw new SimpleDomainException("인증번호 유효시간이 종료되었습니다.\n[인증번호 재발송] 버튼을 클릭하시면,\n인증번호가 재발송 됩니다.");
        }

        // 2. 인증 번호 추출 및 value 값 비교
        if (!request.value().equals(sentData.getValue())) {
            return false;
        }

        // 3. MSF_CRT_VLD_DTL 테이블 등록
        IdVerifValidationDetail idVerifValidationDetail = IdVerifValidationDetail.builder()
            .crtVldDtlSeq(sentData.getKey())
            .moduTypeCd(request.type().getCode())
            .compTypeCd("D")
            .stepEndYn(StepEndStatus.Y)
            .veriRsltSbst("Y")
            .referer(request.type().getCode())
            .authNm(sentData.getName())
            .mobileNo(sentData.getPhone())
            .requestKey(0L)
            .reqSeq(0L)
            .resSeq(0L)
            .rip(RequestUtils.getClientIp())
            .urlTypeCd(request.type().getCode() + "-CHK")
            .build();

        smsRepository.registerMsfCrtVldDtl(idVerifValidationDetail);

        if (CommonSmsType.F_0_OTP.equals(request.type()) || CommonSmsType.A_0_OTP.equals(request.type())) {
            loginSessionFlowProcessor.completeTwoFactor(sentData.getToken());
        }

        return true;
    }

    private void validateVerifyOtpSms(CommonSmsRequest request) {
        if (!StringUtils.hasText(request.token()) || !StringUtils.hasText(request.value())) {
            throw new SimpleDomainException(invalidAccessMessage(request.path()));
        }
    }

    /**
     * SMS 발송 데이터 DB 저장
     *
     * @param rcptData 휴대폰번호
     * @param smsType SMS 문자 형식, 1: SMS, 2: LMS
     * @param title SMS 문자 제목: smsType이 2 (LMS) 일 경우에만 적용됨)
     * @param message 발송메세지
     * @param reserved02 발송형식
     * @param reserved03 사용자ID
     */
    private void saveMspSmsData(String rcptData, Integer smsType, String title, String message, String reserved02, String reserved03) {
        /*
         * 테이블:
         *   - AM2X_SUBMIT
         * SMS 발송 데이터
         *   MSG_ID:
         *     - AM2X_SUBMIT_SEQ.NEXTVAL
         *   MSG_TYPE:
         *     - 1 (SMS)
         *     - 2 (LMS)
         *   SUBJECT:
         *     - MSG_TYPE = 1: NULL
         *     - MSG_TYPE = 2: [제목]
         *   SCHEDULE_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         *   SUBMIT_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         *   MESSGAE: [메세지]
         *   CALLBACK_NUM: 18995000 (콜센터 대표번호)
         *   RCPT_DATA: [휴대폰번호]
         *   K_ADFLAG: 'N'
         *   RESERVED01: 'MSF'
         *   RESERVED02: [사용구분값]
         *   RESERVED03: [사용자ID]
         */
        MspSmsData data = MspSmsData.builder()
            .msgType(smsType)
            .subject(smsType == 2 ? title : null)
            .message(message)
            .callbackNum(CALLCENTER)
            .rcptData(rcptData.replace("-", ""))
            .kAdflag("N")
            .reserved01(RESERVED01)
            .reserved02(reserved02)
            .reserved03(reserved03)
            .build();
        smsRepository.registerSmsInfo(data);
    }

    /**
     * 카카오 알림톡 발송 데이터 DB 저장
     *
     * @param rcptData 휴대폰번호
     * @param title 알림톡 제목
     * @param message 알림톡 메세지
     * @param reserved02 발송형식
     * @param reserved03 사용자ID
     */
    private void saveMspKakaoData(String rcptData, String title, String message, String reserved02, String reserved03) {
        /*
         * 테이블:
         *   - AM2X_SUBMIT
         * SMS 발송 데이터
         *   MSG_ID:
         *     - AM2X_SUBMIT_SEQ.NEXTVAL
         *   MSG_TYPE: 6 (KAKAO)
         *   MSG_TYPE_SECOND: 2
         *   SUBJECT: [제목]
         *   SCHEDULE_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         *   SUBMIT_TIME: TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
         *   MESSGAE: [메세지]
         *   CALLBACK_NUM: 18995000 (콜센터 대표번호)
         *   RCPT_DATA: [휴대폰번호]
         *   RESERVED01: 'MSF'
         *   RESERVED02: [사용구분값]
         *   RESERVED03: [사용자ID]
         *   K_TMPLCODE: [템플릿코드 (MSF 사용자구분값 사용)]
         *   K_SENDERKEY: [카카오 알림톡 발송키]
         *   K_MESSAGE: [메세지]
         *   FAIL_SEND: Y
         */
        String kakaoSenderKey = messageSenderProperties.kakao().senderKey();
        MspSmsData data = MspSmsData.builder()
            .msgType(6)
            .msgTypeSecond(2)
            .subject(title)
            .message(message)
            .callbackNum(CALLCENTER)
            .rcptData(rcptData.replace("-", ""))
            .reserved01(RESERVED01)
            .reserved02(reserved02)
            .reserved03(reserved03)
            .kTmplcode(reserved02)
            .kSenderkey(kakaoSenderKey)
            .kMessage(message)
            .failSend("Y")
            .build();
        smsRepository.registerKakaoInfo(data);
    }
}
