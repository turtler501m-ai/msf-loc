package com.ktis.mcpif.juso.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktis.mcpif.juso.service.JusoService;

@Controller
public class JusoController {
	final private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name = "jusoService")
	private JusoService svc;

	@RequestMapping("/getAreaCode.do")
	public @ResponseBody String getAreaCode( HttpServletRequest request, ModelMap model )  {
		String param = request.getQueryString();
		String result = svc.getAreaCode("getAreaCode.do", param);
		return result;
	}
	
	@RequestMapping("/link/search.do")
	public @ResponseBody String search( HttpServletRequest request, ModelMap model )  {
		String param = request.getQueryString();
		String result = svc.getAreaCode("link/search.do", param);
		return result;
	}

	@RequestMapping("/addrlink/addrLinkApi.do")
	public @ResponseBody String addrLinkApi( HttpServletRequest request, ModelMap model )  {
		String param = request.getQueryString();
		String result = svc.getAddrLink("addrlink/addrLinkApi.do", param);
		return result;
	}


}
