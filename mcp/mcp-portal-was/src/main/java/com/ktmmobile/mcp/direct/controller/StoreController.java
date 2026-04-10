package com.ktmmobile.mcp.direct.controller;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.BannerDto;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.direct.dto.StoreDto;
import com.ktmmobile.mcp.direct.service.StoreSvc;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : DirectController.java
 * 날짜     : 2018. 1. 10
 * 작성자   :
 * 설명     :
 * </pre>
 */
@Controller
public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);


    @Autowired
    FormDtlSvc formDtlSvc;

    @Autowired
    StoreSvc storeSvc;

    /**
     * 설명 : 편의점/마트 구매하기 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param response
     * @param request
     * @param formDtlDTO
     * @param orgnId
     * @return
     */
    @RequestMapping(value = {"/m/direct/storeInfo.do", "/direct/storeInfo.do"})
    public String storeInfo(Model model, HttpServletResponse response,
            HttpServletRequest request,FormDtlDTO formDtlDTO,
            @RequestParam(value = "orgnId", required = false) String orgnId){

        String rtnPageNm = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/direct/storeInfo";
        } else {
            rtnPageNm = "/portal/direct/storeInfo";
        }

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnPageNm = "/mobile/direct/storeInfo";
        }else {
            rtnPageNm = "/portal/direct/storeInfo";
        }

        formDtlDTO.setCdGroupId1("INFO");
        formDtlDTO.setCdGroupId2("STORE00001");
        FormDtlDTO storeInfoFormDt = formDtlSvc.FormDtlView(formDtlDTO);

        formDtlDTO.setCdGroupId1("INFO");
        formDtlDTO.setCdGroupId2("STORECOMM");
        FormDtlDTO storeEtcFormDt = formDtlSvc.FormDtlView(formDtlDTO);

        if (storeInfoFormDt != null) {
            storeInfoFormDt.setDocContent(StringEscapeUtils.unescapeHtml(storeInfoFormDt.getDocContent()));
        }

        if (storeEtcFormDt != null) {
            storeEtcFormDt.setDocContent(StringEscapeUtils.unescapeHtml(storeEtcFormDt.getDocContent()));
        }

        String directUsimFaqCtg = Constants.DIRECT_USIM_FAQ_CTG_CD.get("emart24");

        model.addAttribute("storeInfoFormDt",storeInfoFormDt);
        model.addAttribute("storeEtcFormDt",storeEtcFormDt);
        model.addAttribute("directUsimFaqCtg",directUsimFaqCtg);
        model.addAttribute("orgnId", orgnId);

        List<BannerDto> topBannerList = NmcpServiceUtils.getBannerList("U101");
        List<BannerDto> midBannerList = NmcpServiceUtils.getBannerList("U102");

        model.addAttribute("topBannerList", topBannerList);
        model.addAttribute("midBannerList", midBannerList);



        return rtnPageNm;
    }

    /**
     * 설명 : 매장 찾기 팝업
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = {"/m/direct/searchStoreHtml.do", "/direct/searchStoreHtml.do"})
    public String searchStoreMapHtml(Model model, HttpServletResponse response, HttpServletRequest request){


        // 약관 동의체크
        String isCheckTerms = "false";

        String rtnPageNm = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/direct/html/searchStoreHtml";
        } else {
            rtnPageNm = "/portal/direct/html/searchStoreHtml";
        }

        model.addAttribute("isCheckTerms",isCheckTerms);

        return rtnPageNm;
    }

    /**
     * 설명 : 매장 찾기 팝업 > 구/군/면/리 조회 ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param storeDto
     * @param bind
     * @param pageInfoBean
     * @return
     */
    @RequestMapping(value = {"/m/direct/selectSubAddrAjax.do", "/direct/selectSubAddrAjax.do"})
    @ResponseBody
    public Map<String, Object> selectSubAddrAjax(@ModelAttribute StoreDto storeDto, BindingResult bind,
            @ModelAttribute  PageInfoBean pageInfoBean) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();


        List<StoreDto> resultList = storeSvc.selectSubAddr(storeDto);



        if(resultList.size() < 1) {
            rtnMap.put("RESULT_CODE", "0002");
        }else {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("resultList", resultList);
        }

        return rtnMap;
    }

    /**
     * 설명 : 매장 찾기 팝업 > 매장 조회 ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param storeDto
     * @param bind
     * @param pageInfoBean
     * @return
     */
    @RequestMapping(value = {"/m/direct/selectStoreListAjax.do", "/direct/selectStoreListAjax.do"})
    @ResponseBody
    public Map<String, Object> selectStoreListAjax(@ModelAttribute StoreDto storeDto, BindingResult bind,
            @ModelAttribute  PageInfoBean pageInfoBean) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        int total = storeSvc.countStoreList(storeDto);

        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();    //셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();  // Pagesize

        List<StoreDto> resultList = storeSvc.storeListSelect(storeDto, skipResult, maxResult);

        rtnMap.put("total", total);
        if(resultList.size() < 1) {
            rtnMap.put("RESULT_CODE", "0002");
        }else {
            int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + resultList.size();
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("resultList", resultList);
            rtnMap.put("listCount", listCount);
            rtnMap.put("endPage", pageInfoBean.getTotalPageCount());
        }

        return rtnMap;

    }

    /**
     * 설명 : 약관 동의 체크 ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = {"/m/direct/checkTerms.do", "/direct/checkTerms.do"})
    @ResponseBody
    public Map<String, Object> checkTerms(Model model, HttpServletResponse response, HttpServletRequest request) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 약관등록

        rtnMap.put("result", "0001"); // 성공

        return rtnMap;
    }

    /**
     * 설명 : 매장 찾기 팝업 > 상세보기(지도) 팝업
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = {"/m/direct/mapHtml.do", "/direct/mapHtml.do"})
    public String mapHtml(Model model, HttpServletResponse response, HttpServletRequest request){

        String rtnPageNm = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/direct/html/mapHtml";
        } else {
            rtnPageNm = "/portal/direct/html/mapHtml";
        }

        return rtnPageNm;
    }

    /**
     * 설명 : 매장 찾기 팝업 > 현재위치수집약관 조회 ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param formDtlDTO
     * @return
     */
    @RequestMapping(value = {"/m/direct/getFormDescAjax.do", "/direct/getFormDescAjax.do"})
    @ResponseBody
    public Map<String, Object> getFormDescAjax(FormDtlDTO formDtlDTO){
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        formDtlDTO.setCdGroupId1("TERMSNEA");
        formDtlDTO.setCdGroupId2("TERMSNEA01");
        FormDtlDTO locationTerm = formDtlSvc.FormDtlView(formDtlDTO);

        if (locationTerm != null) {
            locationTerm.setDocContent(StringEscapeUtils.unescapeHtml(locationTerm.getDocContent()));
            rtnMap.put("locationTerm", locationTerm.getDocContent());
        } else {
            rtnMap.put("locationTerm", "");
        }


        return rtnMap;
    }

    /**
     * 설명 : 오픈마켓 유심 메인
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param response
     * @param request
     * @param formDtlDTO
     * @return
     */
    @RequestMapping(value = {"/m/direct/openMarketInfo.do", "/direct/openMarketInfo.do"})
    public String openMarketInfo(Model model, HttpServletResponse response, HttpServletRequest request,FormDtlDTO formDtlDTO){
        String rtnPageNm = "";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnPageNm = "/mobile/direct/openMarketInfo";
        } else {
            rtnPageNm = "/portal/direct/openMarketInfo";
        }
        // 약관 정보 조회 - START
        formDtlDTO.setCdGroupId1("INFO");
        formDtlDTO.setCdGroupId2("STORE00001");
        FormDtlDTO storeInfoFormDt = formDtlSvc.FormDtlView(formDtlDTO);

        formDtlDTO.setCdGroupId1("INFO");
        formDtlDTO.setCdGroupId2("STORECOMM");
        FormDtlDTO storeEtcFormDt = formDtlSvc.FormDtlView(formDtlDTO);

        if (storeInfoFormDt != null) {
            storeInfoFormDt.setDocContent(StringEscapeUtils.unescapeHtml(storeInfoFormDt.getDocContent()));
        }

        if (storeEtcFormDt != null) {
            storeEtcFormDt.setDocContent(StringEscapeUtils.unescapeHtml(storeEtcFormDt.getDocContent()));
        }
        // 약관 정보 조회 - END

        String directUsimFaqCtg = Constants.DIRECT_USIM_FAQ_CTG_CD.get("emart24");
        model.addAttribute("storeInfoFormDt",storeInfoFormDt);
        model.addAttribute("storeEtcFormDt",storeEtcFormDt);
        model.addAttribute("directUsimFaqCtg",directUsimFaqCtg);

        List<BannerDto> topBannerList = NmcpServiceUtils.getBannerList("U001");
        List<BannerDto> midBannerList = NmcpServiceUtils.getBannerList("U002");

        model.addAttribute("topBannerList", topBannerList);
        model.addAttribute("midBannerList", midBannerList);
        return rtnPageNm;
    }

    /**
     * 설명 : emart24 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/direct/emart24.do", "/direct/emart24.do"})
    public String emart24Guide() {
        String returnUrl = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "redirect:/m/direct/storeInfo.do?orgnId=V000019218";
        } else {
            returnUrl = "redirect:/direct/storeInfo.do?orgnId=V000019218";
        }
        return returnUrl;
    }

    /**
     * 설명 : cu 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/direct/cu.do", "/direct/cu.do"})
    public String cuGuide() {
        String returnUrl = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "redirect:/m/direct/storeInfo.do?orgnId=V000019329";
        } else {
            returnUrl = "redirect:/direct/storeInfo.do?orgnId=V000019329";
        }
        return returnUrl;
    }

    /**
     * 설명 : cspace 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/direct/cspace.do", "/direct/cspace.do"})
    public String cspaceGuide() {
        String returnUrl = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "redirect:/m/direct/storeInfo.do?orgnId=NZA0000240";
        } else {
            returnUrl = "redirect:/direct/storeInfo.do?orgnId=NZA0000240";
        }
        return returnUrl;
    }

    /**
     * 설명 : ministop 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/direct/ministop.do", "/direct/ministop.do"})
    public String ministopGuide() {
        String returnUrl = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "redirect:/m/direct/storeInfo.do?orgnId=V000001444";
        } else {
            returnUrl = "redirect:/direct/storeInfo.do?orgnId=V000001444";
        }
        return returnUrl;
    }

    /**
     * 설명 : sevenElleven 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/direct/7-11.do", "/direct/7-11.do"})
    public String sevenEllevenGuide() {
        String returnUrl = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "redirect:/m/direct/storeInfo.do?orgnId=V000017488";
        } else {
            returnUrl = "redirect:/direct/storeInfo.do?orgnId=V000017488";
        }
        return returnUrl;
    }

    /**
     * 설명 : gs25 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/direct/gs25.do", "/direct/gs25.do"})
    public String gs25Guide() {
        String returnUrl = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "redirect:/m/direct/storeInfo.do?orgnId=V000014270";
        } else {
            returnUrl = "redirect:/direct/storeInfo.do?orgnId=V000014270";
        }
        return returnUrl;
    }

    /**
     * 설명 : storyway 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/direct/storyway.do", "/direct/storyway.do"})
    public String storywayGuide() {
        String returnUrl = "";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "redirect:/m/direct/storeInfo.do?orgnId=V000019173";
        } else {
            returnUrl = "redirect:/direct/storeInfo.do?orgnId=V000019173";
        }

        return returnUrl;
    }

}
