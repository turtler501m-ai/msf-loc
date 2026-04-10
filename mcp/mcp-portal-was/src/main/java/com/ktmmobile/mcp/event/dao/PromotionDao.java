package com.ktmmobile.mcp.event.dao;

import com.ktmmobile.mcp.event.dto.PromotionDto;

public interface PromotionDao {

	public int getDoubleCheckCtn(PromotionDto promotionDto);

	public int promotionReg(PromotionDto promotionDto);


}
