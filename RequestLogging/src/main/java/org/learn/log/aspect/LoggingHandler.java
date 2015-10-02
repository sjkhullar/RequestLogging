package org.learn.log.aspect;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingHandler {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controller() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
	}

	@Pointcut("execution(public * *(..))")
	protected void loggingPublicOperation() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void loggingAllOperation() {
	}

	@Pointcut("within(org.learn.log..*)")
	private void logAnyFunctionWithinResource() {
	}

	//before -> Any resource annotated with @Controller annotation 
	//and all method and function taking HttpServletRequest as first parameter
	@Before("controller() && allMethod() && args(..,request)")
	public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {

		log.debug("Entering in Method :  " + joinPoint.getSignature().getName());
		log.debug("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
		log.debug("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
		log.debug("Target class : " + joinPoint.getTarget().getClass().getName());

		if (null != request) {
			log.debug("Start Header Section of request ");
			log.debug("Method Type : " + request.getMethod());
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				log.debug("Header Name: " + headerName + " Header Value : " + headerValue);
			}
			log.debug("Request Path info :" + request.getServletPath());
			log.debug("End Header Section of request ");
		}
	}
	//After -> All method within resource annotated with @Controller annotation 
	// and return a  value
	@AfterReturning(pointcut = "controller() && allMethod()", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {
		String returnValue = this.getValue(result);
		log.debug("Method Return value : " + returnValue);
	}
	//After -> Any method within resource annotated with @Controller annotation 
	// throws an exception ...Log it
	@AfterThrowing(pointcut = "controller() && allMethod()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		log.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
		log.error("Cause : " + exception.getCause());
	}
	//Around -> Any method within resource annotated with @Controller annotation 
	@Around("controller() && allMethod()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		
		long start = System.currentTimeMillis();
		try {
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			Object result = joinPoint.proceed();
			long elapsedTime = System.currentTimeMillis() - start;
			log.debug("Method " + className + "." + methodName + " ()" + " execution time : "
					+ elapsedTime + " ms");
		
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
					+ joinPoint.getSignature().getName() + "()");
			throw e;
		}
	}
	/* Sample point cut for particular function
	//Around -> home function which is taking any kind/number of parameters...which is defined under HomeController
	@Around("execution(* org.learn.log.BookingController.home(..))")
	public void logParticularFunction(ProceedingJoinPoint joinPoint) throws Throwable {

		log.debug("Before Method : " + joinPoint.getSignature().getName());
		log.debug("Method arguments : " + Arrays.toString(joinPoint.getArgs()));
		joinPoint.proceed();
		log.debug("After Method : "+ joinPoint.getSignature().getName());
	}
	*/
	
	//Sample point cut
	//Before execution of any method within any resource annotated with @Controller annotation
	/*
	@Before("within(@org.springframework.stereotype.Controller *)")
	public void logPubicAPI(JoinPoint joinPoint) {
		log.info("**********************************");
		log.debug("Entering in Method : {} ", joinPoint.getSignature().getName());
		log.debug("Class Name : {}", joinPoint.getSignature().getDeclaringTypeName());
		log.debug("Arguments : {}", Arrays.toString(joinPoint.getArgs()));
		log.debug("Target class :{} ", joinPoint.getTarget().getClass().getName());
		log.info("**********************************");
	}
	
	//Sample point cut
	//Before execution of any method that is annotated with @RequestMapping annotation.
	//We can uses our custom annotation in same way....Like we have defined Loggable 
	//Annotation here
	@Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")  
    public void logBefore(JoinPoint joinPoint) {
        if (null == joinPoint) {
            return;
        }
        log.debug("Method Annotated with RequestMapping :"+joinPoint.getSignature().getName());
    }
	*/
	private String getValue(Object result) {
		String returnValue = null;
		if (null != result) {
			if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
				returnValue = ReflectionToStringBuilder.toString(result);
			} else {
				returnValue = result.toString();
			}
		}
		return returnValue;
	}
}
