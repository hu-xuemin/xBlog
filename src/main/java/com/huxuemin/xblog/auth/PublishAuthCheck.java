package com.huxuemin.xblog.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.huxuemin.xblog.infrastructure.SessionConstant;

//@Aspect
public class PublishAuthCheck {

	// @Pointcut("execution(**
	// com.huxuemin.xblog.application.ArticleService.publish(..))")
	// public void publishAuth() {}

	// @Around("publishAuth()")
	@Around("execution(* com.huxuemin.xblog.application.ArticleService.publish(..))")
	public void publishAuthCheck(ProceedingJoinPoint jp) {
		// HttpSession session = request.getSession();
		// String loginid = (String) session.getAttribute("loginId");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		System.out.println("publishAuthCheck(),loginId:" + session.getAttribute(SessionConstant.USERNAME));
		try {
			jp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
