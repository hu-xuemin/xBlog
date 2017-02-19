package com.huxuemin.xblog.infrastructure;

public class AuthException extends RuntimeException {
	
	private static final long serialVersionUID = 8121219522271194298L;
	private AuthConstant auth;
	
	public AuthException(AuthConstant ac){
		this.auth = ac;
	}
	
	public AuthConstant getAuthConstant(){
		return this.auth;
	}
	
	@Override
	public String toString(){
		return "non-Auth:" + auth.descOfAuth();
	}
}
