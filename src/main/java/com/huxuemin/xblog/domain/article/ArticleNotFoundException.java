package com.huxuemin.xblog.domain.article;

public class ArticleNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446139906935067570L;
	
	@Override
	public String toString(){
		return "Article not found!";
	}
}
