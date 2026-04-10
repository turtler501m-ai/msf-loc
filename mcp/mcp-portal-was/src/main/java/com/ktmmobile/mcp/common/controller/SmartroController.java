package com.ktmmobile.mcp.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.service.SmartroService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

/**
 * 스마트로 결제 Controller
 * @author jsh
 * @Date : 2021.12.30
 */
@Controller
public class SmartroController {

    @Autowired
    private SmartroService smartroService;

    /**
     * 설명 : 스마트로 결제 초기 정보 셋팅
     * @Author : jsh
     * @Date : 2021.12.30
     * @param req
     * @return
     */ 
    @RequestMapping(value = {"/smartro/payInit.do", "/m/smartro/payInit.do"}, method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> payInit(HttpServletRequest req){
        return smartroService.smartroPayInit(req);
    }

    /**
     * 설명 : 스마트로 결제 이후 리턴 페이지
     * @Author : jsh
     * @Date : 2021.12.30
     * @param req
     * @param model
     * @return
     */ 
    @RequestMapping(value = {"/smartro/returnPay.do", "/m/smartro/returnPay.do"})
    public String returnPay(HttpServletRequest req, Model model) {

        Map<String, Object> result = smartroService.callApi(req);

        model.addAttribute("result", result);

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/smartro/returnPay";
        }else {
            return "/portal/smartro/returnPay";
        }
    }
    
    /**
     * 설명 :스마트로 결제 이후 리턴 페이지(참고용) 
     * @Author : jsh
     * @Date : 2021.12.30
     * @param req
     * @param model
     * @return
     */ 
    @RequestMapping(value = {"/m/smartro/returnPayTest.do"})
    public String returnPayTest(HttpServletRequest req, Model model) {

        Map<String, Object> result = smartroService.callApi(req);
        model.addAttribute("result", result);
        return "/mobile/smartro/returnPayTest";
    }
}
