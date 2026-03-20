package com.ktmmobile.msf.formOwnChg.service;

import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

import java.util.Map;

/**
 * 명의변경 신청서 - 가능 여부·연락처 검증·납부방법·계좌·카드·청구계정 서비스.
 */
public interface OwnChgInfoSvc {

    boolean eligible(SvcChgInfoDto req);

    OwnChgGrantorReqChkVO grantorReqChk(OwnChgGrantorReqChkReqDto req);

    OwnChgCheckTelNoVO checkTelNo(OwnChgCheckTelNoReqDto req);

    /** 납부방법 가용 코드 목록 반환 (계좌이체/신용카드/지로). */
    Map<String, Object> getPayMethodList();

    /** 계좌번호 유효성 체크 (형식 검증. IF_0016 연동 예정). */
    Map<String, Object> checkAccountNo(String bankCd, String accountNo);

    /** 카드번호 유효성 체크 (형식 검증. IF_0017 연동 예정). */
    Map<String, Object> checkCardNo(String cardNo, String cardYy, String cardMm);

    /** 청구계정ID 유효성 체크 (형식/길이 검증). */
    Map<String, Object> checkBillingAccountId(String billingAccountId);
}
