package com.ktmmobile.mcp.coupon.controller;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.mapper.CouponMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@RestController
public class CouponController {

    private static Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponMapper couponMapper;


    /** @Description : 이벤트코드 상세 요금제 리스트 조회 */
    @RequestMapping(value = "/coupon/selectCouponPlanList", method = RequestMethod.POST)
    public List<CouponCategoryDto> selectCouponPlanList(@RequestBody String coupnCtgCd){

        List<CouponCategoryDto> result = null;

        try {

            result = couponMapper.selectCouponPlanList(coupnCtgCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

}
