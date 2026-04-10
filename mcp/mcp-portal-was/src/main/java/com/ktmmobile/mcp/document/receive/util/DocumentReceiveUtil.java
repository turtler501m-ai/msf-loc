package com.ktmmobile.mcp.document.receive.util;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.document.receive.dto.DocRcvDetailDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvSessionDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvStatusDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvUrlDto;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.ktmmobile.mcp.common.util.DateTimeUtil.isMiddleDateTime2;
import static com.ktmmobile.mcp.document.receive.constants.DocumentReceiveConstants.*;

public class DocumentReceiveUtil {
    public static boolean validateDetail(DocRcvDetailDto docRcvDetail) {
        if (docRcvDetail == null) {
            return false;
        }

        if (!validateStatus(docRcvDetail.getDocRcvStatusDto())) {
            return false;
        }

        if (!validateUrl(docRcvDetail.getDocRcvUrlDto())) {
            return false;
        }

        return true;
    }

    public static boolean validateSession(String docRcvId) {
        DocRcvSessionDto docRcvSessionDto = SessionUtils.getDocumentReceive(docRcvId);
        if (docRcvSessionDto == null) {
            return false;
        }
        if (isExpiredAuth(docRcvSessionDto.getLastAuthDt())) {
            SessionUtils.invalidateDocumentReceive(docRcvId);
            return false;
        }
        return true;
    }

    private static boolean isExpiredAuth(String lastAuthDt) {
        LocalDateTime lastAuthTime;
        try {
            lastAuthTime = DateTimeUtil.getLocalDateTime(lastAuthDt, "yyyyMMddHHmmss");
        } catch (ParseException e) {
            return true;
        }
        return DateTimeUtil.isExceeded(lastAuthTime, ChronoUnit.MINUTES, 30L);
    }

    private static boolean validateStatus(DocRcvStatusDto docRcvStatusDto) {
        String procStatus = docRcvStatusDto.getProcStatus();
        if (!DOC_RCV_PROC_STATUS_NOT_RECEIVED.equals(procStatus) && !DOC_RCV_PROC_STATUS_RETRY_REQUEST.equals(procStatus)) {
            return false;
        }

        return true;
    }

    private static boolean validateUrl(DocRcvUrlDto docRcvUrlDto) {
        if (docRcvUrlDto == null) {
            return false;
        }

        if (!DOC_RCV_URL_STATUS_ACTIVE.equals(docRcvUrlDto.getStatus())) {
            return false;
        }

        try {
            if (!isMiddleDateTime2(docRcvUrlDto.getIssueDt(), docRcvUrlDto.getExpireDt())) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
