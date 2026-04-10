package com.ktis.msp.base.exception;


public class MvnoErrorException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	

	public MvnoErrorException(Throwable exception)
	  {
		Throwable rootException = exception;
		while(rootException.getCause() != null){
			rootException = rootException.getCause();
		}
		
		return;
	  }	
	
//	public String getMessage(Throwable rootException) 
//	{
////		return String.format("MVNO Server - %d: %s", this.errCode, this.errMsg);
//		return String.format("%s", rootException.getCause());
//	}	
}
