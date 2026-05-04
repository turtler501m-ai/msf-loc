package com.ktmmobile.msf.domains.form.extra.receipt.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageCondition;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.vo.ReceiptVo;

@Mapper
public interface ReceiptPageMapper {

    int countReceiptList(ReceiptPageCondition condition);

    List<ReceiptVo> selectReceiptList(ReceiptPageCondition condition);
}
