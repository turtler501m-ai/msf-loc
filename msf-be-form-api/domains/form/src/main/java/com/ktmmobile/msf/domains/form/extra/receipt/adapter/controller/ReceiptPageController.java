package com.ktmmobile.msf.domains.form.extra.receipt.adapter.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageListResponse;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.in.ReceiptPageReader;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.in.ReceiptPageWriter;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageListResponse;

@RestController
@RequiredArgsConstructor
public class ReceiptPageController {

    private final ReceiptPageReader receiptPageReader;
    private final ReceiptPageWriter receiptPageWriter;

    @PostMapping("/api/receiptpage/list")
    public CommonResponse<List<ReceiptPageListResponse>> getReceiptList(
        @RequestBody ReceiptPageCondition condition
    ) {
        return ResponseUtils.ok(receiptPageReader.getReceiptList(condition));
    }

}
