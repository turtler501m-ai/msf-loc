package com.ktmmobile.mcp.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.event.dao.UserPromotionDao;
import com.ktmmobile.mcp.event.dto.UserPromotionDto;


@Service
public class UserPromotionSvcImpl implements UserPromotionSvc {

    @Autowired
    private UserPromotionDao userPromotionDao;

    @Override
    public int insertUserPromotion(UserPromotionDto userPromotionDto) {
        return userPromotionDao.insertUserPromotion(userPromotionDto);
    }

    @Override
    public int selectUserPromoCnt(UserPromotionDto userPromotionDto) {
        return userPromotionDao.selectUserPromoCnt(userPromotionDto);
    }

    @Override
    public int deleteUserPromotion(UserPromotionDto userPromotionDto) {
        return userPromotionDao.deleteUserPromotion(userPromotionDto);
    }

    @Override
    public List<UserPromotionDto> selectUserPromotionList(UserPromotionDto userPromotiondto){
        return userPromotionDao.selectUserPromotionList(userPromotiondto);
    }

}
