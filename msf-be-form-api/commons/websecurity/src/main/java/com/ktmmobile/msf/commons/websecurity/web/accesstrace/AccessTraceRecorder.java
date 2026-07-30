package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

/**
 * API 요청 이력 모델 구성 및 저장 구현체 전달 컴포넌트
 */
@RequiredArgsConstructor
@Component
public class AccessTraceRecorder {

    private final ObjectProvider<AccessTraceWriter> accessTraceWriterProvider;
    private final ObjectProvider<AccessTraceModuleCodeResolver> moduleCodeResolverProvider;
    private final ObjectProvider<AccessTraceParameterSanitizer> parameterSanitizerProvider;

    /**
     * 요청/응답 결과의 API 요청 이력 기록
     *
     * @param request 현재 HTTP 요청
     * @param statusText 응답 상태 문자열
     * @param resultMessage 실패 응답에서 추출한 결과 메시지
     */
    public void recordTrace(
        HttpServletRequest request,
        String statusText,
        String resultMessage
    ) {
        recordTrace(request, statusText, resultMessage, "");
    }

    /**
     * 요청/응답 결과의 API 요청 이력 기록
     *
     * @param request 현재 HTTP 요청
     * @param processContent 처리 내용
     * @param resultContent 처리 결과 내용
     * @param extensionValue1 저장소별 확장 값
     */
    private void recordTrace(
        HttpServletRequest request,
        String processContent,
        String resultContent,
        String extensionValue1
    ) {
        write(new AccessTrace(
            userId(request),
            RequestUtils.getClientIp(request),
            processModuleCode(request),
            processContent,
            resultContent,
            // 저장된 이력만으로 호출 API 식별 가능하도록 메서드와 URL 함께 저장
            request.getMethod() + " " + request.getRequestURI(),
            AccessTraceRequestParameterExtractor.extract(request, parameterSanitizerProvider.getIfAvailable()),
            extensionValue1
        ));
    }

    /**
     * 현재 요청 파라미터를 사용한 수동 API 요청 이력 기록
     *
     * @param processContent 처리 내용
     * @param resultContent 처리 결과 내용
     */
    public void recordTrace(
        String processContent,
        String resultContent
    ) {
        HttpServletRequest request = currentRequest();
        if (request == null) {
            return;
        }
        recordTrace(
            request,
            processContent,
            resultContent,
            ""
        );
    }

    /**
     * 현재 요청 기반 수동 API 요청 이력 기록
     *
     * @param processContent 처리 내용
     * @param resultContent 처리 결과 내용
     * @param extensionValue1 저장소별 확장 값
     */
    public void recordTrace(
        String processContent,
        String resultContent,
        String extensionValue1
    ) {
        HttpServletRequest request = currentRequest();
        if (request == null) {
            return;
        }
        recordTrace(request, processContent, resultContent, extensionValue1);
    }

    /**
     * 요청 URL에 매칭되는 처리 모듈 구분 코드 반환
     */
    private String processModuleCode(HttpServletRequest request) {
        AccessTraceModuleCodeResolver moduleCodeResolver = moduleCodeResolverProvider.getIfAvailable();
        if (moduleCodeResolver == null) {
            return "";
        }
        return moduleCodeResolver.resolve(request.getMethod(), request.getRequestURI());
    }

    /**
     * 요청 속성 사용자 ID 우선 반환
     */
    private String userId(HttpServletRequest request) {
        String requestUserId = AccessTraceRequestAttributes.getUserId(request);
        return requestUserId.isBlank() ? authenticatedUserId() : requestUserId;
    }

    /**
     * 인증된 사용자가 있는 경우 사용자 ID 반환
     */
    private String authenticatedUserId() {
        MsfUser user = authenticatedUser();
        return user == null || user.getUserId() == null ? "" : user.getUserId();
    }

    /**
     * 인증된 사용자 반환
     */
    private MsfUser authenticatedUser() {
        try {
            return AuthenticationUtils.getUser();
        } catch (RuntimeException _) {
            // 인증 전 요청이나 SecurityContext가 없는 요청도 이력 기록 자체는 유지
            return null;
        }
    }

    /**
     * 현재 스레드의 HTTP 요청 반환
     */
    private HttpServletRequest currentRequest() {
        return RequestUtils.getRequestIfNoRequest();
    }

    /**
     * API 요청 이력 저장
     */
    private void write(AccessTrace accessTrace) {
        AccessTraceWriter accessTraceWriter = accessTraceWriterProvider.getIfAvailable();
        if (accessTraceWriter == null) {
            return;
        }
        accessTraceWriter.write(accessTrace);
    }
}
