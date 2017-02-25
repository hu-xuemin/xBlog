package com.huxuemin.xblog.web.viewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huxuemin.xblog.application.UserService;
import com.huxuemin.xblog.infrastructure.AuthException;
import com.huxuemin.xblog.infrastructure.SessionConstant;

@Controller
@RequestMapping(value = "/manage")
public class ManagePageController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String register(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute(SessionConstant.USERNAME);
        String password = (String) session.getAttribute(SessionConstant.PASSWORD);
        try {
            if (userService.manage(userName, password)) {
                response.addHeader("Cache-Control", "no-store");
                return "manage";
            }
        } catch (AuthException e) {
            //e.printStackTrace();
        }
        return "redirect:/index";
    }
}
