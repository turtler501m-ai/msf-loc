package com.ktmmobile.msf.domains.form.extra.receipt.application.port.in;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageResponse;

public interface ReceiptPageReader {

    PagedDataResponse<ReceiptPageResponse> getList(ReceiptPageCondition condition);
}
