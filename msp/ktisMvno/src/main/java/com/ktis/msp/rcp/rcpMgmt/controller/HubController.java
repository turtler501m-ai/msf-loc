package com.ktis.msp.rcp.rcpMgmt.controller;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.rcp.rcpMgmt.service.HubService;
import com.ktis.msp.rcp.rcpMgmt.vo.HubOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class HubController extends BaseController {

    @Autowired
    private HubService hubService;

    /**
     * 설명 : 알뜰폰허브 고객주문정보 조회 prx 연동
     *
     * @return
     * @Date : 2024.05.27
     */
    @RequestMapping(value = "/rcp/rcpMgmt/getHubOrder.do")
    public String getHubOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model, HubOrderVO vo) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);

            // 본사 권한 확인
            if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            vo.setSessionUserId(loginInfo.getUserId());

            Map<String, Object> hubOrder = hubService.getHubOrder(vo);

            if ("200".equals(hubOrder.get("returnCode"))) {
                resultMap.put("data", hubOrder.get("data"));
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
                resultMap.put("msg", hubOrder.get("returnMsg"));
            } else {
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
                resultMap.put("msg", hubOrder.get("returnMsg"));
            }

        } catch (Exception e) {
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

}
