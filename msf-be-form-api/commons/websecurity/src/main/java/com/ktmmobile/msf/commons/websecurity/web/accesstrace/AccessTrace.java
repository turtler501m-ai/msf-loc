package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

/**
 * API 요청 이력 저장에 필요한 값을 전달하는 공통 모델
 *
 * @param userId 인증된 사용자 ID
 * @param accessIp 요청 클라이언트 IP
 * @param processModuleCode 요청 URL에 매핑된 처리 모듈 코드
 * @param processContent 처리 내용
 * @param resultContent 처리 결과 내용
 * @param accessUrlAddress HTTP 메서드와 요청 URL
 * @param parameter 요청에서 추출한 body/query 파라미터
 * @param extensionValue1 저장소별 확장 값
 */
public record AccessTrace(
    String userId,
    String accessIp,
    String processModuleCode,
    String processContent,
    String resultContent,
    String accessUrlAddress,
    String parameter,
    String extensionValue1
) {
}
