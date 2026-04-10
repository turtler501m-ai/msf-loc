package com.ktmmobile.mcp.coupon.service;

import java.util.List;
import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.dto.CouponOutsideDto;
import com.ktmmobile.mcp.coupon.dto.McpUserCertDto;

public interface CouponService {

        public List<CouponCategoryDto> selectCouponCategoryList(CouponCategoryDto couponCategoryDto,int skipResult, int maxResult);
        public CouponCategoryDto selectCouponCategory(CouponCategoryDto couponCategoryDto);
        public int countByselectCouponCategoryList(CouponCategoryDto couponCategoryDto);
        public List<CoupInfoDto> getUseCoupInfoList(CoupInfoDto coupInfoDto);
        public List<CoupInfoDto> getUsedCoupInfoList(CoupInfoDto coupInfoDto);
        public CouponOutsideDto getCouponOutside(CouponOutsideDto couponOutsideDto);
        public int updateCouponOutside(CouponOutsideDto couponOutsideDto);
        /***20230316 wooki 추가 start****/
        //M쿠폰 리스트 조회
        public List<CouponCategoryDto> getMbershipList(CouponCategoryDto couponCategoryDto);
        public CouponCategoryDto getMbershipDtl(CouponCategoryDto couponCategoryDto);
        public CouponCategoryDto getMbershipDetail(CouponCategoryDto couponCategoryDto);
        public CouponCategoryDto getCouponDupInfo(CouponCategoryDto couponCategoryDto);
        public int updateCouponMbership(CouponOutsideDto couponOutsideDto);
        public CouponCategoryDto getCouponInfoForSms(CouponOutsideDto couponOutsideDto);
        public int updateMbershipSmsFail(CouponOutsideDto couponOutsideDto);
        public List<McpUserCertDto> getMcpUserCntrInfoA(McpUserCertDto mcpUserCertDto);
        public List<McpUserCertDto> getMcpUserCntrInfoAByUserId(McpUserCertDto mcpUserCertDto);
        public int getCouponLeftQnty(String coupnCtgCd);
        /***20230316 wooki 추가 end****/
        public int insertCoupnFailHist(CouponOutsideDto couponOutsideDto);

        int getCouponMenuAccessCnt();

    int countMbershipList();
    int countMbershipLoginList(String rateCd);

    List<CouponCategoryDto> getMbershipListPaged(int skipResult, int maxResult);
    List<CouponCategoryDto> getMbershipListPaged(int skipResult, int maxResult, String rateCd);
    CouponCategoryDto getBenefitCouponDetail(CouponCategoryDto couponCategoryDto);
}
