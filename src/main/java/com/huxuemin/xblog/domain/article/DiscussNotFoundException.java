package com.huxuemin.xblog.domain.article;

public class DiscussNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1488000519586378237L;

	public DiscussNotFoundException(){
	    super("Discuss not Found!");
	}
}
