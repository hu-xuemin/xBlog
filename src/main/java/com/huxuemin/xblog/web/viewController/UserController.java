package com.huxuemin.xblog.web.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@RequestMapping(value="/{userName}",method=RequestMethod.GET)
	public String user(@PathVariable String userName){
		return "userinfo";
	}
}
