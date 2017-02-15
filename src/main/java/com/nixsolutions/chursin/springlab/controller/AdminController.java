package com.nixsolutions.chursin.springlab.controller;

import com.google.code.kaptcha.Constants;
import com.nixsolutions.chursin.springlab.bean.SignUpUserBean;
import com.nixsolutions.chursin.springlab.bean.UserBean;
import com.nixsolutions.chursin.springlab.entity.User;
import com.nixsolutions.chursin.springlab.service.RoleService;
import com.nixsolutions.chursin.springlab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getUserList(ModelAndView model) {
        List<User> users = userService.findAll();
        model.addObject("users", users);
        model.setViewName("admin");
        return model;
    }

    @RequestMapping(value = "/edit_user", method = RequestMethod.GET)
    public String editUserForm(@RequestParam("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        model.put("roles", roleService.findAll());
        UserBean userBean = UserBean.userToBean(user);
        model.put("userBean", userBean);
        return "edituser";
    }

    @RequestMapping(value = "/edit_user", method = RequestMethod.POST)
    public String submitEditUser(@Valid @ModelAttribute("userBean") UserBean userBean
            , BindingResult result, ModelMap m) {
        checkEditEmail(userBean, result);
        checkPassConfirm(userBean, result);
        if (result.hasErrors()) {
            m.put("roles", roleService.findAll());
            return "edituser";
        }
        User user = UserBean.beanToUser(userBean);
        userService.update(user);
        return "redirect:admin";
    }

    @RequestMapping(value = "/delete_user", method = RequestMethod.GET)
    public String deleteUser(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        userService.remove(id);
        return "redirect:admin";
    }

    @RequestMapping(value = "/add_user", method = RequestMethod.GET)
    public String addUser(Model m) {
        m.addAttribute("userBean", new UserBean());
        m.addAttribute("roles", roleService.findAll());
        return "adduser";
    }

    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    public String submitAddUser(@Valid @ModelAttribute("userBean") UserBean userBean
            , BindingResult result, ModelMap m) {
        checkUniqueLogin(userBean, result);
        checkUniqueEmail(userBean, result);
        checkPassConfirm(userBean, result);
        if (result.hasErrors()) {
            m.addAttribute("roles", roleService.findAll());
            return "adduser";
        }
        User user = UserBean.beanToUser(userBean);
        userService.create(user);
        return "redirect:admin";
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    public String loadFormPage(Model m) {
        m.addAttribute("userBean", new SignUpUserBean());
        return "signup";
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public String submitForm(@Valid @ModelAttribute("userBean") SignUpUserBean userBean
            , BindingResult result, HttpServletRequest req) {
        checkUniqueLogin(userBean, result);
        checkUniqueEmail(userBean, result);
        checkPassConfirm(userBean, result);
        validateCaptcha(req, userBean, result);
        if (result.hasErrors()) {
            return "signup";
        }
        userBean.setRole(roleService.findById(2L));
        User user = UserBean.beanToUser(userBean);
        userService.create(user);
        return "redirect:index";
    }

    private void checkEditEmail(UserBean userBean, BindingResult result) {
        User user = userService.findByEmail(userBean.getEmail());
        if (user != null && !userBean.getLogin().equals(user.getLogin())) {
            result.rejectValue("email", "emailExists");
        }
    }

    private void checkUniqueLogin(UserBean userBean,
                                  BindingResult result) {
        User user = userService.findByLogin(userBean.getLogin());
        if (user != null) {
            result.rejectValue("login", "loginExists");
        }
    }

    private void checkUniqueEmail(UserBean userBean,
                                  BindingResult result) {
        User user = userService.findByEmail(userBean.getEmail());
        if (user != null) {
            result.rejectValue("email", "emailExists");
        }
    }

    private void checkPassConfirm(UserBean userBean,
                                  BindingResult result) {
        if (!userBean.getPassword().equals(userBean.getPasswordAgain())) {
            result.rejectValue("password", "passwordsNotEquals");
        }
    }

    private void validateCaptcha(HttpServletRequest request,
                                 SignUpUserBean userBean, BindingResult result) {
        System.out.println(request);
        String captchaId = (String) request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY);
        String response = userBean.getCaptcha();
        if (!response.equalsIgnoreCase(captchaId)) {
            result.rejectValue("captcha", "InvalidCaptcha", "Invalid Entry");
        }
    }
}
