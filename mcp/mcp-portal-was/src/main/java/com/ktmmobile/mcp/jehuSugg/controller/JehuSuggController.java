package com.ktmmobile.mcp.jehuSugg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.jehuSugg.dto.JehuSuggDto;
import com.ktmmobile.mcp.jehuSugg.service.JehuSuggService;

@Controller
public class JehuSuggController {

    private static Logger logger = LoggerFactory.getLogger(JehuSuggController.class);

    @Autowired
    private JehuSuggService jehuSuggService;

    @Autowired
    private FCommonSvc fCommonSvc;


    /**
    * @Description : 제휴제안 입력폼
    * @return
    */
    @RequestMapping(value = {"/m/jehuSugg/jehuSuggFormView.do","/jehuSugg/jehuSuggForm.do"})
    public String jehuSuggFormView(Model model) {

        String rtnPageNm = "/portal/jehuSugg/jehuSuggFormView";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/jehuSugg/jehuSuggFormView";
        }

        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.JEHU_SUGGESTION_CATEGORY);

        List<NmcpCdDtlDto> jehuSuggestionCategory = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        model.addAttribute("jehuSuggestionCategory", jehuSuggestionCategory);

        return rtnPageNm;
    }

    /**
     * 일시정지가능여부 조회 ajax
     */
    @RequestMapping("/jehuSugg/insertJehuSuggAjax.do")
    @ResponseBody
    public Map<String, Object> insertJehuSuggAjax(HttpServletRequest request, @ModelAttribute JehuSuggDto jehuSuggDto)  {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String returnCode = "";
        try {
            if(jehuSuggService.insertJehuSuggAjax(jehuSuggDto) > 0){
                returnCode = "00";
            }

        } catch (Exception e) {
            returnCode = "99";
        }
        rtnMap.put("returnCode",returnCode);
        return rtnMap;
    }









}
