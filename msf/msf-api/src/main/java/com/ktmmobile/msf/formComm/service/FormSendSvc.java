package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.FormSendReqDto;
import com.ktmmobile.msf.formComm.dto.FormSendResVO;

/**
 * 서식지 SCAN 전송 서비스.
 * ASIS: CustRequestScanServiceImpl.prodSendScan() 포팅.
 * 흐름: DB 조회 → 데이터 가공 → XML 생성 → SCAN 서버 전송 → DB update(scan_id).
 */
public interface FormSendSvc {

    /**
     * 서식지 SCAN 전송.
     * @param req custReqSeq + reqType(NC/CC) + userId
     */
    FormSendResVO sendScan(FormSendReqDto req);
}
