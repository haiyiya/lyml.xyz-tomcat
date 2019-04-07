package com.lyml.demo1.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Role;
import com.lyml.demo1.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;

    @RequestMapping("")
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/role");
        return modelAndView;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        return renderSuccess(roleService.selectList(new EntityWrapper<Role>().orderBy("sort")));
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@ModelAttribute Role role) {
        if (roleService.selectCount(new EntityWrapper<Role>().eq("code", role.getName())) > 0) {
            return renderError("角色代码重复");
        }

        if (role.getId() == null) {
            role.setCreateTime(new Date());
            role.setState(1);
        }

        if (roleService.insertOrUpdate(role)) {
            return renderSuccess(role);
        } else {
            return renderError("保存失败");
        }
    }

    @RequestMapping("/edit{id}")
    @ResponseBody
    public Object save(@PathVariable Long id) {
        return renderSuccess(roleService.selectById(id));
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        if (roleService.deleteBatchIds(Arrays.asList(ids.split(";")))) {
            return renderSuccess("删除成功");
        } else {
            return renderError("删除失败");
        }
    }
}
