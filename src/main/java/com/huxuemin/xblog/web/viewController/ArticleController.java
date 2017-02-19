package com.huxuemin.xblog.web.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/articles")
public class ArticleController {
	
	@RequestMapping(value="/{articleId}")
	public String article(@PathVariable long articleId){
		return "articles";
	}
	
	@RequestMapping(value="/publish/")
	public String create(){
		return "PublishArticles";
	}
	
	@RequestMapping(value="/publish/{articleId}")
	public String edit(@PathVariable long articleId){
		return "PublishArticles";
	}
}
