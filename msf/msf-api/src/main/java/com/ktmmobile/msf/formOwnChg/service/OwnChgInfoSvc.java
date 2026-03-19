package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 명의변경 신청서 - 가능 여부·연락처 검증 서비스. 연동(join-info)은 formComm.SvcChgRestController.
 */
public interface OwnChgInfoSvc {

    /**
     * 명의변경 가능 여부 체크 (MC0 연동 예정).
     */
    boolean eligible(SvcChgInfoDto req);

    /**
     * 양도인 신청가능여부 체크 (AS-IS grantorReqChk 대응). M전산 연동 예정.
     * @return SUCCESS / STOP / NONPAY / LESSNINETY / ERROR
     */
    OwnChgGrantorReqChkVO grantorReqChk(OwnChgGrantorReqChkReqDto req);

    /**
     * 명의변경 연락처 상이 검증 (AS-IS nameChgChkTelNo 대응).
     * 명의변경 회선 ≠ 양도인 미납 연락처, 명변회선·연락가능 ≠ 법정대리인 연락처.
     */
    OwnChgCheckTelNoVO checkTelNo(OwnChgCheckTelNoReqDto req);
}
