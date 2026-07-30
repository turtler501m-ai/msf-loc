package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;

@Mapper
public interface NewChangeMspReadMapper {

    // 평생할인 프로모션 가입유형 조회
    String selectDisPrmtSlsTp(MspSaleSubsdMstRequest condition);

    // 평생할인 프로모션 ID 조회
    List<String> selectDisPrmtId(MspSaleSubsdMstRequest condition);

    // 프로모션 기본 요금 조회
    Long selectPromoBaseAmt(List<String> prmtIds);
}
