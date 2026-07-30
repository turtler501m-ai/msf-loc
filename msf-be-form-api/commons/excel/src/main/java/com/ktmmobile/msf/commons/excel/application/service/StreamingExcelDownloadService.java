package com.ktmmobile.msf.commons.excel.application.service;

import java.util.Collection;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.excel.application.port.in.ExcelDownloader;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelFile;
import com.ktmmobile.msf.commons.excel.support.properties.ExcelProperties;
import com.ktmmobile.msf.commons.excel.support.util.ExcelDownloadUtils;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceRecorder;

/**
 * 스트리밍 엑셀 다운로드 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StreamingExcelDownloadService implements ExcelDownloader {

    private final ObjectProvider<AccessTraceRecorder> accessTraceRecorderProvider;
    private final ExcelProperties excelProperties;

    /**
     * 엑셀 파일 기준 스트리밍 다운로드
     *
     * @param response HTTP 응답
     * @param excelFile 엑셀 파일
     * @param <T> 행 데이터 타입
     */
    @Override
    public <T> void download(HttpServletResponse response, ExcelFile<T> excelFile) {
        ExcelDownloadUtils.download(response, excelFile, excelProperties);
        recordAccessTrace(
            excelDownloadProcessContent(),
            String.format("엑셀 다운로드 건수: %,d", countRows(excelFile.sheet().data()))
        );
    }

    /**
     * 엑셀 파일 기준 스트리밍 다운로드 후 접속 이력 기록
     *
     * @param response HTTP 응답
     * @param excelFile 엑셀 파일
     * @param processContent 처리 내용
     * @param resultContent 처리 결과 내용
     * @param <T> 행 데이터 타입
     */
    @Override
    public <T> void download(
        HttpServletResponse response,
        ExcelFile<T> excelFile,
        String processContent,
        String resultContent
    ) {
        ExcelDownloadUtils.download(response, excelFile, excelProperties);
        recordAccessTrace(processContent, resultContent);
    }

    /**
     * 엑셀 다운로드 접속 이력 기록
     */
    private void recordAccessTrace(String processContent, String resultContent) {
        try {
            AccessTraceRecorder accessTraceRecorder = accessTraceRecorderProvider.getIfAvailable();
            if (accessTraceRecorder != null) {
                accessTraceRecorder.recordTrace(processContent, resultContent);
            }
        } catch (Exception e) {
            log.warn("Excel download access trace recording failed.", e);
        }
    }

    /**
     * 엑셀 다운로드 처리 내용 생성
     */
    private String excelDownloadProcessContent() {
        MsfUser user = currentUser();
        String userId = user == null ? "" : user.getUserId();
        String userName = user == null ? "" : user.getUserName();
        return String.format("%s(%s)님 Excel 다운로드", userName, userId);
    }

    /**
     * 현재 인증 사용자 반환
     */
    private MsfUser currentUser() {
        try {
            return AuthenticationUtils.getUser();
        } catch (RuntimeException _) {
            return null;
        }
    }

    /**
     * 엑셀 행 데이터 건수 계산
     */
    private long countRows(Iterable<?> data) {
        if (data instanceof Collection<?> collection) {
            return collection.size();
        }
        long count = 0;
        for (Object ignored: data) {
            count++;
        }
        return count;
    }
}
