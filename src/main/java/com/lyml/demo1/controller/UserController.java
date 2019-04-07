package com.lyml.demo1.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Role;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IRoleService;
import com.lyml.demo1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Value("${path.upload}")
    private String path;

    @RequestMapping("")
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", getUser());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        Page<User> page = new Page<>(getIntPar("page"), getIntPar("limit"));
        page = userService.selectPage(page, new EntityWrapper<User>().orderBy("id", false));
        return renderSuccess(page);
    }

    @PreAuthorize("hasRole('haiyiya')")
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@ModelAttribute User user) {
        if (userService.selectCount(new EntityWrapper<User>().eq("username", user.getUsername())) > 0) {
            return renderError("该用户已存在");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        if (userService.insertOrUpdate(user)) {
            return renderSuccess(user);
        } else {
            return renderError("保存失败");
        }
    }

    @PreAuthorize("hasRole('haiyiya')")
    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        if (userService.deleteBatchIds(Arrays.asList(ids.split(";")))) {
            return renderSuccess("删除成功");
        } else {
            return renderError("删除失败");
        }
    }

    @RequestMapping("/test1")
    @ResponseBody
    public Object test1() {
        return "haiyiya";
    }
}
