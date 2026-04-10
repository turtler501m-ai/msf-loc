package com.ktmmobile.mcp.common.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.AUTH_NUM_DIFFERENT_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_SESSION_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SMS_TIME_OUT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
//import com.ktmmobile.mcp.admin.controller.UsimManageController;
//import com.ktmmobile.mcp.admin.dto.NmcpAdminMemberBasDto;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.RoleMenuDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.terms.service.TermsSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;


@Controller
public class TermsController {

    private static Logger logger = LoggerFactory.getLogger(TermsController.class);
    @Autowired
    private TermsSvc termsSvc;

    
//  /*약관 상세 팝업 조회*/
  @RequestMapping(value = "/m/terms/TermsHtml.do")
  public String checkCertSmsAjax(HttpServletRequest request, @ModelAttribute("formDtlDTO") FormDtlDTO formDtlDTO, Model model )  {

      String rtnPageNm = "";
      
      if("Y".equals(NmcpServiceUtils.isMobile())){
			rtnPageNm = "/mobile/terms/termsView";
	  } else {
			rtnPageNm = "/terms/terms";
	  }
      FormDtlDTO resultDto = (FormDtlDTO)termsSvc.getTermsDtl(formDtlDTO);
      
      
      model.addAttribute("terms", resultDto);
      return rtnPageNm;
  }



}
