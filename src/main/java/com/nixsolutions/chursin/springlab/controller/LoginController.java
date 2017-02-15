package com.nixsolutions.chursin.springlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    @RequestMapping(value = "/dispatch", method = RequestMethod.GET)
    public String dispatch(HttpServletRequest request) {
        if (request.isUserInRole(ROLE_ADMIN)) {
            return "redirect:admin";
        } else if (request.isUserInRole(ROLE_USER)) {
            return "redirect:user";
        }
        return "redirect:index";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String login() {
        return "index";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPage() {
        return "user";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:index";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied(Principal user) {
        ModelAndView model = new ModelAndView();
        model.addObject("msg", "Hi " + user.getName()
                + ", you do not have permission to access this page!");
        model.setViewName("403");
        return model;
    }

}
