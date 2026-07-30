package com.ktmmobile.msf.domains.externalclient.nice.application.dto;

import lombok.Builder;

/**
 * NICE 계좌 실명확인 요청값
 *
 * <p>개인/사업자구분:
 * 1-개인
 * 2-사업자
 *
 * <p>identityNumber:
 * 개인은 생년월일 6자리, 사업자는 유효한 사업자번호
 *
 * <p>bankCode:
 * 02-산업
 * 03-기업
 * 04-국민
 * 05-외환
 * 06-국민
 * 07-수협
 * 08-수출입
 * 10~17-농협
 * 19-국민
 * 20-우리
 * 21-신한
 * 22-우리
 * 23-SC제일
 * 24-우리
 * 25-하나
 * 26-신한
 * 27-한국씨티
 * 28-신한
 * 29-국민
 * 31-대구
 * 32-부산
 * 33-하나
 * 34-광주
 * 35-제주
 * 36-한국씨티
 * 37-전북
 * 39-경남
 * 45-새마을금고
 * 46-새마을금고
 * 48-신협
 * 49-신협
 * 50-상호저축은행
 * 53-한국씨티
 * 54-HSBC
 * 71~75-우체국
 * 81-하나
 * 82-하나
 * 84-우리
 * 85-새마을금고
 * 86-새마을금고
 * 88-신한
 *
 * <p>accountNo:
 * 계좌번호 하이픈(-) 제거 후 전달
 *
 * <p>serviceType:
 * 5-계좌 소유주 확인(생년월일 + 은행코드 + 계좌번호 + 계좌소유주명)
 * 2-계좌 성명 확인(은행코드 + 계좌번호 + 계좌소유주명)
 * 4-계좌 유효성 확인(은행코드 + 계좌번호)
 */
@Builder
public record NiceApiAccountCheckRequest(
    // 서비스구분: 1-계좌소유주확인, 2-계좌성명확인, 3-계좌유효성확인
    String service,

    // 고객구분: 1-개인, 2-사업자
    String customerType,

    // 주민번호/사업자번호/법인번호. 개인은 생년월일 6자리
    String identityNumber,

    // 계좌주명
    String name,

    // 은행코드 (NICE 전문 기준)
    String bankCode,

    // 계좌번호. 하이픈(-) 제거 후 전달
    String accountNo,

    // 업무구분: 5-계좌소유주확인, 2-계좌성명확인, 4-계좌유효성확인
    String serviceType,

    // 주문번호. 미입력 시 빌더에서 생성
    String orderNo,

    // 내/외국인구분
    String serviceClass,

    // 조회사유: 10-회원가입, 20-기존회원확인, 30-성인인증, 40-비회원확인, 90-기타
    String inquiryReason
) {
}
