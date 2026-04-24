package com.ktis.msp.base;

import org.springframework.context.MessageSource;

public class MessageBean {
	
	private static MessageSource messageSource;

	public static MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		MessageBean.messageSource = messageSource;
	}
}
