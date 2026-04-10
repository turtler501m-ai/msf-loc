package com.ktmmobile.mcp.mstory.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.mstory.dto.MstoryDto;
import com.ktmmobile.mcp.mstory.service.MstorySvc;

@Controller
public class MstoryController {

    private static Logger logger = LoggerFactory.getLogger(MstoryController.class);

    @Autowired
    MstorySvc mstorySvc;

    @Value("${SERVER_NAME}")
    private String serverName;

    String yy;

    /**
     * 설명 : 월간M 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param mstoryDto
     * @param year
     * @return
     */
    @RequestMapping(value= {"/m/mstory/monthlyUsimList.do", "/mstory/monthlyUsimList.do"})
    public String monthlyUsimList(Model model, @ModelAttribute MstoryDto mstoryDto,
            @RequestParam(value="year", required = false)String year ) {

        String returnUrl = "/portal/mstory/monthlyUsimList";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mstory/monthlyUsimList";
        }
        model.addAttribute("mstoryDto", mstoryDto);
        if(Optional.ofNullable(year).isPresent()) {
            model.addAttribute("year", year);
        }

        List<String> yearList = mstorySvc.getMonthlyUsimYearList();
         if (!"DEV".equals(serverName) && !"LOCAL".equals(serverName)) {
            List<String> testList = new ArrayList<String>();
            yearList = testList;
         }
        if(Optional.ofNullable(yearList).isEmpty() || yearList.size() == 0) {
            returnUrl =  "redirect:/mstory/snsList.do?status=noData";
            if ("Y".equals(NmcpServiceUtils.isMobile())) {
                returnUrl = "redirect:/m/mstory/snsList.do?status=noData";
            }
        }

        return returnUrl;
    }

    /**
     * 설명 : 월간유심 목록 조회 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param mstoryDto
     * @param YY
     * @param request
     * @return
     */
    @RequestMapping(value= {"/m/mstory/monthlyUsimListAjax.do", "/mstory/monthlyUsimListAjax.do"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> monthlyUsimListAjax(@ModelAttribute MstoryDto mstoryDto,
            @RequestParam(value = "YY", required = false, defaultValue = "") String YY,
            HttpServletRequest request
            ){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        rtnMap.put("year", YY);
        rtnMap.put("monthlyUsimList", mstorySvc.getMonthlyUsimList(YY));
        rtnMap.put("yearList", mstorySvc.getMonthlyUsimYearList());

        return rtnMap;
    }

    /**
     * 설명 : 월간유심 상세보기 조회 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param mstoryDto
     * @param model
     * @param bind
     * @param key
     * @return
     */
    @RequestMapping(value = {"/m/mstory/monthlyUsimViewAjax.do", "/mstory/monthlyUsimViewAjax.do"})
    public String monthlyUsimDetail(@ModelAttribute("mstoryDto") MstoryDto mstoryDto
            , Model model , BindingResult bind ,@RequestParam(value = "key" , defaultValue="") String key) {

        String returnUrl = "/portal/mstory/monthlyUsimDetailPop";
        if(key.equals("old")) {
            returnUrl = "/portal/mstory/monthlyUsimDetailRefresh";
        }
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mstory/monthlyUsimDetailPop";
        }

        model.addAttribute("key", key);
        model.addAttribute("mstoryListDto", mstorySvc.getMonthlyUsimDetail(mstoryDto));
        model.addAttribute("monthlyUsimList", mstorySvc.getMonthlyUsimList(mstoryDto.getYy()));
        return returnUrl;
    }

    /**
     * 설명 : 고객소통 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param status
     * @return
     */
    @RequestMapping(value = {"/m/mstory/snsList.do", "/mstory/snsList.do"})
    public String snsList(Model model, @RequestParam(value="status", required=false) String status) {
        String returnUrl = "/portal/mstory/snsList";
        if(Optional.ofNullable(status).isEmpty()) {
            model.addAttribute("status", "T");
        }else {
            model.addAttribute("status", "F");
        }

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mstory/snsList";
        }
        MstoryDto mstoryDto = new MstoryDto();
        mstoryDto.setSnsCtgCd("SNS002");

        model.addAttribute("snsList", mstorySvc.getSnsList(mstoryDto));

        return returnUrl;
    }

    /**
     * 설명 : 고객센터 > 셀프서비스 > 서비스 퀵 가이드
     * @Author : papier
     * @Date 2023.09.19
     */
    @RequestMapping("/cs/serviceQuickGuide.do")
    public String serviceQuickGuide(Model model) {

        String rtnPageNm = "";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnPageNm = "/mobile/cs/guide/serviceQuickGuide";
        } else {
            rtnPageNm = "/portal/cs/guide/serviceQuickGuide";
        }

        MstoryDto mstoryDto = new MstoryDto();
        mstoryDto.setSnsCtgCd("SNS003");
        model.addAttribute("snsBoardList", mstorySvc.getSnsList(mstoryDto));

        return rtnPageNm;
    }



}
