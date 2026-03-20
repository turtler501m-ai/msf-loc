package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.AccountCheckReqDto;
import com.ktmmobile.msf.formComm.dto.CardCheckReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoResVO;
import com.ktmmobile.msf.formSvcChg.dto.UsimCheckReqDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 가입자정보조회 및 공통 유효성 체크 서비스 인터페이스.
 */
public interface SvcChgInfoSvc {

    /**
     * 고객명 + 전화번호로 M전산 계약정보 조회 → Y04 인증 → X01 가입정보 조회.
     */
    SvcChgInfoResVO joinInfo(SvcChgInfoReqDto req, HttpServletRequest httpRequest);

    /**
     * X85 USIM 번호 유효성 체크 (공통).
     * 서비스변경(USIM 변경) · 명의변경 공용.
     */
    Map<String, Object> checkUsim(UsimCheckReqDto req);

    /**
     * IF_0006 NICE 계좌인증 — 계좌번호 유효성 체크 (공통).
     * ASIS NiceCertifySvcImpl.checkNiceAccount() 동일 연동.
     * nice.ext.url 미설정 시 Mock 성공 반환.
     */
    Map<String, Object> checkAccount(AccountCheckReqDto req);

    /**
     * IF_0007 카드번호 유효성 체크 (공통).
     * ASIS myNameChg.js checkCardNumber() Luhn Algorithm + 유효기간 검증을 서버 사이드로 이전.
     * 외부 API 연동 없음 — 서버 사이드 형식 검증.
     */
    Map<String, Object> checkCard(CardCheckReqDto req);

    /**
     * 청구계정ID(BAN) 조회.
     * ASIS MypageServiceImpl.selectBanSel() 동일 구조.
     * 계약번호(ncn)로 MSP_JUO_SUB_INFO.BAN 조회.
     */
    Map<String, Object> lookupBillingAccount(String contractNum);
}
