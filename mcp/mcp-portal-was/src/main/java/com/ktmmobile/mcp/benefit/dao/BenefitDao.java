package com.ktmmobile.mcp.benefit.dao;

import com.ktmmobile.mcp.benefit.dto.BenefitTabViewDto;

import java.util.List;

public interface BenefitDao {
    List<BenefitTabViewDto> getBenefitTabViewCountList();

    int increaseTabViewCount(String tabName);
}
