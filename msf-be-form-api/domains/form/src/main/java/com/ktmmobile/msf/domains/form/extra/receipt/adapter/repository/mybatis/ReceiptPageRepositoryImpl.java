package com.ktmmobile.msf.domains.form.extra.receipt.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.receipt.adapter.repository.mybatis.smartform.mapper.ReceiptPageMapper;
import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.application.port.out.ReceiptPageRepository;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.entity.ReceiptPage;

@RequiredArgsConstructor
@Repository
public class ReceiptPageRepositoryImpl implements ReceiptPageRepository {

    private final ReceiptPageMapper receiptPageMapper;

    @Override
    public Page<ReceiptPage> selectList(ReceiptPageCondition condition) {
        int totalCount = receiptPageMapper.count(condition);
        List<ReceiptPage> data = receiptPageMapper.selectList(condition);
        return Page.of(data, condition.page(), totalCount);
    }
}
