package com.ktmmobile.msf.domains.form.main.adapter.controller;

import java.util.List;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.main.application.dto.FormMainCountResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.NoticeResponse;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaCondition;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaHitsRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaRequest;
import com.ktmmobile.msf.domains.form.main.application.dto.QnaResponse;
import com.ktmmobile.msf.domains.form.main.application.port.in.FormMainReader;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class FormMainController {

    private final FormMainReader formMainReader;

    @PostMapping("/form/count")
    public CommonResponse<FormMainCountResponse> formCount() {
        return ResponseUtils.ok(formMainReader.getCountFormRequest());
    }

    @PostMapping("/notice/list")
    public CommonResponse<List<NoticeResponse>> noticeList(@RequestBody @Valid NoticeCondition condition) {
        return ResponseUtils.ok(formMainReader.getListNotice(condition));
    }

    @PostMapping("/notice/hits/add")
    public CommonResponse<Boolean> noticeHitsAdd(@RequestBody @Valid NoticeHitsRequest request) {
        return ResponseUtils.ok(formMainReader.addNoticeHits(request));
    }

    @PostMapping("/qna/list")
    public CommonResponse<List<QnaResponse>> qnaList(@RequestBody @Valid QnaCondition condition) {
        return ResponseUtils.ok(formMainReader.getListQna(condition));
    }

    @PostMapping("/qna/hits/add")
    public CommonResponse<Boolean> qnaHitsAdd(@RequestBody @Valid QnaHitsRequest request) {
        return ResponseUtils.ok(formMainReader.addQnaHits(request));
    }

    @PostMapping("/qna/regist")
    public CommonResponse<Boolean> qnaRegist(@RequestBody @Valid QnaRequest request) {
        return ResponseUtils.ok(formMainReader.registQna(request));
    }
}
