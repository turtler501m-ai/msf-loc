package com.ktmmobile.msf.domains.policy.password.vo;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;
import tools.jackson.databind.annotation.JsonDeserialize;

import com.ktmmobile.msf.commons.common.exception.InvalidValueException;
import com.ktmmobile.msf.domains.policy.password.serializer.PasswordJsonDeserializer;

@JsonDeserialize(using = PasswordJsonDeserializer.class)
public record Password(@JsonValue String value) {

    public static final String REQUIRED_MESSAGE = "비밀번호를 입력해주세요.";
    public static final String INVALID_FORMAT_MESSAGE = "비밀번호는 영문, 숫자, 특수문자(!@#$%^*+=-)를 포함한 10~15자이며, 연속 숫자 4자리 이상은 사용할 수 없습니다.";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^*+=-])[a-zA-Z\\d!@#$%^*+=-]{10,15}$");

    @JsonCreator
    public Password {
        if (!hasText(value)) {
            throw new InvalidValueException(REQUIRED_MESSAGE);
        }
        if (!isValidFormat(value)) {
            throw new InvalidValueException(INVALID_FORMAT_MESSAGE);
        }
    }

    public static boolean hasText(String value) {
        return StringUtils.hasText(value);
    }

    public static boolean isValidFormat(String value) {
        return PASSWORD_PATTERN.matcher(value).matches()
            && !hasSequentialNumbers(value);
    }

    private static boolean hasSequentialNumbers(String value) {
        int ascendingCount = 1;
        int descendingCount = 1;
        for (int i = 1; i < value.length(); i++) {
            char previous = value.charAt(i - 1);
            char current = value.charAt(i);
            if (!Character.isDigit(previous) || !Character.isDigit(current)) {
                ascendingCount = 1;
                descendingCount = 1;
                continue;
            }

            int diff = current - previous;
            ascendingCount = diff == 1 ? ascendingCount + 1 : 1;
            descendingCount = diff == -1 ? descendingCount + 1 : 1;
            if (ascendingCount >= 4 || descendingCount >= 4) {
                return true;
            }
        }
        return false;
    }
}
