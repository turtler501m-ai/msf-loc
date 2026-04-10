package com.ktmmobile.mcp.usim.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.USIM_REGISTRATION_EXCEPTION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.captcha.Captcha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.service.SmsSvcImpl;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.usim.dto.SellUsimDto;

@Controller
public class SellUsimController {

    private static Logger logger = LoggerFactory.getLogger(SellUsimController.class);
    
    @Autowired
    IpStatisticService ipStatisticService;
    
    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**
     * <pre>
     * 설명     : 개통유심_판매 가능 여부 확인
     * @param sellUsimDto
     * @return
     * @return: Map<String,Object>
     * </pre>
     */
    @RequestMapping(value ="/usim/checkUsimRegCntAjax.do")
    @ResponseBody
    public int checkUsimRegCnt(SellUsimDto sellUsimDto){
        //---- API 호출 S ----//
		RestTemplate restTemplate = new RestTemplate();
		int cnt = restTemplate.postForObject(apiInterfaceServer + "/api/sellUsim/checkUsimRegCount", sellUsimDto, int.class); // sellUsimService.checkUsimRegCnt
		//---- API 호출 E ----//

        return cnt;
    }


}
