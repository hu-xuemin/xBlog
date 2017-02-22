package com.huxuemin.xblog.domain.user;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7671096847221755853L;
	
	public UserNotFoundException(){
	    super("User not found!");
	}
	
}
