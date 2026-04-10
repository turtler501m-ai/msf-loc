package com.ktmmobile.mcp.benefit.controller;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.benefit.dto.BenefitTabViewDto;
import com.ktmmobile.mcp.benefit.service.BenefitService;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MypageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;

@Controller
public class BenefitController {

    @Autowired
    BenefitService benefitService;

    @Autowired
    FormDtlSvc formDtlSvc;

    @Autowired
    private MypageService mypageService;

    @RequestMapping(value = {"/benefit/benefitMain.do", "/m/benefit/benefitMain.do"})
    public String benefitMain(@RequestParam(value = "tab", required = false) String tab,
                              @RequestParam(value = "seq", required = false) String seq,
                              Model model) {
        SessionUtils.saveNiceRes(null);

        String returnUrl;
        FormDtlDTO mcashContentDto;
        FormDtlDTO cardNoticeDto;

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null) {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            MyPageSearchDto searchVO = new MyPageSearchDto();
            boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);

            if(chk) {
                McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());
                    if(mcpUserCntrMngDto !=null && mcpUserCntrMngDto.getSoc() !=null) {
                        model.addAttribute("loginSoc", mcpUserCntrMngDto.getSoc());
                    }
               }
        }

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/benefit/benefitMain";
            mcashContentDto = formDtlSvc.FormDtlView(new FormDtlDTO("INFO", "BNFT0002"));
            cardNoticeDto = formDtlSvc.FormDtlView(new FormDtlDTO("INFO", "BNFT0004"));
        } else {
            returnUrl = "/portal/benefit/benefitMain";
            mcashContentDto = formDtlSvc.FormDtlView(new FormDtlDTO("INFO", "BNFT0001"));
            cardNoticeDto = formDtlSvc.FormDtlView(new FormDtlDTO("INFO", "BNFT0003"));
        }

        model.addAttribute("bannerList", NmcpServiceUtils.getBannerList("B001"));
        model.addAttribute("mcashContent", mcashContentDto.getUnescapedDocContent());
        model.addAttribute("cardNotice", cardNoticeDto.getUnescapedDocContent());

        List<BenefitTabViewDto> tabViewCountList = benefitService.getBenefitTabViewCountList();
        Map<String, String> viewCountMap = tabViewCountList.stream()
                        .collect(Collectors.toMap(
                                BenefitTabViewDto::getTabName,
                                tabView -> StringUtil.addComma(tabView.getViewCount())
                        ));
        model.addAttribute("viewCount", viewCountMap);
        model.addAttribute("tab", tab);
        model.addAttribute("seq", seq);

        return returnUrl;
    }

    @RequestMapping(value = "/benefit/getMonthlyPaymentAmountListAjax.do")
    @ResponseBody
    public List<NmcpCdDtlDto> getMonthlyPaymentAmountList() {
        return benefitService.getMonthlyPaymentAmountList();
    }

    @RequestMapping(value = "/benefit/increaseTabViewCountAjax.do")
    @ResponseBody
    public Map<String, String> increaseTabViewCount(BenefitTabViewDto benefitTabViewDto) {
        benefitService.increaseTabViewCount(benefitTabViewDto.getTabName());

        Map<String, String> map = new HashMap<>();
        map.put("RESULT_CODE", AJAX_SUCCESS);
        return map;
    }
}
