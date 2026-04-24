package com.ktis.msp.base.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AspectSupport {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Pointcut("execution(public * com.ktis.msp.batch.job..*.*Job(..))")
	public void targetMethod() {} // PointCut target 설정. NOPMD by TREXSHIN on 16. 6. 27 오후 8:16
	
	@Before("targetMethod()")
	public void beforeTargetMethod(JoinPoint joinPoint) {
		LOGGER.info("[{}] START!!!!!!!!", joinPoint.getTarget().getClass().getSimpleName());
	}
	
	@After("targetMethod()")
	public void afterTargetMethod(JoinPoint joinPoint) {
		LOGGER.info("[{}] END!!!!!!!!", joinPoint.getTarget().getClass().getSimpleName());
	}
	
}
