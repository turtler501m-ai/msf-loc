package com.ktmmobile.msf.domains.externalclient.nice.application.dto;

import java.util.Arrays;
import java.util.List;

/**
 * NICE 계좌 실명확인 응답값
 *
 * <p>응답 전문 형식:
 * 주문번호|결과코드|결과메시지|
 *
 * <p>결과코드 예시:
 * 0000-정상처리
 * DB01-해당 데이터가 존재하지 않음
 * DB02-실명조회 DB 에러
 * B102-계좌오류
 */
public record NiceApiAccountCheckResponse(
    // NICE 원문 응답. 장애 분석/원문 비교를 위해 보존
    String rawResponse,

    // "|" 구분 결과 전체 컬럼. 레거시와 동일하게 필요한 인덱스 직접 확인 가능
    List<String> values,

    // values[0]: 주문번호
    String orderNo,

    // values[1]: NICE가 내려준 결과코드
    String resultCode,

    // values[2]: NICE가 내려준 결과메시지
    String resultMessage,

    // 결과코드가 0000인 경우 true
    boolean success
) {

    private static final int ORDER_NO_INDEX = 0;
    private static final int RESULT_CODE_INDEX = 1;
    private static final int RESULT_MESSAGE_INDEX = 2;
    private static final String SUCCESS_RESULT_CODE = "0000";

    public NiceApiAccountCheckResponse {
        values = values == null ? List.of() : List.copyOf(values);
    }

    public static NiceApiAccountCheckResponse from(String rawResponse) {
        String normalizedRawResponse = rawResponse == null ? "" : rawResponse.strip();
        // 빈 컬럼까지 보존하기 위해 limit=-1 사용
        List<String> values = Arrays.asList(normalizedRawResponse.split("\\|", -1));
        String resultCode = valueAt(values, RESULT_CODE_INDEX);
        return new NiceApiAccountCheckResponse(
            normalizedRawResponse,
            values,
            valueAt(values, ORDER_NO_INDEX),
            resultCode,
            valueAt(values, RESULT_MESSAGE_INDEX),
            SUCCESS_RESULT_CODE.equals(resultCode)
        );
    }

    public String value(int index) {
        return valueAt(values, index);
    }

    private static String valueAt(List<String> values, int index) {
        return values.size() > index ? values.get(index) : null;
    }
}
