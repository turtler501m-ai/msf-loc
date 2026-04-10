package com.ktmmobile.mcp.event.service;

import java.util.List;

import com.ktmmobile.mcp.event.dto.UserPromotionDto;


public interface UserPromotionSvc {

    int insertUserPromotion(UserPromotionDto userPromotionDto);

    public int selectUserPromoCnt(UserPromotionDto userPromotionDto);

    int deleteUserPromotion(UserPromotionDto userPromotionDto);

    public List<UserPromotionDto> selectUserPromotionList(UserPromotionDto userPromotiondto);
}
