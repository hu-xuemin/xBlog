package com.huxuemin.xblog.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

	private List<AuthPolicy> authPolicys;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
//		System.out.println("AuthFilter.init(FilterConfig filterConfig)");
		authPolicys = new ArrayList<AuthPolicy>();
		authPolicys.add(new AuthPolicy("/xBlog/api/articles","post","put"));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
//		System.out.println("AuthFilter.doFilter(ServletRequest request, ServletResponse response, FilterChain chain)");
		if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)) {
			throw new ServletException("non-Http request or response");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		for(int i = 0;i < authPolicys.size();i++){
			if (authPolicys.get(i).isMatch(httpRequest)) {
				System.out.println("match");
			}
		}
		System.out.println(httpRequest.getRequestURI());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
//		System.out.println("AuthFilter.destroy()");
	}

}
