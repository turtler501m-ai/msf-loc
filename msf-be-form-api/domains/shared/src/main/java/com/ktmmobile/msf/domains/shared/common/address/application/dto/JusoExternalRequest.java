package com.ktmmobile.msf.domains.shared.common.address.application.dto;

public record JusoExternalRequest(
    String confirmKey, // 신청시 발급받은 승인키
    Integer currentPage, // 현재 페이지 번호
    Integer countPerPage, // 페이지당 출력할 결과 Row 수
    String keyword, // 주소 검색어
    String resultType, // 검색결과형식 설정(xml, json)
    String hstryYn, // 변동된 주소정보 포함 여부
    String firstSort, // 정확도순 정렬(none), 우선정렬(road:도로명 포함, location : 지번포함) ※ keyword(검색어)가 우선정렬 항목에 포함된 결과 우선 표출
    String addInfoYn // 출력결과에 추가된 항목(hstryYN, relJibun, hemdNm) 제공여부
) {
    public static JusoExternalRequest of(String confirmKey, String keyword, Integer currentPage) {
        return JusoExternalRequest.of(confirmKey, keyword, currentPage, 5);
    }

    public static JusoExternalRequest of(String confirmKey, String keyword, Integer currentPage, Integer countPerPage) {
        return new JusoExternalRequest(confirmKey, currentPage, countPerPage, keyword, "json", "N", "none", "N");
    }
}
