package com.ktmmobile.msf.commons.masking.support.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 주소 마스킹 Processor
 */
public class AddressMaskingProcessor implements MaskingProcessor {

    /** 도로명 주소 상세주소 마스킹 블록 길이 */
    private static final int ROAD_NAME_DETAIL_MASK_LENGTH = 7;

    /** 지번 주소 상세주소 마스킹 블록 길이 */
    private static final int LOT_NUMBER_DETAIL_MASK_LENGTH = 10;

    /** 도로명 주소는 도로명 뒤에 건물번호가 이어지는 경우 도로명까지 노출 */
    private static final Pattern ROAD_NAME_ADDRESS = Pattern.compile("^(.+?\\s[^\\s,]+(?:대로|로|길))((?:\\s+\\d.*)?)$", Pattern.CANON_EQ);

    /** 번지 또는 산번지 시작 여부 판단 */
    private static final Pattern ADDRESS_NUMBER = Pattern.compile("^(?:산\\s*)?\\d+(?:-\\d+)?.*$", Pattern.CANON_EQ);

    /** 상세주소와 구분할 건물번호 또는 번지 조회 */
    private static final Pattern ADDRESS_NUMBER_AT_START = Pattern.compile("^(?:산\\s*)?\\d+(?:-\\d+)?", Pattern.CANON_EQ);

    /** 공백으로 구분된 주소 토큰 조회 */
    private static final Pattern ADDRESS_TOKEN = Pattern.compile("\\S+");

    @Override
    public MaskingType type() {
        return MaskingType.ADDRESS;
    }

    @Override
    public String mask(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }

        Matcher roadNameMatcher = ROAD_NAME_ADDRESS.matcher(value);
        if (roadNameMatcher.matches()) {
            return maskAddressDetail(roadNameMatcher, ROAD_NAME_DETAIL_MASK_LENGTH);
        }

        String lotNumberAddress = maskLotNumberAddress(value);
        if (lotNumberAddress != null) {
            return lotNumberAddress;
        }

        return MaskingProcessorUtils.maskMiddle(value, 0, 0);
    }

    private String maskAddressDetail(Matcher matcher, int detailMaskLength) {
        String visiblePart = matcher.group(1);
        String detailPart = matcher.group(2);
        if (!StringUtils.hasText(detailPart)) {
            return visiblePart;
        }
        return visiblePart + maskDetailPart(detailPart, detailMaskLength);
    }

    /** 지번 주소를 법정동·가·리까지 노출하고 번지 이후 마스킹 */
    private String maskLotNumberAddress(String value) {
        Token previousToken = null;
        for (Token token: Token.findAll(value)) {
            if (previousToken != null && isLegalDistrictToken(previousToken.value()) && isAddressNumberToken(token.value())) {
                return value.substring(0, previousToken.end())
                    + maskDetailPart(value.substring(previousToken.end()), LOT_NUMBER_DETAIL_MASK_LENGTH);
            }
            previousToken = token;
        }
        return null;
    }

    /** 건물번호 또는 번지는 원래 글자 수만큼 마스킹, 상세주소는 단일 마스킹 블록으로 축약 */
    private String maskDetailPart(String detailPart, int detailMaskLength) {
        String leadingWhitespace = leadingWhitespace(detailPart);
        String detail = detailPart.substring(leadingWhitespace.length());
        int commaIndex = detail.indexOf(',');
        if (commaIndex >= 0) {
            String addressNumberPart = detail.substring(0, commaIndex);
            String detailedAddressPart = detail.substring(commaIndex + 1);
            return leadingWhitespace
                + maskEveryNonWhitespace(addressNumberPart)
                + ","
                + leadingWhitespace(detailedAddressPart)
                + String.valueOf(MaskingProcessorUtils.MASK).repeat(detailMaskLength);
        }

        int detailSeparatorIndex = addressNumberEndIndex(detail);
        if (detailSeparatorIndex < 0) {
            return leadingWhitespace + maskEveryNonWhitespace(detail);
        }

        String addressNumberPart = detail.substring(0, detailSeparatorIndex);
        String detailedAddressPart = detail.substring(detailSeparatorIndex);
        if (!StringUtils.hasText(detailedAddressPart)) {
            return leadingWhitespace + maskEveryNonWhitespace(addressNumberPart) + detailedAddressPart;
        }

        return leadingWhitespace
            + maskEveryNonWhitespace(addressNumberPart)
            + leadingWhitespace(detailedAddressPart)
            + String.valueOf(MaskingProcessorUtils.MASK).repeat(detailMaskLength);
    }

    /** 법정동·가·리 토큰 여부 판단 */
    private boolean isLegalDistrictToken(String value) {
        return value.endsWith("읍")
            || value.endsWith("면")
            || value.endsWith("동")
            || value.endsWith("가")
            || value.endsWith("리");
    }

    /** 번지 또는 산번지 토큰 여부 판단 */
    private boolean isAddressNumberToken(String value) {
        return ADDRESS_NUMBER.matcher(value).matches();
    }

    /** 상세주소 이전의 건물번호 또는 번지 끝 위치 조회 */
    private int addressNumberEndIndex(String value) {
        Matcher matcher = ADDRESS_NUMBER_AT_START.matcher(value);
        if (!matcher.find()) {
            return -1;
        }
        return matcher.end();
    }

    /** 공백을 제외한 모든 문자를 마스킹 */
    private String maskEveryNonWhitespace(String value) {
        StringBuilder masked = new StringBuilder(value.length());
        for (int codePoint: value.codePoints().toArray()) {
            if (Character.isWhitespace(codePoint)) {
                masked.appendCodePoint(codePoint);
            } else {
                masked.append(MaskingProcessorUtils.MASK);
            }
        }
        return masked.toString();
    }

    /** 문자열 앞쪽의 공백 추출 */
    private String leadingWhitespace(String value) {
        StringBuilder whitespaces = new StringBuilder();
        for (int codePoint: value.codePoints().toArray()) {
            if (Character.isWhitespace(codePoint)) {
                whitespaces.appendCodePoint(codePoint);
            } else {
                break;
            }
        }
        return whitespaces.toString();
    }

    /** 공백으로 구분된 주소 토큰 */
    private record Token(String value, int end) {

        /** 문자열에서 주소 토큰 목록 조회 */
        static Iterable<Token> findAll(String value) {
            return () -> ADDRESS_TOKEN.matcher(value).results()
                .map(result -> new Token(result.group().replace(",", ""), result.end()))
                .iterator();
        }
    }
}
