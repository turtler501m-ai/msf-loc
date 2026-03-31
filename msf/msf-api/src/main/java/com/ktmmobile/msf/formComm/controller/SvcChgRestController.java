package com.ktmmobile.msf.formComm.controller;

import com.ktmmobile.msf.formComm.dto.AccountCheckReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoResVO;
import com.ktmmobile.msf.formComm.service.SvcChgInfoSvc;
import com.ktmmobile.msf.formSvcChg.dto.UsimCheckReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 공통 인터페이스 연계 REST 컨트롤러.
 * M플랫폼(Y04/X01/X85)/NICE(IF_0006)/MSP DB링크 등 외부 인터페이스 연동 엔드포인트만 모음.
 * 인터페이스 연계 없는 공통 기능(대리점 조회·카드번호 검증 등)은 FormCommController 참조.
 */
@RestController
@RequestMapping("/api/v1")
public class SvcChgRestController {

    @Autowired
    private SvcChgInfoSvc joinInfoSvc;

    /**
     * 가입자정보조회 (서비스변경).
     * POST /api/v1/join-info
     */
    @PostMapping("/join-info")
    public SvcChgInfoResVO joinInfo(@RequestBody SvcChgInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }

    /**
     * 가입자정보조회 (명의변경).
     * POST /api/v1/ident/join-info
     */
    @PostMapping("/ident/join-info")
    public SvcChgInfoResVO identJoinInfo(@RequestBody SvcChgInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }

    /**
     * 가입자정보조회 (서비스해지).
     * POST /api/v1/cancel/join-info
     */
    @PostMapping("/cancel/join-info")
    public SvcChgInfoResVO cancelJoinInfo(@RequestBody SvcChgInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }

    /**
     * 가입자정보조회 (부가서비스).
     * POST /api/v1/addition/join-info
     */
    @PostMapping("/addition/join-info")
    public SvcChgInfoResVO additionJoinInfo(@RequestBody SvcChgInfoReqDto req, HttpServletRequest httpRequest) {
        return joinInfoSvc.joinInfo(req, httpRequest);
    }

    /**
     * X85 USIM 번호 유효성 체크 (공통).
     * 서비스변경(USIM 변경) · 명의변경 공용.
     * POST /api/v1/comm/usim-check
     */
    @PostMapping("/comm/usim-check")
    public Map<String, Object> commUsimCheck(@RequestBody UsimCheckReqDto req) {
        return joinInfoSvc.checkUsim(req);
    }

    /**
     * IF_0006 NICE 계좌인증 — 계좌번호 유효성 체크 (공통).
     * ASIS NiceCertifyController.checkNiceAccount() 동일 연동.
     * nice.ext.url 미설정 시 Mock 성공 반환.
     * POST /api/v1/comm/account-check
     */
    @PostMapping("/comm/account-check")
    public Map<String, Object> commAccountCheck(@RequestBody AccountCheckReqDto req) {
        return joinInfoSvc.checkAccount(req);
    }

    /**
     * 청구계정ID(BAN) 조회.
     * ASIS MypageServiceImpl.selectBanSel() 동일 구조.
     * 계약번호(ncn) → MSP_JUO_SUB_INFO.BAN 조회 (M플랫폼 DB링크).
     * POST /api/v1/comm/billing-account
     * 요청: { "ncn": "계약번호" }
     */
    @PostMapping("/comm/billing-account")
    public Map<String, Object> commBillingAccount(@RequestBody Map<String, String> req) {
        return joinInfoSvc.lookupBillingAccount(req.get("ncn"));
    }
}
