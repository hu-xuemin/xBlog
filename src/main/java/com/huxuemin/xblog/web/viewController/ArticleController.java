package com.huxuemin.xblog.web.viewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huxuemin.xblog.application.UserService;
import com.huxuemin.xblog.infrastructure.AuthException;
import com.huxuemin.xblog.infrastructure.SessionConstant;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{articleId}")
    public String article(@PathVariable long articleId) {
        return "article";
    }

    @RequestMapping(value = "/publish/")
    public String create(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute(SessionConstant.USERNAME);
        String password = (String) session.getAttribute(SessionConstant.PASSWORD);
        try {
            if (userService.manage(userName, password)) {
                response.addHeader("Cache-Control", "no-store");
                return "publish";
            }
        } catch (AuthException e) {
            // e.printStackTrace();
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/publish/{articleId}")
    public String edit(@PathVariable long articleId, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute(SessionConstant.USERNAME);
        String password = (String) session.getAttribute(SessionConstant.PASSWORD);
        try {
            if (userService.manage(userName, password)) {
                response.addHeader("Cache-Control", "no-store");
                return "publish";
            }
        } catch (AuthException e) {
            // e.printStackTrace();
        }
        return "redirect:/article/" + articleId;
    }
}
