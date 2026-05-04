package com.ktmmobile.msf.domains.shared.common.sms.domain.code;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum CommonSmsType implements CommonEnum {
    F_0_OTP("F-0-OTP", 1, "", "[kt M mobile 스마트서식지 인증] 인증번호는 {0}입니다. 3분내에 입력해 주세요."), // 스마트서식지 SMS 번호인증
    F_1_FTH("F-1-FTH", 2, "안면인증 포탈URL 발급","[kt M모바일 스마트서식지]\n안녕하세요 \nkt M모바일입니다.\n\n아래 안내된 URL을 통해 안면인증을 완료해주세요.\n\n■ 셀프안면인증 URL\n{1}\n\n※URL유효기간이 만료된 경우 고객센터로 연락해 주세요."), // 스마트서식지 신규/변경 안면인증 URL 전송
    F_1_CMP("F-1-CMP", 2, "[kt 엠모바일 스마트서식지]","{2} {1} 고객님 신청접수가 완료되었습니다."), // 스마트서식지 신규/변경 접수완료 신청서 URL 전송
    F_1_DLN("F-1-DLN", 1, "",""), // 스마트서식지 신규/변경 APP 다운로드 URL 전송
    F_1_VDP("F-1-VDP", 1, "","[kt M모바일 스마트서식지] 인증번호는 {0}입니다."), // 스마트서식지 신규/변경 법정대리인 인증
    F_2_CMP("F-2-CMP", 2, "[kt 엠모바일 스마트서식지]","{2} {1} 고객님 신청접수가 완료되었습니다."), // 스마트서식지 서비스변경 접수완료 신청서 URL 전송
    F_2_DLN("F-2-DLN", 1, "",""), // 스마트서식지 서비스변경 APP 다운로드 URL 전송
    F_2_VDP("F-2-VDP", 1, "","[kt M모바일 스마트서식지] 인증번호는 {0}입니다."), // 스마트서식지 서비스변경 법정대리인 인증
    F_3_CMP("F-3-CMP", 2, "[kt 엠모바일 스마트서식지]","{2} {1} 고객님 신청접수가 완료되었습니다."), // 스마트서식지 명의변경 접수완료 신청서 URL 전송
    F_3_DLN("F-3-DLN", 1, "",""), // 스마트서식지 명의변경 APP 다운로드 URL 전송
    F_3_VDP("F-3-VDP", 1, "","[kt M모바일 스마트서식지] 인증번호는 {0}입니다."), // 스마트서식지 명의변경 법정대리인 인증
    F_4_CMP("F-4-CMP", 2, "[kt 엠모바일 스마트서식지]","{2} {1} 고객님 신청접수가 완료되었습니다."), // 스마트서식지 서비스해지 접수완료 신청서 URL 전송
    F_4_DLN("F-4-DLN", 1, "",""), // 스마트서식지 서비스해지 APP 다운로드 URL 전송
    F_4_VDP("F-4-VDP", 1, "","[kt M모바일 스마트서식지] 인증번호는 {0}입니다."), // 스마트서식지 서비스해지 법정대리인 인증
    A_0_ESY("A-0-ESY", 1, "",""), // 관리자 간편신청서 URL 발송 (대리점관리자)
    A_0_OTP("A-0-OTP", 1, "","(kt M모바일 스마트서식지 관리자 인증번호) {0}"); // 관리자 SMS 번호인증 (대시점관리자, 시스템관리자)

    private final String code;
    private final Integer type;
    private final String title;
    private final String message;

    @JsonCreator
    public static CommonSmsType from(String code) {
        for (CommonSmsType type : CommonSmsType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    @Override public String getCode() {
        return code;
    }

    @Override public String getTitle() {
        return title;
    }

    public String getMessage(Object... params) {
        if (params == null || params.length == 0) {
            return message;
        }

        // ${...} 패턴을 찾는 정규식
        return MessageFormat.format(message, params);
    }
}
