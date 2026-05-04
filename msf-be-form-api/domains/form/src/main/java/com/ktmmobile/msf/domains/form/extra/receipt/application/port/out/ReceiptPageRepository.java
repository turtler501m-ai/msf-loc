package com.ktmmobile.msf.domains.form.extra.receipt.application.port.out;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.vo.ReceiptVo;

public interface ReceiptPageRepository {

    Page<ReceiptVo> selectReceiptList(ReceiptPageCondition condition);
}
