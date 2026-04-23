package com.ktmmobile.msf.domains.shared.common.address.application.dto;

import java.util.List;

public record JusoHttpClientResponse(
    Results results
) {

    public record Results(
        Common common,
        List<Juso> juso
    ) {}

    public record Common(
        String totalCount, // 총 검색 데이터수
        Integer currentPerPage, // 페이지 번호
        Integer countPerPage, // 페이지당 출력할 결과 Row 수
        String errorCode, // 에러 코드
        String errorMessage // 에러 메시지
    ) {}
    public record Juso(
        String roadAddr, // 전체 도로명주소
        String roadAddrPart1, // 도로명주소(참고항목 제외)
        String roadAddrPart2, // 도로명주소 참고항목
        String jibunAddr, // 지번주소
        String engAddr, // 도로명주소(영문)
        String zipNo, // 우편번호
        String admCd, // 행정구역코드
        String rnMgtSn, // 도로명코드
        String bdMgtSn, // 건물관리번호
        String detBdNmList, // 상세건물명
        String bdNm, // 건물명
        String bdKdcd, // 공동주택여부(1:공동주택, 0:비공동주택)
        String siNm, // 시도명
        String sggNm, // 시군구명
        String emdNm, // 읍면동명
        String liNm, // 법정리명
        String rn, // 도로명
        String udrtYn, // 지하여부(0:지상, 1:지하)
        Integer buldMnnm, // 건물본번
        Integer buldSlno, // 건물부번
        String mtYn, // 산여부(0:대지, 1:산)
        Integer lnbrMnnm, // 지번본번(번지)
        String lnbrSlno, // 읍면동일련번호
        String hstryYn, // 변동이력여부(0:현행 주소정보, 1:요청변수의 keyword(검색어)가 변동된 주소정보에서 검색된 정보)
        String relJibun, // 관련지번
        String hemdNm // 관할주민센터
    ) {}
}
