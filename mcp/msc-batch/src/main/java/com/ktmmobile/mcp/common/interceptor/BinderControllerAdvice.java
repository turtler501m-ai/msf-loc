package com.ktmmobile.mcp.common.interceptor;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
@Order(10000)
public class BinderControllerAdvice {

	private static Logger logger = LoggerFactory.getLogger(BinderControllerAdvice.class);

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {

		String[] denylist = new String[]{"class.*", "Class.*","*.class.*","*.Class.*"};
		dataBinder.setDisallowedFields(denylist);
	}

}
