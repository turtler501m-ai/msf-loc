package com.ktmmobile.msf.domains.form.form.newchange.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeValidCheckService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class JoinConditionInfoController {

    private final NewChangeValidCheckService newChangeValidCheckService; //신규/변경 유효성체크

    /**
     * 가입조건조회
     */
    @PostMapping("/eligibility/check")
    public CommonResponse<SubscriptionResponse> getEligibilityCheck(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getEligibilityCheck(request));
    }

    /**
     * 대량 법인 가입조건조회
     */
    @PostMapping("/bulkCorporateOpenInfo/check")
    public CommonResponse<FormResponse<BulkCorporateInfoResponse>> getBulkCorporateOpenCheck(@RequestBody BulkCorporateInfoRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getBulkCorporateOpenCheck(request));
    }

    /**
     * 대량 법인 가능정보 조회
     */
    @PostMapping("/bulkCorporateOpenInfo/get")
    public CommonResponse<BulkCorporateInfoResponse> getBulkCorporateOpenInfo(@RequestBody BulkCorporateInfoRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getBulkCorporateOpenInfo(request));
    }

    /**
     * 1년이내 사용회선 조회
     */
    @PostMapping("/eligibility/actyearcnt/get")
    public CommonResponse<Integer> getActYearCount(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getActYearCnt(request));
    }

    /**
     * 1년이내 해지
     */
    @PostMapping("/eligibility/cancelyearcnt/get")
    public CommonResponse<Integer> getCancelYearCnt(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getCancelYearCnt(request));
    }

    /**
     * 미납조회
     */
    @PostMapping("/eligibility/unpaidcnt/get")
    public CommonResponse<Integer> getUnpaidCnt(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getUnpaidCnt(request));
    }

    /**
     * 당월개통회선
     */
    @PostMapping("/eligibility/actthismonthcnt/get")
    public CommonResponse<Integer> getActThisMonthCnt(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getActThisMonthCnt(request));
    }

    /**
     * 전체 개통 회선
     */
    @PostMapping("/eligibility/acttotalcnt/get")
    public CommonResponse<Integer> getActTotalCnt(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseUtils.ok(newChangeValidCheckService.getActTotalCnt(request));
    }
}
