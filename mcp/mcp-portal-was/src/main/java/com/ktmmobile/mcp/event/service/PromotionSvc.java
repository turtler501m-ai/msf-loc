package com.ktmmobile.mcp.event.service;

import com.ktmmobile.mcp.event.dto.PromotionDto;

public interface PromotionSvc {

    public int getDoubleCheckCtn(PromotionDto promotionDto);

    public int promotionReg(PromotionDto promotionDto);

    String getDocumentContent(String cdGroupId1, String cdGroupId2);
}
