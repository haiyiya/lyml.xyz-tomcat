package com.lyml.demo1.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Role;
import com.lyml.demo1.model.RoleUser;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IRoleService;
import com.lyml.demo1.service.IRoleUserService;
import com.lyml.demo1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/roleUser")
public class RoleUserController extends BaseController {
    @Autowired
    private IRoleUserService roleUserService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        return renderSuccess(roleUserService.selectList(new EntityWrapper<RoleUser>().eq("roleId",getLongPar("roleId"))));
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@ModelAttribute RoleUser roleUser) {
        if (roleUserService.selectCount(new EntityWrapper<RoleUser>().eq("roleId", roleUser.getRoleId()).eq("userId",roleUser.getUserId())) > 0) {
            renderError("该用户已添加");
        }

        if (roleUser.getId() == null) {
            Role role = roleService.selectById(roleUser.getRoleId());
            User user = userService.selectById(roleUser.getUserId());
            roleUser.setRoleName(role.getName());
            roleUser.setUserName(user.getUsername());
        }

        if (roleUserService.insertOrUpdate(roleUser)) {
            return renderSuccess(roleUser);
        } else {
            return renderError("保存失败");
        }
    }

    @RequestMapping("/edit{id}")
    @ResponseBody
    public Object save(@PathVariable Long id) {
        return renderSuccess(roleUserService.selectById(id));
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        if (roleUserService.deleteBatchIds(Arrays.asList(ids.split(";")))) {
            return renderSuccess("删除成功");
        } else {
            return renderError("删除失败");
        }
    }
}
