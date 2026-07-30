package com.ktmmobile.msf.domains.shared.common.sms.domain.code;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum CommonSmsType implements CommonEnum {
    F_0_OTP("F-0-OTP", "사용자 OTP 인증번호 발송", 1, "", "[kt M mobile 스마트신청서 인증] 인증번호는 {0}입니다. 3분내에 입력해 주세요."), // 스마트신청서 SMS 번호인증
    F_1_FTH("F-1-FTH",
        "신규/변경 안면인증 URL 발송",
        2,
        "안면인증 포탈URL 발급",
        "[kt M모바일 스마트신청서]\n안녕하세요 \nkt M모바일입니다.\n\n아래 안내된 URL을 통해 안면인증을 완료해주세요.\n\n■ 셀프안면인증 URL\n{3}\n\n※URL유효기간이 만료된 경우 고객센터로 연락해 주세요."), // 스마트신청서 신규/변경 안면인증 URL 전송
    F_2_FTH("F-2-FTH",
        "서비스변경 안면인증 URL 발송",
        2,
        "안면인증 포탈URL 발급",
        "[kt M모바일 스마트신청서]\n안녕하세요 \nkt M모바일입니다.\n\n아래 안내된 URL을 통해 안면인증을 완료해주세요.\n\n■ 셀프안면인증 URL\n{3}\n\n※URL유효기간이 만료된 경우 고객센터로 연락해 주세요."), // 스마트신청서 서비스변경 안면인증 URL 전송
    F_3_FTH("F-3-FTH",
        "명의변경 안면인증 URL 발송",
        2,
        "안면인증 포탈URL 발급",
        "[kt M모바일 스마트신청서]\n안녕하세요 \nkt M모바일입니다.\n\n아래 안내된 URL을 통해 안면인증을 완료해주세요.\n\n■ 셀프안면인증 URL\n{3}\n\n※URL유효기간이 만료된 경우 고객센터로 연락해 주세요."), // 스마트신청서 명의변경 안면인증 URL 전송
    F_4_FTH("F-4-FTH",
        "서비스해지 안면인증 URL 발송",
        2,
        "안면인증 포탈URL 발급",
        "[kt M모바일 스마트신청서]\n안녕하세요 \nkt M모바일입니다.\n\n아래 안내된 URL을 통해 안면인증을 완료해주세요.\n\n■ 셀프안면인증 URL\n{3}\n\n※URL유효기간이 만료된 경우 고객센터로 연락해 주세요."), // 스마트신청서 서비스해지 안면인증 URL 전송
    F_1_CMP("F-1-CMP", "신규/변경 신청완료 안내 발송", 2, "개통 안내", "[kt 엠모바일]\n\n고객님 환영합니다.\n\nkt엠모바일 가입해주신 내역 안내 입니다.\n\n{3}\n\n■ 가입신청서는 가입점에서 수령 가능합니다.\n\n■ 자세한 상담 및 문의는 고객센터를 이용하여 주시기 바랍니다.\n\n[가입 휴대폰에서 114(무료), 다른전화에서 1899-5000(유료)]\n\n■ 상세 가입내역 확인은 홈페이지[http://www.ktmmobile.com]에서도 확인 가능합니다."), // 스마트신청서 신규/변경 접수완료 신청서 URL 전송
    F_2_CMP("F-2-CMP", "서비스변경 신청완료 안내 발송", 2, "개통 안내", "[kt 엠모바일]\n\n고객님 환영합니다.\n\nkt엠모바일 가입해주신 내역 안내 입니다.\n\n{3}\n\n■ 가입신청서는 가입점에서 수령 가능합니다.\n\n■ 자세한 상담 및 문의는 고객센터를 이용하여 주시기 바랍니다.\n\n[가입 휴대폰에서 114(무료), 다른전화에서 1899-5000(유료)]\n\n■ 상세 가입내역 확인은 홈페이지[http://www.ktmmobile.com]에서도 확인 가능합니다."), // 스마트신청서 서비스변경 접수완료 신청서 URL 전송
    F_3_CMP("F-3-CMP", "명의변경 신청완료 안내 발송", 2, "개통 안내", "[kt 엠모바일]\n\n고객님 환영합니다.\n\nkt엠모바일 가입해주신 내역 안내 입니다.\n\n{3}\n\n■ 가입신청서는 가입점에서 수령 가능합니다.\n\n■ 자세한 상담 및 문의는 고객센터를 이용하여 주시기 바랍니다.\n\n[가입 휴대폰에서 114(무료), 다른전화에서 1899-5000(유료)]\n\n■ 상세 가입내역 확인은 홈페이지[http://www.ktmmobile.com]에서도 확인 가능합니다."), // 스마트신청서 명의변경 접수완료 신청서 URL 전송
    F_4_CMP("F-4-CMP", "서비스해지 신청완료 안내 발송", 2, "개통 안내", "[kt 엠모바일]\n\n고객님 환영합니다.\n\nkt엠모바일 가입해주신 내역 안내 입니다.\n\n{3}\n\n■ 가입신청서는 가입점에서 수령 가능합니다.\n\n■ 자세한 상담 및 문의는 고객센터를 이용하여 주시기 바랍니다.\n\n[가입 휴대폰에서 114(무료), 다른전화에서 1899-5000(유료)]\n\n■ 상세 가입내역 확인은 홈페이지[http://www.ktmmobile.com]에서도 확인 가능합니다."), // 스마트신청서 서비스해지 접수완료 신청서 URL 전송
    F_1_ADN("F-1-ADN",
        "신규/변경 Android APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://play.google.com/store/apps/details?id=kt.co.ktmmobile&hl=ko"), // 스마트신청서 신규/변경 GooglePlay APP 다운로드 URL 전송
    F_2_ADN("F-2-ADN",
        "서비스변경 Android APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://play.google.com/store/apps/details?id=kt.co.ktmmobile&hl=ko"), // 스마트신청서 서비스변경 GooglePlay APP 다운로드 URL 전송
    F_3_ADN("F-3-ADN",
        "명의변경 Android APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://play.google.com/store/apps/details?id=kt.co.ktmmobile&hl=ko"), // 스마트신청서 명의변경 GooglePlay APP 다운로드 URL 전송
    F_4_ADN("F-4-ADN",
        "서비스해지 Android APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://play.google.com/store/apps/details?id=kt.co.ktmmobile&hl=ko"), // 스마트신청서 서비스해지 GooglePlay APP 다운로드 URL 전송
    F_1_IDN("F-1-IDN",
        "신규/변경 IOS APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8"), // 스마트신청서 신규/변경 AppStore APP 다운로드 URL 전송
    F_2_IDN("F-2-IDN",
        "서비스변경 IOS APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8"), // 스마트신청서 서비스변경 AppStore APP 다운로드 URL 전송
    F_3_IDN("F-3-IDN",
        "명의변경 IOS APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8"), // 스마트신청서 명의변경 AppStore APP 다운로드 URL 전송
    F_4_IDN("F-4-IDN",
        "서비스해지 IOS APP 다운로드 안내 발송",
        2,
        "kt M모바일 다운로드 안내",
        "kt M모바일에서 제공하는 서비스를 이용할수 있습니다.\n신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.\nhttps://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8"), // 스마트신청서 서비스해지 AppStore APP 다운로드 URL 전송
    F_1_VDP("F-1-VDP", "신규/변경 법정대리인 휴대폰 인증번호 발송", 1, "", "[kt M모바일 스마트신청서] 인증번호는 {0}입니다."), // 스마트신청서 신규/변경 법정대리인 인증
    F_2_VDP("F-2-VDP", "서비스변경 법정대리인 휴대폰 인증번호 발송", 1, "", "[kt M모바일 스마트신청서] 인증번호는 {0}입니다."), // 스마트신청서 서비스변경 법정대리인 인증
    F_3_VDP("F-3-VDP", "명의변경 법정대리인 휴대폰 인증번호 발송", 1, "", "[kt M모바일 스마트신청서] 인증번호는 {0}입니다."), // 스마트신청서 명의변경 법정대리인 인증
    F_4_VDP("F-4-VDP", "서비스해지 법정대리인 휴대폰 인증번호 발송", 1, "", "[kt M모바일 스마트신청서] 인증번호는 {0}입니다."), // 스마트신청서 서비스해지 법정대리인 인증
    A_0_ESY("A-0-ESY", "간편신청서 URL 발송", 1, "", ""), // 관리자 간편신청서 URL 발송 (대리점관리자)
    A_0_OTP("A-0-OTP", "관리자 OTP 인증번호 발송", 1, "", "(kt M모바일 스마트신청서 관리자 인증번호) {0}"); // 관리자 SMS 번호인증 (대시점관리자, 시스템관리자)

    private final String code;
    private final String name;
    private final Integer type;
    private final String title;
    private final String message;

    @JsonCreator
    public static CommonSmsType from(String code) {
        for (CommonSmsType type: CommonSmsType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static CommonSmsType fromOfFth(String typeCode) {
        for (CommonSmsType type: CommonSmsType.values()) {
            if (type.getCode().equals("F-" + typeCode + "FTH")) {
                return type;
            }
        }
        return null;
    }

    public String getMessage(Object... params) {
        if (params == null || params.length == 0) {
            return message;
        }

        // ${...} 패턴을 찾는 정규식
        return MessageFormat.format(message, params);
    }
}
