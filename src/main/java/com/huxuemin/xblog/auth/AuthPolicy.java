package com.huxuemin.xblog.auth;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class AuthPolicy {
	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_HEAD = "HEAD";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_OPTIONS = "OPTIONS";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_TRACE = "TRACE";

	private static final String[] METHOD_CONSTANTS = { METHOD_DELETE, METHOD_HEAD, METHOD_GET, METHOD_OPTIONS,
			METHOD_POST, METHOD_PUT, METHOD_TRACE };
	private static final int METHOD_NUM = METHOD_CONSTANTS.length;

	private List<String> methodsPolicy = new ArrayList<String>();
	private String url;

	public AuthPolicy(String url, String... methods) {
		this.url = url;
		for (int i = 0; i < methods.length; i++) {
			addMethod(methods[i]);
		}
	}

	public void addMethod(String method) {
		String upMethod = method.toUpperCase();
		if (isValid(upMethod)) {
			if (!this.methodsPolicy.contains(upMethod)) {
				this.methodsPolicy.add(upMethod);
			}
		}
	}

	public void removeMethod(String method) {
		String upMethod = method.toUpperCase();
		if (isValid(upMethod)) {
			if (this.methodsPolicy.contains(upMethod)) {
				this.methodsPolicy.remove(upMethod);
			}
		}
	}

	private boolean isValid(String method) {
		for (int i = 0; i < METHOD_NUM; i++) {
			if (method.equals(METHOD_CONSTANTS[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean isMatch(HttpServletRequest request) {
		if (request.getRequestURI().startsWith(url)) {
			for (int i = 0; i < methodsPolicy.size(); i++) {
				if (request.getMethod().equals(methodsPolicy.get(i))) {
					return true;
				}
			}
		}
		return false;
	}
}
