package com.catalyst.drive.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging exceptions in model classes
 * @author kpolen
 *
 */
@Component
@Aspect
public class DaoCatchAspect { 
	
	/**
	 * Pointcut that calls all methods in the daos
	 */
	@Pointcut("execution(* com.catalyst.drive.daos.*.*(..))")
	public void daoMethods(){}

	/**
	 * Around advice for dao methods to eliminate case by case try catches and logs
	 * @param pjp
	 * @return
	 */
	@Around("daoMethods()")
	public Object logDaoMethods(ProceedingJoinPoint pjp){
		Logger logger = Logger.getLogger(DaoCatchAspect.class);
		Object daoMethodObject = null;
		
		try{
			daoMethodObject = pjp.proceed(); 
		}
		catch(Throwable e)//NOSONAR Throwable is the only exception I can use without getting an error
		{
			logger.error("Exception caught at method: " + pjp.toShortString(), e);
		}
		return daoMethodObject;
	}
}


