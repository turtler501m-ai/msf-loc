package com.ktmmobile.mcp.benefit.service;

import com.ktmmobile.mcp.benefit.dto.BenefitTabViewDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;

import java.util.List;

public interface BenefitService {
    List<NmcpCdDtlDto> getMonthlyPaymentAmountList();

    List<BenefitTabViewDto> getBenefitTabViewCountList();

    void increaseTabViewCount(String tabName);
}
