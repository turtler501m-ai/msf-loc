package com.ktmmobile.mcp.coupon.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.coupon.dao.CouponDao;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.dto.CouponOutsideDto;
import com.ktmmobile.mcp.coupon.dto.McpUserCertDto;

@Service
public class CouponServiceImpl implements CouponService{
    //private static Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    CouponDao couponDao;

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    /** API 서버 */
    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    public String parseDateForDb(String text,boolean isStartDate) {
        String rtnText = "";
        String paramText = text;
        String replaceText = paramText.replaceAll("-", "");
        if (isStartDate) {
            rtnText = replaceText+"000000";
        }else {
            rtnText = replaceText+"235959";
        }
        return rtnText;
    }

    public List<CouponCategoryDto> selectCouponCategoryList(CouponCategoryDto couponCategoryDto,int skipResult, int maxResult){
        return couponDao.selectCouponCategoryList(couponCategoryDto, skipResult,  maxResult);
    }

    public CouponCategoryDto selectCouponCategory(CouponCategoryDto couponCategoryDto){
        return couponDao.selectCouponCategory(couponCategoryDto);
    }

    public int countByselectCouponCategoryList(CouponCategoryDto couponCategoryDto) {
        return couponDao.countByselectCouponCategoryList(couponCategoryDto);
    }

    public List<CoupInfoDto> getUseCoupInfoList(CoupInfoDto coupInfoDto){
        return couponDao.getUseCoupInfoList(coupInfoDto);
    }
    public List<CoupInfoDto> getUsedCoupInfoList(CoupInfoDto coupInfoDto){
        return couponDao.getUsedCoupInfoList(coupInfoDto);
    }
    public CouponOutsideDto getCouponOutside(CouponOutsideDto couponOutsideDto){
        return couponDao.getCouponOutside(couponOutsideDto);
    }
    public int updateCouponOutside(CouponOutsideDto couponOutsideDto) {
        return couponDao.updateCouponOutside(couponOutsideDto);
    }

    @Override
    public  List<CouponCategoryDto> getMbershipList(CouponCategoryDto couponCategoryDto) {
        return couponDao.getMbershipList(couponCategoryDto);
    }

    @Override
    public CouponCategoryDto getMbershipDtl(CouponCategoryDto couponCategoryDto) {
        return couponDao.getMbershipDtl(couponCategoryDto);
    }

    @Override
    public CouponCategoryDto getMbershipDetail(CouponCategoryDto couponCategoryDto) {
        return couponDao.getMbershipDetail(couponCategoryDto);
    }

    @Override
    public CouponCategoryDto getCouponDupInfo(CouponCategoryDto couponCategoryDto) {
        return couponDao.getCouponDupInfo(couponCategoryDto);
    }

    @Override
    public int updateCouponMbership(CouponOutsideDto couponOutsideDto) {
        return couponDao.updateCouponMbership(couponOutsideDto);
    }

    @Override
    public CouponCategoryDto getCouponInfoForSms(CouponOutsideDto couponOutsideDto) {
        return couponDao.getCouponInfoForSms(couponOutsideDto);
    }

    @Override
    public int updateMbershipSmsFail(CouponOutsideDto couponOutsideDto) {
        return couponDao.updateMbershipSmsFail(couponOutsideDto);
    }

    @Override
    public List<McpUserCertDto> getMcpUserCntrInfoA(McpUserCertDto mcpUserCertDto) {

        RestTemplate restTemplate = new RestTemplate();
        McpUserCertDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/usermanage/getMcpUserCntrInfoA", mcpUserCertDto, McpUserCertDto[].class);

        List<McpUserCertDto> returnDtoList = null;
        if(Optional.ofNullable(resultList).isPresent() && 0 != resultList.length) {
            returnDtoList = Arrays.asList(resultList);
        }

        return returnDtoList;
    }

    @Override
    public List<McpUserCertDto> getMcpUserCntrInfoAByUserId(McpUserCertDto mcpUserCertDto) {

        RestTemplate restTemplate = new RestTemplate();
        McpUserCertDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/usermanage/getMcpUserCntrInfoAByUserId", mcpUserCertDto, McpUserCertDto[].class);

        List<McpUserCertDto> returnDtoList = null;
        if(Optional.ofNullable(resultList).isPresent() && 0 != resultList.length) {
            returnDtoList = Arrays.asList(resultList);
        }

        return returnDtoList;
    }

    @Override
    public int getCouponLeftQnty(String coupnCtgCd) {
        return couponDao.getCouponLeftQnty(coupnCtgCd);
    }

    @Override
    public int insertCoupnFailHist(CouponOutsideDto couponOutsideDto) {
        return couponDao.insertCoupnFailHist(couponOutsideDto);
    }

    @Override
    public int getCouponMenuAccessCnt() {
        return couponDao.getCouponMenuAccessCnt();
    }

    @Override
    public int countMbershipList() {
        return couponDao.countMbershipList();
    }

    @Override
    public int countMbershipLoginList(String rateCd) {
        CouponCategoryDto couponCategoryDto = new CouponCategoryDto();

        UserSessionDto userCookieBean = SessionUtils.getUserCookieBean();
        if (userCookieBean != null) {
            couponCategoryDto.setUserId(userCookieBean.getUserId());
            couponCategoryDto.setUserDivision(userCookieBean.getUserDivision());
            couponCategoryDto.setRateCd(rateCd);
        }

        return couponDao.countMbershipLoginList(couponCategoryDto);
    }

    @Override
    public List<CouponCategoryDto> getMbershipListPaged(int skipResult, int maxResult) {
        CouponCategoryDto couponCategoryDto = new CouponCategoryDto();

        UserSessionDto userCookieBean = SessionUtils.getUserCookieBean();
        if (userCookieBean != null) {
            couponCategoryDto.setUserId(userCookieBean.getUserId());
            couponCategoryDto.setUserDivision(userCookieBean.getUserDivision());
        }

        return couponDao.getMbershipListPaged(couponCategoryDto, skipResult, maxResult);
    }

    @Override
    public List<CouponCategoryDto> getMbershipListPaged(int skipResult, int maxResult, String rateCd) {
        CouponCategoryDto couponCategoryDto = new CouponCategoryDto();

        UserSessionDto userCookieBean = SessionUtils.getUserCookieBean();
        if (userCookieBean != null) {
            couponCategoryDto.setUserId(userCookieBean.getUserId());
            couponCategoryDto.setUserDivision(userCookieBean.getUserDivision());
            couponCategoryDto.setRateCd(rateCd);
        }

        return couponDao.getMbershipListPaged(couponCategoryDto, skipResult, maxResult);
    }

    @Override
    public CouponCategoryDto getBenefitCouponDetail(CouponCategoryDto couponCategoryDto) {
        return couponDao.getBenefitCouponDetail(couponCategoryDto);
    }

}
