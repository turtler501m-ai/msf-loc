package com.ktmmobile.msf.domains.form.form.termination.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCancelConsultSvc;

@RestController
@Deprecated
public class MsfCancelConsultController {

    @Autowired
    private MsfCancelConsultSvc msfCancelConsultSvc;

    // [ASIS] 해지 상담 신청 JSP 화면 렌더링 - TOBE는 Vue SPA 구조로 서버 사이드 View 렌더링 불필요
    // @RequestMapping(value = {"/mypage/cancelConsult.do", "/m/mypage/cancelConsult.do"})
    // public String cancelConsult(Model model, HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO) { ... }

    // 해지 상담 신청 AJAX 진입점: 상세 검증과 처리 흐름은 서비스 구현에서 담당한다.
    @RequestMapping(value = "/mypage/cancelConsultAjax.do")
    public CommonResponse<FormResponse<Void>> cancelConsultAjax(@ModelAttribute CancelConsultDto cancelConsultDto, HttpSession session) {
        return ResponseUtils.ok(msfCancelConsultSvc.cancelConsultAjax(cancelConsultDto, session));
    }
}
