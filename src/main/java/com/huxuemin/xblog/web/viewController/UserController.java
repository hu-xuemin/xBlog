package com.huxuemin.xblog.web.viewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huxuemin.xblog.infrastructure.SessionConstant;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public String user(@PathVariable String userName) {
        return "userinfo";
    }

    @RequestMapping(value = "/{userName}/password", method = RequestMethod.GET)
    public String password(@PathVariable String userName, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute(SessionConstant.USERNAME);
        if (loginId != null && loginId.equals(userName)) {
            response.addHeader("Cache-Control", "no-store");
            return "password";
        }
        return "404";
    }
}
