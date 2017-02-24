package com.huxuemin.xblog.web.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/article")
public class ArticleController {
	
	@RequestMapping(value="/{articleId}")
	public String article(@PathVariable long articleId){
		return "article";
	}
	
	@RequestMapping(value="/publish/")
	public String create(){
		return "publish";
	}
	
	@RequestMapping(value="/publish/{articleId}")
	public String edit(@PathVariable long articleId){
		return "publish";
	}
}
