package com.huxuemin.xblog.domain.article;

public class ArticleNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446139906935067570L;
	
	public ArticleNotFoundException(){
	    super("Article not found!");
	}
	
}
