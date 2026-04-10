package com.ktmmobile.mcp.cmmn.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;

//@ControllerAdvice
@RestControllerAdvice
public class McpExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(McpExceptionHandler.class);
	
    /**
     * @Description : Mcp공통 예외 클래스 
     */
     @ExceptionHandler(McpCommonJsonException.class)
     @ResponseBody
     public Map<String, Object> handlerException(McpCommonJsonException e) {
         
    	    	 
         //HashMap<String, Object> rtnMap = new HashMap<String, Object>();
         HashMap<String, Object> rtnMap = null;
         //HttpStatus.BAD_REQUEST.value();
         //System.currentTimeMillis();
         
         //rtnMap.put("RESULT_CODE", e.getStatusCode());
         //rtnMap.put("RESULT_MESSAGE", e.getMessage());

         return rtnMap;
     }    
}
