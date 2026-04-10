package com.ktmmobile.mcp.common.controller;

import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.ktmmobile.mcp.common.constants.Constants.POPUP_CONTENT_TYPE_EDITOR;

@Controller
public class DbCacheController {

    private static final Logger logger = LoggerFactory.getLogger(DbCacheController.class);

    @Autowired
    private FCommonSvc fCommonSvc;


    /**
     * 설명 : 캐시현행화-공통코드
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/resetComCodeListAjax.do"})
    @ResponseBody
    public JsonReturnDto resetComCodeListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        try {
            fCommonSvc.getCodeCahe();

            returnCode = "00";
            message = "성공";
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);

        return result;
    }


    /**
     * 설명 : 캐시-공통코드
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/getComCodeListAjax.do","/m/getComCodeListAjax.do"})
    @ResponseBody
    public JsonReturnDto getComCodeListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        String grpCd = StringUtil.NVL(request.getParameter("grpCd"),"");

        List<NmcpCdDtlDto> mplatFormServiceWorkList = NmcpServiceUtils.getCodeList(grpCd); //grpCd

        if(mplatFormServiceWorkList != null) {
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("mplatFormServiceWorkList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(mplatFormServiceWorkList);

        return result;
    }


    /**
     * 설명 : 공통코드 조회 ajax
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param nmcpCdDtlDto
     * @return
     */
    @RequestMapping({"/getCodeNmAjax.do", "/m/getCodeNmAjax.do"})
    @ResponseBody
    public NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto)  {

        NmcpCdDtlDto rtnObj = NmcpServiceUtils.getCodeNmObj(nmcpCdDtlDto);

        if (rtnObj == null) {
            rtnObj = new NmcpCdDtlDto();
        }

        return rtnObj;
     }


   /**
     * 설명 : 공통코드 조회 ajax
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param grpCd
     * @return
     */
    @RequestMapping("/getCodeListAjax.do")
    @ResponseBody
    public List<NmcpCdDtlDto> getCodeList(String grpCd)  {
        List<NmcpCdDtlDto> rtnList = new ArrayList<>();
        if (StringUtil.isNotEmpty(grpCd)) {
            rtnList = NmcpServiceUtils.getCodeList(grpCd);
        }
        return rtnList != null ? rtnList : null ;
    }


    /**
     * 설명 : 캐시현행화-메뉴, 작업공지포함
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/resetMenuListAjax.do"})
    @ResponseBody
    public JsonReturnDto resetMenuListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        try {
            fCommonSvc.getMenuCahe();
            fCommonSvc.getMenuAuthCahe();
            fCommonSvc.getMenuUrlNotiCahe();
            fCommonSvc.getAcesAlwdCahe();

            returnCode = "00";
            message = "성공";
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);

        return result;
    }


    /**
     * 설명 : 캐시-메뉴
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/getMenuListAjax.do","/m/getMenuListAjax.do"})
    @ResponseBody
    public JsonReturnDto getMenuListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        String menuOutputCd = StringUtil.NVL(request.getParameter("menuOutputCd"),"");

        List<SiteMenuDto> menuList = NmcpServiceUtils.getMenuList(menuOutputCd); //menuOutputCd

        if(menuList != null) {
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("menuList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(menuList);

        return result;
    }


    /**
     * 설명 : 캐시현행화-배너
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/resetBannerListAjax.do"})
    @ResponseBody
    public JsonReturnDto resetBannerListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        try {
            fCommonSvc.getBannerCahe();
            fCommonSvc.getBannerApdCahe();
            fCommonSvc.getBannerTextCahe();
            fCommonSvc.getBannerFloatCahe();

            returnCode = "00";
            message = "성공";
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);

        return result;
    }


    /**
     * 설명 : 캐시-배너
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/getBannerListAjax.do","/m/getBannerListAjax.do"})
    @ResponseBody
    public JsonReturnDto getBannerListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        String bannCtg = StringUtil.NVL(request.getParameter("bannCtg"),"");

        List<BannerDto> bannerList = NmcpServiceUtils.getBannerList(bannCtg); //bannCtg

        if(bannerList != null) {
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("menuList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(bannerList);

        return result;
    }


    /**
     * 설명 : 캐시현행화-팝업
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/resetPopupListAjax.do"})
    @ResponseBody
    public JsonReturnDto resetPopupListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        try {
            fCommonSvc.getPopupCahe();

            returnCode = "00";
            message = "성공";
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);

        return result;
    }


    /**
     * 설명 : 캐시-팝업-outputMenu
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/getPopupListAjax.do","/m/getPopupListAjax.do"})
    @ResponseBody
    public JsonReturnDto getPopupListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        //String outputMenu = StringUtil.NVL(request.getParameter("outputMenu"),"");
        String popupShowUrl = StringUtil.decodeXssTag(StringUtil.NVL(request.getParameter("popupShowUrl"),""));



        List<PopupDto> popupList = NmcpServiceUtils.getPopupList(popupShowUrl); //outputMenu

        if(popupList != null) {
            returnCode = "00";
            message = "성공";

            //ContentType check
            for(PopupDto pDto : popupList) {
                if(POPUP_CONTENT_TYPE_EDITOR.equals(pDto.getContentType()) && !StringUtil.isEmpty(pDto.getPopupSbst())) { //ContentType이 'EDITOR'면서 Edit 내용 있을 때에만
                    String popupSbsst = pDto.getPopupSbst();

                    popupSbsst = popupSbsst.replaceAll("&lt;", "<");
                    popupSbsst = popupSbsst.replaceAll("&gt;", ">");
                    popupSbsst = popupSbsst.replaceAll("&quot;", "\"");
                    popupSbsst = popupSbsst.replaceAll("&#39;", "'");
                    popupSbsst = popupSbsst.replaceAll("&amp;", "&");
                    popupSbsst = popupSbsst.replaceAll("amp;", "");// &amp;amp;  중복으로 존재 할때.. 삭제를 위해 한번 더.. 처리
                    pDto.setPopupSbst(popupSbsst);
                }
            }
        } else {
            logger.debug("popupList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(popupList);

        return result;
    }


    /**
     * 설명 : 캐시현행화-작업공지
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/resetWorkNotiListAjax.do"})
    @ResponseBody
    public JsonReturnDto resetWorkNotiListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        try {
            fCommonSvc.getMenuUrlNotiCahe();
            fCommonSvc.getAcesAlwdCahe();

            returnCode = "00";
            message = "성공";
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);

        return result;
    }


    /**
     * 설명 : 캐시-작업공지
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/getWorkNotiListAjax.do","/m/getWorkNotiListAjax.do"})
    @ResponseBody
    public JsonReturnDto getWorkNotiListAjax(HttpServletRequest request, Model model){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        String cdChkY = StringUtil.NVL(request.getParameter("cdChkY"),"");

        List<WorkNotiDto> workNotiList = NmcpServiceUtils.getWorkNotiList(cdChkY); //workNoti (작업공지) / mustLogin(로그인필수여부)

        if(workNotiList != null) {
            returnCode = "00";
            message = "성공";
        } else {
            logger.debug("workNotiList is null");
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(workNotiList);

        return result;
    }

    @RequestMapping(value = {"/resetRateAdsvcGdncVersionAjax.do", "/m//resetRateAdsvcGdncVersionAjax.do"})
    @ResponseBody
    public JsonReturnDto resetRateAdsvcGdncVersionAjax(HttpServletRequest request, Model model){
        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        try {
            fCommonSvc.getRateAdsvcGdncVersionCache();

            returnCode = "00";
            message = "성공";
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);

        return result;
    }
}
