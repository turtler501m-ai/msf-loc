package com.ktis.msp.base.exception;

import com.ktis.msp.base.MessageBean;

import egovframework.rte.fdl.cmmn.exception.BaseException;

public class MvnoServiceException extends BaseException
{
	private static final long serialVersionUID = -2870585054772220895L;
	
	public MvnoServiceException(String messageKey) {
		super(MessageBean.getMessageSource(), messageKey);
	}
	
	public MvnoServiceException(String messageKey, Throwable causeThrowable) {
		super(MessageBean.getMessageSource(), messageKey, causeThrowable);
		this.setWrappedException((Exception) causeThrowable);
	}
	
	public MvnoServiceException(String messageKey, Object[] args) {
		super(MessageBean.getMessageSource(), messageKey, args, new Throwable());
	}
	
	public MvnoServiceException(String messageKey, Object[] args, Throwable causeThrowable) {
		super(MessageBean.getMessageSource(), messageKey, args, causeThrowable);
		this.setWrappedException((Exception) causeThrowable);
	}
	
	public MvnoServiceException(String messageKey, String message) {
		super(message);
		this.setMessageKey(messageKey);
	}
	
	public MvnoServiceException(String messageKey, String message, Throwable causeThrowable) {
		super(message, causeThrowable);
		this.setMessageKey(messageKey);
	}
	
	public MvnoServiceException(String messageKey, String message, Object[] args, Throwable causeThrowable) {
		super(message, args, causeThrowable);
		this.setMessageKey(messageKey);
	}
	
}