package com.ktmmobile.msf.domains.form.extra.receipt.application.port.out;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.entity.ReceiptPage;

public interface ReceiptPageRepository {

    Page<ReceiptPage> selectList(ReceiptPageCondition condition);
}
