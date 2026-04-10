package com.ktis.msp.rcp.rcpMgmt.controller;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.rcp.rcpMgmt.service.NicePinService;
import com.ktis.msp.rcp.rcpMgmt.vo.NiceLogVO;
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
public class NicePinController extends BaseController {

    @Autowired
    private NicePinService nicePinService;

    /**
     * 설명 : 고객 ci 조회 prx 연동
     *
     * @return
     * @Date : 2024.04.25
     */
    @RequestMapping(value = "/rcp/rcpMgmt/getNicePinCiAjax.do")
    public String getNicePinCi(HttpServletRequest request, HttpServletResponse response, ModelMap model, NiceLogVO niceLogVO) {

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

            Map<String, Object> connInfo = nicePinService.infNicePinCiCall(niceLogVO);

            logger.error((String) connInfo.get("connInfo"));

            if ("0000".equals(connInfo.get("returnCode"))) {
                resultMap.put("data", connInfo);
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
                resultMap.put("msg", "성공");
            } else {
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
                resultMap.put("msg", "본인확인 시도 중 오류가 발생했습니다.");
            }


        } catch (Exception e) {
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

}
