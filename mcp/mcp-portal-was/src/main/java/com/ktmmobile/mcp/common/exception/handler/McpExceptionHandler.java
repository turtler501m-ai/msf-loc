package com.ktmmobile.mcp.common.exception.handler;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.DB_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.DUPLICATE_SQL_EXCEPTION;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.McpErropPageException;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;


/**
 * @Class Name : McpExceptionHandler
 * @Description : annotation 방식을 이용한 exception 핸들러
 *
 * @author : ant
 * @Create Date : 2015. 12. 30.
 */
@ControllerAdvice
public class McpExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(McpExceptionHandler.class);


    /**
    * @Description :
    *  Mcp공통 예외 클래스
    * @param e
    * @return
    * @Author : ant
    * @Create Date : 2015. 12. 30.
    */
    @ExceptionHandler({McpCommonException.class,McpMplatFormException.class})
    public ModelAndView handlerException(McpCommonException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("errMsg", e.getErrorMsg());
        mv.addObject("redirectUrl", e.getRedirectUrl());
        mv.setViewName("/errmsg/redirect");
        return mv;
    }


    @ExceptionHandler({McpErropPageException.class})
    public ModelAndView handlerException(McpErropPageException e) {

        String returnUrl = "/portal/errmsg/errorPage";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "/mobile/errmsg/errorPage";
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("errMsg", e.getErrorMsg());
        mv.addObject("redirectUrl", e.getRedirectUrl());
        mv.setViewName(returnUrl);
        return mv;
    }





    /**
     * @Description :
     *  Mcp공통 예외 클래스
     * @param e
     * @return
     * @Author : ant
     * @Create Date : 2015. 12. 30.
     */
     @ExceptionHandler(McpCommonJsonException.class)
     @ResponseBody
     public Map<String, Object> handlerException(McpCommonJsonException e) {
         HashMap<String, Object> rtnMap = new HashMap<String, Object>();

         rtnMap.put("RESULT_CODE", e.getRtnCode());
         rtnMap.put("RESULT_MSG", e.getErrorMsg());

         return rtnMap;
     }


    /**
    * @Description :
    *  DB 처리중 예외가 발생할경우 핸들링 한다.
    * @param e
    * @return
    * @Author : ant
    * @Create Date : 2015. 12. 30.
    */
    @ExceptionHandler(DataAccessException.class)
    public ModelAndView handlerException(DataAccessException e) {
        ModelAndView mv = new ModelAndView();
        if (e instanceof DuplicateKeyException) {
            mv.addObject("errMsg", DUPLICATE_SQL_EXCEPTION);
        } else {
            mv.addObject("errMsg", DB_EXCEPTION);
        }
        mv.setViewName("errmsg/redirect");
        return mv;
    }
}
