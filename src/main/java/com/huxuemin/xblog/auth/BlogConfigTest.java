package com.huxuemin.xblog.auth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.huxuemin.xblog.application.ArticleService;
import com.huxuemin.xblog.config.RootConfig;

public class BlogConfigTest {
	
	public static void main(String[] args){
		AnnotationConfigApplicationContext context =
	            new AnnotationConfigApplicationContext(RootConfig.class);
		ArticleService articleS = context.getBean(ArticleService.class);
		articleS.publish("afsadf", "asfsdf", "admin", "admin1234");
		context.close();
	}
	
}
