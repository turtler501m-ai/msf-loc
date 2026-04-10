package com.ktis.msp.base.exception;

import egovframework.rte.fdl.cmmn.exception.BaseException;

public class MvnoServiceException extends BaseException
{
  private static final long serialVersionUID = -2870585054772220895L;

  public MvnoServiceException(String messageKey, Throwable causeThrowable)
  {
    super(messageKey, causeThrowable);
  }

  public MvnoServiceException(String messageKey)
  {
    super(messageKey);
  }

  public MvnoServiceException(String messageKey, Object[] args, Throwable causeThrowable)
  {
    super(messageKey, args, causeThrowable);
  }

}