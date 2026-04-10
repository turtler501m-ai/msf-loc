package com.ktmmobile.mcp.direct.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

@Controller
public class DirectController {

	private static final Logger logger = LoggerFactory.getLogger(DirectController.class);
	
	@RequestMapping(value = {"/direct/directMallInfo.do","/m/direct/directMallInfo.do"})
    public String directMallInfo() {
		if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/direct/directMallInfo";
    	} else {
            return "/portal/direct/directMallInfo";
    	}
	}
    	
}
