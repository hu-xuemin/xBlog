package com.huxuemin.xblog.domain.repository;

import com.huxuemin.xblog.domain.article.Article;

public class ArticleRepository extends IRepository<Article>{

	public ArticleRepository(){
		super(Article.class);
	}

	@Override
	protected boolean comparePrimaryKeyPolicy(Article domainObject, Object primaryKey) {
		// TODO Auto-generated method stub
		if(domainObject.getArticleId() == Long.valueOf(String.valueOf(primaryKey))){
			return true;
		}
		return false;
	}
	
}
