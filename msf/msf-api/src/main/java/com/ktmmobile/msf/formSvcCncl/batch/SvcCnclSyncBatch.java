package com.ktmmobile.msf.formSvcCncl.batch;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclSyncItemDto;
import com.ktmmobile.msf.formSvcCncl.mapper.SvcCnclMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * [R4] 서비스해지 처리 완료 동기화 배치.
 *
 * 실행 주기: 6시간마다 (0시, 6시, 12시, 18시)
 * 처리 대상: MSF_REQUEST_CANCEL WHERE PROC_CD='RC'
 *
 * 방안 B (Phase 1) - M플랫폼 X01(perMyktfInfo) 계약상태 직접 조회.
 *   ACNT_STAT='C' : 해지 완료 - PROC_CD='CP' 업데이트.
 *   ACNT_STAT='A'/'S' 또는 X01 실패 : 스킵.
 *
 * LOCAL 모드(SERVER_NAME=LOCAL)에서는 M플랫폼 호출 없이 배치 스킵.
 *
 * ASIS 참조: msc-batch CancelSyncBatch
 * 설계 참조: Z10.서비스해지처리_연동설계_v1.0 4.4절
 */
@Component
public class SvcCnclSyncBatch {

    private static final Logger logger = LoggerFactory.getLogger(SvcCnclSyncBatch.class);

    /** 해지 완료 계약상태 코드 (X01 응답 acntStat) */
    private static final String ACNT_STAT_CANCEL = "C";
    /** 배치 1회 처리 단위 */
    private static final int PAGE_SIZE = 100;
    /** PROC_CD — 접수 */
    private static final String PROC_RC = "RC";
    /** PROC_CD — 완료 */
    private static final String PROC_CP = "CP";

    private final SvcCnclMapper svcCnclMapper;
    private final MplatFormSvc mplatFormSvc;

    @Value("${SERVER_NAME:}")
    private String serverName;

    public SvcCnclSyncBatch(SvcCnclMapper svcCnclMapper, MplatFormSvc mplatFormSvc) {
        this.svcCnclMapper = svcCnclMapper;
        this.mplatFormSvc = mplatFormSvc;
    }

    /**
     * 6시간 주기 실행 — 해지 처리 완료 동기화 (방안 B: X01 직접 조회).
     * LOCAL 모드에서는 스킵.
     */
    @Scheduled(cron = "0 0 */6 * * *")
    public void syncCancelResult() {
        if ("LOCAL".equals(serverName)) {
            logger.debug("[SvcCnclSyncBatch] LOCAL 모드 — 배치 스킵");
            return;
        }

        logger.info("[SvcCnclSyncBatch] 시작 — 해지 완료 동기화 (방안 B: X01)");
        int totalProcessed = 0;
        int totalCompleted = 0;
        int totalSkipped = 0;
        int totalError = 0;

        int offset = 0;
        List<SvcCnclSyncItemDto> page;

        do {
            page = svcCnclMapper.selectPendingCancelList(offset, PAGE_SIZE);
            if (page == null || page.isEmpty()) break;

            for (SvcCnclSyncItemDto item : page) {
                totalProcessed++;
                try {
                    String acntStat = getAcntStat(item);

                    if (ACNT_STAT_CANCEL.equals(acntStat)) {
                        int updated = svcCnclMapper.updateSyncProcCd(item.getRequestKey(), PROC_CP);
                        if (updated > 0) {
                            totalCompleted++;
                            logger.info("[SvcCnclSyncBatch] 완료 동기화: requestKey={}, ctn={}",
                                item.getRequestKey(), maskCtn(item.getCancelMobileNo()));
                        }
                    } else {
                        totalSkipped++;
                        logger.debug("[SvcCnclSyncBatch] 스킵: requestKey={}, acntStat={}",
                            item.getRequestKey(), acntStat);
                    }
                } catch (Exception e) {
                    totalError++;
                    logger.warn("[SvcCnclSyncBatch] 오류 — requestKey={}, ncn={}, msg={}",
                        item.getRequestKey(), item.getContractNum(), e.getMessage());
                }
            }
            offset += PAGE_SIZE;

        } while (page.size() == PAGE_SIZE);

        logger.info("[SvcCnclSyncBatch] 완료 — 처리={}, 동기화={}, 스킵={}, 오류={}",
            totalProcessed, totalCompleted, totalSkipped, totalError);
    }

    /**
     * X01(perMyktfInfo)로 계약상태코드(acntStat) 조회.
     * contractNum 없으면 null 반환 → 스킵.
     */
    private String getAcntStat(SvcCnclSyncItemDto item) {
        String ncn = item.getContractNum();
        String ctn = item.getCancelMobileNo();
        if (ncn == null || ncn.isEmpty()) {
            logger.warn("[SvcCnclSyncBatch] contractNum 없음 — requestKey={}", item.getRequestKey());
            return null;
        }
        MpPerMyktfInfoVO vo = mplatFormSvc.perMyktfInfo(ncn, ctn, null);
        if (!vo.isSuccess()) {
            logger.warn("[SvcCnclSyncBatch] X01 실패 — requestKey={}, globalNo={}",
                item.getRequestKey(), vo.getGlobalNo());
            return null;
        }
        return vo.getAcntStat();
    }

    /** 개인정보 마스킹 (010-****-5678 형태) */
    private String maskCtn(String ctn) {
        if (ctn == null || ctn.length() < 7) return "***";
        return ctn.substring(0, 3) + "-****-" + ctn.substring(ctn.length() - 4);
    }
}
