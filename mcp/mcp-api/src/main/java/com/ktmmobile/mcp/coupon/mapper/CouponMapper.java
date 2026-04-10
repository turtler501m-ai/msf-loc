package com.ktmmobile.mcp.coupon.mapper;

import com.ktmmobile.mcp.coupon.dto.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface CouponMapper {

    List<CouponCategoryDto> selectCouponPlanList(String coupnCtgCd);

}
