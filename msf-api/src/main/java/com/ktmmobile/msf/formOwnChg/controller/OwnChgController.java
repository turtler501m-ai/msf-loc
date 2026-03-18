package com.ktmmobile.msf.formOwnChg.controller;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgApplyVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgCheckTelNoVO;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkReqDto;
import com.ktmmobile.msf.formOwnChg.dto.OwnChgGrantorReqChkVO;
import com.ktmmobile.msf.formOwnChg.service.OwnChgAddSvc;
import com.ktmmobile.msf.formOwnChg.service.OwnChgApplySvc;
import com.ktmmobile.msf.formOwnChg.service.OwnChgInfoSvc;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 명의변경 신청서 REST 컨트롤러.
 * 설계서 S103010101~S103040101.
 */
@RestController
@RequestMapping("/api/v1/ident")
public class OwnChgController {

    @Autowired
    private OwnChgInfoSvc ownChgInfoSvc;

    @Autowired
    private OwnChgAddSvc ownChgAddSvc;

    @Autowired
    private OwnChgApplySvc ownChgApplySvc;

    /**
     * 명의변경 가능 여부 조회 (MC0 연동 예정).
     * POST /api/v1/ident/eligible
     */
    @PostMapping("/eligible")
    public Map<String, Object> eligible(@RequestBody SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        boolean ok = ownChgInfoSvc.eligible(req);
        result.put("eligible", ok);
        result.put("success", true);
        return result;
    }

    /**
     * 양도인 신청가능여부 체크 (M전산 cntrListNmChg).
     * POST /api/v1/ident/grantor-req-chk
     */
    @PostMapping("/grantor-req-chk")
    public OwnChgGrantorReqChkVO grantorReqChk(@RequestBody OwnChgGrantorReqChkReqDto req) {
        return ownChgInfoSvc.grantorReqChk(req);
    }

    /**
     * 명의변경 회선 vs 연락처 상이 검증 (nameChgChkTelNo).
     * POST /api/v1/ident/check-tel-no
     */
    @PostMapping("/check-tel-no")
    public OwnChgCheckTelNoVO checkTelNo(@RequestBody OwnChgCheckTelNoReqDto req) {
        return ownChgInfoSvc.checkTelNo(req);
    }

    /**
     * X20 현재 가입 부가서비스 조회 (명의변경용).
     * POST /api/v1/ident/addition/current
     */
    @PostMapping("/addition/current")
    public AdditionCurrentResVO additionCurrent(@RequestBody SvcChgInfoDto req) {
        return ownChgAddSvc.selectAdditionCurrent(req);
    }

    /**
     * 명의변경 신청서 등록 (양도인/양수인 저장).
     * POST /api/v1/ident/apply
     */
    @PostMapping("/apply")
    public OwnChgApplyVO apply(@RequestBody OwnChgApplyDto req) {
        return ownChgApplySvc.apply(req);
    }
}
