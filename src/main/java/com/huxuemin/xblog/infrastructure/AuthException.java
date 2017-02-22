package com.huxuemin.xblog.infrastructure;

public class AuthException extends RuntimeException {
	
	private static final long serialVersionUID = 8121219522271194298L;
	private AuthConstant auth;
	
	public AuthException(AuthConstant ac){
	    super("non-Auth:" + ac.descOfAuth());
		this.auth = ac;
	}
	
	public AuthConstant getAuthConstant(){
		return this.auth;
	}
}
