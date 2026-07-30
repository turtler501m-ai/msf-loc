package com.ktmmobile.msf.domains.externalclient.nice.support.builder;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckRequest;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiFormRequest;
import com.ktmmobile.msf.domains.externalclient.common.property.ExternalServiceProperties;
import com.ktmmobile.msf.domains.externalclient.common.property.ServiceProperties;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_NICE_API;

/**
 * NICE API 요청 파라미터 빌더
 */
@Component
public class NiceApiRequestBuilder {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final ZoneId ORDER_NO_ZONE = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter ORDER_NO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    // 레거시 주문번호 난수 범위: SecureRandom.nextDouble() * 10,000,000,000L 기준
    private static final long ORDER_NO_RANDOM_BOUND = 10_000_000_000L;
    // 레거시 StringUtil.substringByBytes(name, 0, 60) 기준
    private static final int ACCOUNT_OWNER_NAME_MAX_BYTES = 60;
    // 레거시 checkNiceAccount() 기본값: 1-개인
    private static final String DEFAULT_CUSTOMER_TYPE = "1";
    // 레거시 checkNiceAccount() 기본값: 10-회원가입
    private static final String DEFAULT_INQUIRY_REASON = "10";
    // 레거시 rlnmCheck() 고정값
    private static final String DEFAULT_SEQUENCE = "0000001";

    private final ServiceProperties serviceProperties;

    public NiceApiRequestBuilder(ExternalServiceProperties externalServiceProperties) {
        this.serviceProperties = externalServiceProperties.service(SERVICE_NAME_NICE_API);
    }

    public NiceApiFormRequest buildAccountCheckFormRequest(NiceApiAccountCheckRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("NICE 계좌 실명확인 요청값이 없습니다.");
        }

        return NiceApiFormRequest.builder()
            // NICE 계좌 실명확인 사용자 ID (레거시 NICE_UID)
            .parameter("niceUid", requiredProperty("niceUid"))
            // NICE 계좌 실명확인 비밀번호 (레거시 niceUidPassword)
            .parameter("svcPwd", requiredProperty("svcPwd"))
            // 서비스구분: 1-계좌소유주확인, 2-계좌성명확인, 3-계좌유효성확인
            .parameter("service", request.service())
            // 고객구분: 1-개인, 2-사업자. 미입력 시 레거시 기본값 1
            .parameter("strGbn", valueOrDefault(request.customerType(), DEFAULT_CUSTOMER_TYPE))
            // 주민번호/사업자번호/법인번호. 개인은 생년월일 6자리
            .parameter("strResId", request.identityNumber())
            // 계좌주명: 레거시 NiceResDto#getName() 기준 대문자 변환 + UTF-8 60바이트 제한
            .parameter("strNm", truncateUtf8Bytes(accountOwnerName(request.name())))
            // 은행코드 (NICE 전문 기준)
            .parameter("strBankCode", request.bankCode())
            // 계좌번호. 하이픈(-) 제거 후 전달
            .parameter("strAccountNo", request.accountNo())
            // 업무구분: 5-계좌소유주확인, 2-계좌성명확인, 4-계좌유효성확인
            .parameter("svcGbn", request.serviceType())
            // 주문번호: 업무에서 지정 가능, 미입력 시 레거시 형식으로 생성
            .parameter("strOrderNo", valueOrDefault(request.orderNo(), newOrderNo()))
            // 내/외국인구분 (레거시 svc_cls 파라미터명 유지)
            .parameter("svc_cls", request.serviceClass())
            // 조회사유: 10-회원가입, 20-기존회원확인, 30-성인인증, 40-비회원확인, 90-기타
            .parameter("inq_rsn", valueOrDefault(request.inquiryReason(), DEFAULT_INQUIRY_REASON))
            // 전문 일련번호: 레거시 rlnmCheck()에서 0000001 고정
            .parameter("seq", DEFAULT_SEQUENCE)
            .build();
    }

    /**
     * 레거시 주문번호 형식: yyyyMMdd + 10자리 이하 난수
     */
    public String newOrderNo() {
        String date = ORDER_NO_DATE_FORMATTER.format(ZonedDateTime.now(ORDER_NO_ZONE));
        return date + SECURE_RANDOM.nextLong(ORDER_NO_RANDOM_BOUND);
    }

    /**
     * UTF-8 멀티바이트 문자가 중간에서 깨지지 않도록 문자 단위로 누적
     */
    private String truncateUtf8Bytes(String value) {
        if (value == null) {
            return null;
        }

        if (value.getBytes(StandardCharsets.UTF_8).length <= ACCOUNT_OWNER_NAME_MAX_BYTES) {
            return value;
        }

        StringBuilder builder = new StringBuilder();
        int byteLength = 0;
        for (int offset = 0; offset < value.length(); ) {
            int codePoint = value.codePointAt(offset);
            String character = new String(Character.toChars(codePoint));
            int characterByteLength = character.getBytes(StandardCharsets.UTF_8).length;
            if (byteLength + characterByteLength > ACCOUNT_OWNER_NAME_MAX_BYTES) {
                break;
            }

            builder.append(character);
            byteLength += characterByteLength;
            offset += Character.charCount(codePoint);
        }
        return builder.toString();
    }

    private String valueOrDefault(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }

    /**
     * 레거시 NiceResDto#getName()의 대문자 변환 규칙 유지
     */
    private String accountOwnerName(String name) {
        return valueOrEmpty(name).toUpperCase(Locale.KOREA);
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }

    private String requiredProperty(String name) {
        if (serviceProperties == null) {
            throw new IllegalStateException("NICE API service properties are required.");
        }

        String value = serviceProperties.property(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("NICE API property is required: " + name);
        }
        return value;
    }
}
