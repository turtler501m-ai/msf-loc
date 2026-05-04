package com.ktmmobile.msf.domains.form.extra.receipt.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.extra.receipt.application.dto.ReceiptPageListResponse;
import com.ktmmobile.msf.domains.form.extra.receipt.domain.vo.ReceiptVo;

@Mapper(componentModel = "spring")
public interface ReceiptPageFieldMapper {
    ReceiptPageFieldMapper INSTANCE = Mappers.getMapper(ReceiptPageFieldMapper.class);

    ReceiptPageListResponse toReceiptPageListResponse(ReceiptVo receiptVo);
}
