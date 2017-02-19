package com.huxuemin.xblog.web.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/","/home","/homepage","/index"})
public class HomeController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(){
		return "index";
	}
}
