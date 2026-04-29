package com.ktmmobile.msf.domains.form.form.termination.controller;

import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListResDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessResVO;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCanCustMgmtSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 해지상담 처리 컨트롤러.
 * 기능 판단과 처리 로직은 서비스 계층에서 담당한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/msf/admin/cancel", "/api/msf/admin/application"})
public class MsfCanCustMgmtController {

    private final MsfCanCustMgmtSvc msfCanCustMgmtSvc;

    /** 신청서 목록을 조회한다. */
    @PostMapping("/list")
    public ListResDto list(@RequestBody ListReqDto req) {
        return msfCanCustMgmtSvc.list(req);
    }

    /** 신청서 상세 정보를 조회한다. */
    @PostMapping("/get")
    public DetailDto get(@RequestBody ProcessReqDto req) {
        return msfCanCustMgmtSvc.get(req);
    }

    /** 처리 가능 여부를 확인한다. */
    @PostMapping("/status/check")
    public ProcessResVO statusCheck(@RequestBody ProcessReqDto req) {
        return msfCanCustMgmtSvc.statusCheck(req);
    }

    /** EP0 실시간 해지 후 처리완료 상태로 변경한다. */
    @PostMapping("/complete")
    public ProcessResVO complete(@RequestBody ProcessReqDto req) {
        return msfCanCustMgmtSvc.complete(req);
    }

    /** 처리완료 상태를 접수 상태로 되돌린다. */
    @PostMapping("/revert")
    public ProcessResVO revert(@RequestBody ProcessReqDto req) {
        return msfCanCustMgmtSvc.revert(req);
    }
}
