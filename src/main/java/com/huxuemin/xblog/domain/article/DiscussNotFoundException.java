package com.huxuemin.xblog.domain.article;

public class DiscussNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1488000519586378237L;

	@Override
	public String toString(){
		return "Discuss not Found!";
	}
}
