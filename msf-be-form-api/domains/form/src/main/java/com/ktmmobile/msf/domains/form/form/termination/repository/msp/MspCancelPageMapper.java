package com.ktmmobile.msf.domains.form.form.termination.repository.msp;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MspCancelPageMapper {

    Integer selectPrePayment(String contractNum);
}
