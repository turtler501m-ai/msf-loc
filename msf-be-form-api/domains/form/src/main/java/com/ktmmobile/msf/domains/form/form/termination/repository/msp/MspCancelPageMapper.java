package com.ktmmobile.msf.domains.form.form.termination.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessUpdateDto;

@Mapper
public interface MspCancelPageMapper {

    Integer selectPrePayment(String contractNum);

    int updateMcpCancelRequestProcCd(ProcessUpdateDto req);
}
