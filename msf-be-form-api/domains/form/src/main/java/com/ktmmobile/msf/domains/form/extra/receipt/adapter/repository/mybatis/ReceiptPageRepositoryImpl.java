package com.ktmmobile.msf.domains.form.extra.receipt.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.receipt.adapter.repository.mybatis.smartform.mapper.ReceiptPageMapper;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.out.ReceiptPageRepository;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.vo.ReceiptVo;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.vo.TempSaveVo;

@Repository
@RequiredArgsConstructor
public class ReceiptPageRepositoryImpl implements ReceiptPageRepository {

    private final ReceiptPageMapper receiptPageMapper;

    @Override public Page<ReceiptVo> selectReceiptList(ReceiptPageCondition condition) {
        int totalCount = receiptPageMapper.countReceiptList(condition);
        List<ReceiptVo> data = receiptPageMapper.selectReceiptList(condition);
        return Page.of(data, condition.page(), totalCount);
    }
}
