package com.ktis.msp.batch.manager.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;
	
	private ApplicationContextProvider() {
		
	}

	public static ApplicationContext getApplicationContext() {
		return ApplicationContextProvider.applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		ApplicationContextProvider.applicationContext = applicationContext;
		
	}

}