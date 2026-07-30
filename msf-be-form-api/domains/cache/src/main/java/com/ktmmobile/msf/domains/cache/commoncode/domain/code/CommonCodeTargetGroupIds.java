package com.ktmmobile.msf.domains.cache.commoncode.domain.code;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 스마트신청서에서 사용하는 MSP/MCP 공통코드 그룹
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonCodeTargetGroupIds {

    private static final List<String> MSP_GROUP_IDS = List.of(
        "RCP0021",  // 신청인과의 관계
        "RCP2006",  // 고객정보 본인인증방식
        "RCP0006",  // 진행상태
        //"RCP0056",  // 유심비 납부방법
        "RCP0090"   // 명의변경 진행상태
    );

    private static final List<String> MCP_CODE_GROUP_IDS = List.of();

    private static final List<String> MCP_DETAIL_GROUP_IDS = List.of(
        "AGR",                      // 미성년자대리인 관계구분
        "BNK",                      // 계좌은행구분
        "CARD",                     // 신용카드
        "CL01",                     // 포탈 고객요청 처리결과
        "CRD",                      // 카드
        "ChangeNumberException",    // 번호변경 셀프케어 제한
        "ClauseInsur",              // 휴대폰 안심보험
        "CmmPeriodLimit",           // 기간제한
        "Constant",                 // 사이트 전체 정의
        "DirectUsimPrice",          // 직영 유심비 가격 정보
        "ExceptionListPriceCd",     // 셀프개통신규 다회선 제한 예외 요금제 목록
        "F002",                     // 지원금유형
        "FORMGROUP",                // 서식지 이용약관 분류
        "FORMINFO",                 // 고지
        "FORMREQUIRED",             // 필수
        "FORMSELECT",               // 선택
        "GoldNumberList",           // 골드번호 목록
        "MarketJoinUsimPriceInfo",  // 위탁온라인 가입비 유심비 면제 여부
        "MPPY",                     // 핸드폰 할부금 결제구분
        "MRA",                      // 미환급액 요금상계
        "NATIONLIST",               // 국가목록
        "NSC",                      // 통신사구분
        "NpNscException",           // 사전인증 예외 통신사
        "PAYM",                     // 요금납부방법구분
        "STRE",                     // 명세서수신구분
        "TERMSELF",                 // 약관 유심구매
        "driverLicenseAgency",      // 운전면허 발급기관 코드
        "fathCertIdType",           // 안면인증 대상 신분증 유형
        "fathCertPolicy",           // 안면인증 관련 설정값
        "usimProdInfo"              // 셀프개통 배송 요청 유심코드
    );

    public static List<String> mspGroupIds() {
        return MSP_GROUP_IDS;
    }

    public static List<String> mcpCodeGroupIds() {
        return MCP_CODE_GROUP_IDS;
    }

    public static List<String> mcpDetailGroupIds() {
        return MCP_DETAIL_GROUP_IDS;
    }
}
