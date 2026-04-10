package com.ktis.msp.cmn.main.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.main.vo.CmnReportVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class MainController extends BaseController {

	@Autowired
	protected EgovPropertyService propertyService;
	
	@RequestMapping(value="/main", method={POST, GET})
	public String page(ModelMap model) {
		
		String ibsheetUrl = propertyService.getString("mvno.ibsheet.url");
		
		model.addAttribute("ibsheetUrl", ibsheetUrl);
		
		return "main";
	}
	
	@RequestMapping(value="/", method={POST, GET})
	public String pageRoot(ModelMap model) {
		
		String ibsheetUrl = propertyService.getString("mvno.ibsheet.url");
		
		model.addAttribute("ibsheetUrl", ibsheetUrl);
		
		return "main";
	}
	
	@RequestMapping(value="/cmn/report/report.do", method={POST, GET})
	public ModelAndView report( @ModelAttribute("CmnReportVO") CmnReportVO inVo, 
	            ModelMap model, 
	            HttpServletRequest pRequest, 
	            HttpServletResponse pResponse, 
	            @RequestParam Map<String, Object> pRequestParamMap
	           )
	{
		ModelAndView mv = new ModelAndView();
		// ubi report resource id 가져오기
		String resId = System.getProperty("server");
		logger.debug("res_id = " + resId);
        
        mv.addObject("res_id", resId);
		mv.addObject("jrf", inVo.getJrf());
		mv.addObject("arg", inVo.getArg());
		mv.addObject("w_gap", inVo.getWgap());
		mv.addObject("h_gap", inVo.getHgap());
		mv.setViewName("cmn/report/report");
		return mv; 
	}
	
}
