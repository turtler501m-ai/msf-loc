package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.CombineCheckReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgCombineSvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 아무나 SOLO 결합 Controller.
 * ASIS CombineController 역할.
 */
@RestController
@RequestMapping("/api/v1/service-change/combine")
public class SvcChgCombineController {

    private final SvcChgCombineSvc combineSvc;

    public SvcChgCombineController(SvcChgCombineSvc combineSvc) {
        this.combineSvc = combineSvc;
    }

    /**
     * 아무나 SOLO 결합 가입 가능 여부 체크 (X87 + DB 할인SOC 매핑).
     * POST /api/v1/service-change/combine/check
     */
    @PostMapping("/check")
    public Map<String, Object> checkCombineSelf(@RequestBody CombineCheckReqDto req) {
        return combineSvc.checkCombineSelf(req);
    }

    /**
     * 요금제별 아무나 SOLO 결합 할인SOC 목록 (DB 조회).
     * GET /api/v1/service-change/combine/solo-type?rateCd=XXX
     */
    @GetMapping("/solo-type")
    public Map<String, Object> getCombineSoloType(
            @RequestParam(required = false) String rateCd) {
        return combineSvc.getCombineSoloType(rateCd);
    }

    /**
     * 아무나 SOLO 결합 신청 (Y44).
     * POST /api/v1/service-change/combine/reg
     */
    @PostMapping("/reg")
    public Map<String, Object> regCombineSelf(@RequestBody CombineCheckReqDto req) {
        return combineSvc.regCombineSelf(req);
    }
}
