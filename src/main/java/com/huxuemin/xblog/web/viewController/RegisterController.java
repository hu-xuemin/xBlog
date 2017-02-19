package com.huxuemin.xblog.web.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/register")
public class RegisterController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String register(){
		return "register";
	}
}
