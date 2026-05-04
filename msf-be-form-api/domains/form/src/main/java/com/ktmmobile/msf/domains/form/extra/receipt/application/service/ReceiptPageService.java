package com.ktmmobile.msf.domains.form.extra.receipt.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageListResponse;
import com.ktmmobile.msf.domains.form.extra.receipt.application.fieldmapper.ReceiptPageFieldMapper;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.in.ReceiptPageReader;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.in.ReceiptPageWriter;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.out.ReceiptPageRepository;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.vo.ReceiptVo;

@Service
@RequiredArgsConstructor
public class ReceiptPageService implements ReceiptPageReader, ReceiptPageWriter {

    private ReceiptPageRepository receiptPageRepository;
    private ReceiptPageFieldMapper receiptPageFieldMapper;

    @Override public PagedDataResponse<ReceiptPageListResponse> getReceiptList(ReceiptPageCondition condition) {
        Page<ReceiptVo> page = receiptPageRepository.selectReceiptList(condition);
        return PagedDataResponse.of(page, receiptPageFieldMapper::toReceiptPageListResponse);
    }
}
