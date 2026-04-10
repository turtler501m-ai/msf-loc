package com.ktmmobile.mcp.coupon.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.dto.CouponOutsideDto;


@Repository
public
class CouponDaoImpl implements CouponDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<CouponCategoryDto> selectCouponCategoryList(CouponCategoryDto couponCategoryDto,int skipResult, int maxResult){
        return sqlSessionTemplate.selectList("MyBenefitMapper.selectCouponCategoryList",couponCategoryDto,new RowBounds(skipResult, maxResult));
    }

    public CouponCategoryDto selectCouponCategory(CouponCategoryDto couponCategoryDto){
        return sqlSessionTemplate.selectOne("MyBenefitMapper.selectCouponCategory",couponCategoryDto);
    }

    public int countByselectCouponCategoryList(CouponCategoryDto couponCategoryDto) {
        Object resultObj = sqlSessionTemplate.selectOne("MyBenefitMapper.countByselectCouponCategoryList",couponCategoryDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "boardSql.countBylistBoardBas"));
        }
    }

    //2023.03.15 wooki 수정
    public List<CoupInfoDto> getUseCoupInfoList(CoupInfoDto coupInfoDto){
        if("MB".equals(coupInfoDto.getCoupnDivCd())) {
            return sqlSessionTemplate.selectList("MyBenefitMapper.getMbershipInfoList",coupInfoDto);
        }else {
            return sqlSessionTemplate.selectList("MyBenefitMapper.getUseCoupInfoList",coupInfoDto);
        }

    }
    public List<CoupInfoDto> getUsedCoupInfoList(CoupInfoDto coupInfoDto){
        return sqlSessionTemplate.selectList("MyBenefitMapper.getUsedCoupInfoList",coupInfoDto);
    }

    public CouponOutsideDto getCouponOutside(CouponOutsideDto couponOutsideDto){
        return sqlSessionTemplate.selectOne("MyBenefitMapper.getCouponOutside",couponOutsideDto);
    }

    public int updateCouponOutside(CouponOutsideDto couponOutsideDto) {
        return sqlSessionTemplate.update("MyBenefitMapper.updateCouponOutside",couponOutsideDto);
    }

    @Override
    public List<CouponCategoryDto> getMbershipList(CouponCategoryDto couponCategoryDto) {
        return sqlSessionTemplate.selectList("MyBenefitMapper.getMbershipList", couponCategoryDto);
    }

    @Override
    public List<CouponCategoryDto> getMbershipListPaged(CouponCategoryDto couponCategoryDto, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("MyBenefitMapper.getMbershipList", couponCategoryDto, new RowBounds(skipResult, maxResult));
    }

    @Override
    public int countMbershipList() {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.countMbershipList");
    }

    @Override
    public int countMbershipLoginList(CouponCategoryDto couponCategoryDto) {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.countMbershipLoginList", couponCategoryDto);
    }

    @Override
    public CouponCategoryDto getMbershipDtl(CouponCategoryDto couponCategoryDto) {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.getMbershipDtl", couponCategoryDto);
    }

    @Override
    public CouponCategoryDto getMbershipDetail(CouponCategoryDto couponCategoryDto) {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.getMbershipDetail", couponCategoryDto);
    }

    @Override
    public CouponCategoryDto getCouponDupInfo(CouponCategoryDto couponCategoryDto) {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.getCouponDupInfo", couponCategoryDto);
    }

    @Override
    public int updateCouponMbership(CouponOutsideDto couponOutsideDto) {
        return sqlSessionTemplate.update("MyBenefitMapper.updateCouponMbership", couponOutsideDto);
    }

    @Override
    public CouponCategoryDto getCouponInfoForSms(CouponOutsideDto couponOutsideDto) {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.getCouponInfoForSms", couponOutsideDto);
    }

    @Override
    public int updateMbershipSmsFail(CouponOutsideDto couponOutsideDto) {
        return sqlSessionTemplate.update("MyBenefitMapper.updateMbershipSmsFail", couponOutsideDto);
    }

    @Override
    public int getCouponLeftQnty(String coupnCtgCd) {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.getCouponLeftQnty", coupnCtgCd);
    }
    @Override
    public int insertCoupnFailHist(CouponOutsideDto couponOutsideDto) {
        return sqlSessionTemplate.insert("MyBenefitMapper.insertCoupnFailHist", couponOutsideDto);
    }

    @Override
    public int getCouponMenuAccessCnt() {
        Object resultObj = sqlSessionTemplate.selectOne("MyBenefitMapper.getCouponMenuAccessCnt");
        if (resultObj instanceof Number) {
            Number number = (Number) resultObj;
            return number.intValue();
        } else {
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "CommonMapper.checkCrawlingCount"));
        }
    }

    @Override
    public CouponCategoryDto getBenefitCouponDetail(CouponCategoryDto couponCategoryDto) {
        return sqlSessionTemplate.selectOne("MyBenefitMapper.getBenefitCouponDetail", couponCategoryDto);
    }
}
