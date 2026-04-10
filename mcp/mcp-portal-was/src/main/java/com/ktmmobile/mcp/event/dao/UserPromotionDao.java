package com.ktmmobile.mcp.event.dao;

import java.util.List;

import com.ktmmobile.mcp.event.dto.UserPromotionDto;

public interface UserPromotionDao {


    int insertUserPromotion(UserPromotionDto userPromotionDto);

    public int selectUserPromoCnt(UserPromotionDto userPromotionDto);

    int deleteUserPromotion(UserPromotionDto userPromotionDto);

    public List<UserPromotionDto> selectUserPromotionList(UserPromotionDto userPromotiondto);

}
