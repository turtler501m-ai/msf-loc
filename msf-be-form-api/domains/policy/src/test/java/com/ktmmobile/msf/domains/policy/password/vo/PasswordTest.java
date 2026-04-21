package com.ktmmobile.msf.domains.policy.password.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.ktmmobile.msf.commons.common.exception.InvalidValueException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    @DisplayName("유효한 패스워드")
    @ParameterizedTest
    @ValueSource(strings = {
        "Abcd1234!!",      // 영문, 숫자, 허용 특수문자를 모두 포함
        "Password1!",      // 10자 길이를 만족
        "A1!bcdefgh",      // 최소 길이 10자를 만족
        "abcDEF123+=",     // 허용 특수문자 2개를 포함
        "Zz9@abcd1234567", // 최대 길이 15자
        "AA11!!bbcc",      // 대소문자, 숫자, 허용 특수문자 조합
        "aB3$567890",      // 허용 특수문자 '$'를 사용
    })
    void shouldCreatePasswordWhenValueIsValid(String value) {
        assertThatCode(() -> new Password(value))
            .doesNotThrowAnyException();
    }

    @DisplayName("유효하지 않은 패스워드")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        " ",                // 공백만 있는 경우
        "        ",         // 여러 공백만 있는 경우
        "Ab1!",             // 최소 길이 10자 미만인 경우
        "abcdefghij!",      // 숫자가 없는 경우
        "1234567890!",      // 영문이 없는 경우
        "Abcdefghijk1",     // 허용 특수문자가 없는 경우
        "Abcd1234한!",      // 한글이 포함된 경우
        "한글1234!!Aa",     // 한글과 영문이 함께 포함된 경우
        "한글만포함!!12",   // 한글과 숫자/특수문자는 있으나 허용 문자 집합을 벗어나는 경우
        "Abcd1234?!",       // 허용되지 않은 특수문자 '?'가 포함된 경우
        "Abcd 1234!",       // 중간에 공백이 포함된 경우
        " Abcd1234!!",      // 앞에 공백이 포함된 경우
        "Abcd1234!! ",      // 뒤에 공백이 포함된 경우
        "Abcd\t1234!!",     // 탭 문자가 포함된 경우
        "Abcd\n1234!!",     // 개행 문자가 포함된 경우
        "Abcd1234_!",       // 허용되지 않은 특수문자 '_'가 포함된 경우
        "Abcd1234/.",       // 허용되지 않은 특수문자 '/', '.'가 포함된 경우
        "Abcd1234🙂!",     // 이모지가 포함된 경우
        "Abcd1234!!!12345", // 최대 길이 15자를 초과하는 경우
    })
    void shouldThrowExceptionWhenValueIsInvalid(String value) {
        assertThatThrownBy(() -> new Password(value))
            .isInstanceOf(InvalidValueException.class);
    }
}
