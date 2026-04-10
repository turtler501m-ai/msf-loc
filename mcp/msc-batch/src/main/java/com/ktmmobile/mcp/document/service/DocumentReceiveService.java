package com.ktmmobile.mcp.document.service;

import com.ktmmobile.mcp.document.dao.DocumentReceiveDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentReceiveService {

    private final DocumentReceiveDao documentReceiveDao;

    public DocumentReceiveService(DocumentReceiveDao documentReceiveDao) {
        this.documentReceiveDao = documentReceiveDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public int expireDocumentReceive(String batchExeDate) {
        // 1. 기간 지난 URL 만료 처리
        documentReceiveDao.expireDocRcvUrl(batchExeDate);

        // 2. (24시간 내로 수정 && URL 만료 상태) URL 데이터 조회
        List<String> expiredDocRcvIds = documentReceiveDao.getExpiredDocRcvIds(batchExeDate);
        if (expiredDocRcvIds.isEmpty()) {
            return 0;
        }

        // 3. URL 만료 대상 상태 현행화
        //      3-1. 접수대기(PROC_STATUS : N) => 만료종료(E, expired)
        //      3-2. 재접수요청(PROC_STATUS : R) => 미처리 종료(I, Incompleted)
        //      * 접수여부(RCV_YN)를 확인해야할까?
        int totalCount = 0;
        totalCount += documentReceiveDao.syncDocRcvStatusExpired(expiredDocRcvIds);
        totalCount += documentReceiveDao.syncDocRcvStatusIncompleted(expiredDocRcvIds);
        return totalCount;
    }
}
