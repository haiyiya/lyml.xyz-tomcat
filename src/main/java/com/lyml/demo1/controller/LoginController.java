package com.lyml.demo1.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Role;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = {"/login", ""})
public class LoginController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("")
    public ModelAndView login(Authentication authentication,
                              User user,
                              @RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView();

        //密码错误
        if (error != null) {
            modelAndView.addObject("msg", "用户名或密码不正确");
            modelAndView.addObject("user", user);//将用户输入的账号密码传到前台
            modelAndView.setViewName("login");
            return modelAndView;
        }

        //注销
        if (logout != null) {
            modelAndView.addObject("msg", "你已经成功退出");
            modelAndView.setViewName("login");
            return modelAndView;
        }

        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            //登录成功
            modelAndView.addObject("user", getUser());

            //浏览器UA判断
            String userAgent = request.getHeader("User-Agent");
            if (userAgent != null && Pattern.matches(".*(iPhone|iPod|Android|ios|iPad).*", userAgent)) {
                modelAndView.setViewName("mobile/user");
            } else {
                modelAndView.setViewName("user");
            }
        } else {
            //未携带任何参数，且未登录，跳转到登录页
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
}
