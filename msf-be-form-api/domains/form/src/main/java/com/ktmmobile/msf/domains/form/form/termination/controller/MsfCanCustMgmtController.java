package com.ktmmobile.msf.domains.form.form.termination.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListResDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessResVO;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCanCustMgmtSvc;

/**
 * 관리자 해지상담 처리 컨트롤러
 * ASIS: MSP CanCustController (canCanCslMgmt)
 * TOBE: MSF 관리자 화면 → 접수완료 건 목록조회·상세확인·처리완료(EP0 실시간 해지)
 */
@RestController
@RequestMapping({"/api/msf/admin/cancel", "/api/msf/admin/application"})
public class MsfCanCustMgmtController {

    private static final Logger logger = LoggerFactory.getLogger(MsfCanCustMgmtController.class);

    @Autowired
    private MsfCanCustMgmtSvc msfCanCustMgmtSvc;

    /**
     * 목록 조회
     * ASIS: selectCanCslList.do
     * POST /api/msf/admin/cancel/list
     */
    @PostMapping("/list")
    public ListResDto list(@RequestBody ListReqDto req) {
        logger.info("[admin/cancel/list] procCd={}, formTypeCd={}, searchGbn={}, searchName={}, startDt={}, endDt={}",
                req.getProcCd(), req.getFormTypeCd(), req.getSearchGbn(), req.getSearchName(), req.getStartDt(), req.getEndDt());
        return msfCanCustMgmtSvc.selectAppFormList(req);
    }

    /**
     * 상세 조회 (신청서류 확인)
     * POST /api/msf/admin/cancel/get
     */
    @PostMapping("/get")
    public DetailDto get(@RequestBody ProcessReqDto req) {
        logger.info("[admin/cancel/get] requestKey={}", req.getRequestKey());
        if (req.getRequestKey() == null) {
            return null;
        }
        return msfCanCustMgmtSvc.selectCanCustDetail(req.getRequestKey());
    }

    /**
     * 처리 가능 여부 확인 (이중처리 방지 사전 체크)
     * ASIS: getCustStatus.json → PROC_CD='CP' 이면 NOK
     * POST /api/msf/admin/cancel/status/check
     */
    @PostMapping("/status/check")
    public ProcessResVO statusCheck(@RequestBody ProcessReqDto req) {
        logger.info("[admin/cancel/status/check] requestKey={}", req.getRequestKey());
        if (req.getRequestKey() == null) {
            return ProcessResVO.fail("신청번호(requestKey)가 없습니다.");
        }
        DetailDto detail = msfCanCustMgmtSvc.selectCanCustDetail(req.getRequestKey());
        if (detail == null) {
            return ProcessResVO.fail("해지신청 건을 찾을 수 없습니다.");
        }
        if ("CP".equals(detail.getProcCd())) {
            return ProcessResVO.fail("이미 처리완료된 건입니다.");
        }
        return ProcessResVO.ok(null);
    }

    /**
     * 처리완료 — EP0(M플랫폼 실시간 해지) 호출 후 PROC_CD='CP' 저장
     * ASIS: updateCanCsl.json (DB 상태만) + BATCH00233(실해지)
     * TOBE: EP0 실시간 → 성공 시에만 PROC_CD='CP'
     * POST /api/msf/admin/cancel/complete
     */
    @PostMapping("/complete")
    public ProcessResVO complete(@RequestBody ProcessReqDto req) {
        logger.info("[admin/cancel/complete] requestKey={}, itgOderWhyCd={}, aftmnIncInCd={}, apyRelTypeCd={}, custTchMediCd={}",
                req.getRequestKey(), req.getItgOderWhyCd(), req.getAftmnIncInCd(),
                req.getApyRelTypeCd(), req.getCustTchMediCd());

        if (req.getRequestKey() == null) {
            return ProcessResVO.fail("신청번호(requestKey)가 없습니다.");
        }
        if (StringUtils.isBlank(req.getItgOderWhyCd())) {
            return ProcessResVO.fail("해지사유코드(itgOderWhyCd)가 없습니다.");
        }
        if (StringUtils.isBlank(req.getAftmnIncInCd())) {
            return ProcessResVO.fail("해지후성향코드(aftmnIncInCd)가 없습니다.");
        }
        if (StringUtils.isBlank(req.getApyRelTypeCd())) {
            return ProcessResVO.fail("고객접촉매체코드(apyRelTypeCd)가 없습니다.");
        }
        if (StringUtils.isBlank(req.getCustTchMediCd())) {
            return ProcessResVO.fail("신청관계유형코드(custTchMediCd)가 없습니다.");
        }

        return msfCanCustMgmtSvc.processComplete(req);
    }

    /**
     * 완료취소 — PROC_CD를 'RC'(접수)로 되돌림
     * POST /api/msf/admin/cancel/revert
     */
    @PostMapping("/revert")
    public ProcessResVO revert(@RequestBody ProcessReqDto req) {
        logger.info("[admin/cancel/revert] requestKey={}", req.getRequestKey());
        if (req.getRequestKey() == null) {
            return ProcessResVO.fail("신청번호(requestKey)가 없습니다.");
        }
        return msfCanCustMgmtSvc.processRevert(req.getRequestKey());
    }
}
