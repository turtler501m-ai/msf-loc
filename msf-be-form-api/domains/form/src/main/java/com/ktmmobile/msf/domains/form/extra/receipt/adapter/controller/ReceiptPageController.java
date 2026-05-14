package com.ktmmobile.msf.domains.form.extra.receipt.adapter.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageResponse;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.in.ReceiptPageReader;

@RestController
@RequiredArgsConstructor
public class ReceiptPageController {

    private final ReceiptPageReader reader;

    @PostMapping("/api/receiptpage/list")
    public CommonResponse<List<ReceiptPageResponse>> getList(@RequestBody ReceiptPageCondition condition) {
        return ResponseUtils.ok(reader.getList(condition));
    }
}
