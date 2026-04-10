package com.ktmmobile.mcp.coupon.dao;

import java.util.List;

import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.dto.CouponOutsideDto;

public interface CouponDao {

    public List<CouponCategoryDto> selectCouponCategoryList(CouponCategoryDto couponCategoryDto,int skipResult, int maxResult);
    public CouponCategoryDto selectCouponCategory(CouponCategoryDto couponCategoryDto);
    public int countByselectCouponCategoryList(CouponCategoryDto couponCategoryDto);
    public List<CoupInfoDto> getUseCoupInfoList(CoupInfoDto coupInfoDto);
    public List<CoupInfoDto> getUsedCoupInfoList(CoupInfoDto coupInfoDto);
    public CouponOutsideDto getCouponOutside(CouponOutsideDto couponOutsideDto);
    public int updateCouponOutside(CouponOutsideDto couponOutsideDto);
    /***20230316 wooki 추가 start****/
    public  List<CouponCategoryDto> getMbershipList(CouponCategoryDto couponCategoryDto);
    public List<CouponCategoryDto> getMbershipListPaged(CouponCategoryDto couponCategoryDto, int skipResult, int maxResult);
    public int countMbershipList();
    public int countMbershipLoginList(CouponCategoryDto couponCategoryDto);
    public CouponCategoryDto getMbershipDtl(CouponCategoryDto couponCategoryDto);
    public CouponCategoryDto getMbershipDetail(CouponCategoryDto couponCategoryDto);
    public CouponCategoryDto getCouponDupInfo(CouponCategoryDto couponCategoryDto);
    public int updateCouponMbership(CouponOutsideDto couponOutsideDto);
    public CouponCategoryDto getCouponInfoForSms(CouponOutsideDto couponOutsideDto);
    public int updateMbershipSmsFail(CouponOutsideDto couponOutsideDto);
    public int getCouponLeftQnty(String coupnCtgCd);
    public int insertCoupnFailHist(CouponOutsideDto couponOutsideDto);
    /***20230316 wooki 추가 end****/

    int getCouponMenuAccessCnt();

    CouponCategoryDto getBenefitCouponDetail(CouponCategoryDto couponCategoryDto);
}
